package com.qinhao.cloudeureka.listen;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.stereotype.Component;

/**
 * 监听服务
 *
 * @author Qh
 * @version 1.0
 * @date 2021/12/1 14:50
 */
@Component
public class EventListen {

    public void listen(EurekaInstanceCanceledEvent event){
        // 模拟发邮件或短信
        System.out.println("服务下线: " + event.getServerId());
    }

}
