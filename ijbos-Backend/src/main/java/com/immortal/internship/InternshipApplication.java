package com.immortal.internship;


import com.immortal.internship.repository.CustomRepository;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.ElasticSearchService;
import com.immortal.internship.service.EmailService;
import com.immortal.internship.service.ServiceImpl.EmailServiceImpl;
import com.immortal.internship.service.ServiceImpl.RecruitmentServiceImpl;
import com.immortal.internship.utility.NotificationValidationUtil;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class InternshipApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(InternshipApplication.class, args);

//		ElasticSearchService elasticSearchService = context.getBean(ElasticSearchService.class);
//		elasticSearchService.getCVSuggestByRecruitment("T0OBHEvm9Rk1923").forEach(System.out::println);
		DBContext dbContext = context.getBean(DBContext.class);
//		System.out.println(dbContext.customRepository.getImageByDiscussId("6MWhEMEJglWnyV8gR"));

//		System.out.println(dbContext.customRepository.getNumofstudentByCompanyId(UUID.fromString("953bb57d-7a9f-4898-97cf-174a4969b590")));
//		dbContext.customRepository.getEmailByGroupID(1|2|4);
//		System.out.println(dbContext.customRepository.getNumofstudentByCompanyId(UUID.fromString("953bb57d-7a9f-4898-97cf-174a4969b590")));
//		System.out.println(dbContext.cvRepository.findByStudentIDAndApprovedByEquals(UUID.fromString("37e45bf3-ec55-417c-830e-13a34db90b08"),UUID.fromString(null)));
        //RecruitmentServiceImpl recruitmentService = context.getBean(RecruitmentServiceImpl.class);
//        System.out.println(recruitmentService.getRecruitmentsByCreateBy(UUID.fromString("d2708a7d-0b96-47fe-95a4-6491dc144b3c")));
//
//		System.out.println(elasticSearchService.getAllCVBySkills("JAVA","PHP"));
//		List<Integer> list = new ArrayList<>();
//		list.add(1);
////		list.add(2);
////		list.add(3);
//		System.out.println(customRepository.getAllCVBySkills(list));

//		EmailServiceImpl emailService = context.getBean(EmailServiceImpl.class);
//		emailService.sendMessageWithAttachment("Ijobs Test HTML","Test Lan 1","ngovanxuanduc1@gmail.com","xuanduclb123@gmail.com");
//		System.out.println("gui xong");

//		dbContext.discussRepository
//				.findAllByOwnerOrReceiver(UUID.fromString("f5882c35-c8ec-4c13-9518-ee5550c27a98"),UUID.fromString("f5882c35-c8ec-4c13-9518-ee5550c27a98"))
//		dbContext.customRepository.getAllDiscussByOwner(UUID.fromString("3e522955-f49b-4938-a95f-c692807ee0ad")).forEach(System.out::println);
//		System.out.println(dbContext.customRepository.getDiscussHeaderByID("zqUGLo27SGqA3SKmo"));
//		System.out.println("receiver");
//		dbContext.customRepository.getAllDiscussByOwner(UUID.fromString("5273cb19-e008-4721-9d8a-4e56ecbd679c")).forEach(System.out::println);
//		System.out.println("owner");
//		dbContext.customRepository.getAllDiscussByOwner(UUID.fromString("3e522955-f49b-4938-a95f-c692807ee0ad")).forEach(System.out::println);
	}

}
