package clinix.com.clinix_sistema_usuarios;

import clinix.com.clinix_sistema_usuarios.rmi.UsuarioServiceImpl;
import com.clinix.api.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
public class ClinixSistemaUsuariosApplication {

    @Autowired
    private static UsuarioService usuarioService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClinixSistemaUsuariosApplication.class, args);
        usuarioService = context.getBean(UsuarioServiceImpl.class);
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.rebind("UsuarioService", usuarioService);
            System.out.println("Serviço RMI de Usuários registrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}