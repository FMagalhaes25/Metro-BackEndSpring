package pi2025_semestre6.metro_obras.dto;

import pi2025_semestre6.metro_obras.model.Usuario;
import pi2025_semestre6.metro_obras.model.UserRole;

public record UsuarioResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        UserRole role
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getName(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getRole()
        );
    }
}