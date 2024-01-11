package com.service.apiservice.service;

import com.service.apiservice.dto.*;
import com.service.apiservice.exception.CustomException;
import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.AppUserRole;
import com.service.apiservice.model.ContactDTO;
import com.service.apiservice.model.PasswordResetDTO;
import com.service.apiservice.repository.PasswordResetRepository;
import com.service.apiservice.repository.UserRepository;
import com.service.apiservice.security.JwtTokenProvider;
import com.service.apiservice.utils.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ContactService {
    Boolean save(ContactDTO model);
}
