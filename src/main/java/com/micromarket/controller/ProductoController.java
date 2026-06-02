package com.micromarket.controller;

import com.micromarket.dto.*;
import com.micromarket.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping("/crear")
    public ResponseEntity<MessageResponseDTO> crearProducto(@Valid @RequestBody ProductoRequestDTO request) {
        MessageResponseDTO response = productoService.crearProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        List<ProductoResponseDTO> response = productoService.listarProductos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<ProductoResponseDTO>> obtenerProducto(@PathVariable Long id) {
        HttpGlobalResponse<ProductoResponseDTO> response = productoService.obtenerProducto(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HttpGlobalResponse<ProductoResponseDTO>> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO request) {
        HttpGlobalResponse<ProductoResponseDTO> response = productoService.actualizarProducto(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<MessageResponseDTO> desactivarProducto(@PathVariable Long id) {
        MessageResponseDTO response = productoService.desactivarProducto(id);
        return ResponseEntity.ok(response);
    }
}
