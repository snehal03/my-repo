package com.example.bootmongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableAutoConfiguration
@SpringBootApplication
public class BootmongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootmongoApplication.class, args);
	}
	

	@Bean
	public Docket swaggerSettings() {
		Parameter parameter = new ParameterBuilder().name("Authorization").description("Authorization Token")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(parameter);
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.bootmongo")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).pathMapping("/").globalOperationParameters(parameters)
				.useDefaultResponseMessages(false);

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("BootMongo SERVICE", "BootMongo service Rest API Document", "API TOS",
				"Terms of service", "Agile Soft Systems India Pvt Ltd", "License of API", "");
		return apiInfo;
	}

}



