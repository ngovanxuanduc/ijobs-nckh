package com.immortal.internship.service;

import com.immortal.internship.entity.SkillsEntity;

import java.util.List;


public interface SkillService {
    List<SkillsEntity> getAllSkills();

    List<SkillsEntity> addListSkills(List<String> skills);

}
