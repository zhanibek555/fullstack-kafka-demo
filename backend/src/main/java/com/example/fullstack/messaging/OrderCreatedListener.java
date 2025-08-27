package com.example.fullstack.messaging;
import com.example.fullstack.domain.OrderEntity; import com.example.fullstack.events.OrderCreatedEvent;
import com.example.fullstack.repo.OrderRepository; import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener; import org.springframework.messaging.handler.annotation.Payload; import org.springframework.stereotype.Component;
@Slf4j @Component @RequiredArgsConstructor
public class OrderCreatedListener {
  private final OrderRepository repo;
  @KafkaListener(topics="orders.created", groupId="demo-group", containerFactory="kafkaListenerContainerFactory")
  public void on(@Payload OrderCreatedEvent e){
    if(e.getOrderId()!=null && e.getOrderId().startsWith("ERR-"))
      throw new IllegalArgumentException("Forced failure for testing DLT. orderId="+e.getOrderId());
    repo.findByOrderId(e.getOrderId()).orElseGet(() -> repo.save(OrderEntity.builder()
      .orderId(e.getOrderId()).amount(e.getAmount()).currency(e.getCurrency()).status("CREATED").build()));
    log.info("\uD83D\uDCE5 Received and saved: {}", e);
  }
}
