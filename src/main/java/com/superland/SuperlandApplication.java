package com.superland;

import com.superland.entity.Games;
import com.superland.entity.UserAccounts;
import com.superland.entity.UserCredentials;
import com.superland.entity.Role;
import com.superland.repository.AccountRepository;
import com.superland.repository.GameRepository;
import com.superland.repository.RoleRepository;
import com.superland.repository.UserRepository;
import com.superland.utils.QRCodeGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class SuperlandApplication {
	public static void main(String[] args) {
		SpringApplication.run(SuperlandApplication.class, args);
	}
	@Bean
	CommandLineRunner run(
			RoleRepository roleRepository,
			UserRepository userRepository,
			AccountRepository accountRepository,
			PasswordEncoder passwordEncode,
			GameRepository gameRepository){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);
			UserCredentials admin = new UserCredentials("admin@gmail.com", passwordEncode.encode("password"), roles,true,"VERIFY");
			userRepository.saveAndFlush(admin);
			UserAccounts accounts = new UserAccounts();
			accounts.setCredential(admin);
			accounts.setProfileImage("adminProfile.jpg");
			accounts.setPoint(10000);
			accounts.setStatusMember("PLATINUM");
			accounts.setQrImage("adminQrCode.jpg");
			QRCodeGeneratorService.generate("adminQrCode",admin.getUserId());
			accountRepository.save(accounts);
			Games game1 = new Games();
			Games game2 = new Games();
			Games game3 = new Games();
			Games game4 = new Games();
			Games game5 = new Games();
			game1.setName("Roller Coaster");
			game1.setPointPrice(200);

			game2.setName("KORA-KORA");
			game2.setPointPrice(150);

			game3.setName("HYSTERIA");
			game3.setPointPrice(300);

			game4.setName("KARAVEL");
			game4.setPointPrice(170);

			game5.setName("TURBO-DROP");
			game5.setPointPrice(250);

//			QRCodeGeneratorService.generate("ROLERCOSTER","WHN001ROLLECOSTER");
//			QRCodeGeneratorService.generate("KORA_KORA","WHN002KORAKORA");
//			QRCodeGeneratorService.generate("HYSTERIA","WHN003HYSTERIA");
//			QRCodeGeneratorService.generate("KARAVEL","WHN004KARAVEL");
//			QRCodeGeneratorService.generate("TURBO_DROP","WHN005TURBODROP");

			game1.setId("WHN001ROLLECOSTER");
			game2.setId("WHN002KORAKORA");
			game3.setId("WHN003HYSTERIA");
			game4.setId("WHN004KARAVEL");
			game5.setId("WHN005TURBODROP");
			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);
			gameRepository.save(game4);
			gameRepository.save(game5);
		};
	}
}
