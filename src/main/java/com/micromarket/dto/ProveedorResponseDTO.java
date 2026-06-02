package com.micromarket.dto;

import lombok.Data;

@Data
public class ProveedorResponseDTO {
    private Long id;
    private String nombre;
    private String nit;
    private String telefono;
    private String direccion;
}
