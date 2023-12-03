package ru.alivit.weatherReport.logging.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomPointcut {

    @Pointcut("(@within(org.springframework.web.bind.annotation.RestController) " +
            "|| @within(org.springframework.stereotype.Controller))" +
            "&& @within(ru.alivit.weatherReport.logging.annotation.Logging)")
    public void isControllerLayer(){}

    @Pointcut("@within(org.springframework.stereotype.Service)" +
    "&& @within(ru.alivit.weatherReport.logging.annotation.Logging)")
    public void isServiceLayer(){}

}
