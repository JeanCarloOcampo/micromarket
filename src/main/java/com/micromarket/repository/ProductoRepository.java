package com.micromarket.repository;

import com.micromarket.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigoBarras(String codigoBarras);

    Optional<Producto> findByCodigoBarrasAndActivoTrue(String codigoBarras);
}
