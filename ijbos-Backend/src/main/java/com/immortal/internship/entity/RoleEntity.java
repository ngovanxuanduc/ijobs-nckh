package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;

    // mappedBy trỏ tới tên biến roles ở trong Account.
    @ManyToMany(mappedBy = "roles")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Collection<AccountEntity> account;

}
