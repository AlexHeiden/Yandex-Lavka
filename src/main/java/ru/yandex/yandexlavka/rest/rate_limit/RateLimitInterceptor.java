package ru.yandex.yandexlavka.rest.rate_limit;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.rest.exception_handling.exception.TooManyRequestsException;

import java.time.Duration;

@Component
@Aspect
public class RateLimitInterceptor {

    private final RateLimiterRegistry rateLimiterRegistry;

    @Autowired
    public RateLimitInterceptor(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    @Before("@annotation(rateLimited)")
    public void applyRateLimit(JoinPoint joinPoint, RateLimited rateLimited) {
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(joinPoint.toShortString(), RateLimiterConfig.custom()
                        .limitForPeriod(rateLimited.value())
                        .limitRefreshPeriod(Duration.ofSeconds(1))
                        .timeoutDuration(Duration.ofSeconds(0))
                        .build());

        if (!rateLimiter.acquirePermission()) {
            throw new TooManyRequestsException();
        }
    }
}
