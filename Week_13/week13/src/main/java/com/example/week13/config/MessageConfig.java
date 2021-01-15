package com.example.week13.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

/**
 * @Author: alibobo
 * @Description:
 **/
@Configuration
public class MessageConfig {

    // 配置信息
    @Bean
    public ActiveMQProperties properties() {
        return new ActiveMQProperties();
    }

    // ConnectionFactory
    @Bean
    public ActiveMQConnectionFactory factory(ActiveMQProperties properties) {
        return new ActiveMQConnectionFactory(properties.getUser(), properties.getPassword(), properties.getBrokerUrl());
    }
     // 开启pub/sub模式的ListenerContainer
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }

    @Bean
    public ActiveMQQueue queue() {
        return new ActiveMQQueue("test.queue");
    }

    @Bean
    public ActiveMQTopic topic() {
        return new ActiveMQTopic("test.topic");
    }

}
