package clinix.com.clinix_sistema_usuarios.controller;

import clinix.com.clinix_sistema_usuarios.model.Paciente;
import clinix.com.clinix_sistema_usuarios.service.PacienteProducer;
import clinix.com.clinix_sistema_usuarios.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/list")
    public List<Paciente> listar() {
        return this.pacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Paciente buscar(@PathVariable Long id) {
        return this.pacienteService.buscarPorId(id);
    }
/*
    @PostMapping("/save")
    public String criar(@RequestBody Paciente paciente) {
        //System.out.println("Recebendo paciente: " + paciente);
        //return this.pacienteService.salvar(paciente);
        System.out.println("✅ Recebendo paciente para envio à fila: " + paciente);
        //pacienteProducer.enviarPacienteParaFila(paciente);
        //return "Paciente enviado para processamento!";
    }

 */
    @PostMapping("/mensageria/enviar")
    public ResponseEntity<String> enviarParaFila(@RequestBody Paciente paciente) {
        pacienteService.enviarParaFila(paciente);
        return ResponseEntity.ok("Mensagem enviada para a fila");
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