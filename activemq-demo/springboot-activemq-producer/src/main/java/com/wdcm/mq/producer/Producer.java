package com.wdcm.mq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

@Component
public class Producer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;

    public void produceQueueMsg() {
        System.out.println("开始发送队列消息。。。");
        jmsMessagingTemplate.convertAndSend(queue, "boot queue msg");
    }


    public void produceTopicMsg() {
        System.out.println("开始发送Topic消息。。。");
        jmsMessagingTemplate.convertAndSend(topic, "boot topic msg");
    }

    /**
     * 定时发送
     */
    @Scheduled(fixedDelay = 3000)
    public void scheduledTopicMsg() {
        System.out.println("开始发送topic消息scheduled。。。");
        jmsMessagingTemplate.convertAndSend(topic, "scheduled topic msg");
    }

    /**
     * 定时发送
     */
    @Scheduled(fixedDelay = 5000)
    public void scheduledQueueMsg() {
        System.out.println("开始发送queue消息scheduled。。。");
        jmsMessagingTemplate.convertAndSend(queue, "scheduled queue msg");
    }
}
