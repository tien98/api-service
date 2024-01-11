package com.service.apiservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
public class AppUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 4, max = 255, message = "Minimum fullName length: 4 characters")
    @Column(unique = true, nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private Date emailVerifiedAt;

    @Size(min = 10, message = "Minimum phone length: 10 characters")
    private String phone;

    private String code;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    @JsonIgnore
    private String password;

    private String avatar;

    private Date dob;

    private Integer provinceId;

    private Integer districtId;

    private Integer wardId;

    private Integer sex;

    private String address;

    @Column(name = "STATUS",  columnDefinition = "Bit(1) default true")
    private Boolean status = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    List<AppUserRole> appUserRoles;
}
