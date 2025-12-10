package com.gym.controller; // <-- ajusta al paquete real de tu proyecto

import com.gym.Repository.UsuarioRepository;
import com.gym.Repository.MembresiaPlanRepository;
import com.gym.Repository.UsuarioMembresiaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final MembresiaPlanRepository membresiaPlanRepository;
    private final UsuarioMembresiaRepository usuarioMembresiaRepository;

    public AdminController(UsuarioRepository usuarioRepository,
                           MembresiaPlanRepository membresiaPlanRepository,
                           UsuarioMembresiaRepository usuarioMembresiaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.membresiaPlanRepository = membresiaPlanRepository;
        this.usuarioMembresiaRepository = usuarioMembresiaRepository;
    }

    /**
     * Vista principal de administración (tarjetas Usuarios, Membresías, Reportes)
     */
    @GetMapping
    public String adminHome() {
        return "admin/index";
    }

    /**
     * Administración de usuarios:
     * lista todos los usuarios, sus datos básicos y roles.
     */
    @GetMapping("/usuarios")
    public String administrarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios";
    }

    /**
     * Administración de planes de membresía:
     * muestra el catálogo de planes configurados.
     */
    @GetMapping("/membresias")
    public String administrarMembresias(Model model) {
        model.addAttribute("planes", membresiaPlanRepository.findAll());
        return "admin/membresias";
    }

    /**
     * Reportes:
     * muestra las membresías de usuarios (histórico / estado).
     */
    @GetMapping("/reportes")
    public String verReportes(Model model) {
        model.addAttribute("membresias", usuarioMembresiaRepository.findAll());
        return "admin/reportes";
    }
}
