package pi2025_semestre6.metro_obras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi2025_semestre6.metro_obras.model.Relatorio;

import java.time.Instant;
import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    // Get com filtro por data (created_at)
    List<Relatorio> findByCreatedAtBetween(Instant start, Instant end);

    List<Relatorio> findByObraId(Long obraId);
}