package com.gym.service;

import com.gym.domain.Membresia;
import java.util.List;
import java.util.Optional;

public interface MembresiaService {

    List<Membresia> listarActivasOrdenPrecio();

    Optional<Membresia> buscarPorId(Long id);
}
