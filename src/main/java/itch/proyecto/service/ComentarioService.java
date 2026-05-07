package itch.proyecto.service;

import java.util.List;

import itch.proyecto.dto.ComentarioRequestDto;
import itch.proyecto.dto.ComentarioResponseDto;

public interface ComentarioService {

    ComentarioResponseDto crearComentario(ComentarioRequestDto requestDto);

    ComentarioResponseDto obtenerComentarioPorId(Long id);

    List<ComentarioResponseDto> listarComentariosPorIncidenciaId(Long incidenciaId);

    List<ComentarioResponseDto> listarTodosLosComentarios();

    void eliminarComentario(Long id);
    
    String obtenerNombreUsuario(Long id);

	List<ComentarioResponseDto> listarComentariosPorUsuarioId(Long usuarioId);

}
