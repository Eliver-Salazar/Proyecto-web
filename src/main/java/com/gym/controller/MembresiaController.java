package com.gym.controller;

import com.gym.Repository.UsuarioRepository;
import com.gym.domain.MembresiaPlan;
import com.gym.domain.Usuario;
import com.gym.domain.UsuarioMembresia;
import com.gym.service.MembresiaPlanService;
import com.gym.service.UsuarioMembresiaService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/membresias")
public class MembresiaController {

    private final MembresiaPlanService planService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMembresiaService usuarioMembresiaService;

    public MembresiaController(MembresiaPlanService planService,
            UsuarioRepository usuarioRepository,
            UsuarioMembresiaService usuarioMembresiaService) {
        this.planService = planService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMembresiaService = usuarioMembresiaService;
    }

   
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("planes", planService.listarActivasOrdenPrecio());
        return "membresias";
    }

    
    @GetMapping("/seleccionar/{id}")
    public String confirmar(@PathVariable("id") Long idPlan, Model model) {
        MembresiaPlan plan = planService.buscarPorId(idPlan)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("plan", plan);
        return "membresias_confirmar";
    }

    
    @PostMapping("/aplicar")
    public String aplicar(@RequestParam("planId") Long planId,
            Authentication authentication,
            RedirectAttributes redirect) {
        String principal = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(principal);
        if (usuario == null) {
            usuario = usuarioRepository.findByUsername(principal);
        }
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado");
        }

        UsuarioMembresia nueva = usuarioMembresiaService.contratarPlan(usuario, planId);

        redirect.addFlashAttribute("mensaje",
                "¡Listo! Tu membresía \"" + nueva.getPlan().getNombre() + "\" fue activada. "
                + "Enviamos un comprobante a " + usuario.getEmail() + ".");

        return "redirect:/perfil";
    }
}
