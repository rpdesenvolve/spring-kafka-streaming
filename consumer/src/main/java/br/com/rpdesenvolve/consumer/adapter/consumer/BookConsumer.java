package br.com.rpdesenvolve.consumer.adapter.consumer;

import br.com.rpdesenvolve.consumer.domain.model.Book;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {

    private static final Logger log = LoggerFactory.getLogger(BookConsumer.class);

    @Value(value = "${spring.kafka.consumer.group-id}")
    private final String groupId;

    @Value(value = "${topic.name.consumer}")
    private final String topic;

    public BookConsumer(@Value("${spring.kafka.consumer.group-id}") String groupId,
                        @Value("${topic.name.consumer}") String topic) {
        this.groupId = groupId;
        this.topic = topic;
    }

    @KafkaListener(
            topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, Book> record) {
        log.info("Received message Partition: {}", record.partition());
        log.info("Received message Json: {}", record.value());
    }
}
