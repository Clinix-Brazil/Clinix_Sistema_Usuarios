package clinix.com.clinix_sistema_usuarios.config;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String QUEUE_NAME = "cadastro.queue";
    private static final String EXCHANGE_NAME = "cadastro.exchange";
    private static final String ROUTING_KEY = "cadastro.novo";

    @Bean
    public Queue cadastroQueue() {
        return new Queue(QUEUE_NAME, true); // 'true' torna a fila durável
    }

    @Bean
    public DirectExchange cadastroExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue cadastroQueue, DirectExchange cadastroExchange) {
        return BindingBuilder.bind(cadastroQueue).to(cadastroExchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    public void enviarMensagemCadastro(RabbitTemplate rabbitTemplate, Paciente paciente) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, paciente);
    }
}