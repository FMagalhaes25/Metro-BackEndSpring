package pi2025_semestre6.metro_obras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false) //não pode repetir
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING) // Salva a string no banco, ao invés do ID
    @Column(nullable = false)
    private UserRole role;

    @CreationTimestamp // Define a data de criação automaticamente
    @Column(updatable = false)
    private Instant created_at;

    @UpdateTimestamp // Define a data de atualização automaticamente
    private Instant updated_at;

    // Construtor customizado para o registro (sem os campos automáticos)
    public Usuario(String name, String email, String senha, String cpf, UserRole role) {
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN_GLOBAL) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN_GLOBAL"));
        }
        // Por padrão, qualquer outro é funcionário
        return List.of(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
