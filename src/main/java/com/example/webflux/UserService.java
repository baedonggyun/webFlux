package com.example.webflux;


import com.example.webflux.circuitBreaker.CustomCircuitBreaker;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    @CustomCircuitBreaker(name = "sampleCircuitBreaker")
    public Mono<String> testCircuitBreaker() {
        throw new RuntimeException("test");
    }


}