package com.immortal.internship.service;

import com.immortal.internship.model.SkillScore;
import com.immortal.internship.payload.response.SearchCVResponse;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class ElasticSearchServiceTest {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void SHOULD_RESPONSE_LIST_SKILLS(){
        Map<String,Double> result = elasticSearchService.recommendations("java");
        result.forEach((skillName, score) -> System.out.println(skillName +" - "+score));
        Assert.notEmpty(result);
    }

    @Test
    public void SHOULD_RESPONSE_LIST_CV_STUDENT(){
//        List<SearchCVResponse> cvSuggests = elasticSearchService.getAllCVBySkills(elasticSearchService.recommendations("java"));
//        cvSuggests.forEach(System.out::println);
//        Assert.notEmpty(cvSuggests);
    }
}
