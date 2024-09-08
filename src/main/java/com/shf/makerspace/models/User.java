package com.shf.makerspace.models;

import com.shf.makerspace.application.module.user.bean.UserRequestBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    public void initModel(UserRequestBean requestParam) {
        if (!isNullOrEmpty(requestParam.getId())) {
            this.id = requestParam.getId();
        } else {
            this.createdDate = LocalDateTime.now();
        }
        this.firstName = requestParam.getFirstName();
        this.lastName = requestParam.getLastName();
        this.email = requestParam.getEmail();
        this.userName = requestParam.getUserName();
        this.password = requestParam.getPassword();
        this.isActive = requestParam.getIsActive();
    }
}
