package pi2025_semestre6.metro_obras.dto;

import pi2025_semestre6.metro_obras.model.Relatorio;

import java.time.Instant;

// DTO de resposta (sem o base64, que é muito grande)
public record RelatorioResponseDTO(
        Long id,
        String name,
        Long obraId,
        String usuarioName,
        Instant createdAt,
        String base64
) {
    public RelatorioResponseDTO(Relatorio relatorio) {
        this(
                relatorio.getId(),
                relatorio.getName(),
                relatorio.getObra().getId(),
                relatorio.getUsuario().getName(),
                relatorio.getCreatedAt(),
                relatorio.getBase64()

        );
    }
}