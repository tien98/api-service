package com.service.apiservice.repository;

import com.service.apiservice.model.PasswordResetDTO;
import com.service.apiservice.model.WardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetDTO, Integer> {
    List<PasswordResetDTO> getAllByUserName(String userName);
    Optional<PasswordResetDTO> getAllByUserNameAndCodeAndStatusIsTrue(String userName, String code);
}
