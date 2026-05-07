package itch.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionRequestDto {

    private Long incidenciaId;  // ID de la incidencia

    private Long estadoId;  // ID del estado de la notificación (e.g., PENDIENTE = 1)

    private String estado;  // Nombre del estado de la incidencia (e.g., "PENDIENTE", "REPORTADO")

    private String mensaje;  // Mensaje para la notificación
}