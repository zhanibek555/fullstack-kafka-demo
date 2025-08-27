package com.example.fullstack.messaging;
import com.example.fullstack.events.OrderCreatedEvent; import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate; import org.springframework.stereotype.Component;
@Component @RequiredArgsConstructor
public class OrderProducer {
  private final KafkaTemplate<String,Object> template;
  public void send(OrderCreatedEvent e){ template.send("orders.created", e.getOrderId(), e); }
}
