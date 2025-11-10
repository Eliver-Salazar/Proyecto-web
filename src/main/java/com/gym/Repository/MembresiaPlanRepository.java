package com.gym.Repository;

import com.gym.domain.MembresiaPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembresiaPlanRepository extends JpaRepository<MembresiaPlan, Long> {

    List<MembresiaPlan> findByActivoTrueOrderByPrecioMensualAsc();

    Optional<MembresiaPlan> findByCodigoAndActivoTrue(String codigo);
}
