package com.example.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Wallet API", description = "Wallet service to manage user's money", version = "${version.api}"))
@SpringBootApplication
public class WalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}

}
