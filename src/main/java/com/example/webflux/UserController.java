package com.example.webflux;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RedisService redisService;

    public UserController(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
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

    @PostMapping("/redis")
    public void saveRedis(@RequestBody Map<String, String> payload) {
        String key = payload.get("key");
        String value = payload.get("value");

        redisService.save(key, value);
    }

    @GetMapping("/redis/{key}")
    public String findRedis(@PathVariable String key) {
        return redisService.find(key);
    }

    @DeleteMapping("/redis/{key}")
    public void deleteRedis(@PathVariable String key) {
        redisService.delete(key);
    }

    @GetMapping("/CircuitBreakerTest")
    public Mono<String> CircuitBreakerTest() {
        return userService.testCircuitBreaker();
    }

}