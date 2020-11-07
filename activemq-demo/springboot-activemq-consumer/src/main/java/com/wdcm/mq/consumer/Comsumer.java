package com.wdcm.mq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Comsumer {
    @JmsListener(destination = "${myqueue}")
    public void receiveQueue(TextMessage textMessage) throws JMSException {
        System.out.println("接收到队列消息=" + textMessage.getText());
    }

    @JmsListener(destination = "${mytopic}")
    public void receiveTopic(TextMessage textMessage) throws JMSException {
        System.out.println("接收到Topic消息=" + textMessage.getText());
    }
}
