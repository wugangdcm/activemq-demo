package com.wdcm.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMqQueueProducer {
    private static final String CONNECTION_URL = "tcp://192.168.1.101:61616";
    private static final String QUEUE_NAME = "queue_demo";

    public static void main(String[] args) throws JMSException {
        //创建一个连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(CONNECTION_URL);
        //获取一个连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //创建一个会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //声明消息传递类型为队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        for (int i = 0; i < 3; i++) {
            //具体消息以及发送
            TextMessage textMessage = session.createTextMessage("queue msg " + i);
            messageProducer.send(textMessage);
        }
        //关闭连接
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("生产消息结束...");
    }
}
