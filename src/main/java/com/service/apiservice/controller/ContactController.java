package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.*;
import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.AppUserRole;
import com.service.apiservice.model.ContactDTO;
import com.service.apiservice.service.ContactService;
import com.service.apiservice.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contact")
@Api(tags = "Contact")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/save")
    @ApiOperation(value = "Save contact")
    @ApiResponses(value = {//
    @ApiResponse(code = 400, message = "Something went wrong"), //
    @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Boolean> save(@Valid @RequestBody ContactDTO model) {
        return ResponseDataConfiguration.success(contactService.save(model));
    }
}
