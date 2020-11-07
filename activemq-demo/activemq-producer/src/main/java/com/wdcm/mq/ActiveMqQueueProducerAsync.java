package com.wdcm.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

public class ActiveMqQueueProducerAsync {
    private static final String CONNECTION_URL = "tcp://192.168.1.101:61616?jms.useAsyncSend=true";
    private static final String QUEUE_NAME = "queue_demo";

    public static void main(String[] args) throws JMSException {
        //创建一个连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(CONNECTION_URL);
//        connectionFactory.setUseAsyncSend(true); //开启异步投递消息
        //获取一个连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //创建一个会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //声明消息传递类型为队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息的生产者
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        for (int i = 0; i < 3; i++) {
            //具体消息以及发送
            TextMessage textMessage = session.createTextMessage("queue msg " + i);
            final String msgId = "每个消息的唯一ID" + UUID.randomUUID().toString();
            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                public void onSuccess() {
                    System.out.println("发送成功的消息id"+msgId);
                }

                public void onException(JMSException e) {
                    System.out.println("发送失败的消息id"+msgId);
                }
            });
        }
        //关闭连接
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("生产消息结束...");
    }
}
