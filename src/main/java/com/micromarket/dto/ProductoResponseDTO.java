package com.micromarket.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String codigoBarras;
    private Double precio;
    private Integer stock;
    private Boolean activo;
    private String categoriaNombre;
}
