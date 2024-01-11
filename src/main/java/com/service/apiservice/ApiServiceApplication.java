package com.service.apiservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.AppUserRole;
import com.service.apiservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class ApiServiceApplication implements CommandLineRunner{
	final UserService userService;

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
	}
	public static void main(String[] args) {
		SpringApplication.run(ApiServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		AppUser superadmin = new AppUser();
		superadmin.setUsername("superadmin");
		superadmin.setPassword("mothaiba456@!#");
		superadmin.setFullName("superadmin");
		superadmin.setEmail("superadmin@email.com");
		superadmin.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_SUPER_ADMIN)));
		userService.signup(superadmin);

		AppUser admin = new AppUser();
		admin.setUsername("admin");
		admin.setPassword("mothaiba456@!#");
		admin.setEmail("admin@email.com");
		admin.setFullName("admin");
		admin.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_ADMIN)));
		userService.signup(admin);

		AppUser sale = new AppUser();
		sale.setUsername("sale");
		sale.setPassword("mothaiba456@!#");
		sale.setFullName("sale");
		sale.setEmail("sale@email.com");
		sale.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_SALES)));
		userService.signup(sale);

		AppUser staff = new AppUser();
		staff.setUsername("staff");
		staff.setPassword("mothaiba456@!#");
		staff.setFullName("staff");
		staff.setEmail("staff@email.com");
		staff.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_STAFF)));
		userService.signup(staff);

		AppUser client = new AppUser();
		client.setUsername("client");
		client.setPassword("mothaiba456@!#");
		client.setFullName("client");
		client.setEmail("client@email.com");
		client.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT)));
		userService.signup(client);
	}
}
