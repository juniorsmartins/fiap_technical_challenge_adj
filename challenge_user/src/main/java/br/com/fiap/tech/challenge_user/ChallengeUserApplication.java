package br.com.fiap.tech.challenge_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChallengeUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeUserApplication.class, args);
	}
}

