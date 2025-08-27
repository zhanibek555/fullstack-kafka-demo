package com.example.fullstack.config;
import org.apache.kafka.clients.admin.NewTopic; import org.springframework.context.annotation.*;
@Configuration public class TopicConfig {
  @Bean public NewTopic ordersCreated(){ return new NewTopic("orders.created",3,(short)1); }
  @Bean public NewTopic ordersCreatedDLT(){ return new NewTopic("orders.created.DLT",3,(short)1); }
}
