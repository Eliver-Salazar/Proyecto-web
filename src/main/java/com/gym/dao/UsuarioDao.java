package com.gym.dao;

import com.gym.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByUsernameAndPassword(String username, String password);
    Usuario findByUsernameOrEmail(String username, String email);
    boolean existsByUsernameOrEmail(String username, String email);
    Usuario findByCodigoConfirmacion(String codigoConfirmacion);
}