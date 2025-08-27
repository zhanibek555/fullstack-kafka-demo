package com.example.fullstack.events;
import lombok.*; @Data @NoArgsConstructor @AllArgsConstructor
public class OrderCreatedEvent { private String orderId; private long amount; private String currency; }
