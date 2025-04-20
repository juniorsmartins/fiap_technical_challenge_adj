package br.com.fiap.tech.challenge_user.adapter.kafka.producer;

import br.com.fiap.tech.challenge_user.adapter.dto.response.UsuarioDtoResponse;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioEventProducerOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.random.RandomGenerator;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioEventProducer implements UsuarioEventProducerOutputPort {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Override
    public UsuarioDtoResponse sendUsuario(@NonNull final UsuarioDtoResponse usuarioDtoResponse) {

        var key = RandomGenerator.getDefault().nextInt();
        var usuarioEvent = new UsuarioEvent(key, usuarioDtoResponse);

        try {
            var value = objectMapper.writeValueAsString(usuarioEvent);

            kafkaTemplate.send(topic, key, value)
                    .whenComplete((sendResult, throwable) -> {
                        if (throwable != null) {
                            handleFailure(key, value, throwable);
                        } else {
                            handleSuccess(key, value, sendResult);
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.info("Erro ao converter objeto em string: {}", ex.getMessage(), ex);
            // lançar exceção e Tratar globalmente
        }

        return usuarioDtoResponse;
    }

    private void handleFailure(int key, String value, Throwable throwable) {
        log.info("Erro ao enviar mensagem via kafka - key {}, value {} e exception {}", key, value, throwable.getMessage(), throwable);
    }

    private void handleSuccess(int key, String value, SendResult<Integer, String> sendResult) {
        log.info("Mensagem enviada com sucesso - key {}, value {} e resultado {}", key, value, sendResult);
    }
}

