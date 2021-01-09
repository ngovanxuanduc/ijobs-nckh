package com.immortal.internship.service;

import com.immortal.internship.constant.ElasticSearchConstants;
import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.constant.RecruitmentConstant;
import com.immortal.internship.entity.RecruitmentEntity;
import com.immortal.internship.entity.SkillsEntity;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.model.SkillScore;
import com.immortal.internship.payload.response.SearchCVResponse;
import com.immortal.internship.repository.DBContext;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.SignificantTerms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElasticSearchService {
    private Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);
    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private DBContext dbContext;

    @Value("${elasticsearch.index}")
    private String elasticSearchIndex;

    public Map<String,Double> recommendations(String ...skill){
        SearchRequest searchRequest = new SearchRequest(elasticSearchIndex);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        for (int i = 0; i < skill.length; i++) {
            skill[i]=skill[i].toLowerCase();
        }

        QueryBuilder query = QueryBuilders
                .boolQuery()
                .should(QueryBuilders.termsQuery(
                        ElasticSearchConstants.SKILLS,skill))
                .minimumShouldMatch(ElasticSearchConstants.MINIMUM_SHOULD_MATCH);

        AggregationBuilder aggs = AggregationBuilders
                .significantTerms(ElasticSearchConstants.RECOMMENDATIONS)
                .field(ElasticSearchConstants.SKILLS)
                .minDocCount(ElasticSearchConstants.MIN_DOC_COUNT);

        sourceBuilder.query(query);
        sourceBuilder.aggregation(aggs);

        sourceBuilder.fetchSource(false);
        String[] includeFields = new String[] {"title","skills"};
        String[] excludeFields = new String[] {"skills"};
        sourceBuilder.fetchSource(includeFields,excludeFields);

        //Set Source Builder for Search Request
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SignificantTerms agg = searchResponse.getAggregations().get(ElasticSearchConstants.RECOMMENDATIONS);
            Map<String, Double> mapSkillScore = new HashMap<>();
            for (SignificantTerms.Bucket entry : agg.getBuckets()) {
                mapSkillScore.put(entry.getKeyAsString().toUpperCase(), entry.getSignificanceScore());
            }
            return mapSkillScore;
        } catch (IOException e) {
            logger.error("------------------ recommendations ---------------------------");
            e.printStackTrace();
        }
        return null;
    }

    public List<SearchCVResponse> getAllCVBySkills(Map<String, Double> mapSkillScore){
        String[] skills = mapSkillScore.keySet().stream().toArray(String[]::new);

        Map<SearchCVResponse, Double> cvSuggest = new HashMap<SearchCVResponse, Double>();

        dbContext.customRepository.getAllCVBySkills(getListSkillId(skills))
                .forEach(s ->{
                    cvSuggest.put(s,dbContext.cvRepository.findById(s.getCvId())
                            .map(cv -> calculate(mapSkillScore,cv.getSkillsCV()))
                            .orElse(0d));
                });

        Map<SearchCVResponse, Double> result = sortByValue(cvSuggest);
        System.out.println(result);
        return new ArrayList<>(result.keySet());

    }

    public Map<SearchCVResponse, Double> getAllCVBySkillss(Map<String, Double> mapSkillScore){
        String[] skills = mapSkillScore.keySet().stream().toArray(String[]::new);

        Map<SearchCVResponse, Double> cvSuggest = new HashMap<SearchCVResponse, Double>();

        dbContext.customRepository.getAllCVBySkills(getListSkillId(skills))
                .forEach(s ->{
                    cvSuggest.put(s,dbContext.cvRepository.findById(s.getCvId())
                            .map(cv -> calculate(mapSkillScore,cv.getSkillsCV()))
                            .orElse(0d));
                });
        return sortByValue(cvSuggest);

    }

    private Map<SearchCVResponse, Double> sortByValue(Map<SearchCVResponse, Double> unSortMap){
        List<Map.Entry<SearchCVResponse,Double>> list = new LinkedList<Map.Entry<SearchCVResponse, Double>>(unSortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<SearchCVResponse, Double>>() {
            @Override
            public int compare(Map.Entry<SearchCVResponse, Double> t1, Map.Entry<SearchCVResponse, Double> t2) {
                return t2.getValue().compareTo(t1.getValue());
            }
        });
        Map<SearchCVResponse, Double> sortedMap = new LinkedHashMap<SearchCVResponse, Double>();
        for (Map.Entry<SearchCVResponse, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private Double calculate(Map<String, Double> mapSkillScore, Collection<SkillsEntity> cvSkills){
        return cvSkills.stream().mapToDouble(skill ->  mapSkillScore.getOrDefault(skill.getSkillName().toUpperCase(),0d)).sum();
    }

    private List<Integer> getListSkillId(String ...skills){
        List<Integer> skillsId = new ArrayList<>();
        List<SkillsEntity> skillsEntities = Arrays.stream(skills).map(
                name->dbContext.skillsRepository.findBySkillName(name)
                        .orElseThrow(()->new InvalidParamException(
                                MessageConstants.ForSystem.ENTITY_NOT_FOUND
                                , MessageConstants.ForUser.SKILL_NOT_EXIST
                                , MessageConstants.ResultCode.ENTITY_NOT_FOUND))
        ).collect(Collectors.toList());
        skillsEntities.forEach(x->skillsId.add(x.getId()));
        return skillsId;
    }


    public List<SearchCVResponse> getCVSuggestByRecruitment(String recrID){
        Optional.ofNullable(dbContext.recruitmentRepository.findByIdAndStatusEquals(recrID, RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED))
                .orElseThrow(()->new InvalidParamException(
                        MessageConstants.ForSystem.INVALID_PARAM,
                        MessageConstants.ForUser.RECRUITMENT_NOT_EXIST,
                        MessageConstants.ResultCode.ENTITY_NOT_FOUND
                ));
        RecruitmentEntity recruitmentEntity = dbContext.recruitmentRepository
                .findByIdAndStatusEquals(recrID, RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED);
        String[] skills = recruitmentEntity.getSkills().stream()
                .map(SkillsEntity::getSkillName)
                .toArray(String[]::new);
        System.out.println("skill suggest");
        System.out.println(Arrays.toString(skills));
        return getAllCVBySkills(recommendations(skills));

    }

}
