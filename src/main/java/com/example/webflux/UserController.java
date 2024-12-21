package com.example.webflux;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        // 강제로 예외 발생
        //throw new RuntimeException("강제로 발생한 500 Internal Server Error");

        System.out.println("Hello WebFlux");
        return "Hello WebFlux";
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/CircuitBreakerTest")
    public Mono<String> CircuitBreakerTest() {
        return userService.testCircuitBreaker();
    }

}