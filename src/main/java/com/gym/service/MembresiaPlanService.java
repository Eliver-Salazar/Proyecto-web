package com.gym.service;

import com.gym.domain.MembresiaPlan;

import java.util.List;
import java.util.Optional;

public interface MembresiaPlanService {

    List<MembresiaPlan> listarActivasOrdenPrecio();

    Optional<MembresiaPlan> buscarPorId(Long id);
}
