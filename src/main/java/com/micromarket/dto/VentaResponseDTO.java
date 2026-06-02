package com.micromarket.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fechaVenta;
    private String empleadoNombre;
    private Double subtotal;
    private Double iva;
    private Double total;
    private List<DetalleVentaResponseDTO> detalles;

    @Data
    public static class DetalleVentaResponseDTO {
        private Long id;
        private String productoNombre;
        private Integer cantidad;
        private Double precioUnitario;
        private Double subtotalLinea;
    }
}
