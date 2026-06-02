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
        MessageResponseDTO response = proveedorService.crearProveedor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProveedorResponseDTO>> listarProveedores() {
        List<ProveedorResponseDTO> response = proveedorService.listarProveedores();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<ProveedorResponseDTO>> obtenerProveedor(@PathVariable Long id) {
        HttpGlobalResponse<ProveedorResponseDTO> response = proveedorService.obtenerProveedor(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HttpGlobalResponse<ProveedorResponseDTO>> actualizarProveedor(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequestDTO request) {
        HttpGlobalResponse<ProveedorResponseDTO> response = proveedorService.actualizarProveedor(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminarProveedor(@PathVariable Long id) {
        MessageResponseDTO response = proveedorService.eliminarProveedor(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/entrada-almacen")
    public ResponseEntity<MessageResponseDTO> entradaAlmacen(@Valid @RequestBody EntradaAlmacenRequestDTO request) {
        MessageResponseDTO response = proveedorService.entradaAlmacen(request);
        return ResponseEntity.ok(response);
    }
}
