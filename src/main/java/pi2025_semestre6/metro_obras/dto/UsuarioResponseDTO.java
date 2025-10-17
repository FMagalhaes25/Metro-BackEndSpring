package pi2025_semestre6.metro_obras.dto;

import pi2025_semestre6.metro_obras.model.Usuario;

public record UsuarioResponseDTO(Long id, String email) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail());
    }
}