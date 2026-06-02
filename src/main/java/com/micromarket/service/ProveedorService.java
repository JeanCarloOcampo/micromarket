package com.micromarket.service;

import com.micromarket.dto.*;
import com.micromarket.entity.Producto;
import com.micromarket.entity.Proveedor;
import com.micromarket.repository.ProductoRepository;
import com.micromarket.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;

    public MessageResponseDTO crearProveedor(ProveedorRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();

        Optional<Proveedor> existente = proveedorRepository.findByNit(request.getNit());
        if (existente.isPresent()) {
            response.setMessage("Ya existe un proveedor con ese NIT");
            return response;
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(request.getNombre());
        proveedor.setNit(request.getNit());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
        proveedorRepository.save(proveedor);

        response.setMessage("Proveedor creado correctamente");
        return response;
    }

    public List<ProveedorResponseDTO> listarProveedores() {
        List<ProveedorResponseDTO> lista = new ArrayList<>();
        List<Proveedor> proveedores = proveedorRepository.findAll();

        for (Proveedor proveedor : proveedores) {
            lista.add(mapearProveedor(proveedor));
        }
        return lista;
    }

    public HttpGlobalResponse<ProveedorResponseDTO> obtenerProveedor(Long id) {
        HttpGlobalResponse<ProveedorResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Proveedor> encontrado = proveedorRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        response.setMessage("Proveedor encontrado");
        response.setData(mapearProveedor(encontrado.get()));
        return response;
    }

    public HttpGlobalResponse<ProveedorResponseDTO> actualizarProveedor(Long id, ProveedorRequestDTO request) {
        HttpGlobalResponse<ProveedorResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Proveedor> encontrado = proveedorRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        Proveedor proveedor = encontrado.get();

        if (!proveedor.getNit().equals(request.getNit())) {
            Optional<Proveedor> duplicado = proveedorRepository.findByNit(request.getNit());
            if (duplicado.isPresent()) {
                response.setMessage("Ya existe un proveedor con ese NIT");
                return response;
            }
        }

        proveedor.setNombre(request.getNombre());
        proveedor.setNit(request.getNit());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
        proveedorRepository.save(proveedor);

        response.setMessage("Proveedor actualizado correctamente");
        response.setData(mapearProveedor(proveedor));
        return response;
    }

    public MessageResponseDTO eliminarProveedor(Long id) {
        MessageResponseDTO response = new MessageResponseDTO();
        Optional<Proveedor> encontrado = proveedorRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        proveedorRepository.deleteById(id);
        response.setMessage("Proveedor eliminado correctamente");
        return response;
    }

    public MessageResponseDTO entradaAlmacen(EntradaAlmacenRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();

        Optional<Producto> productoOpt = productoRepository.findById(request.getProductoId());
        if (productoOpt.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(request.getProveedorId());
        if (proveedorOpt.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        Producto producto = productoOpt.get();
        Proveedor proveedor = proveedorOpt.get();

        if (!producto.getProveedores().contains(proveedor)) {
            producto.getProveedores().add(proveedor);
        }

        int stockActual = producto.getStock() != null ? producto.getStock() : 0;
        producto.setStock(stockActual + request.getCantidad());
        productoRepository.save(producto);

        response.setMessage("Entrada de almacén registrada. Stock actualizado a: " + producto.getStock());
        return response;
    }

    private ProveedorResponseDTO mapearProveedor(Proveedor proveedor) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setNit(proveedor.getNit());
        dto.setTelefono(proveedor.getTelefono());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }
}
