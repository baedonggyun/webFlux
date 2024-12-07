package com.example.webflux;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.github.resilience4j.circuitbreaker.*;
import java.time.Duration;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/test")
    public void test() {

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