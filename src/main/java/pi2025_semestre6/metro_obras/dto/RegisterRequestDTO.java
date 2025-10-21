package pi2025_semestre6.metro_obras.dto;

import pi2025_semestre6.metro_obras.model.UserRole;

// DTO para receber os dados de registro
public record RegisterRequestDTO(
        String name,
        String email,
        String senha,
        String cpf,
        UserRole role // O JSON deve enviar "ADMIN_GLOBAL" ou "FUNCIONARIO"
) {}