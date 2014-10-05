package br.com.uaijug.kairos.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("br.com.uaijug.kairos.repository")
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DatabaseConfiguration implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

	private RelaxedPropertyResolver propertyResolver;

	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
		this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
	}

	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingClass(name = "br.com.uaijug.kairos.config.HerokuDatabaseConfiguration.class")
	public DataSource dataSource() {
		this.log.debug("Configuring Datasource");
		if (this.propertyResolver.getProperty("url") == null
				&& this.propertyResolver.getProperty("databaseName") == null) {
			this.log.error("Your database connection pool configuration is incorrect! The application"
					+ "cannot start. Please check your Spring profile, current profiles are: {}",
					Arrays.toString(this.environment.getActiveProfiles()));

			throw new ApplicationContextException("Database connection pool is not configured correctly");
		}
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(this.propertyResolver.getProperty("dataSourceClassName"));
		if (this.propertyResolver.getProperty("url") == null || "".equals(this.propertyResolver.getProperty("url"))) {
			config.addDataSourceProperty("databaseName", this.propertyResolver.getProperty("databaseName"));
			config.addDataSourceProperty("serverName", this.propertyResolver.getProperty("serverName"));
		} else {
			config.addDataSourceProperty("url", this.propertyResolver.getProperty("url"));
		}
		config.addDataSourceProperty("user", this.propertyResolver.getProperty("username"));
		config.addDataSourceProperty("password", this.propertyResolver.getProperty("password"));

		// MySQL optimizations, see
		// https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
		if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(this.propertyResolver
				.getProperty("dataSourceClassName"))) {
			config.addDataSourceProperty("cachePrepStmts", this.propertyResolver.getProperty("cachePrepStmts", "true"));
			config.addDataSourceProperty("prepStmtCacheSize",
					this.propertyResolver.getProperty("prepStmtCacheSize", "250"));
			config.addDataSourceProperty("prepStmtCacheSqlLimit",
					this.propertyResolver.getProperty("prepStmtCacheSqlLimit", "2048"));
			config.addDataSourceProperty("useServerPrepStmts",
					this.propertyResolver.getProperty("useServerPrepStmts", "true"));
		}
		return new HikariDataSource(config);
	}

	@Bean(name = { "org.springframework.boot.autoconfigure.AutoConfigurationUtils.basePackages" })
	public List<String> getBasePackages() {
		List<String> basePackages = new ArrayList<>();
		basePackages.add("br.com.uaijug.kairos.domain");
		return basePackages;
	}

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {
		this.log.debug("Configuring Liquibase");
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:config/liquibase/master.xml");
		liquibase.setContexts("development, production");
		return liquibase;
	}

	@Bean
	public Hibernate4Module hibernate4Module() {
		return new Hibernate4Module();
	}

}
