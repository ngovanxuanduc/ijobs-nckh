package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "image")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @JsonIgnore
    private UUID id;

    @Column(name = "logo")
    private String logo;

    @Column(name = "background")
    private String background;
}
