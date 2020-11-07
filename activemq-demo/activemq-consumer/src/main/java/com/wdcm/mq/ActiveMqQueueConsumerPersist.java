package com.wdcm.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * topic 发布订阅模式 先订阅在接收
 */
public class ActiveMqQueueConsumerPersist {
    private static final String CONNECTION_URL = "tcp://192.168.1.101:61616";
    private static final String QUEUE_NAME = "topic_persist_demo";

    public static void main(String[] args) throws JMSException, IOException {
        //创建一个连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(CONNECTION_URL);
        //获取一个连接
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("kka");
        //创建一个会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //声明消息传递类型为topic
        Topic topic = session.createTopic(QUEUE_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "goods");
        connection.start();
        Message message = topicSubscriber.receive();
        while (message != null) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("persist msg " + textMessage.getText());
            message = topicSubscriber.receive();
        }
        //关闭连接
        session.close();
        connection.close();
        System.out.println("消费消息结束...");
    }
}
