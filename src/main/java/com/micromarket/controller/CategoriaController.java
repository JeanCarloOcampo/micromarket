package com.micromarket.controller;

import com.micromarket.dto.*;
import com.micromarket.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping("/crear")
    public ResponseEntity<MessageResponseDTO> crearCategoria(@Valid @RequestBody CategoriaRequestDTO request) {
        try {
            MessageResponseDTO response = categoriaService.crearCategoria(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Error al crear la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        try {
            List<CategoriaResponseDTO> response = categoriaService.listarCategorias();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<CategoriaResponseDTO>> obtenerCategoria(@PathVariable Long id) {
        try {
            HttpGlobalResponse<CategoriaResponseDTO> response = categoriaService.obtenerCategoria(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            HttpGlobalResponse<CategoriaResponseDTO> error = new HttpGlobalResponse<>();
            error.setMessage("Error al obtener la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HttpGlobalResponse<CategoriaResponseDTO>> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO request) {
        try {
            HttpGlobalResponse<CategoriaResponseDTO> response = categoriaService.actualizarCategoria(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            HttpGlobalResponse<CategoriaResponseDTO> error = new HttpGlobalResponse<>();
            error.setMessage("Error al actualizar la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminarCategoria(@PathVariable Long id) {
        try {
            MessageResponseDTO response = categoriaService.eliminarCategoria(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Error al eliminar la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}