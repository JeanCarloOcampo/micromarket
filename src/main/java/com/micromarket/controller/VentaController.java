package com.micromarket.controller;

import com.micromarket.dto.*;
import com.micromarket.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping("/procesar")
    public ResponseEntity<HttpGlobalResponse<VentaResponseDTO>> procesarVenta(
            @Valid @RequestBody VentaRequestDTO request) {
        HttpGlobalResponse<VentaResponseDTO> response = ventaService.procesarVenta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VentaResponseDTO>> listarVentas() {
        List<VentaResponseDTO> response = ventaService.listarVentas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<VentaResponseDTO>> obtenerVenta(@PathVariable Long id) {
        HttpGlobalResponse<VentaResponseDTO> response = ventaService.obtenerVenta(id);
        return ResponseEntity.ok(response);
    }
}
