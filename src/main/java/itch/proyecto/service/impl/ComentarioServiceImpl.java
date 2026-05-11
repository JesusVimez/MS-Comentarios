package itch.proyecto.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayuntamiento.security_lib.jwt.UsuarioToken;

import itch.proyecto.cliente.NotificacionClient;
import itch.proyecto.dto.ComentarioRequestDto;
import itch.proyecto.dto.ComentarioResponseDto;
import itch.proyecto.dto.NotificacionRequestDto;
import itch.proyecto.dto.NotificacionResponseDto;
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
    private final NotificacionClient notificacionClient;  // Agregado el cliente de notificaciones

    @Override
    public ComentarioResponseDto crearComentario(ComentarioRequestDto requestDto) {
        log.info("Creando comentario para incidencia ID: {}", requestDto.getIncidenciaId());

        // Crear el comentario
        Comentario comentario = Comentario.builder()
                .incidenciaId(requestDto.getIncidenciaId())
                .usuarioId(requestDto.getUsuarioId())
                .mensaje(requestDto.getMensaje())
                .build();

        // Guardar el comentario
        Comentario comentarioGuardado = comentarioRepository.save(comentario);
        log.info("Comentario creado exitosamente con ID: {}", comentarioGuardado.getId());

        // Crear la notificación automáticamente
        NotificacionRequestDto notificacionRequestDto = NotificacionRequestDto.builder()
                .incidenciaId(comentarioGuardado.getIncidenciaId())  // ID de la incidencia
                .estadoId(2L)  // Estado por defecto de la notificación (PENDIENTE)
                .mensaje("Nuevo comentario sobre la incidencia: " + comentarioGuardado.getMensaje())
                .estado("REPORTADO")  // Estado de la incidencia, lo que será notificado
                .build();

        // Llamada al microservicio de Notificaciones para crear la notificación
        NotificacionResponseDto notificacionResponse = notificacionClient.crearNotificacion(notificacionRequestDto);

        log.info("Notificación creada con ID: {}", notificacionResponse.getId());

        // Devolver el comentario guardado
        return comentarioMapper.toResponseDto(comentarioGuardado);
    }


    @Override
    public void eliminarComentario(Long id) {
        log.info("Eliminando comentario con ID: {}", id);

        if (!comentarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comentario no encontrado con ID: " + id);
        }

        comentarioRepository.deleteById(id);
        log.info("Comentario eliminado con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ComentarioResponseDto obtenerComentarioPorId(Long id) {
        // 1. Buscar el comentario en la DB
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El comentario no existe."));

        // 2. Obtener el objeto de autenticación actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // 3. Castear el principal a tu clase UsuarioToken de la librería
        UsuarioToken usuarioLogueado = (UsuarioToken) auth.getPrincipal();

        // 4. Verificar si el usuario es "Personal Autorizado" (Admin, Sistema o Funcionario)
        boolean esPersonal = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || 
                               a.getAuthority().equals("ROLE_SISTEMA") || 
                               a.getAuthority().equals("FUNCIONARIO_COMENTARIOS"));

        // 5. Lógica de restricción:
        // Si NO es personal autorizado, DEBE ser el dueño del comentario
        if (!esPersonal) {
            if (!comentario.getUsuarioId().equals(usuarioLogueado.getId())) {
                throw new AccessDeniedException("No tienes permiso para ver un comentarios.");
            }
        }
        
        return comentarioMapper.toResponseDto(comentario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDto> listarComentariosPorIncidenciaId(Long incidenciaId) {
        log.info("Listando comentarios para incidencia ID: {}", incidenciaId);

        // Obtener todos los comentarios de la incidencia
        List<Comentario> comentarios = comentarioRepository.findByIncidenciaId(incidenciaId);

        // Convertir los comentarios a DTOs
        return comentarios.stream()
                .map(comentarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDto> listarComentariosPorUsuarioId(Long usuarioId) {
        log.info("Listando comentarios para usuario ID: {}", usuarioId);

        // Obtener todos los comentarios de un usuario
        Optional<Comentario> comentarios = comentarioRepository.findById(usuarioId);

        // Convertir los comentarios a DTOs
        return comentarios.stream()
                .map(comentarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

  

    @Override
    public List<ComentarioResponseDto> listarTodosLosComentarios() {
        log.info("Listando todos los comentarios");

        List<Comentario> comentarios = comentarioRepository.findAll();

        return comentarios.stream()
                .map(comentarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }


	@Override
	public String obtenerNombreUsuario(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}