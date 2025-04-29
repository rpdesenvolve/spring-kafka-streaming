package br.com.rpdesenvolve.producer.adapter.configuration;

import br.com.rpdesenvolve.producer.domain.model.Book;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class kafkaconfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private final String bootstrapAddress;
    @Value("${topic.name.producer}")
    private final String topic;

    public kafkaconfig(@Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress,
                       @Value("${topic.name.producer}") String topic) {
        this.bootstrapAddress = bootstrapAddress;
        this.topic = topic;
    }

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topic, 3, (short) 1);
    }

    @Bean
    public ProducerFactory<String, Book> bookProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Book> bookKafkaTemplate() {
        return new KafkaTemplate<>(bookProducerFactory());
    }
}
