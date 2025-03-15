package clinix.com.clinix_sistema_usuarios.service;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import clinix.com.clinix_sistema_usuarios.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PacienteProducer {
/*
    private final RabbitTemplate rabbitTemplate;

    public PacienteProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPacienteParaFila(Paciente paciente) {
        System.out.println("📤 Enviando paciente para a fila: " + paciente);
        rabbitTemplate.convertAndSend(RabbitMQConfig.PACIENTE_QUEUE, paciente);
    }

 */
}