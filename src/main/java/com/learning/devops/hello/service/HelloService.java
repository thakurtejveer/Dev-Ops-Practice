package com.learning.devops.hello.service;

import com.learning.devops.hello.model.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public HelloResponse sayHello() {
        return new HelloResponse("Hello from Spring Boot API using layered structure!");
    }
}
