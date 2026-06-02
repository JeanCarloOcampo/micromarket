package com.micromarket.repository;

import com.micromarket.entity.Empleado;
import com.micromarket.entity.Empleado.CargoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByCargo(CargoEmpleado cargo);

    List<Empleado> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);
}
