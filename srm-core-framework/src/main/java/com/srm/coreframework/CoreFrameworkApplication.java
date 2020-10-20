package com.srm.coreframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories("com.srm.coreframework.*")
@ComponentScan(basePackages = {"com.srm.coreframework.*" })
@EntityScan("com.srm.coreframework.*")   
@SpringBootApplication
@EnableCaching
public class CoreFrameworkApplication  extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(CoreFrameworkApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/*
	 * @Bean public Docket productApi() { return new
	 * Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.
	 * basePackage("com.srm.")) .paths(PathSelectors.any()) .build(); }
	 */
}
