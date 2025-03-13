package clinix.com.clinix_sistema_usuarios.service;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteConsumer {

    @Autowired
    private PacienteService pacienteService;
    @RabbitListener(queues = "cadastro.queue")
    public Paciente processarCadastro(Paciente paciente) {
        System.out.println("Processando cadastro: " + paciente.getNome());
        return this.pacienteService.salvar(paciente);
    }
}