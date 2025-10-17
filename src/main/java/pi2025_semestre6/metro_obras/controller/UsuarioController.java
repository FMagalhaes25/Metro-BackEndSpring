package pi2025_semestre6.metro_obras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pi2025_semestre6.metro_obras.dto.UsuarioResponseDTO;
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
}