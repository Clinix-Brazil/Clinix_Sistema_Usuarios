package clinix.com.clinix_sistema_usuarios.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import clinix.com.clinix_sistema_usuarios.dto.MedicoRmiDTO;
import clinix.com.clinix_sistema_usuarios.dto.PacienteRmiDTO;
import clinix.com.clinix_sistema_usuarios.model.Paciente;
import clinix.com.clinix_sistema_usuarios.repository.PacienteRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.amqp.rabbit.support.micrometer.RabbitTemplateObservation.TemplateLowCardinalityTags.ROUTING_KEY;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "cadastro.exchange";
    private static final String ROUTING_KEY = "cadastro.novo";

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, RabbitTemplate rabbitTemplate){
        this.pacienteRepository = pacienteRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Paciente> listarTodos() {
        return this.pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return this.pacienteRepository.findById(id).orElse(null);
    }

    public PacienteRmiDTO buscarPorIdRmiDto(Long id) {
        return this.pacienteRepository.findPacienteRmiDTOById(id);
    }

    public Paciente salvar(Paciente paciente) {
        return this.pacienteRepository.save(paciente);
    }

    public void enviarParaFila(Paciente paciente) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, paciente);
    }

    public Paciente atualizar(Paciente pacienteAtualizado) {
        //Usuario usuario = buscarPorId(id);
        //usuario.atualizar(usuarioAtualizado);
        //return usuarioRepository.save(usuario);
        return this.pacienteRepository.save(pacienteAtualizado);
    }

    public void deletar(Long id) {
        this.pacienteRepository.deleteById(id);
    }
}
