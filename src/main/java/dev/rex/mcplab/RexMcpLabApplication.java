package dev.rex.mcplab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RexMcpLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(RexMcpLabApplication.class, args);
    }

}
