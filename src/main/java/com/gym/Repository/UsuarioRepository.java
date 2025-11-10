package com.gym.Repository;

import com.gym.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Usuario findByEmail(String email);

    Usuario findByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = {"roles", "membresia"})
    Optional<Usuario> findOneWithAllByUsername(String username);
}
