package com.example.fullstack.messaging;
import lombok.extern.slf4j.Slf4j; import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener; import org.springframework.messaging.handler.annotation.Headers; import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets; import java.util.Map;
@Slf4j @Component
public class DltListener {
  @KafkaListener(topics="orders.created.DLT", groupId="dlt-inspector")
  public void on(ConsumerRecord<String,Object> r, @Headers Map<String,Object> h){
    log.warn("\uD83D\uDEE7 DLT key={}, value={}, causeClass={}, causeMsg={}, topic={}, part={}, offset={}",
      r.key(), r.value(), S(h.get("kafka_dlt-exception-fqcn")), S(h.get("kafka_dlt-exception-message")),
      S(h.get("kafka_dlt-original-topic")), S(h.get("kafka_dlt-original-partition")), S(h.get("kafka_dlt-original-offset")));
  }
  private String S(Object v){ if(v==null) return null; if(v instanceof byte[] b) return new String(b, StandardCharsets.UTF_8); return v.toString(); }
}
