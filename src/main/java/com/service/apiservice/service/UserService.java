package com.service.apiservice.service;

import javax.servlet.http.HttpServletRequest;

import ch.qos.logback.core.joran.event.BodyEvent;
import com.service.apiservice.dto.*;
import com.service.apiservice.exception.CustomException;
import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.AppUserRole;
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

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public SignUpResDTO signin(String username, String password) {
        SignUpResDTO signUpResDTO = new SignUpResDTO();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            signUpResDTO.setSuccess(true);
            signUpResDTO.setMessage("");
            signUpResDTO.setToken(jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles()));
            return signUpResDTO;
        } catch (AuthenticationException e) {
            signUpResDTO.setSuccess(false);
            signUpResDTO.setMessage("Tên đăng nhập hoặc mật khẩu không đúng!");
            signUpResDTO.setToken("");
            return signUpResDTO;
        }
    }

    public SignUpResDTO signup(AppUser appUser) {
        SignUpResDTO signUpResDTO = new SignUpResDTO();
        if (!userRepository.existsByUsername(appUser.getUsername())) {
            if(userRepository.existsByEmail(appUser.getEmail())){
                signUpResDTO.setSuccess(false);
                signUpResDTO.setMessage("Email đã được sử dụng. Vui lòng nhập email khác!");
                signUpResDTO.setToken("");
                return signUpResDTO;
            }
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            userRepository.save(appUser);
            signUpResDTO.setSuccess(true);
            signUpResDTO.setMessage("");
            signUpResDTO.setToken(jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles()));
            return signUpResDTO;
        } else {
            List<String> supplierNames = Arrays.asList("superadmin", "admin", "sale", "staff", "client");

            if (supplierNames.contains(appUser.getUsername())) {
                signUpResDTO.setSuccess(false);
                signUpResDTO.setMessage("");
                signUpResDTO.setToken("");
                return signUpResDTO;
            }else {
                signUpResDTO.setSuccess(false);
                signUpResDTO.setMessage("Tên đăng nhập đã được sử dụng. Vui lòng nhập tên đăng nhập khác!");
                signUpResDTO.setToken("");
                return signUpResDTO;
            }
        }
    }

    public Boolean update(UserUpdateDTO user, HttpServletRequest httpServlet) {
        try{
            var userOne = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServlet)));
            if(userOne == null) {
                return false;
            }
            userOne.setFullName(user.getFullName());
            userOne.setEmail(user.getEmail());
            userOne.setSex(user.getSex());
            userOne.setDob(user.getDob());
            userOne.setPhone(user.getPhone());
            userOne.setProvinceId(user.getProvinceId());
            userOne.setDistrictId(user.getDistrictId());
            userOne.setWardId(user.getWardId());
            userOne.setAddress(user.getAddress());
            userRepository.save(userOne);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public String changePassword(ChangePasswordDTO model, HttpServletRequest httpServlet) {
        try{
            var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServlet)));
            if(user == null) {
                return "Tài khoản không tồn tại";
            }
            String pass = user.getPassword();
            boolean valid = passwordEncoder.matches(model.getOldPassword(), pass);
            if(!valid) {
                return "Mật khẩu cũ không đúng";
            }
            user.setPassword(passwordEncoder.encode(model.getNewPassword()));
            userRepository.save(user);
            return "";
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public AppUser search(String username) {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return appUser;
    }

    public AppUser whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
    }

    public Boolean checkInfo(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<AppUser> getUserByRole(AppUserRole appUserRole) {
        return userRepository.findByAppUserRolesEquals(appUserRole);
    }

    public CodeResponseDTO sendCode(String userName) {
        CodeResponseDTO codeResponseDTO = new CodeResponseDTO();
        try{
            AppUser appUser = userRepository.findByUsername(userName);
            if(appUser == null) {
                codeResponseDTO.setIsSuccess(false);
                codeResponseDTO.setMessage("Tên tài khoản không tồn tại");
                return codeResponseDTO;
            }
            List<PasswordResetDTO> passwordResetDTOList = passwordResetRepository.getAllByUserName(userName);
            for (var item : passwordResetDTOList) {
                item.setStatus(false);
                passwordResetRepository.save(item);
            }
            PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
            String code = DataUtil.getRandomNumberString();
            passwordResetDTO.setCode(code);
            passwordResetDTO.setUserName(userName);
            passwordResetRepository.save(passwordResetDTO);
            codeResponseDTO.setIsSuccess(true);
            codeResponseDTO.setUsername(userName);
            codeResponseDTO.setMail(appUser.getEmail());
            codeResponseDTO.setCode(code);
        }catch (Exception e) {
            codeResponseDTO.setIsSuccess(false);
            codeResponseDTO.setMessage(e.getMessage());
        }
        return codeResponseDTO;
    }

    public Boolean verifyCode(String userName, String code) {
        try{
            AppUser appUser = userRepository.findByUsername(userName);
            if(appUser == null) {
                return false;
            }
            Optional<PasswordResetDTO> passwordResetDTOList = passwordResetRepository.getAllByUserNameAndCodeAndStatusIsTrue(userName, code);
            if(passwordResetDTOList.isPresent()) {
                PasswordResetDTO pass = passwordResetDTOList.get();
                pass.setStatus(false);
                passwordResetRepository.save(pass);
                return  true;
            }else {
                return false;
            }

        }catch (Exception e) {
            return false;
        }
    }

    public String resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) {
        try{
            AppUser appUser = userRepository.findByUsername(resetPasswordRequestDTO.getUsername().trim());
            if(appUser == null) {
                return "Tài khoản không tồn tại";
            }
            if(!resetPasswordRequestDTO.getPassword().equals(resetPasswordRequestDTO.getRePassword())) {
                return "Mật khẩu không khớp nhau";
            }
            Boolean resVerify = verifyCode(resetPasswordRequestDTO.getUsername(), resetPasswordRequestDTO.getCode());
            if(!resVerify) {
                return "Mã xác nhận không hợp lệ";
            }
            appUser.setPassword(passwordEncoder.encode(resetPasswordRequestDTO.getPassword()));
            userRepository.save(appUser);
            return "";
        }catch (Exception e) {
            return e.getMessage();
        }
    }
}
