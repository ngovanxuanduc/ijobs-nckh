package com.immortal.internship.repository;

import com.immortal.internship.entity.AccountEntity;
import com.immortal.internship.entity.DiscussContentEntity;
import com.immortal.internship.entity.ImageEntity;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.model.BaseStatistical;
import com.immortal.internship.model.DiscussHeader;
import com.immortal.internship.model.InternDetail;
import com.immortal.internship.payload.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DBContext dbContext;

    @Autowired
    @Qualifier("ExceptionDiscussNotFound")
    private InvalidParamException exceptionDiscussNotFound;

    public List<AccountEntity> getAllAccount() {
        // Khai Bao
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<AccountEntity> query = builder.createQuery(AccountEntity.class);
        Root<AccountEntity> root = query.from(AccountEntity.class);
        query.select(root);
        // Thuc thi
        TypedQuery<AccountEntity> query1 = em.createQuery(query);
        return query1.getResultList();
    }

    public List<StudentCVResponse> getStudents() {
        Query query = em.createNativeQuery("select bin_to_uuid(s.account_id) as account_id, student_code, school_year, first_name, last_name, cv.active as active\n" +
                "from student s inner join curriculum_vitae cv on s.account_id = cv.student_id\n" +
                "order by cv.active desc");
        List<Object[]> rows = query.getResultList();
        List<StudentCVResponse> list = new ArrayList<>();
        for (Object[] row : rows) {
            StudentCVResponse studentCVResponse = StudentCVResponse.builder()
                    .accountId(UUID.fromString((String) row[0]))
                    .studentCode((String) row[1])
                    .schoolYear((String) row[2])
                    .firstName((String) row[3])
                    .lastName((String) row[4])
                    .active((Boolean) row[5])
                    .build();
            list.add(studentCVResponse);
        }
        return list;

    }



    public List<AccountEntity> getAllAccountInGroup(int groupId) {
        Query query = em.createNativeQuery("select distinct a.user_name, a.active,a.create_by,a.id,a.password" +
                ",a.updated_by from groupz g \n" +
                "inner join group_description gd \n" +
                "on g.id = gd.group_id\n" +
                "inner join role r \n" +
                "on gd.role_id = r.id \n" +
                "inner join account_role ar\n" +
                "on r.id = ar.role_id\n" +
                "inner join account a \n" +
                "on ar.account_id = a.id where g.id  = :group_id", AccountEntity.class);
        query.setParameter("group_id", groupId);

        return query.getResultList();
    }

    public List<StudentProfileResponse> getAllStudentProfile() {
        Query query = em.createNativeQuery("select HEX(s.account_id) as accountId, student_code as studentCode, school_year as schoolYear, first_name as firstName,\n" +
                "       last_name as lastName, birth_date as birthDate, gender as gender, email as email,\n" +
                "       phone_number as phoneNumber, address as address, department as department, klass as klass,\n" +
                "       a.updated_by as updatedBy, a.create_by as createBy,\n" +
                "       i.logo as logo, i.background as background,\n" +
                "       r.name as roleName\n" +
                "from student s\n" +
                "inner join account a on s.account_id = a.id\n" +
                "left join image i on a.id = i.id\n" +
                "inner join account_role ar on a.id = ar.account_id\n" +
                "inner join role r on ar.role_id = r.id\n" +
                "where a.active>0");
        List<Object[]> rows = query.getResultList();
        List<StudentProfileResponse> list = new ArrayList<>();
        for (Object[] row : rows) {

            StudentProfileResponse studentProfileResponse = StudentProfileResponse.builder()
                    .accountId(UUID.fromString(convertToUUID((String) row[0])))
                    .studentCode((String) row[1])
                    .schoolYear((String) row[2])
                    .firstName((String) row[3])
                    .lastName((String) row[4])
                    .birthDate((Date) row[5])
                    .gender((Boolean) row[6])
                    .email((String) row[7])
                    .phoneNumber((String) row[8])
                    .address((String) row[9])
                    .department((String) row[10])
                    .klass((String) row[11])
                    .updatedBy((String) row[12])
                    .createBy((String) row[13])
                    .logo((String) row[14])
                    .background((String) row[15])
                    .roleName((String) row[16])
                    .build();
            list.add(studentProfileResponse);
        }
        return list;
    }

    public List<TeacherProfileResponse> getAllTeacherProfile() {
        Query query = em.createNativeQuery("select HEX(t.account_id), first_name, last_name, phone_number, email, gender, address, department,\n" +
                "       a.updated_by as updatedBy, a.create_by as createBy,\n" +
                "       i.logo as logo, i.background as background,\n" +
                "       r.name as roleName\n" +
                "from teacher t\n" +
                "         inner join account a on t.account_id = a.id\n" +
                "         left join image i on a.id = i.id\n" +
                "         inner join account_role ar on a.id = ar.account_id\n" +
                "         inner join role r on ar.role_id = r.id\n" +
                "where a.active>0");
        List<Object[]> rows = query.getResultList();
        List<TeacherProfileResponse> list = new ArrayList<>();
        for (Object[] row : rows) {

            TeacherProfileResponse teacherProfileResponse = TeacherProfileResponse.builder()
                    .accountId(UUID.fromString(convertToUUID((String) row[0])))
                    .firstName((String) row[1])
                    .lastName((String) row[2])
                    .gender((Boolean) row[5])
                    .email((String) row[4])
                    .phoneNumber((String) row[3])
                    .address((String) row[6])
                    .department((String) row[7])
                    .updatedBy((String) row[8])
                    .createBy((String) row[9])
                    .logo((String) row[10])
                    .background((String) row[11])
                    .roleName((String) row[12])
                    .build();
            list.add(teacherProfileResponse);
        }
        return list;
    }

    public List<DiscussAllResponse> getAllDiscussByOwner(UUID ownerID) {
        Query query = em.createNativeQuery("select d.id,r.title,ifnull(c.name, concat(t.first_name,' ',t.last_name)) as ownerName, concat(st.first_name, ' ', st.last_name) as reciverName, d.status, d.created_at " +
                " from discuss d inner join recruitment r on d.recruitment_id = r.id inner join student st on st.account_id = d.receiver left join company c on d.owner = c.account_id left join teacher t on d.owner = t.account_id " +
                " where d.owner = UUID_TO_BIN(:owner_id) or d.receiver = UUID_TO_BIN(:owner_id)");
        query.setParameter("owner_id", ownerID.toString());
        List<Object[]> rows = query.getResultList();
        List<DiscussAllResponse> list = new ArrayList<>();
        for (Object[] row : rows) {
            list.add(DiscussAllResponse.builder()
                    .id((String) row[0])
                    .recruitmentName((String) row[1])
                    .ownerName((String) row[2])
                    .receiverName((String) row[3])
                    .status((int) row[4])
                    .createdAt((Timestamp) row[5])
                    .build());
        }
        return list;
    }

    public DiscussFullResponse getFullDiscuss(String disId){
        Query query = em.createNativeQuery("select d.id, ifnull(bin_to_uuid(c.account_id)," +
                " bin_to_uuid(t.account_id)) as ownerId,\n" +
                "bin_to_uuid(st.account_id) as receiverId,\n" +
                "ifnull(c.name, concat(t.first_name,' ',t.last_name)) as ownerName,\n" +
                "concat(st.first_name, ' ', st.last_name) as reciverName,\n" +
                "r.id as recId ,r.title,  d.created_at\n" +
                "from discuss d\n" +
                "inner join recruitment r on d.recruitment_id = r.id\n" +
                "inner join student st on st.account_id = d.receiver\n" +
                "left join company c on d.owner = c.account_id\n" +
                "left join teacher t on d.owner = t.account_id\n" +
                "where d.id = :id");
        query.setParameter("id", disId);
//        Optional.ofNullable((Object[]) query.getSingleResult())
//                .orElseThrow(()-> exceptionDiscussNotFound);
        Object [] row = null;
        try{
           row = (Object[]) query.getSingleResult();
        }catch (NoResultException ex){
            throw exceptionDiscussNotFound;

        }
        ImageEntity ownerImage = dbContext.imageRepository.findById(UUID.fromString((String) row[1])).get();
        ImageEntity receiverImage = dbContext.imageRepository.findById(UUID.fromString((String) row[2])).get();
        List<DiscussContentEntity> contentlist = new ArrayList<>();
        Optional.ofNullable(dbContext.discussContentRepository
                .findAllByDiscussIDOrderByCreatedAtDesc(disId))
                .orElseThrow(()->exceptionDiscussNotFound);
        contentlist = dbContext.discussContentRepository.findAllByDiscussIDOrderByCreatedAtDesc(disId)
        .orElseGet(ArrayList::new);
        System.out.println(contentlist);
        List<DiscussContentFull> contentFullList = new ArrayList<>();
        for(int i=0;i<contentlist.size();i++){
            DiscussContentFull contentFull = new DiscussContentFull();
            contentFull.setId(contentlist.get(i).getId());
            contentFull.setContent(contentlist.get(i).getContent());
            contentFull.setDiscussID(contentlist.get(i).getDiscussID());
            contentFull.setOwner(contentlist.get(i).getOwner());
            contentFull.setCreatedAt(contentlist.get(i).getCreatedAt());
            contentFull.setOwnerName((String) row[3]);
            contentFull.setOwnerImage(ownerImage.getLogo());
            contentFull.setReceiverImage(receiverImage.getLogo());
            contentFullList.add(contentFull);
        }
        return DiscussFullResponse.builder()
                .discussId((String) row[0])
                .ownerId(UUID.fromString((String) row[1]))
                .ownerName((String) row[3])
                .receiverId(UUID.fromString((String) row[2]))
                .receiverName((String) row[4])
                .recId((String) row[5])
                .recTittle((String) row[6])
                .listContent(contentFullList)
                .build();
    }



    public DiscussHeader getDiscussHeaderByID(String id) {
        Query query = em.createNativeQuery("select d.id,r.title,ifnull(c.name, concat(t.first_name,' ',t.last_name)) as ownerName, concat(st.first_name, ' ', st.last_name) as reciverName, d.status, d.created_at, st.email, st.phone_number\n" +
                " from discuss d inner join recruitment r on d.recruitment_id = r.id inner join student st on st.account_id = d.receiver left join company c on d.owner = c.account_id left join teacher t on d.owner = t.account_id\n" +
                " where d.id = :id ");
        query.setParameter("id", id);
        List<Object[]> rows = query.getResultList();
        DiscussHeader discussHeader = new DiscussHeader();
        for (Object[] row : rows) {
            discussHeader.setId((String) row[0]);
            discussHeader.setRecruitmentName((String) row[1]);
            discussHeader.setOwnerName((String) row[2]);
            discussHeader.setReceiverName((String) row[3]);
            discussHeader.setStatus((int) row[4]);
            discussHeader.setCreatedAt((Timestamp) row[5]);
            discussHeader.setStudentEmail((String) row[6]);
            discussHeader.setStudentPhone((String) row[7]);
        }
        return discussHeader;
    }

    public List<InternDetail> getAllInternStudentByCompanyId(UUID comId) {
        Query query = em.createNativeQuery("select concat(s.first_name,' ', s.last_name ) as full_name, s.email, s.phone_number, s.address, s.birth_date, i.position, c.name\n" +
                "from intern i\n" +
                "inner join student s on i.student_id = s.account_id\n" +
                "inner join company c on i.company_id = c.account_id\n" +
                "where bin_to_uuid(c.account_id) = :id");
        query.setParameter("id", comId.toString());
        List<Object[]> rows = query.getResultList();

        List<InternDetail> list = new ArrayList<>();
        for (Object[] row : rows) {
            InternDetail internDetail = InternDetail.builder()
                    .fullName((String) row[0])
                    .email((String) row[1])
                    .phoneNumber((String) row[2])
                    .address((String) row[3])
                    .birthDay((Date) row[4])
                    .position((String) row[5])
                    .companyName((String) row[6])
                    .build();
            list.add(internDetail);
        }
        return list;
    }

    private BaseStatistical parseFromQuery(Object[] row) {
        BaseStatistical baseStatistical = new BaseStatistical();
        baseStatistical.setCompanyId(UUID.fromString((String) row[0]));
        baseStatistical.setCompanyName((String) row[1]);
        baseStatistical.setPhoneNumber((String) row[2]);
        baseStatistical.setEmail((String) row[3]);
        baseStatistical.setLocation((String) row[4]);
        baseStatistical.setNumOfStudent(((BigInteger) row[5]).intValue());
        return baseStatistical;
    }

    public CompanyStatisticalResponse getStatistical() {
        Query query = em.createNativeQuery("\n" +
                "select bin_to_uuid(c.account_id), c.name, c.phone_number, c.email, c.location ,COUNT( DISTINCT i.student_id)\n" +
                "from intern i\n" +
                "inner join company c on i.company_id = c.account_id\n" +
                "group by  c.name\n" +
                "order by   COUNT( DISTINCT i.student_id) desc;");
        List<Object[]> rows = query.getResultList();
        List<BaseStatistical> details = rows.stream().map(this::parseFromQuery).collect(Collectors.toList());
        return CompanyStatisticalResponse.builder()
                .numOfCompany(details.size())
                .details(details)
                .build();
    }

    public List<CompanyProfileResponse> getAllCompanyProfile() {
        Query query = em.createNativeQuery("select HEX(c.account_id), c.name, company_info, location, phone_number, email, working_date, country, over_view,\n" +
                "       a.updated_by as updatedBy, a.create_by as createBy,\n" +
                "       i.logo as logo, i.background as background,\n" +
                "       r.name as roleName\n" +
                "from company c\n" +
                "         inner join account a on c.account_id = a.id\n" +
                "         left join image i on a.id = i.id\n" +
                "         inner join account_role ar on a.id = ar.account_id\n" +
                "         inner join role r on ar.role_id = r.id\n" +
                "where a.active>0");
        List<Object[]> rows = query.getResultList();
        List<CompanyProfileResponse> list = new ArrayList<>();
        for (Object[] row : rows) {
            CompanyProfileResponse companyProfileResponse = CompanyProfileResponse.builder()
                    .accountId(UUID.fromString(convertToUUID((String) row[0])))
                    .name((String) row[1])
                    .companyInfo((String) row[2])
                    .location((String) row[3])
                    .phoneNumber((String) row[4])
                    .email((String) row[5])
                    .workingDay((String) row[6])
                    .country((String) row[7])
                    .overView((String) row[8])
                    .updatedBy((String) row[9])
                    .createBy((String) row[10])
                    .logo((String) row[11])
                    .background((String) row[12])
                    .roleName((String) row[13])
                    .build();
            list.add(companyProfileResponse);
        }
        return list;
    }

    public List<SearchCVResponse> getAllCVBySkills(List<Integer> skills) {
        Query query = em.createNativeQuery("select distinct cv_id, bin_to_uuid(account_id) " +
                ",concat(first_name,' ', last_name ) as fullname" +
                ", st.school_year, st.email , img.logo as logo" +
                ", img.background as background\n" +
                "from cv_skills cs  inner join curriculum_vitae cv " +
                "on cv.id = cs.cv_id inner join student st " +
                "on st.account_id = cv.student_id left join image img " +
                "on st.account_id = img.id\n" +
                "where cv.active = 1 and  cs.skills_id in (?1)\n" +
                "order by fullname");
        List<Object[]> rows = query.setParameter(1, skills).getResultList();
        List<SearchCVResponse> list = new ArrayList<>();
        for (Object[] row : rows) {
            SearchCVResponse searchResponse = SearchCVResponse.builder()
                    .cvId((String) row[0])
                    .accountId(UUID.fromString((String) row[1]))
                    .fullName((String) row[2])
                    .schoolYear((String) row[3])
                    .email((String) row[4])
                    .logo((String) row[5])
                    .background((String) row[6])
                    .build();
            list.add(searchResponse);
        }
        return list;

    }

    public boolean isUserExistOnDiscuss(String discussID, UUID userID) {
        Query query = em.createNativeQuery("SELECT  *  FROM discuss WHERE id = :discuss_id AND " +
                "( owner = UUID_TO_BIN(:user_id) or receiver =  UUID_TO_BIN(:user_id) )");
        query.setParameter("user_id", userID.toString());
        query.setParameter("discuss_id", discussID);
        return query.getResultList().size() > 0;
    }

    public String convertToUUID(String s1) {
        s1 = s1.substring(0, 8) + '-' + s1.substring(8, 12) + '-'
                + s1.substring(12, 16) + '-' + s1.substring(16, 20) + '-' + s1.substring(20, s1.length());
        return s1;
    }

    public String[] getEmailByGroupID(int groupID) {
        StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("get_email_by_group_id");
        procedureQuery.registerStoredProcedureParameter("group_id", Integer.class, ParameterMode.IN);
        procedureQuery.setParameter("group_id", groupID);
        procedureQuery.execute();
        List<String> resultList = procedureQuery.getResultList();
        resultList = resultList.stream().filter(x -> x != null).collect(Collectors.toList());
        return resultList.toArray(new String[resultList.size()]);
    }

}
