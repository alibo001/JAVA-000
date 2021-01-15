package com.example.week13.test;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;

/**
 * @Author: alibobo
 * @Description:
 **/
@RestController
public class JmsController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private ActiveMQQueue queue;
    @Autowired
    private ActiveMQTopic topic;

    @RequestMapping("sendToQueue")
    public void sendToQueue(String msg) {
        send(queue, createTextMessage(msg));
    }

    @RequestMapping("sendToTopic")
    public void sendToTopic(String msg) {
        send(topic,createTextMessage(msg));
    }

    // 创建文件型消息
    private ActiveMQTextMessage createTextMessage(String text) {
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        try {
            message.setText(text);
        } catch (MessageNotWriteableException e) {
            e.printStackTrace();
        }
        return message;
    }
    // 发送
    private void send(Destination destination, Message message) {
        jmsMessagingTemplate.convertAndSend(destination,message);
    }


    //queue模式的消费者
    @JmsListener(destination="test.queue")
    public void readActiveQueue(String message) {
        System.out.println("queue接受到：" + message);
    }

    //topic模式的消费者 (需要指定一个开启PubSubDomain的factory)
    @JmsListener(destination="test.topic",containerFactory = "jmsListenerContainerTopic")
    public void readActiveTopic1(String message) {
        System.out.println("topic1接受到：" + message);
    }
    @JmsListener(destination="test.topic",containerFactory = "jmsListenerContainerTopic")
    public void readActiveTopic2(String message) {
        System.out.println("topic2接受到：" + message);
    }


    //======= main ===========

    /**
     * main 方式
     * @param args
     */
    public static void main(String[] args) {
        ActiveMQQueue queue = new ActiveMQQueue("test.alibo");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        try {
            ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(message -> {
                System.out.println("收到消息"+message.toString());
            });

            //===== 生产消息===
            MessageProducer producer = session.createProducer(queue);
            for (int i = 0; i < 6; i++) {
                TextMessage textMessage = session.createTextMessage("hello mq" + i);
                producer.send(textMessage);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
