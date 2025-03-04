package clinix.com.clinix_sistema_usuarios;

import clinix.com.clinix_sistema_usuarios.rmi.UsuarioServiceImpl;
import com.clinix.api.interfaces.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
public class ClinixSistemaUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinixSistemaUsuariosApplication.class, args);
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            UsuarioService usuarioService = new UsuarioServiceImpl();
            registry.rebind("UsuarioService", usuarioService);
            System.out.println("Serviço RMI de Usuários registrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
