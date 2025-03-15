package clinix.com.clinix_sistema_usuarios.controller;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import clinix.com.clinix_sistema_usuarios.service.PacienteService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.List;


@RestController
@RequestMapping("/paciente")
//@CrossOrigin(origins = "http://localhost:3000")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

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
    public Paciente criar(@RequestBody Paciente paciente) {
        System.out.println("Recebendo paciente: " + paciente);
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