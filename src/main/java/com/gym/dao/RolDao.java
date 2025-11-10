package com.gym.dao;

import com.gym.domain.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolDao extends JpaRepository<Rol, Long> {

    @Query("SELECT r FROM Rol r WHERE r.usuario.idUsuario = :idUsuario")
    List<Rol> findByIdUsuario(@Param("idUsuario") Long idUsuario);

}