package pi2025_semestre6.metro_obras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "imagens")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String base64;

    // muitas imagens para uma Obra
    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant created_at;

    // construtor para criacao
    public Imagem(String base64, Obra obra) {
        this.base64 = base64;
        this.obra = obra;
    }
}