package com.gym.controller;

import com.gym.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

    private final UsuarioService usuarioService;

    public IndexController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

   
    @GetMapping("/inscribete")
    public String inscribete() {
        return "redirect:/registro";
    }

    @GetMapping("/nosotros")
    public String sobreNosotros() {
        return "nosotros";
    }

    @GetMapping("/faq")
    public String preguntasFrecuentes() {
        return "PreguntasFrecuentes";
    }

    @GetMapping("/contacto")
    public String contacto(@RequestParam(required = false) String enviado, Model model) {
        if (enviado != null) {
            model.addAttribute("mensaje", "¡Gracias! Pronto nos pondremos en contacto contigo.");
        }
        return "Contacto";
    }

    @PostMapping("/contacto/enviar")
    public String enviarContacto(@RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono,
            @RequestParam String asunto,
            @RequestParam String preferencia,
            RedirectAttributes ra) {
        ra.addAttribute("enviado", "ok");
        return "redirect:/contacto";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/confirmar")
    public String confirmarCuenta(@RequestParam String codigo, Model model) {
        boolean confirmado = usuarioService.confirmarUsuario(codigo);
        if (confirmado) {
            model.addAttribute("mensaje", "Cuenta confirmada con éxito. Ya puedes iniciar sesión.");
        } else {
            model.addAttribute("error", "Código inválido o cuenta ya confirmada.");
        }
        return "index";
    }

    @GetMapping("/horarios")
    public String horarios() {
        return "Horarios";
    }
}
