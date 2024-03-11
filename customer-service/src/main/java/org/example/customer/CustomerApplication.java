package org.example.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EntityScan(basePackages = {"org.example.customer.model"})
public class CustomerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =SpringApplication.run(CustomerApplication.class, args);
        context.getBean(Main.class).main();
    }

}
