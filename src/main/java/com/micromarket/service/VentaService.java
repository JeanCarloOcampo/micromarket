package com.micromarket.service;

import com.micromarket.dto.*;
import com.micromarket.entity.*;
import com.micromarket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ProductoRepository productoRepository;

    public HttpGlobalResponse<VentaResponseDTO> procesarVenta(VentaRequestDTO request) {
        HttpGlobalResponse<VentaResponseDTO> response = new HttpGlobalResponse<>();

        Optional<Empleado> empleadoOpt = empleadoRepository.findById(request.getEmpleadoId());
        if (empleadoOpt.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        for (VentaRequestDTO.DetalleVentaRequestDTO detalle : request.getDetalles()) {
            Optional<Producto> productoOpt = productoRepository.findById(detalle.getProductoId());
            if (productoOpt.isEmpty()) {
                response.setMessage("Producto con ID " + detalle.getProductoId() + " no encontrado");
                return response;
            }
            Producto producto = productoOpt.get();
            if (!Boolean.TRUE.equals(producto.getActivo())) {
                response.setMessage("El producto '" + producto.getNombre() + "' no está disponible");
                return response;
            }
            if (producto.getStock() < detalle.getCantidad()) {
                response.setMessage("Stock insuficiente para el producto '" + producto.getNombre() +
                        "'. Stock disponible: " + producto.getStock());
                return response;
            }
        }

        Venta venta = new Venta();
        venta.setEmpleado(empleadoOpt.get());
        venta.setFechaVenta(LocalDateTime.now());

        double subtotal = 0;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (VentaRequestDTO.DetalleVentaRequestDTO detalleReq : request.getDetalles()) {
            Producto producto = productoRepository.findById(detalleReq.getProductoId()).get();

            producto.setStock(producto.getStock() - detalleReq.getCantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleReq.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            double subtotalLinea = producto.getPrecio() * detalleReq.getCantidad();
            detalle.setSubtotalLinea(subtotalLinea);
            detalle.setVenta(venta);
            detalles.add(detalle);

            subtotal += subtotalLinea;
        }

        double iva = subtotal * 0.19;
        double total = subtotal + iva;

        venta.setSubtotal(subtotal);
        venta.setIva(iva);
        venta.setTotal(total);
        venta.setDetalles(detalles);
        ventaRepository.save(venta);

        response.setMessage("Venta procesada correctamente");
        response.setData(mapearVenta(venta));
        return response;
    }

    public List<VentaResponseDTO> listarVentas() {
        List<VentaResponseDTO> lista = new ArrayList<>();
        List<Venta> ventas = ventaRepository.findAll();

        for (Venta venta : ventas) {
            lista.add(mapearVenta(venta));
        }
        return lista;
    }

    public HttpGlobalResponse<VentaResponseDTO> obtenerVenta(Long id) {
        HttpGlobalResponse<VentaResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Venta> encontrada = ventaRepository.findById(id);

        if (encontrada.isEmpty()) {
            response.setMessage("Venta no encontrada");
            return response;
        }

        response.setMessage("Venta encontrada");
        response.setData(mapearVenta(encontrada.get()));
        return response;
    }

    private VentaResponseDTO mapearVenta(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(venta.getId());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setSubtotal(venta.getSubtotal());
        dto.setIva(venta.getIva());
        dto.setTotal(venta.getTotal());

        if (venta.getEmpleado() != null) {
            dto.setEmpleadoNombre(venta.getEmpleado().getNombre());
        }

        List<VentaResponseDTO.DetalleVentaResponseDTO> detallesDTO = new ArrayList<>();
        if (venta.getDetalles() != null) {
            for (DetalleVenta detalle : venta.getDetalles()) {
                VentaResponseDTO.DetalleVentaResponseDTO detalleDTO = new VentaResponseDTO.DetalleVentaResponseDTO();
                detalleDTO.setId(detalle.getId());
                detalleDTO.setCantidad(detalle.getCantidad());
                detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
                detalleDTO.setSubtotalLinea(detalle.getSubtotalLinea());
                if (detalle.getProducto() != null) {
                    detalleDTO.setProductoNombre(detalle.getProducto().getNombre());
                }
                detallesDTO.add(detalleDTO);
            }
        }
        dto.setDetalles(detallesDTO);
        return dto;
    }
}
