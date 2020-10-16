package com.srm.repg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) throws Exception {
       // SpringApplication.run(WebApplication.class, args);
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(WebApplication.class);
		builder.headless(false).run(args);
    }
}