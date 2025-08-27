package com.example.fullstack.web;
import com.example.fullstack.domain.OrderEntity; import com.example.fullstack.events.OrderCreatedEvent;
import com.example.fullstack.messaging.OrderProducer; import com.example.fullstack.repo.OrderRepository;
import jakarta.validation.constraints.*; import lombok.Data; import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequiredArgsConstructor @RequestMapping("/api/orders")
public class OrdersController {
  private final OrderProducer producer; private final OrderRepository repo;
  @PostMapping public ResponseEntity<Void> create(@RequestBody CreateOrderRequest r){
    producer.send(new OrderCreatedEvent(r.getOrderId(), r.getAmount(), r.getCurrency())); return ResponseEntity.accepted().build();
  }
  @GetMapping public List<OrderEntity> list(){ return repo.findAll(); }
  @Data public static class CreateOrderRequest {
    @NotBlank private String orderId; @Positive private long amount; @NotBlank private String currency;
  }
}
