package dev.njari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author njari_mathenge
 * on 12/09/2023.
 * github.com/iannjari
 */

@SpringBootApplication
@ComponentScan(basePackages = {"io.temporal.spring.boot.autoconfigure", "dev.njari"})
public class IprsApp {
    public static void main(String[] args) {
        SpringApplication.run(IprsApp.class, args);
    }
}