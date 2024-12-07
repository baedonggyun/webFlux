package com.example.webflux;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.github.resilience4j.circuitbreaker.*;
import java.time.Duration;
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
    public void CircuitBreakerTest() {

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)  // 50% 실패율 기준
                .waitDurationInOpenState(Duration.ofSeconds(10))  // Open 상태 유지 시간
                .slidingWindow(10, 5, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)  // 최근 10번 요청 기준
                .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of("testBreaker", config);

        for (int i = 0; i < 20; i++) {
            try {
                CircuitBreaker.decorateRunnable(circuitBreaker, UserController::mockRequest).run();
            } catch (Exception e) {
                System.out.println("Request failed: " + e.getMessage());
            }
        }

    }

    // Mock 요청
    private static void mockRequest() {
        if (Math.random() > 0.5) {  // 50% 실패 확률
            throw new RuntimeException("Service failed");
        } else {
            System.out.println("Service succeeded");
        }
    }

}