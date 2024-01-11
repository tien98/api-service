package com.service.apiservice.repository;

import javax.transaction.Transactional;

import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    AppUser findByUsername(String username);
    List<AppUser> findByAppUserRolesEquals(AppUserRole appUserRole);
    @Transactional
    void deleteByUsername(String username);

}
