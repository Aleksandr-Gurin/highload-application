package ru.itmo.highload.stroroom.notificationsws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationsWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationsWsApplication.class, args);
	}

}
