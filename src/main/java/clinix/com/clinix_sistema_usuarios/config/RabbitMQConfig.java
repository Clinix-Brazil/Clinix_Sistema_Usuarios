package clinix.com.clinix_sistema_usuarios.config;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import clinix.com.clinix_sistema_usuarios.service.PacienteService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    PacienteService pacienteService;
    @RabbitListener(queues = "fila_pacientes")
    public void consumirMensagem(Paciente paciente) {
        System.out.println("📥 Consumindo paciente: " + paciente);
        pacienteService.salvar(paciente);
    }

    @Bean
    public Queue filaPacientes() {
        return new Queue("fila_pacientes", true);
    }
}