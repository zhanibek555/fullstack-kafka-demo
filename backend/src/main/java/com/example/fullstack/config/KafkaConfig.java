package com.example.fullstack.config;
import org.apache.kafka.common.TopicPartition; import org.springframework.context.annotation.*;
import org.springframework.kafka.core.*; import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter; import org.springframework.util.backoff.FixedBackOff;
@Configuration
public class KafkaConfig {
  @Bean DeadLetterPublishingRecoverer dlt(KafkaTemplate<Object,Object> t){
    return new DeadLetterPublishingRecoverer(t,(r,e)->new TopicPartition(r.topic()+".DLT", r.partition()));
  }
  @Bean DefaultErrorHandler err(DeadLetterPublishingRecoverer dlt){
    return new DefaultErrorHandler(dlt, new FixedBackOff(1000L, 3L));
  }
  @Bean ConcurrentKafkaListenerContainerFactory<String,Object> kafkaListenerContainerFactory(
      ConsumerFactory<String,Object> cf, KafkaTemplate<Object,Object> t, DefaultErrorHandler eh){
    var f=new ConcurrentKafkaListenerContainerFactory<String,Object>();
    f.setConsumerFactory(cf); f.setConcurrency(3); f.setCommonErrorHandler(eh); f.setReplyTemplate(t);
    f.setRecordMessageConverter(new StringJsonMessageConverter()); return f;
  }
}
