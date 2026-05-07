package itch.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponseDto {

    private Long id;
    private Long usuarioId;
    private Long incidenciaId;
    private String mensaje;
    private Long estadoId;
    private String estadoNombre;
    private String fecha;
}