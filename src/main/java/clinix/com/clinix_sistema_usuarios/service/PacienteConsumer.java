package clinix.com.clinix_sistema_usuarios.service;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Consumidor - fila - mensageria
@Service
public class PacienteConsumer {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteConsumer(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @RabbitListener(queues = "fila_pacientes")
    public void consumirPacienteDaFila(Paciente paciente) {
        System.out.println("📥 Mensagem recebida na fila: " + paciente);

        pacienteService.salvar(paciente);

        System.out.println("✅ Paciente cadastrado no banco: " + paciente.getNome());
    }
}