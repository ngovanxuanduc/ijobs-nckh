package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.entity.SkillsEntity;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private DBContext dbContext;

    @Override
    public List<SkillsEntity> getAllSkills() {
        return dbContext.skillsRepository.findAll();
    }

    @Override
    public List<SkillsEntity> addListSkills(List<String> skills) {
        List<SkillsEntity> data = skills.stream()
                .map(s -> s.toUpperCase())
                .distinct()
                .filter(s -> !isSkillExist(s))
                .map(skill ->
                        SkillsEntity.builder().skillName(skill).build()).collect(Collectors.toList());
        return dbContext.skillsRepository.saveAll(data);
    }

    private boolean isSkillExist(String skill){
        return dbContext.skillsRepository.existsBySkillName(skill);
    }
}
