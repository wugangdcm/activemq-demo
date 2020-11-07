package com.wdcm.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

public class ActiveMqQueueProducerDelay {
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
        //延迟投递的时间
        long AMQ_scheduled_delay = 3 * 1000;
        //重复投递的时间间隔
        long AMQ_scheduled_period = 4 * 1000;
        //重复投递次数
        int AMQ_scheduled_repeat = 3;

        for (int i = 0; i < 3; i++) {
            //具体消息以及发送
            TextMessage textMessage = session.createTextMessage("queue msg " + i);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, AMQ_scheduled_delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, AMQ_scheduled_period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, AMQ_scheduled_repeat);
            messageProducer.send(textMessage);
        }
        //关闭连接
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("生产消息结束...");
    }
}
