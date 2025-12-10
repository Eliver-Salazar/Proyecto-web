package com.gym.controller;  // 👈 cambia al paquete real de tu proyecto

import com.gym.Repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal")
public class PersonalController {

    private final UsuarioRepository usuarioRepository;

    public PersonalController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Panel principal del personal.
     * Soporta /personal, /personal/ y /personal/panel
     */
    @GetMapping({"", "/", "/panel"})
    public String panelPersonal() {
        // 👇 nombre de la vista (plantilla Thymeleaf)
        return "personal/panel";   // Asegúrate que exista personal/panel.html
    }

    /**
     * Turnos del personal.
     */
    @GetMapping("/turnos")
    public String verTurnos() {
        return "personal/turnos";
    }

    /**
     * Socios: lista de usuarios (socios del gym).
     */
    @GetMapping("/socios")
    public String verSocios(Model model) {
        model.addAttribute("socios", usuarioRepository.findAll());
        return "personal/socios";
    }
}
