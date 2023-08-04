package com.example.modulithtest

import javax.sql.DataSource
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.modulith.events.EventPublication
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionManager
import org.springframework.transaction.annotation.Transactional

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(basePackages = ["org.springframework.modulith.events.jdbc"])
class DataSourceConfiguration {

  /**
   * We have to import the [DataSourceAutoConfiguration] because when a [ConnectionFactory] bean is
   * available, the regular JDBC [DataSource] auto-configuration backs off. Since Spring Modulith
   * currently doesn't support reactive persistence of [EventPublication]s, we have to re-enable it.
   *
   * @see <a
   *   href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#data.sql.r2dbc">Spring
   *   Boot Reference Documentation</a>
   * @see DataSourceAutoConfiguration
   * @see R2dbcAutoConfiguration
   */
  @Configuration
  @Import(DataSourceAutoConfiguration::class)
  class ReEnableJdbcDataSourceConfiguration

  /**
   * Configures a [PlatformTransactionManager] to set it as [Primary]. This is required because
   * Spring Modulith uses [Transactional] annotations without qualifier.
   *
   * Since this application uses a blocking and a reactive data access layer, we have two
   * autoconfigured [TransactionManager] beans.
   *
   * The manual configuration is set up the same way as the corresponding Spring Boot
   * autoconfiguration.
   *
   * @see DataSourceTransactionManagerAutoConfiguration
   * @see R2dbcTransactionManagerAutoConfiguration
   */
  @Bean
  @Primary
  fun transactionManager(
      environment: Environment,
      dataSource: DataSource,
      transactionManagerCustomizers: ObjectProvider<TransactionManagerCustomizers>
  ): DataSourceTransactionManager {
    val transactionManager = createTransactionManager(environment, dataSource)
    transactionManagerCustomizers.ifAvailable { customizers: TransactionManagerCustomizers ->
      customizers.customize(transactionManager)
    }
    return transactionManager
  }

  private fun createTransactionManager(
      environment: Environment,
      dataSource: DataSource
  ): DataSourceTransactionManager {
    return if (environment.getProperty(
        "spring.dao.exceptiontranslation.enabled", Boolean::class.java, java.lang.Boolean.TRUE))
        JdbcTransactionManager(dataSource)
    else DataSourceTransactionManager(dataSource)
  }
}
