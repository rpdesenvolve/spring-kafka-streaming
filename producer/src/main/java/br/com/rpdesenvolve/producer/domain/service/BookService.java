package br.com.rpdesenvolve.producer.domain.service;

import br.com.rpdesenvolve.producer.domain.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookService implements br.com.rpdesenvolve.producer.port.out.BookService {

    private final String topic;
    private final KafkaTemplate<String, Book> kafkaTemplate;

    public BookService(@Value("${topic.name.producer}") String topic, KafkaTemplate<String, Book> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Book book) {
        try {
            kafkaTemplate.send(topic, book);
            log.info("Message sent to topic {}: {}", topic, book);
        } catch (Exception e) {
            log.error("Error sending message to Kafka: {}", e.getMessage());
            throw e;
        }
    }
}
