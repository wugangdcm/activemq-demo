package com.wdcm.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class ActiveMqQueueConsumer {
    private static final String CONNECTION_URL = "tcp://192.168.1.101:61616";
    private static final String QUEUE_NAME = "queue_demo";

    public static void main(String[] args) throws JMSException, IOException {
        //创建一个连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(CONNECTION_URL);
        //获取一个连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //创建一个会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //声明消息传递类型为队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

//        while (true) {
//            //获取具体消息
//            TextMessage message = (TextMessage) messageConsumer.receive();//receive(3*1000) 等待3秒没消息就退出
//            if (message != null) {
//                System.out.println("consumer queue msg " + message.getText());
//            } else {
//                break;
//            }
//        }
        //通过监听方式
        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("consumer queue msg " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();

        //关闭连接
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("消费消息结束...");
    }
}
