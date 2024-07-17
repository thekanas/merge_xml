package com.vpolosov.trainee.mergexml.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * {@code LogAspect} is aspect responsible for logging the operation of the methods.
 *
 * @author Ali Takushinov
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * Advice for logging the start of the method marked with the annotation {@code @Loggable"}.
     *
     * @param joinPoint an object that allows you to access information about the method
     */
    @Before("@annotation(com.vpolosov.trainee.mergexml.aspect.Loggable)")
    public void logMethodBefore(JoinPoint joinPoint) {
        String methodName = getMethodName(joinPoint);
        log.info("Start method {}", methodName);
    }

    /**
     * Возвращает имя метода.
     *
     * @param joinPoint рефлексивный объект аспекта.
     * @return имя метода.
     */
    private static String getMethodName(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().toString();
    }

    /**
     * Advice for logging the finish of the method marked with the annotation {@code @Loggable"}.
     *
     * @param joinPoint      an object that allows you to access information about the method
     * @param returnedObject a return value of the business logic method
     */
    @AfterReturning(
        pointcut = "@annotation(com.vpolosov.trainee.mergexml.aspect.Loggable)",
        returning = "returnedObject")
    public void logMethodAfterReturning(JoinPoint joinPoint, Object returnedObject) {
        String methodName = getMethodName(joinPoint);
        if (returnedObject == null) {
            log.info("Correct finish method" + methodName);
        } else {
            log.info("Correct finish method " + methodName + ". Returning values: " + returnedObject);
        }
    }

    /**
     * Advice for logging the throw exception to the method marked with the annotation {@code @Loggable"}.
     *
     * @param joinPoint an object that allows you to access information about the method
     * @param exception a thrown exception to the business logic method
     */
    @AfterThrowing(pointcut = "@annotation(com.vpolosov.trainee.mergexml.aspect.Loggable)", throwing = "exception")
    public void logMethodAfterReturning(JoinPoint joinPoint, Throwable exception) {
        String methodName = getMethodName(joinPoint);
        log.warn("Incorrect finish method " + methodName + ". Exception message: " + exception.getMessage());
    }
}