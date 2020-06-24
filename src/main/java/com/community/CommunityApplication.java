package com.community;

import org.mybatis.spring.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.*;
import org.springframework.boot.web.servlet.support.*;

@SpringBootApplication
@MapperScan(basePackages = "com.community.dao")
public class CommunityApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CommunityApplication.class);
    }
}
