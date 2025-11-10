package com.gym.service;

import com.gym.domain.Usuario;
import com.gym.domain.UsuarioMembresia;

import java.util.Optional;

public interface UsuarioMembresiaService {

    UsuarioMembresia contratarPlan(Usuario usuario, Long idPlan);

    Optional<UsuarioMembresia> obtenerActiva(Long idUsuario);
}
