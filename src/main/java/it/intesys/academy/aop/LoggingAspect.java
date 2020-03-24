package it.intesys.academy.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("bean(*Dao) || bean(*Controller) || bean(*Service)")
    public void applicationPackagePointcut() {

    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {

        LOGGER.error("Exception in {}.{}() with cause = '{}' and exception = '{}'", joinPoint.getSignature()
            .getDeclaringTypeName(),
                        joinPoint.getSignature()
                            .getName(),
                        e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    }

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint)
        throws Throwable {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature()
                .getDeclaringTypeName(),
                            joinPoint.getSignature()
                                .getName(),
                            Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature()
                    .getDeclaringTypeName(),
                                joinPoint.getSignature()
                                    .getName(),
                                result);
            }
            return result;
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature()
                .getDeclaringTypeName(),
                            joinPoint.getSignature()
                                .getName());

            throw e;
        }
    }
}
