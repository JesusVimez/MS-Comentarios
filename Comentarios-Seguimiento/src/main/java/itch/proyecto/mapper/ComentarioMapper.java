package itch.proyecto.mapper;

import org.springframework.stereotype.Component;

import itch.proyecto.dto.ComentarioRequestDto;
import itch.proyecto.dto.ComentarioResponseDto;
import itch.proyecto.entity.Comentario;

@Component
public class ComentarioMapper {

    public Comentario toEntity(ComentarioRequestDto requestDto) {
        return Comentario.builder()
                .incidenciaId(requestDto.getIncidenciaId())
                .usuarioId(requestDto.getUsuarioId())
                .mensaje(requestDto.getMensaje())
                .build();
    }

    public ComentarioResponseDto toResponseDto(Comentario comentario) {
        return ComentarioResponseDto.builder()
                .id(comentario.getId())
                .incidenciaId(comentario.getIncidenciaId())
                .usuarioId(comentario.getUsuarioId())
                .mensaje(comentario.getMensaje())
                .fecha(comentario.getFecha())
                .build();
    }

}
