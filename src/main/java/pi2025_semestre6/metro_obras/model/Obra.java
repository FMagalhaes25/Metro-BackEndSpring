package pi2025_semestre6.metro_obras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "obras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Usuario admin;

    @Column(columnDefinition = "double default 0.0")
    private Double conclusao;

    private Instant data_inicio;
    private Instant data_termino;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    private Instant updated_at;

    // Construtor para criacao (sem ID e os campos automaticos)
    public Obra(String name, Usuario admin, Instant data_inicio, Instant data_termino) {
        this.name = name;
        this.admin = admin;
        this.data_inicio = data_inicio;
        this.data_termino = data_termino;
        this.conclusao = 0.0; //aqui vai ser uma porcentagem
    }
}