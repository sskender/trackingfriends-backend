package hr.fer.tel.ruazosa.trackingfriendsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TrackingFriendsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingFriendsServiceApplication.class, args);
	}

}
