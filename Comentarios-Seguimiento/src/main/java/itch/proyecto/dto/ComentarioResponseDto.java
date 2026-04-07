package itch.proyecto.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioResponseDto {

    private Long id;
    private Long incidenciaId;
    private Long usuarioId;
    private String mensaje;
    private LocalDateTime fecha;

}
