package com.gym.service;

import com.gym.dao.RolDao;
import com.gym.dao.UsuarioDao;
import com.gym.domain.Rol;
import com.gym.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        return usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameYPassword(String username, String password) {
        return usuarioDao.findByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameOEmail(String username, String email) {
        return usuarioDao.findByUsernameOrEmail(username, email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioPorUsernameOEmail(String username, String email) {
        return usuarioDao.existsByUsernameOrEmail(username, email);
    }

    @Override
    @Transactional
    public void save(Usuario usuario, boolean crearRolUsuario) {
        usuario = usuarioDao.save(usuario);
        if (crearRolUsuario) {
            Rol rol = new Rol();
            rol.setNombre("USUARIO");
            rol.setUsuario(usuario);
            rolDao.save(rol);
        }
    }

    @Override
    @Transactional
    public void delete(Usuario usuario) {
        usuarioDao.delete(usuario);
    }

    @Override
    @Transactional
    public boolean registrarUsuario(Usuario usuario) {
        if (usuarioDao.existsByUsernameOrEmail(usuario.getUsername(), usuario.getEmail())) {
            return false; // usuario ya existe
        }
        String codigo = UUID.randomUUID().toString();
        usuario.setCodigoConfirmacion(codigo);
        usuario.setActivo(false);
        usuarioDao.save(usuario);
        return true;
    }

    @Override
    @Transactional
    public boolean confirmarUsuario(String codigo) {
        Usuario usuario = usuarioDao.findByCodigoConfirmacion(codigo);
        if (usuario == null) {
            return false; 
        }
        if (usuario.isActivo()) {
            return false; 
        }
        usuario.setActivo(true);
        usuario.setCodigoConfirmacion(null);
        usuarioDao.save(usuario);
        return true;
    }
}