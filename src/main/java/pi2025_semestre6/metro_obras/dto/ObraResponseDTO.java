package pi2025_semestre6.metro_obras.dto;

import pi2025_semestre6.metro_obras.model.Obra;

import java.time.Instant;

// DTO para enviar os dados da obra como resposta
public record ObraResponseDTO(
        Long id,
        String name,
        Double conclusao,
        Instant data_inicio,
        Instant data_termino,
        Instant created_at,
        String adminName // Para sabermos quem é o admin que criou
) {
    // Construtor auxiliar para mapear da Entidade para o DTO
    public ObraResponseDTO(Obra obra) {
        this(
                obra.getId(),
                obra.getName(),
                obra.getConclusao(),
                obra.getData_inicio(),
                obra.getData_termino(),
                obra.getCreated_at(),
                obra.getAdmin().getName() // Puxa o nome do admin
        );
    }
}