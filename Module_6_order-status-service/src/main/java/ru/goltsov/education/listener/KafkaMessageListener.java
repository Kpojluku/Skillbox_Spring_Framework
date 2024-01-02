package ru.goltsov.education.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.goltsov.education.model.OrderEvent;
import ru.goltsov.education.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final KafkaTemplate<String, OrderStatus> kafkaTemplate;
    @Value("${app.kafka.kafkaMessageTopics[1]}")
    private String orderStatusTopic;


    @KafkaListener(topics = "${app.kafka.kafkaMessageTopics[0]}",
            groupId = "${app.kafka.kafkaMessageGroup}",
            containerFactory = "kafkaMessageListenerContainerFactory")
    public void listen(@Payload OrderEvent orderEvent,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {

        log.info("Received message: {}", orderEvent);
        log.info("Key: {}, partition: {}, topic: {}, timestamp: {}", key, partition, topic, timestamp);

        kafkaTemplate.send(orderStatusTopic, new OrderStatus("CREATED", Instant.now()));
    }

}
