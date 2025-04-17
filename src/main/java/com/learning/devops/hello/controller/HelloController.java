package com.learning.devops.hello.controller;

import com.learning.devops.hello.model.HelloResponse;
import com.learning.devops.hello.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public HelloResponse sayHello() {
        return helloService.sayHello();
    }
}
