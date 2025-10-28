package pi2025_semestre6.metro_obras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "relatorios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String base64;

    // muitos relatorios para um usuario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    // muitos relatorios para uma obra
    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Instant createdAt;

    // Construtor para criacao
    public Relatorio(String name, String base64, Usuario usuario, Obra obra) {
        this.name = name;
        this.base64 = base64;
        this.usuario = usuario;
        this.obra = obra;
    }
}