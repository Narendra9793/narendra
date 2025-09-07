package com.assignment.narendra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "com.assignment.narendra")
@EnableJpaRepositories(basePackages = "com.assignment.narendra.repository")
@EntityScan(basePackages = "com.assignment.narendra.model")
public class NarendraApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load(); 

        setSystemProperty("DATASOURCE_URL", dotenv);
        setSystemProperty("DATASOURCE_USER", dotenv);
        setSystemProperty("DATASOURCE_PASSWORD", dotenv);
        setSystemProperty("FRONTEND_URL", dotenv);
        setSystemProperty("CLOUDINARY_API_KEY", dotenv);
        setSystemProperty("CLOUDINARY_CLOUD_NAME", dotenv);
        setSystemProperty("CLOUDINARY_API_SECRET", dotenv);

        SpringApplication.run(NarendraApplication.class, args);
    }

    private static void setSystemProperty(String key, Dotenv dotenv) {
        String value = dotenv.get(key);
        if (value != null && !value.isEmpty()) {
            System.setProperty(key, value);
            System.out.println(key +"-------------------------------- "+ System.getProperty(key));
        } else {
            System.err.println("Warning: Environment variable " + key + " is missing or empty!");
        }
    }

}
