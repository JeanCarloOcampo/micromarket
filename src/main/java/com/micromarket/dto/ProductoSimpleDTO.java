package com.micromarket.dto;

import lombok.Data;

@Data
public class ProductoSimpleDTO {
    private Long id;
    private String nombre;
    private String codigoBarras;
    private Double precio;
    private Integer stock;
}
