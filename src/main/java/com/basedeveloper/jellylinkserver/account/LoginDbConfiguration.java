package com.basedeveloper.jellylinkserver.account;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import jakarta.activation.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "", basePackages = "com.basedeveloper.jellylinkserver.account")
public class LoginDbConfiguration {

	@Bean
	@Primary
	public DataSource LoginDataSource() {
		return (DataSource) DataSourceBuilder.create()
			.url("jdbc:postgresql://localhost:5433/jellylink_db_lg")
			.username("dev")
			.password("dev")
			.driverClassName("org.postgresql.Driver")
			.build();
	}
}
