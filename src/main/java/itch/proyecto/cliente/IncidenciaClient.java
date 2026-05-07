package itch.proyecto.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import itch.proyecto.dto.IncidenciaResponseDto;

@FeignClient(name = "SERVICIOINCIDENCIAS")
public interface IncidenciaClient {

    @GetMapping("/api/incidencias/{id}")
    IncidenciaResponseDto obtenerIncidenciaPorId(@PathVariable Long id);
}