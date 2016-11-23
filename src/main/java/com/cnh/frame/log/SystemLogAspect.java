package com.cnh.frame.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import com.cnh.frame.log.service.ISystemLogService;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志记录切点类
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/9
 */
@Aspect
@Service
public class SystemLogAspect implements ApplicationContextAware {

    private final static Logger LOGGER = LoggerFactory.getLogger(SystemLogAspect.class);

    @Resource
    private List<ISystemLogService> systemLogServiceList;

    /**
     * 用户操作切点定义
     */
    @Pointcut("@annotation(com.cnh.frame.log.annotation.SystemLog)")
    public void userAspect () {}

    /**
     * 前置通知，用于拦截用户操作记录日志
     * @param joinPoint
     */
    @Before("userAspect()")
    public void before (JoinPoint joinPoint) {
        try {
            this.callService(joinPoint, getSystemLog(joinPoint));
        } catch (Exception e) {
            LOGGER.error("write system log error: {}", e.getMessage());
        }
    }


    /**
     * 将切点解析为SystemLog对象
     * @param joinPoint
     * @return
     */
    public static SystemLog getSystemLog (JoinPoint joinPoint) {
        SystemLog systemLog = new SystemLog();
        systemLog.setClassName(joinPoint.getTarget().getClass().getName());
        systemLog.setMethodName(joinPoint.getSignature().getName());
        systemLog.setArgs(joinPoint.getArgs());
        try {
            systemLog.setTargetClass(Class.forName(systemLog.getClassName()));
            systemLog.setTargetMethods(systemLog.getTargetClass().getMethods());
            for (Method method: systemLog.getTargetMethods()) {
                if (method.getName().equals(systemLog.getMethodName())) {
                    Class[] clazz = method.getParameterTypes();
                    if (clazz.length == systemLog.getArgs().length) {
                        com.cnh.frame.log.annotation.SystemLog systemLogAnnotation = method.getAnnotation(com.cnh.frame.log.annotation.SystemLog.class);
                        systemLog.setSystemLogAnnotation(systemLogAnnotation);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get target class error: {}", e.getMessage());
        }
        return systemLog;
    }

    private void callService (JoinPoint joinPoint, SystemLog systemLog) {
        if (systemLogServiceList != null) {
            for(ISystemLogService systemLogService: systemLogServiceList) {
                try {
                    systemLogService.write(joinPoint, systemLog);
                } catch (Exception e) {
                    LOGGER.error("callService error: {}", e.getMessage());
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.systemLogServiceList = new ArrayList<ISystemLogService>(applicationContext.getBeansOfType(ISystemLogService.class).values());
    }
}
