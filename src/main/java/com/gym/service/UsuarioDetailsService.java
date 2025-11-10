package com.gym.service;

import com.gym.Repository.UsuarioRepository;
import com.gym.domain.Rol;
import com.gym.domain.Usuario;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userOrEmail) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByEmailIgnoreCase(userOrEmail);
        if (u == null) {
            u = usuarioRepository.findByUsername(userOrEmail);
        }
        if (u == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        if (!u.isActivo()) {
            throw new UsernameNotFoundException("Cuenta inactiva");
        }

        var authorities = (u.getRoles() == null) ? java.util.List.<GrantedAuthority>of()
                : u.getRoles().stream()
                        .map(Rol::getNombre)
                        .map(n -> "ROLE_" + n.toUpperCase())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

       
        return User.withUsername(u.getEmail())
                .password(u.getPassword())
                .authorities(authorities)
                .build();
    }
}
