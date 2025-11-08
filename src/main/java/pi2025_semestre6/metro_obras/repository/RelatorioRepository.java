package pi2025_semestre6.metro_obras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi2025_semestre6.metro_obras.model.Relatorio;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    // Get com filtro por data (created_at)
    List<Relatorio> findByObraIdAndCreatedAtBetween(Long obraId, Instant start, Instant end);

    List<Relatorio> findByObraId(Long obraId);
    Optional<Relatorio> findTopByObraIdOrderByCreatedAtDesc(Long obraId);
}