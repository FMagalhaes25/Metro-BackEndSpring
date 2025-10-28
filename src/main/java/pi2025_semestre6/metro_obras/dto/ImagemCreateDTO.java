package pi2025_semestre6.metro_obras.dto;

// DTO para receber a nova imagem
public record ImagemCreateDTO(
        String base64,
        Long obraId // O ID da Obra que a imagem pertence
) {}