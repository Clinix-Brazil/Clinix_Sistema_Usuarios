package clinix.com.clinix_sistema_usuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("This endpoint is only accessible to admins.");
    }
}
