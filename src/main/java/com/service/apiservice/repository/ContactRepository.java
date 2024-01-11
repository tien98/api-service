package com.service.apiservice.repository;

import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.AppUserRole;
import com.service.apiservice.model.ContactDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ContactRepository extends JpaRepository<ContactDTO, Integer> {

}
