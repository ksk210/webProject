package org.embed;

import org.embed.configuration.MyTestsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;



@SpringBootApplication
@Import(MyTestsConfiguration.class)
public class SpringMyApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringMyApplication.class);
		application.setBannerMode(org.springframework.boot.Banner.Mode.OFF);
		application.setAdditionalProfiles("myprofile");
		application.run(args);
	}

}










