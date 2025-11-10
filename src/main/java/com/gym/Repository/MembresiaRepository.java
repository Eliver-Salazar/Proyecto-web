package com.gym.Repository;

import com.gym.domain.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MembresiaRepository extends JpaRepository<Membresia, Long> {

    
    List<Membresia> findByActivoTrueOrderByPrecioMensualAsc();
}
