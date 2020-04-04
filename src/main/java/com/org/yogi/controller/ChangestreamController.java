package com.org.yogi.controller;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import com.org.yogi.config.ChangeStreamConfig;
import com.org.yogi.constent.ChangeStreamConstent;
import com.org.yogi.service.RabbitMQSender;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ChangestreamController {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ChangeStreamConfig changeStreamConfig;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @EventListener(ContextRefreshedEvent.class)
    public void pushMessageIntoChangeStreamQueue() {
        MongoCollection<Document> employeeDocument = mongoTemplate.getCollection(changeStreamConfig.getCollection());
        Block<ChangeStreamDocument<Document>> employeeHnadlerBlock = (final ChangeStreamDocument<Document> changeStreamDocument) -> {
            Document document = changeStreamDocument.getFullDocument();
            System.out.println("pull the ==" + changeStreamDocument.getOperationType().getValue().toString());
            System.out.println("full document before sending queue" + document);
            rabbitMQSender.send(document);

            System.out.println("full document aftre sending queue" + document);

        };
        employeeDocument.watch(Arrays.asList(Aggregates.match(Filters.in(ChangeStreamConstent.OPERATIONAL_TYPE, Arrays.asList(ChangeStreamConstent.INSERT))))).fullDocument(FullDocument.UPDATE_LOOKUP).forEach(employeeHnadlerBlock);
    }

}

