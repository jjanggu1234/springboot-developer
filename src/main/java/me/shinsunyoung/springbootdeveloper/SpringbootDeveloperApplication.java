package me.shinsunyoung.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration	// created_at, updated_at 자동 업데이트
@SpringBootApplication
public class SpringbootDeveloperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDeveloperApplication.class, args);
	}

}
