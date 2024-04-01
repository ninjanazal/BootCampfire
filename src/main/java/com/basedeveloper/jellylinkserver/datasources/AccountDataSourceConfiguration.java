package com.basedeveloper.jellylinkserver.datasources;

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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "accountEntityManagerFactory",transactionManagerRef = "accountTransactionManager",  basePackages = {
		"com.basedeveloper.jellylinkserver.account.repository" })
public class AccountDataSourceConfiguration {

	// Account data Source
	@Primary
	@Bean(name = "accountDataSource")
	@ConfigurationProperties(prefix = "spring.account.datasource")
	public DataSource accountDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "accountEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean accountEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("accountDataSource") DataSource accounDataSource) {
		return builder.dataSource(accounDataSource)
				.packages("com.basedeveloper.jellylinkserver.account.entity")
				.build();
	}

	@SuppressWarnings("null")
	@Bean(name = "accountTransactionManager")
	@Primary
	public PlatformTransactionManager accountTransactionManager(
			final @Qualifier("accountEntityManagerFactory") LocalContainerEntityManagerFactoryBean accountEntityManagerFactory) {

		if (accountEntityManagerFactory.getObject() == null) {
			throw new IllegalStateException("EntityManager is null on accountTransactionManager");
		}
		return new JpaTransactionManager(accountEntityManagerFactory.getObject());
	}
}
