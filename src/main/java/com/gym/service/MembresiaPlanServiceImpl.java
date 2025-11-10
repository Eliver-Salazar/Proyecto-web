package com.gym.service;

import com.gym.Repository.MembresiaPlanRepository;
import com.gym.domain.MembresiaPlan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembresiaPlanServiceImpl implements MembresiaPlanService {

    private final MembresiaPlanRepository repo;

    public MembresiaPlanServiceImpl(MembresiaPlanRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<MembresiaPlan> listarActivasOrdenPrecio() {
        return repo.findByActivoTrueOrderByPrecioMensualAsc();
    }

    @Override
    public Optional<MembresiaPlan> buscarPorId(Long id) {
        return repo.findById(id);
    }
}
