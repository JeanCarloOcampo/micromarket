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
        MessageResponseDTO response = categoriaService.crearCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> response = categoriaService.listarCategorias();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<CategoriaResponseDTO>> obtenerCategoria(@PathVariable Long id) {
        HttpGlobalResponse<CategoriaResponseDTO> response = categoriaService.obtenerCategoria(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HttpGlobalResponse<CategoriaResponseDTO>> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO request) {
        HttpGlobalResponse<CategoriaResponseDTO> response = categoriaService.actualizarCategoria(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminarCategoria(@PathVariable Long id) {
        MessageResponseDTO response = categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok(response);
    }
}
