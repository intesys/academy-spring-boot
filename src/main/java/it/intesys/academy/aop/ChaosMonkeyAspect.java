package it.intesys.academy.aop;

import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Profile("chaosmonkey")
public class ChaosMonkeyAspect {

    private final Random random;

    public ChaosMonkeyAspect() {

        random = new Random();
    }

    @Around("execution(int it.intesys.academy..*(..))")
    public int messUp(ProceedingJoinPoint pjp)
        throws Throwable {

        int returnValue = (int) pjp.proceed();
        if (random.nextDouble() >= 0.8) {
            returnValue = random.nextInt();
        }
        return returnValue;
    }

    @AfterReturning("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public void throwRandomException() {
        if (random.nextDouble() >= 0.6) {
            throw new RuntimeException("The monkey was here!");
        }
    }
}
