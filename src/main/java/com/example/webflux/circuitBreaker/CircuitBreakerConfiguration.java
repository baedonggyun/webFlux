package com.example.webflux.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        // 사용자 정의 CircuitBreaker 설정
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // 실패율 임계값 (50%)
                .waitDurationInOpenState(Duration.ofSeconds(30)) // 열린 상태 대기 시간
                .slidingWindowSize(10) // 최근 5번 호출 기준으로 상태 평가
                .minimumNumberOfCalls(5) // 최소 3번 호출 이후 상태 평가
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        return CircuitBreakerRegistry.of(config);
    }
}
