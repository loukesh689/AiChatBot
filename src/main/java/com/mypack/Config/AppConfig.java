package com.mypack.Config;

import com.mypack.entity.User;
import com.mypack.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    // RestClient bean with Custom Timeout settings for Ollama
    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        // Connection establish timeout (30 Seconds)
        requestFactory.setConnectTimeout(30000);

        // Response read timeout (5 Minutes = 300,000 ms)
        requestFactory.setReadTimeout(300000);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }

    // Application startup run code
    @Bean
    public CommandLineRunner createTestUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User("chaman", "chaman@example.com");
                userRepository.save(user);

                System.out.println("Test user created. ID = " + user.getId());
            }
        };
    }
}