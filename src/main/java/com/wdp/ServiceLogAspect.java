package com.wdp;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author wdp
 * @date 2025/3/26
 */

@Slf4j
@Component
@Aspect
public class ServiceLogAspect {

    // 切面表达式：
    // * 返回任意类型，比如void,object,list等
    // com.wdp.service.impl 指定包名，要去具体切入切面的位置(某个Java class所在包的位置)
    // .. 可以匹配到当前包以及它的下属子包
    @Around("execution(* com.wdp.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        String point = joinPoint.getTarget().getClass().getName() + "." +
                joinPoint.getSignature().getName();
        stopWatch.stop();
        log.info("执行方法：{}", point);
        log.info(stopWatch.prettyPrint());
        log.info(stopWatch.shortSummary());

        // 任何信息总览
        log.info("所有任务总耗时：{}",stopWatch.getTotalTimeMillis());
        log.info("任务总数：{}",stopWatch.getTaskCount());
        return result;
    }
}
