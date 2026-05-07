package itch.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioRequestDto {

    @NotNull(message = "El ID de incidencia es requerido")
    private Long incidenciaId;

    @NotNull(message = "El ID de usuario es requerido")
    private Long usuarioId;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

}
