package com.cnh.frame.log.service;

import com.cnh.frame.log.SystemLog;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/9
 */
@Service
public class DefaultSystemLogService implements ISystemLogService {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultSystemLogService.class);

    @Override
    public void write(JoinPoint joinPoint, SystemLog systemLog) {
        LOGGER.debug("{}", systemLog);
    }
}
