package com.cookswp.milkstore;

import com.cookswp.milkstore.dtos.RoleDTO;
import com.cookswp.milkstore.repository.RoleDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MilkstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilkstoreApplication.class, args);
	}


}
