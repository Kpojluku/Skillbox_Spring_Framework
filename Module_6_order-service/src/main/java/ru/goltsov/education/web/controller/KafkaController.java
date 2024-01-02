package ru.goltsov.education.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.goltsov.education.model.OrderEvent;
import ru.goltsov.education.web.model.Order;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaMessageTopics[0]}")
    private String orderTopic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public String sendMessage(@RequestBody Order order) {
        OrderEvent orderEvent = OrderEvent.builder()
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .build();

        kafkaTemplate.send(orderTopic, orderEvent);
        return "Message sent to kafka";
    }

}
