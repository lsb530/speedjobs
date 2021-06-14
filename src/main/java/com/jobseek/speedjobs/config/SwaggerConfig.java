package com.jobseek.speedjobs.config;

import com.jobseek.speedjobs.config.auth.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
			.apis(RequestHandlerSelectors.basePackage("com.jobseek.speedjobs.controller"))
			.paths(PathSelectors.any())
			.build()
			.ignoredParameterTypes(LoginUser.class)
			.useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SpeedJobs API Documentation")
			.description("채용 사이트 SpeedJobs를 위한 서버 API 문서입니다.")
			.license("jobseek").licenseUrl("https://github.com/jobseek").version("1").build();
	}
}
