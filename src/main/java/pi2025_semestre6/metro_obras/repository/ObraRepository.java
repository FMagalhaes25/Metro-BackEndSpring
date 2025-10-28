package pi2025_semestre6.metro_obras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi2025_semestre6.metro_obras.model.Obra;

public interface ObraRepository extends JpaRepository<Obra, Long> {
}