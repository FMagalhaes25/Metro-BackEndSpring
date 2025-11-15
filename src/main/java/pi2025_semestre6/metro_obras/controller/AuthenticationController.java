package pi2025_semestre6.metro_obras.controller;

import org.springframework.web.bind.annotation.*;
import pi2025_semestre6.metro_obras.dto.LoginRequestDTO;
import pi2025_semestre6.metro_obras.dto.LoginResponseDTO;
import pi2025_semestre6.metro_obras.dto.RegisterRequestDTO;
import pi2025_semestre6.metro_obras.model.Usuario;
import pi2025_semestre6.metro_obras.repository.UsuarioRepository;
import pi2025_semestre6.metro_obras.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO data) {
        if (this.usuarioRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Este e-mail já está em uso.");
        }

        if (this.usuarioRepository.findByCpf(data.cpf()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Este CPF já está cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(data.senha());
        Usuario newUser = new Usuario(
                data.name(),
                data.email(),
                encryptedPassword,
                data.cpf(),
                data.role()
        );

        this.usuarioRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String subject = tokenService.validateToken(token);

        if (subject == null || subject.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok().build();
    }

}