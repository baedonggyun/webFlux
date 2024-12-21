package com.example.webflux.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component()
@Aspect
public class CustomCircuitBreakerAspect {

    private final CircuitBreakerRegistry registry;

    public CustomCircuitBreakerAspect(CircuitBreakerRegistry registry) {
        this.registry = registry;
    }

    @Around("@annotation(CircuitBreakerEnabled)")
    public Object applyCircuitBreaker(ProceedingJoinPoint joinPoint) throws Throwable {

        // 어노테이션 정보 가져오기
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CircuitBreakerEnabled annotation = signature.getMethod().getAnnotation(CircuitBreakerEnabled.class);

        // 어노테이션에서 name 가져오기
        String circuitBreakerName = annotation.name();

        // 서킷 브레이커 이름 생성
        CircuitBreaker circuitBreaker = registry.circuitBreaker(circuitBreakerName);

        // recordExceptions 가져오기
        Class<? extends Throwable>[] recordExceptions = annotation.recordExceptions();

        System.out.println("circuitBreaker.getName() : " + circuitBreaker.getName());
        System.out.println("circuitBreaker.getState() : " + circuitBreaker.getState());

        // 서킷 브레이커를 통해 메서드 실행
        return circuitBreaker.executeSupplier(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                // 처리할 예외인지 확인
                for (Class<? extends Throwable> exceptionClass : recordExceptions) {
                    if (exceptionClass.isInstance(throwable)) {
                        throw new RuntimeException("CircuitBreaker recorded exception", throwable);
                    }
                }
                // 처리하지 않을 예외는 다시 던짐
                try {
                    throw throwable;
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}