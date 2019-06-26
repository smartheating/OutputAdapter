package de.smartheating.outputAdapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"de.smartheating.SmartHeatingCommons","de.smartheating.outputAdapter"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableDiscoveryClient
public class OutputAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutputAdapterApplication.class, args);
	}

}
