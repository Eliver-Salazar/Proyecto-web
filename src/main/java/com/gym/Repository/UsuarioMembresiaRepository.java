package com.gym.Repository;

import com.gym.domain.EstadoMembresia;
import com.gym.domain.UsuarioMembresia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioMembresiaRepository extends JpaRepository<UsuarioMembresia, Long> {

    Optional<UsuarioMembresia> findTopByUsuarioIdUsuarioAndEstadoOrderByFechaInicioDesc(
            Long idUsuario, EstadoMembresia estado);

    boolean existsByUsuarioIdUsuarioAndEstado(Long idUsuario, EstadoMembresia estado);

    List<UsuarioMembresia> findByUsuarioIdUsuarioOrderByFechaInicioDesc(Long idUsuario);
}
