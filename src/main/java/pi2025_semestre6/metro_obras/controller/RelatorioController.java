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

    // Criar novo relatório
    @PostMapping
    public ResponseEntity<RelatorioResponseDTO> createRelatorio(
            @RequestBody RelatorioCreateDTO data,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        Obra obra = obraRepository.findById(data.obraId())
                .orElse(null);

        if (obra == null) {
            return ResponseEntity.notFound().build();
        }

        Relatorio novoRelatorio = new Relatorio(
                data.name(),
                data.base64(),
                usuarioLogado,
                obra
        );
        Relatorio relatorioSalvo = relatorioRepository.save(novoRelatorio);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RelatorioResponseDTO(relatorioSalvo));
    }

    // Deletar relatório
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelatorio(@PathVariable Long id) {
        if (!relatorioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        relatorioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar relatórios por obra e data
    @GetMapping("/por-obra-e-data")
    public ResponseEntity<List<RelatorioResponseDTO>> getRelatoriosPorObraEData(
            @RequestParam(name = "obraId") Long obraId,
            @RequestParam(name = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        if (!obraRepository.existsById(obraId)) {
            return ResponseEntity.notFound().build();
        }

        Instant startOfDay = data.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = data.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();

        var relatorios = relatorioRepository.findByObraIdAndCreatedAtBetween(obraId, startOfDay, endOfDay);
        var relatoriosDTO = relatorios.stream()
                .map(RelatorioResponseDTO::new)
                .toList();

        return ResponseEntity.ok(relatoriosDTO);
    }

    // Buscar relatórios por obra
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

    // ✅ Novo endpoint: Buscar o relatório mais recente de uma obra
    @GetMapping("/mais-recente")
    public ResponseEntity<RelatorioResponseDTO> getRelatorioMaisRecente(
            @RequestParam(name = "obraId") Long obraId
    ) {
        if (!obraRepository.existsById(obraId)) {
            return ResponseEntity.notFound().build();
        }

        // Busca o último relatório (ordenado por createdAt desc)
        Relatorio relatorio = relatorioRepository
                .findTopByObraIdOrderByCreatedAtDesc(obraId)
                .orElse(null);

        if (relatorio == null) {
            return ResponseEntity.noContent().build(); // Nenhum relatório encontrado
        }

        return ResponseEntity.ok(new RelatorioResponseDTO(relatorio));
    }
}
