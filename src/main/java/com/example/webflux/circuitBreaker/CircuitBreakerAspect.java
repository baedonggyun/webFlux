package com.example.webflux.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Component("customCircuitBreakerAspect")
@RequiredArgsConstructor
public class CircuitBreakerAspect {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Around("@annotation(circuitBreakerAnnotation)")
    public Object handleCircuitBreaker(ProceedingJoinPoint joinPoint, CustomCircuitBreaker circuitBreakerAnnotation) throws Throwable {
        // 애노테이션에서 서킷 브레이커 이름 가져오기
        String circuitBreakerName = circuitBreakerAnnotation.name();
        io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName);

        System.out.println("Circuit breaker state: " + circuitBreaker.getState());

        return Mono.defer(() -> {
            try {
                return (Mono<?>) joinPoint.proceed();
            } catch (Throwable throwable) {
                return Mono.error(throwable);
            }
        }).transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
    }
}
