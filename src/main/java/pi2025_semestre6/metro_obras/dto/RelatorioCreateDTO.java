package pi2025_semestre6.metro_obras.dto;

// DTO para receber o novo relatorio
public record RelatorioCreateDTO(
        String name,
        String base64,
        Long obraId
) {
    // O user_id virá do token de autenticacao
}