package itch.proyecto.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itch.proyecto.dto.ComentarioRequestDto;
import itch.proyecto.dto.ComentarioResponseDto;
import itch.proyecto.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/comentarios")
@RequiredArgsConstructor
@Slf4j
public class ComentarioController {

    private final ComentarioService comentarioService;

    // Crear un nuevo comentario
    @PostMapping
    public ResponseEntity<ComentarioResponseDto> crearComentario(
            @Valid @RequestBody ComentarioRequestDto requestDto) {
        log.info("POST - Crear nuevo comentario");
        ComentarioResponseDto response = comentarioService.crearComentario(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //     * Obtener comentario por ID
     
    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponseDto> obtenerComentarioPorId(
            @PathVariable Long id) {
        log.info("GET - Obtener comentario por ID: {}", id);
        ComentarioResponseDto response = comentarioService.obtenerComentarioPorId(id);
        return ResponseEntity.ok(response);
    }

    // Listar comentarios por ID de incidencia
    
    @GetMapping("/incidencia/{incidenciaId}")
    public ResponseEntity<List<ComentarioResponseDto>> listarComentariosPorIncidenciaId(
            @PathVariable Long incidenciaId) {
        log.info("GET - Listar comentarios por incidencia ID: {}", incidenciaId);
        List<ComentarioResponseDto> response = comentarioService.listarComentariosPorIncidenciaId(incidenciaId);
        return ResponseEntity.ok(response);
    }

    // Listar todos los comentarios
     
    @GetMapping
    public ResponseEntity<List<ComentarioResponseDto>> listarTodosLosComentarios() {
        log.info("GET - Listar todos los comentarios");
        List<ComentarioResponseDto> response = comentarioService.listarTodosLosComentarios();
        return ResponseEntity.ok(response);
    }

    //Eliminar comentario por ID
     
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long id) {
        log.info("DELETE - Eliminar comentario con ID: {}", id);
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

}
