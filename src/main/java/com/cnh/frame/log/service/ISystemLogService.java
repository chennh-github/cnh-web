package com.cnh.frame.log.service;

import com.cnh.frame.log.SystemLog;
import org.aspectj.lang.JoinPoint;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/9
 */
public interface ISystemLogService {

    /**
     * 记录系统日志
     * @param joinPoint
     * @param systemLog
     */
    public void write (JoinPoint joinPoint, SystemLog systemLog);

}
