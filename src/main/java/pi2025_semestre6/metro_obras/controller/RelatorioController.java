package pi2025_semestre6.metro_obras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pi2025_semestre6.metro_obras.dto.RelatorioCreateDTO;
import pi2025_semestre6.metro_obras.dto.RelatorioResponseDTO;
import pi2025_semestre6.metro_obras.model.Obra;
import pi2025_semestre6.metro_obras.model.Relatorio;
import pi2025_semestre6.metro_obras.model.Usuario;
import pi2025_semestre6.metro_obras.repository.ObraRepository;
import pi2025_semestre6.metro_obras.repository.RelatorioRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private ObraRepository obraRepository;

    // REQUISITO: "Create"
    @PostMapping
    public ResponseEntity<RelatorioResponseDTO> createRelatorio(
            @RequestBody RelatorioCreateDTO data,
            @AuthenticationPrincipal Usuario usuarioLogado // Pega o usuario do token
    ) {
        // encontrar a Obra
        Obra obra = obraRepository.findById(data.obraId())
                .orElse(null);

        if (obra == null) {
            return ResponseEntity.notFound().build(); // Obra não encontrada
        }

        // criar e salvar o Relatório (usuario vem do token)
        Relatorio novoRelatorio = new Relatorio(
                data.name(),
                data.base64(),
                usuarioLogado,
                obra
        );
        Relatorio relatorioSalvo = relatorioRepository.save(novoRelatorio);

        // retornar o DTO de resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(new RelatorioResponseDTO(relatorioSalvo));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelatorio(@PathVariable Long id) {
        if (!relatorioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        relatorioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Get com filtro por data (created_at)
    @GetMapping
    public ResponseEntity<List<RelatorioResponseDTO>> getRelatorios(
            // Recebe a data no formato AAAA-MM-DD
            @RequestParam(name = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        // converte a data (ex: 2025-10-27) para um intervalo de Instant (dia inteiro)
        Instant startOfDay = data.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = data.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();

        // busca no repositorio
        var relatorios = relatorioRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        var relatoriosDTO = relatorios.stream()
                .map(RelatorioResponseDTO::new)
                .toList();

        return ResponseEntity.ok(relatoriosDTO);
    }

    // listar relatorio por obra
    @GetMapping("/por-obra")
    public ResponseEntity<List<RelatorioResponseDTO>> getRelatoriosPorObra(
            @RequestParam(name = "obraId") Long obraId
    ) {
        if (!obraRepository.existsById(obraId)) {
            return ResponseEntity.notFound().build();
        }
        var relatorios = relatorioRepository.findByObraId(obraId);
        var relatoriosDTO = relatorios.stream()
                .map(RelatorioResponseDTO::new)
                .toList();
        return ResponseEntity.ok(relatoriosDTO);
    }
}