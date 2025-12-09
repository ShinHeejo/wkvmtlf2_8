package kr.ac.ewha.java2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "kr.ac.ewha.java2",
                "domain",
                "dto",
                "repository",
                "service"          
        }
)
public class CardGame1Application {

    public static void main(String[] args) {
        SpringApplication.run(CardGame1Application.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
