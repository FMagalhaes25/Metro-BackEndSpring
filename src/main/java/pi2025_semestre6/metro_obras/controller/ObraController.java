package pi2025_semestre6.metro_obras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pi2025_semestre6.metro_obras.dto.ObraCreateDTO;
import pi2025_semestre6.metro_obras.dto.ObraResponseDTO;
import pi2025_semestre6.metro_obras.dto.ObraUpdateDTO;
import pi2025_semestre6.metro_obras.model.Obra;
import pi2025_semestre6.metro_obras.model.Usuario;
import pi2025_semestre6.metro_obras.model.UserRole;
import pi2025_semestre6.metro_obras.repository.ObraRepository;

import java.util.List;

@RestController
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraRepository obraRepository;

    // so admin pode criar obra
    @PostMapping
    public ResponseEntity<ObraResponseDTO> createObra(
            @RequestBody ObraCreateDTO data,
            @AuthenticationPrincipal Usuario adminLogado // pega o usuario logado
    ) {
        // O admin é pego diretamente da autenticação
        Obra novaObra = new Obra(
                data.name(),
                adminLogado,
                data.data_inicio(),
                data.data_termino()
        );

        Obra obraSalva = obraRepository.save(novaObra);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ObraResponseDTO(obraSalva));
    }

    // fazer um Get (all)
    @GetMapping
    public ResponseEntity<List<ObraResponseDTO>> getAllObras() {
        var obras = obraRepository.findAll();
        var obrasDTO = obras.stream().map(ObraResponseDTO::new).toList();
        return ResponseEntity.ok(obrasDTO);
    }

    // fazer um getById
    @GetMapping("/{id}")
    public ResponseEntity<ObraResponseDTO> getObraById(@PathVariable Long id) {
        // converte a Obra para ObraResponseDTO ou retorna null se não encontrar
        var obraDTO = obraRepository.findById(id)
                .map(ObraResponseDTO::new)
                .orElse(null);

        if (obraDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(obraDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ObraResponseDTO> updateObra(
            @PathVariable Long id,
            @RequestBody ObraUpdateDTO data,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        //verifica a permissao (so ADMIN pode atualizar)
        if (usuarioLogado.getRole() != UserRole.ADMIN_GLOBAL) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var obra = obraRepository.findById(id).orElse(null);
        if (obra == null) {
            return ResponseEntity.notFound().build();
        }

        //atualização parcial, caso não venha, mantém
        if (data.name() != null) {
            obra.setName(data.name());
        }
        if (data.conclusao() != null) {
            obra.setConclusao(data.conclusao());
        }
        if (data.data_inicio() != null) {
            obra.setData_inicio(data.data_inicio());
        }
        if (data.data_termino() != null) {
            obra.setData_termino(data.data_termino());
        }

        Obra obraAtualizada = obraRepository.save(obra);

        return ResponseEntity.ok(new ObraResponseDTO(obraAtualizada));
    }
}