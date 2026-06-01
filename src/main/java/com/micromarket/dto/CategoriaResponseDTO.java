package com.micromarket.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoriaResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<ProductoSimpleDTO> productos;
}
