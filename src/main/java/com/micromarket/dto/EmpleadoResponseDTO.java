package com.micromarket.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoResponseDTO {
    private Long id;
    private String cedula;
    private String nombre;
    private String cargo;
    private LocalDate fechaIngreso;
    private Double salario;
}
