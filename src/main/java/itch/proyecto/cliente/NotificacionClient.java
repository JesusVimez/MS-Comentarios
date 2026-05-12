package itch.proyecto.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import itch.proyecto.dto.NotificacionRequestDto;
import itch.proyecto.dto.NotificacionResponseDto;

@FeignClient(name = "Notificaciones")
	public interface NotificacionClient  {
    @PostMapping
    NotificacionResponseDto crearNotificacion(@RequestBody NotificacionRequestDto requestDto);
}