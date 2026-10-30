package com.example.parking.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    @KafkaListener(
            topics = "parking-topic",
            groupId = "parking-group"
    )
    public void consumeMessage(String message) {
        System.out.println("Message received: " + message);
    }
}
