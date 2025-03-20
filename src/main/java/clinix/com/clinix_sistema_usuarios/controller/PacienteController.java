package clinix.com.clinix_sistema_usuarios.controller;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import clinix.com.clinix_sistema_usuarios.model.User;
import clinix.com.clinix_sistema_usuarios.repository.UserRepository;
import clinix.com.clinix_sistema_usuarios.service.PacienteService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/paciente")
//@CrossOrigin(origins = "http://localhost:3000")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UserRepository userRepository;

    //private final RabbitTemplate rabbitTemplate;

    public PacienteController(PacienteService pacienteService, RabbitTemplate rabbitTemplate) {
        this.pacienteService = pacienteService;
        //this.rabbitTemplate = rabbitTemplate;
    }
    /*
    @GetMapping("/fila")
    public Object verificarFilaPacientes() {
        return rabbitTemplate.receiveAndConvert("fila_pacientes");
    }

    @PostMapping("/enviarFila")
    public ResponseEntity<String> enviarPacienteParaFila(@RequestBody Paciente paciente) {
        pacienteService.enviarParaFila(paciente);
        return ResponseEntity.ok("Paciente enviado para a fila.");
    }
    */

    @GetMapping("/list")
    public List<Paciente> listar() {
        return this.pacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Paciente buscar(@PathVariable Long id) {
        return this.pacienteService.buscarPorId(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> criar(@RequestBody Paciente paciente) {
        System.out.println("Recebendo paciente: " + paciente);

        String username = paciente.getNomeUsuario();
        String password = paciente.getSenha();

        if (userRepository.findByUsername(username).isPresent()) {
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_PACIENTE");
        user.setRoles(roles);

        userRepository.save(user);

        return this.pacienteService.salvar(paciente);
        //System.out.println("✅ Recebendo paciente para envio à fila: " + paciente);
        //pacienteProducer.enviarPacienteParaFila(paciente);
        //return "Paciente enviado para processamento!";
    }

    @PutMapping("/{id}")
    public Paciente atualizar(@RequestBody Paciente paciente) {
        return this.pacienteService.atualizar(paciente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.pacienteService.deletar(id);
    }
}