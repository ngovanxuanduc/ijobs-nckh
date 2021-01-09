package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "active")
    private int status;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "updated_by")
    private String updatedBy;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "id")
    private StudentEntity student;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "id")
    private TeacherEntity teacher;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "id")
    private CompanyEntity company;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id")
    private ImageEntity imageEntity;

    @JsonProperty("inf")
    public BaseAccountEntity getInformationAccount(){
        if (student!= null) {
            return student;
        }if (company!= null) {
            return company;
        }if (teacher!= null) {
            return teacher;
        }
        return null;
    }



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // Quan hệ n-n với đối tượng ở dưới (Person) (1 account có nhiều role)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JoinTable(name = "account_role", //Tạo ra một join Table tên là account_role
        joinColumns = @JoinColumn(name = "account_id"),// TRong đó, khóa ngoại chính là role_id trỏ tới class hiện tại (Account)
        inverseJoinColumns = @JoinColumn(name = "role_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Role)
    )
    private Collection<RoleEntity> roles;


//    @OneToOne
//    @JoinColumn(name = "id")
//    private CompanyEntity company;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonManagedReference
    private Collection<NotificationHistoryEntity> notificationHistories;

}
