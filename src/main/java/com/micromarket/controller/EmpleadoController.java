package com.micromarket.controller;

import com.micromarket.dto.*;
import com.micromarket.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PostMapping("/crear")
    public ResponseEntity<MessageResponseDTO> crearEmpleado(@Valid @RequestBody EmpleadoRequestDTO request) {
        MessageResponseDTO response = empleadoService.crearEmpleado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarEmpleados() {
        List<EmpleadoResponseDTO> response = empleadoService.listarEmpleados();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<HttpGlobalResponse<EmpleadoResponseDTO>> obtenerEmpleado(@PathVariable Long id) {
        HttpGlobalResponse<EmpleadoResponseDTO> response = empleadoService.obtenerEmpleado(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HttpGlobalResponse<EmpleadoResponseDTO>> actualizarEmpleado(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO request) {
        HttpGlobalResponse<EmpleadoResponseDTO> response = empleadoService.actualizarEmpleado(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminarEmpleado(@PathVariable Long id) {
        MessageResponseDTO response = empleadoService.eliminarEmpleado(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EmpleadoResponseDTO>> buscarEmpleados(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        if (cargo != null) {
            return ResponseEntity.ok(empleadoService.buscarPorCargo(cargo));
        }

        if (inicio != null && fin != null) {
            return ResponseEntity.ok(empleadoService.buscarPorFechas(inicio, fin));
        }

        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }
}
