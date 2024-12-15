package com.example.webflux.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Aspect
@Component
public class ReactiveCircuitBreakerAspect {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public ReactiveCircuitBreakerAspect(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Around("@annotation(io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker) && @annotation(circuitBreakerAnnotation)")
    public Object applyCircuitBreaker(ProceedingJoinPoint joinPoint, io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker circuitBreakerAnnotation) throws Throwable {
        String circuitBreakerName = circuitBreakerAnnotation.name();
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName);

        Object result = joinPoint.proceed();

        if (result instanceof Mono) {
            return ((Mono<?>) result).transform(CircuitBreakerOperator.of(circuitBreaker));
        } else if (result instanceof Flux) {
            return ((Flux<?>) result).transform(CircuitBreakerOperator.of(circuitBreaker));
        }

        throw new IllegalArgumentException("Unsupported return type: " + result.getClass().getName());
    }
}