package org.nightingaale.userservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.nightingaale.userservice.event.KafkaUserUpdateRequestEvent;
import org.nightingaale.userservice.event.consumer.KafkaUserRegistrationEvent;
import org.nightingaale.userservice.event.consumer.KafkaUserRemoveEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, KafkaUserRegistrationEvent> consumerUserRegistrationFactory() {
        Map<String, Object> consumerConfig = new HashMap<>();

        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        consumerConfig.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        consumerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaUserRegistrationEvent.class.getName());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(consumerConfig);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaUserRegistrationEvent>> kafkaListenerContainerFactoryUserRegistration(
            ConsumerFactory<String, KafkaUserRegistrationEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaUserRegistrationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaUserRemoveEvent> consumerUserRemoveFactory() {
        Map<String, Object> consumerConfig = new HashMap<>();

        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        consumerConfig.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        consumerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaUserRemoveEvent.class.getName());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(consumerConfig);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaUserRemoveEvent>> kafkaListenerContainerFactoryUserRemove(
            ConsumerFactory<String, KafkaUserRemoveEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaUserRemoveEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaUserUpdateRequestEvent> consumerUserUpdatedFactory() {
        Map<String, Object> consumerConfig = new HashMap<>();

        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        consumerConfig.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        consumerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaUserUpdateRequestEvent.class.getName());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(consumerConfig);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaUserUpdateRequestEvent>> kafkaListenerContainerFactoryUserUpdated(
            ConsumerFactory<String, KafkaUserUpdateRequestEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaUserUpdateRequestEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}