package com.qinhao.cloudeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Timer;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-11-04 22:58
 */
@SpringBootApplication
@EnableEurekaServer
public class CloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaApplication.class, args);

        /*
        多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，
        其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题
         */
        //Timer timer = new Timer();

    }

}
