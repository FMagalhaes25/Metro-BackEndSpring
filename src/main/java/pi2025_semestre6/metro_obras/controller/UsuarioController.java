package pi2025_semestre6.metro_obras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pi2025_semestre6.metro_obras.dto.UsuarioResponseDTO;
import pi2025_semestre6.metro_obras.model.Usuario;
import pi2025_semestre6.metro_obras.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        var usuarios = repository.findAll();

        var listaDeUsuariosDTO = usuarios.stream()
                .map(UsuarioResponseDTO::new)
                .toList();

        return ResponseEntity.ok(listaDeUsuariosDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getMe(@AuthenticationPrincipal Usuario usuarioLogado) {
        // O Spring injeta o 'Usuario' logado automaticamente
        // Nós apenas o convertemos para o DTO de resposta e retornamos
        return ResponseEntity.ok(new UsuarioResponseDTO(usuarioLogado));
    }
}