package com.immortal.internship.model;

import com.immortal.internship.entity.ImageEntity;
import com.immortal.internship.entity.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ShortInformationAccount {
    private UUID id;
    private ImageEntity images;
    private String fullName;
}
