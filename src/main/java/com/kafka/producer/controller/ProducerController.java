package com.kafka.producer.controller;

import com.kafka.producer.model.User;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerController {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private KafkaTemplate<String,User> kafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topicName;

    public String getTopicName() {
        return this.topicName;
    }

    @PostMapping(value = "/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public void userPost(@RequestBody User user1, HttpServletRequest request, HttpServletResponse response) {

        kafkaTemplate.send(getTopicName(), user1);

        LOG.info("sending data ='{}' to topic ='{}'," + getTopicName(), user1.toString());

    }

}
