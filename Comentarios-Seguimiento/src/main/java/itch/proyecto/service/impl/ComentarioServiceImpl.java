package itch.proyecto.service.impl;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itch.proyecto.dto.ComentarioRequestDto;
import itch.proyecto.dto.ComentarioResponseDto;
import itch.proyecto.entity.Comentario;
import itch.proyecto.exception.ResourceNotFoundException;
import itch.proyecto.mapper.ComentarioMapper;
import itch.proyecto.repository.ComentarioRepository;
import itch.proyecto.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;

    @Override
    public ComentarioResponseDto crearComentario(ComentarioRequestDto requestDto) {
        log.info("Creando nuevo comentario para incidencia: {}", requestDto.getIncidenciaId());

        Comentario comentario = comentarioMapper.toEntity(requestDto);
        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        log.info("Comentario creado exitosamente con ID: {}", comentarioGuardado.getId());
        return comentarioMapper.toResponseDto(comentarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ComentarioResponseDto obtenerComentarioPorId(Long id) {
        log.info("Obteniendo comentario con ID: {}", id);

        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comentario no encontrado con ID: " + id));

        return comentarioMapper.toResponseDto(comentario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDto> listarComentariosPorIncidenciaId(Long incidenciaId) {
        log.info("Listando comentarios para incidencia ID: {}", incidenciaId);

        List<Comentario> comentarios = comentarioRepository.findByIncidenciaId(incidenciaId);
        return comentarios.stream()
                .map(comentarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDto> listarTodosLosComentarios() {
        log.info("Listando todos los comentarios");

        return comentarioRepository.findAll().stream()
                .map(comentarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarComentario(Long id) {
        log.info("Eliminando comentario con ID: {}", id);

        if (!comentarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comentario no encontrado con ID: " + id);
        }

        comentarioRepository.deleteById(id);
        log.info("Comentario eliminado exitosamente con ID: {}", id);
    }

}
