package pi2025_semestre6.metro_obras.dto;

import pi2025_semestre6.metro_obras.model.Imagem;

import java.time.Instant;

// DTO de resposta para confirmar a criacao
// nao retornamos o base64 por ser um dado muito grande
public record ImagemResponseDTO(
        Long id,
        Long obraId,
        Instant created_at
) {
    public ImagemResponseDTO(Imagem imagem) {
        this(
                imagem.getId(),
                imagem.getObra().getId(),
                imagem.getCreated_at()
        );
    }
}