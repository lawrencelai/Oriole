package org.oriole;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
public class Application {
	public static void main(String[] args) {
	    SpringApplicationBuilder parentBuilder  = new SpringApplicationBuilder(Application.class);
	    // For HTML Verison Oriole
	    parentBuilder.child(Application.class).properties("server.port:80").run(args);
	    // For Webservice Verison Oriole
	    parentBuilder.child(Application.class).properties("server.port:8080").run(args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/sqlci-javaconfig").allowedOrigins("http://localhost");
			}
		};
	}
}
