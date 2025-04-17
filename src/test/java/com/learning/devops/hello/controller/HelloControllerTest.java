package com.learning.devops.hello.controller;

import com.learning.devops.hello.controller.HelloController;
import com.learning.devops.hello.model.HelloResponse;
import com.learning.devops.hello.service.HelloService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HelloService helloService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public HelloService helloService() {
            HelloService mock = mock(HelloService.class);
            when(mock.sayHello()).thenReturn(
                    new HelloResponse("Hello from Spring Boot API using layered structure!")
            );
            return mock;
        }
    }

    @Test
    void shouldReturnHelloResponse() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello from Spring Boot API using layered structure!"));
    }
}
