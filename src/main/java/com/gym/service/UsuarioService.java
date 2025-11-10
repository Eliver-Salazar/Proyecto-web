package com.gym.service;

import com.gym.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> getUsuarios();
    Usuario getUsuario(Usuario usuario);
    Usuario getUsuarioPorUsername(String username);
    Usuario getUsuarioPorUsernameYPassword(String username, String password);
    Usuario getUsuarioPorUsernameOEmail(String username, String email);
    boolean existeUsuarioPorUsernameOEmail(String username, String email);
    void save(Usuario usuario, boolean crearRolUsuario);
    void delete(Usuario usuario);
    boolean registrarUsuario(Usuario usuario);
    boolean confirmarUsuario(String codigo);
}