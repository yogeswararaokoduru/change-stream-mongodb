package com.org.yogi.service;

import com.org.yogi.config.RabbitMQConfig;
import com.org.yogi.model.Employee;
import org.bson.Document;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * created by yogi
 * */
@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;


    public void send(Document document) {
        amqpTemplate.convertAndSend(rabbitMQConfig.getExchange(), rabbitMQConfig.getRoutingkey(), document);
        System.out.println("Send msg = " + document);

    }
}