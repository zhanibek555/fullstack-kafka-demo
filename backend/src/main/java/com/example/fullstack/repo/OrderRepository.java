package com.example.fullstack.repo;
import com.example.fullstack.domain.OrderEntity; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  Optional<OrderEntity> findByOrderId(String orderId);
}
