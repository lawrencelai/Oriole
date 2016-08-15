package org.oriole;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
	public static void main(String[] args) {
	    SpringApplicationBuilder parentBuilder  = new SpringApplicationBuilder(Application.class);
	    parentBuilder.child(Application.class).properties("server.port:8080").run(args);
	}
}
