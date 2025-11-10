package com.gym.controller;

import com.gym.Repository.UsuarioRepository;
import com.gym.domain.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordController {

    private final UsuarioRepository usuarioRepository;

    public PasswordController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    
    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model) {
        return "auth/forgot-password";
    }

   
    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam("email") String email,
                                       RedirectAttributes ra) {
    
        Usuario u = usuarioRepository.findByEmailIgnoreCase(email);
      
        ra.addFlashAttribute("info",
                "Si el correo existe en el sistema, enviaremos un enlace de recuperación.");
        return "redirect:/forgot-password/done";
    }

    @GetMapping("/forgot-password/done")
    public String forgotPasswordDone() {
        return "auth/forgot-password-done";
    }
}

//Controller Nuevo - PasswordController