package com.micromarket.service;

import com.micromarket.dto.*;
import com.micromarket.entity.Empleado;
import com.micromarket.entity.Empleado.CargoEmpleado;
import com.micromarket.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public MessageResponseDTO crearEmpleado(EmpleadoRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();

        Empleado empleado = new Empleado();
        empleado.setCedula(request.getCedula());
        empleado.setNombre(request.getNombre());
        empleado.setCargo(request.getCargo());
        empleado.setFechaIngreso(request.getFechaIngreso());
        empleado.setSalario(request.getSalario());
        empleadoRepository.save(empleado);

        response.setMessage("Empleado creado correctamente");
        return response;
    }

    public List<EmpleadoResponseDTO> listarEmpleados() {
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findAll();

        for (Empleado empleado : empleados) {
            lista.add(mapearEmpleado(empleado));
        }
        return lista;
    }

    public HttpGlobalResponse<EmpleadoResponseDTO> obtenerEmpleado(Long id) {
        HttpGlobalResponse<EmpleadoResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Empleado> encontrado = empleadoRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        response.setMessage("Empleado encontrado");
        response.setData(mapearEmpleado(encontrado.get()));
        return response;
    }

    public HttpGlobalResponse<EmpleadoResponseDTO> actualizarEmpleado(Long id, EmpleadoRequestDTO request) {
        HttpGlobalResponse<EmpleadoResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Empleado> encontrado = empleadoRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        Empleado empleado = encontrado.get();
        empleado.setCedula(request.getCedula());
        empleado.setNombre(request.getNombre());
        empleado.setCargo(request.getCargo());
        empleado.setFechaIngreso(request.getFechaIngreso());
        empleado.setSalario(request.getSalario());
        empleadoRepository.save(empleado);

        response.setMessage("Empleado actualizado correctamente");
        response.setData(mapearEmpleado(empleado));
        return response;
    }

    public MessageResponseDTO eliminarEmpleado(Long id) {
        MessageResponseDTO response = new MessageResponseDTO();
        Optional<Empleado> encontrado = empleadoRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        empleadoRepository.deleteById(id);
        response.setMessage("Empleado eliminado correctamente");
        return response;
    }

    public List<EmpleadoResponseDTO> buscarPorCargo(String cargo) {
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        CargoEmpleado cargoEnum = CargoEmpleado.valueOf(cargo.toUpperCase());
        List<Empleado> empleados = empleadoRepository.findByCargo(cargoEnum);

        for (Empleado empleado : empleados) {
            lista.add(mapearEmpleado(empleado));
        }
        return lista;
    }

    public List<EmpleadoResponseDTO> buscarPorFechas(LocalDate inicio, LocalDate fin) {
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findByFechaIngresoBetween(inicio, fin);

        for (Empleado empleado : empleados) {
            lista.add(mapearEmpleado(empleado));
        }
        return lista;
    }

    private EmpleadoResponseDTO mapearEmpleado(Empleado empleado) {
        EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
        dto.setId(empleado.getId());
        dto.setCedula(empleado.getCedula());
        dto.setNombre(empleado.getNombre());
        dto.setCargo(empleado.getCargo().name());
        dto.setFechaIngreso(empleado.getFechaIngreso());
        dto.setSalario(empleado.getSalario());
        return dto;
    }
}
