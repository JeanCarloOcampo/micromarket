package com.micromarket.controller;

import com.micromarket.dto.*;
import com.micromarket.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping("/crear")
    public ResponseEntity<MessageResponseDTO> crearProveedor(@Valid @RequestBody ProveedorRequestDTO request) {
        try {
            MessageResponseDTO response = proveedorService.crearProveedor(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Error al crear el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProveedorResponseDTO>> listarProveedores() {
        try {
            List<ProveedorResponseDTO> response = proveedorService.listarProveedores();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<ProveedorResponseDTO>> obtenerProveedor(@PathVariable Long id) {
        try {
            HttpGlobalResponse<ProveedorResponseDTO> response = proveedorService.obtenerProveedor(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            HttpGlobalResponse<ProveedorResponseDTO> error = new HttpGlobalResponse<>();
            error.setMessage("Error al obtener el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HttpGlobalResponse<ProveedorResponseDTO>> actualizarProveedor(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequestDTO request) {
        try {
            HttpGlobalResponse<ProveedorResponseDTO> response = proveedorService.actualizarProveedor(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            HttpGlobalResponse<ProveedorResponseDTO> error = new HttpGlobalResponse<>();
            error.setMessage("Error al actualizar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminarProveedor(@PathVariable Long id) {
        try {
            MessageResponseDTO response = proveedorService.eliminarProveedor(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Error al eliminar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/entrada-almacen")
    public ResponseEntity<MessageResponseDTO> entradaAlmacen(@Valid @RequestBody EntradaAlmacenRequestDTO request) {
        try {
            MessageResponseDTO response = proveedorService.entradaAlmacen(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Error en entrada de almacén: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}