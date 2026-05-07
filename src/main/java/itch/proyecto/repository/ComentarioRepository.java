package itch.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itch.proyecto.entity.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByIncidenciaId(Long incidenciaId);

}
