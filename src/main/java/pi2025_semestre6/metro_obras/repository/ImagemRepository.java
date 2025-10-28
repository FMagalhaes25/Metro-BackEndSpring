package pi2025_semestre6.metro_obras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi2025_semestre6.metro_obras.model.Imagem;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    List<Imagem> findByObraId(Long obraId);
}