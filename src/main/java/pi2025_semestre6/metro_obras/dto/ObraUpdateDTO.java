package pi2025_semestre6.metro_obras.dto;

import java.time.Instant;

// DTO para atualização parcial. Campos não enviados serão 'null'.
public record ObraUpdateDTO(
        String name,
        Double conclusao,
        Instant data_inicio,
        Instant data_termino
) {}