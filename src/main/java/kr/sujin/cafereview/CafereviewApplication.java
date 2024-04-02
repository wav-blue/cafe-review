package kr.sujin.cafereview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;


@Controller
@SpringBootApplication
@EnableJpaAuditing
public class CafereviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafereviewApplication.class, args);
	}

}
