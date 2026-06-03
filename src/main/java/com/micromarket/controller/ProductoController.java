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
        try {
            MessageResponseDTO response = productoService.crearProducto(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Error al crear el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        try {
            List<ProductoResponseDTO> response = productoService.listarProductos();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<ProductoResponseDTO>> obtenerProducto(@PathVariable Long id) {
        try {
            HttpGlobalResponse<ProductoResponseDTO> response = productoService.obtenerProducto(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            HttpGlobalResponse<ProductoResponseDTO> error = new HttpGlobalResponse<>();
            error.setMessage("Error al obtener el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HttpGlobalResponse<ProductoResponseDTO>> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO request) {
        try {
            HttpGlobalResponse<ProductoResponseDTO> response = productoService.actualizarProducto(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            HttpGlobalResponse<ProductoResponseDTO> error = new HttpGlobalResponse<>();
            error.setMessage("Error al actualizar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<MessageResponseDTO> desactivarProducto(@PathVariable Long id) {
        try {
            MessageResponseDTO response = productoService.desactivarProducto(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Error al desactivar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}