package com.micromarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "cedula", nullable = false, unique = true)
    private String cedula;

    @NotBlank
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo", nullable = false)
    private CargoEmpleado cargo;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @NotNull
    @Positive
    @Column(name = "salario", nullable = false)
    private Double salario;

    public enum CargoEmpleado {
        ADMINISTRADOR, CAJERO, AUXILIAR
    }
}
