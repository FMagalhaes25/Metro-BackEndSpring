package pi2025_semestre6.metro_obras.dto;

import java.time.Instant;

// DTO para receber os dados de criacao de uma obra
public record ObraCreateDTO(
        String name,
        Instant data_inicio,
        Instant data_termino
) {
}