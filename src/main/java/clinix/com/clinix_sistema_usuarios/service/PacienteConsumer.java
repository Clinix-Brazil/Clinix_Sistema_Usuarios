package clinix.com.clinix_sistema_usuarios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;

//Consumidor - fila - mensageria
@Service
public class PacienteConsumer {

    private final PacienteService pacienteService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public PacienteConsumer(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumirPacienteDaFila(Paciente paciente) {
        System.out.println("📥 Mensagem recebida da fila: " + paciente);
        pacienteService.salvar(paciente);
        System.out.println("✅ Paciente cadastrado no banco: " + paciente.getNome());
    }
}