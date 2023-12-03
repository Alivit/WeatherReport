package ru.alivit.weatherReport.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {


    @Around(value = "ru.alivit.weatherReport.logging.pointcut.CustomPointcut.isControllerLayer()")
    public Object aroundAllControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String nameMethod = signature.getName();
        Object[] args = joinPoint.getArgs();
        log.info("Controller request: Method - {} accepted parameters: {}", nameMethod, args);

        try {
            Object result = joinPoint.proceed(args);
            log.info("Controller response: Method - {} return parameters: {}", nameMethod,  result.toString());

            return result;
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
            throw e;
        }

    }


    @Around(value = "ru.alivit.weatherReport.logging.pointcut.CustomPointcut.isServiceLayer()")
    public Object aroundAllService(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String nameMethod = methodSignature.getName();
        String nameClass = joinPoint.getSourceLocation().getWithinType().getSimpleName();
        Object[] args = joinPoint.getArgs();

        log.info("Class {}: method {} accepted parameters - {}",nameClass, nameMethod, args);
        Object result = joinPoint.proceed(args);
        log.info("Class {}: method - {} returned parameters - {}",nameClass, nameMethod, result);

        return result;
    }

}
