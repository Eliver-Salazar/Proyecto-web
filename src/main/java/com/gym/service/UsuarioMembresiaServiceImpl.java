package com.gym.service;

import com.gym.Repository.MembresiaPlanRepository;
import com.gym.Repository.UsuarioMembresiaRepository;
import com.gym.domain.EstadoMembresia;
import com.gym.domain.MembresiaPlan;
import com.gym.domain.Usuario;
import com.gym.domain.UsuarioMembresia;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioMembresiaServiceImpl implements UsuarioMembresiaService {

    private final UsuarioMembresiaRepository usuarioMembresiaRepository;
    private final MembresiaPlanRepository membresiaPlanRepository;

    public UsuarioMembresiaServiceImpl(UsuarioMembresiaRepository usuarioMembresiaRepository,
            MembresiaPlanRepository membresiaPlanRepository) {
        this.usuarioMembresiaRepository = usuarioMembresiaRepository;
        this.membresiaPlanRepository = membresiaPlanRepository;
    }

    @Override
    public Optional<UsuarioMembresia> obtenerActiva(Long idUsuario) {
        return usuarioMembresiaRepository
                .findTopByUsuarioIdUsuarioAndEstadoOrderByFechaInicioDesc(idUsuario, EstadoMembresia.ACTIVA);
    }

    @Transactional
    @Override
    public UsuarioMembresia contratarPlan(Usuario usuario, Long idPlan) {
        MembresiaPlan plan = membresiaPlanRepository.findById(idPlan)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));

        
        usuarioMembresiaRepository
                .findTopByUsuarioIdUsuarioAndEstadoOrderByFechaInicioDesc(usuario.getIdUsuario(), EstadoMembresia.ACTIVA)
                .ifPresent(actual -> {
                    actual.setEstado(EstadoMembresia.CANCELADA);
                    actual.setFechaFin(LocalDateTime.now());
                    usuarioMembresiaRepository.save(actual);
                });

        UsuarioMembresia nueva = new UsuarioMembresia();
        nueva.setUsuario(usuario);
        nueva.setPlan(plan);
        nueva.setFechaInicio(LocalDateTime.now());
        nueva.setEstado(EstadoMembresia.ACTIVA);
        nueva.setRenovacionAuto(true);

        return usuarioMembresiaRepository.save(nueva);
    }
}
