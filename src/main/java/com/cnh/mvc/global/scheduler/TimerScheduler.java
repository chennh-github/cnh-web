package com.cnh.mvc.global.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
//@Component
//@EnableScheduling
public class TimerScheduler {


    /**
     * 每五秒钟执行一次
     */
    @Scheduled(cron = "*/5 * *  * * * ")
    public void report () {
        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }

}
