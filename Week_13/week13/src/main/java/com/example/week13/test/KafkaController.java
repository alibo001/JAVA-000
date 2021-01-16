package com.example.week13.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: alibobo
 * @Description:
 **/

@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> template;

    // 生产
    @RequestMapping("send")
    public void send(String msg) {
        for (int i = 0; i < 5; i++) {
            template.send("testk", "hello"+msg+ i);
        }
    }

    // 消费
    @KafkaListener(groupId = "group1",topics = {"testk"})
    public void listen(ConsumerRecord<String, ?> record) {
        System.out.println(record.value());
    }

}
