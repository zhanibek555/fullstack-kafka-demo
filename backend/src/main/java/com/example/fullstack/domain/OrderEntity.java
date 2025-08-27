package com.example.fullstack.domain;
import jakarta.persistence.*; import lombok.*; import java.time.OffsetDateTime;
@Entity @Table(name="orders") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderEntity {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true) private String orderId;
  private long amount; private String currency;
  @Builder.Default private String status="CREATED";
  @Builder.Default private OffsetDateTime createdAt=OffsetDateTime.now();
}
