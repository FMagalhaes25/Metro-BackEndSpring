package pi2025_semestre6.metro_obras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi2025_semestre6.metro_obras.dto.ImagemCreateDTO;
import pi2025_semestre6.metro_obras.dto.ImagemResponseDTO;
import pi2025_semestre6.metro_obras.model.Imagem;
import pi2025_semestre6.metro_obras.repository.ImagemRepository;
import pi2025_semestre6.metro_obras.repository.ObraRepository;

import java.util.List;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private ObraRepository obraRepository; // para buscar a Obra

    @GetMapping
    public ResponseEntity<List<ImagemResponseDTO>> getImagensByObra(
            @RequestParam(name = "obraId") Long obraId
    ) {
        // verifica se a obra existe
        if (!obraRepository.existsById(obraId)) {
            return ResponseEntity.notFound().build();
        }

        // busca as imagens pelo ID da obra
        var imagens = imagemRepository.findByObraId(obraId);

        // converte a lista de Entidades para a lista de DTOs
        var imagensDTO = imagens.stream()
                .map(ImagemResponseDTO::new)
                .toList();

        return ResponseEntity.ok(imagensDTO);
    }

    // Create
    @PostMapping
    public ResponseEntity<ImagemResponseDTO> createImage(@RequestBody ImagemCreateDTO data) {
        // encontrar a Obra
        var obra = obraRepository.findById(data.obraId())
                .orElse(null);

        if (obra == null) {
            // retorna 404 Not Found se a Obra nao existir
            return ResponseEntity.notFound().build();
        }

        // criar e salvar a Imagem
        Imagem novaImagem = new Imagem(data.base64(), obra);
        Imagem imagemSalva = imagemRepository.save(novaImagem);

        // retornar o DTO de resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(new ImagemResponseDTO(imagemSalva));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        // verificar se a imagem existe
        if (!imagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // deletar a imagem
        imagemRepository.deleteById(id);

        // retornar 204 No Content (sucesso sem corpo de resposta pq é muito grande)
        return ResponseEntity.noContent().build();
    }
}