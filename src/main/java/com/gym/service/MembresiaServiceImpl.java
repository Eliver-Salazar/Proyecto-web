package com.gym.service;

import com.gym.Repository.MembresiaRepository;
import com.gym.domain.Membresia;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembresiaServiceImpl implements MembresiaService {

    private final MembresiaRepository membresiaRepository;

    public MembresiaServiceImpl(MembresiaRepository membresiaRepository) {
        this.membresiaRepository = membresiaRepository;
    }

    @Override
    public List<Membresia> listarActivasOrdenPrecio() {
        return membresiaRepository.findByActivoTrueOrderByPrecioMensualAsc();
    }

    @Override
    public Optional<Membresia> buscarPorId(Long id) {
        return membresiaRepository.findById(id);
    }
}
