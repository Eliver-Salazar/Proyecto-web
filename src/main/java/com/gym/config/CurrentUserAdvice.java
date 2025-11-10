package com.gym.config;

import com.gym.dao.UsuarioDao;
import com.gym.domain.Usuario;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class CurrentUserAdvice {

    private final UsuarioDao usuarioDao;

    public CurrentUserAdvice(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @ModelAttribute
    public void addGlobals(Model model, Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("authName", auth.getName());
            Set<String> roles = auth.getAuthorities()
                    .stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toSet());
            model.addAttribute("authRoles", roles);
            model.addAttribute("isAdmin", roles.contains("ROLE_ADMIN"));
            model.addAttribute("isPersonal", roles.contains("ROLE_PERSONAL"));

            Usuario usuario = usuarioDao.findByUsernameOrEmail(auth.getName(), auth.getName());
            model.addAttribute("usuarioActual", usuario);
        } else {
            model.addAttribute("usuarioActual", null);
        }
    }
}
