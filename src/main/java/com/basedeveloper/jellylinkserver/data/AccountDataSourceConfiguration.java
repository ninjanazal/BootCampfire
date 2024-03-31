package com.basedeveloper.jellylinkserver.data;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "accountEntityManagerFactory", transactionManagerRef = "accountTransactionManager", basePackages = {
		"com.basedeveloper.jellylinkserver.account.repository" })
public class AccountDataSourceConfiguration {

	// Account data Source
	@Bean(name = "accountDataSource")
	@Primary
	@ConfigurationProperties("spring.account.datasource")
	public DataSource accountDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "accountEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean accouEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			@Qualifier("accountDataSource") DataSource accounDataSource) {
		return builder.dataSource(accounDataSource)
				.packages("com.basedeveloper.jellylinkserver.account.entity")
				.build();
	}

	@SuppressWarnings("null")
	@Bean(name = "accountTransactionManager")
	public PlatformTransactionManager accouTransactionManager(
			@Qualifier("accountEntityManagerFactory") EntityManagerFactory accountEntityManagerFactory) {
		return new JpaTransactionManager(accountEntityManagerFactory);
	}

}
