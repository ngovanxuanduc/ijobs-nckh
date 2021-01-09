package com.immortal.internship.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBContext {
    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public CurrentTokenRepository currentTokenRepository;

    @Autowired
    public RecruitmentRepository recruitmentRepository;

    @Autowired
    public SkillsRepository skillsRepository;

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public CustomRepository customRepository;

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public TeacherRepository teacherRepository;

    @Autowired
    public CompanyRepository companyRepository;

    @Autowired
    public NotificationHistoryRepository notificationHistoryRepository;

    @Autowired
    public RecruitmentStudentStatusRepository recruitmentStudentStatusRepository;

    @Autowired
    public ImageRepository imageRepository;

    @Autowired
    public CVRepository cvRepository;

    @Autowired
    public DiscussRepository discussRepository;

    @Autowired
    public DiscussContentRepository discussContentRepository;

    @Autowired
    public InternRepository internRepository;

}
