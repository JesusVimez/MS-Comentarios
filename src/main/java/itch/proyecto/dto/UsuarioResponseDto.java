package itch.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDto {

    private Long id;
    private String nombre;
    private String email;
    private Boolean activo;
}