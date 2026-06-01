package com.micromarket.service;

import com.micromarket.dto.*;
import com.micromarket.entity.Categoria;
import com.micromarket.entity.Producto;
import com.micromarket.repository.CategoriaRepository;
import com.micromarket.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public MessageResponseDTO crearProducto(ProductoRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();

        Optional<Producto> existente = productoRepository.findByCodigoBarras(request.getCodigoBarras());
        if (existente.isPresent()) {
            response.setMessage("Ya existe un producto con ese código de barras");
            return response;
        }

        Optional<Categoria> categoria = categoriaRepository.findById(request.getCategoriaId());
        if (categoria.isEmpty()) {
            response.setMessage("La categoría indicada no existe");
            return response;
        }

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setCodigoBarras(request.getCodigoBarras());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock() != null ? request.getStock() : 0);
        producto.setActivo(true);
        producto.setCategoria(categoria.get());
        productoRepository.save(producto);

        response.setMessage("Producto creado correctamente");
        return response;
    }

    public List<ProductoResponseDTO> listarProductos() {
        List<ProductoResponseDTO> lista = new ArrayList<>();
        List<Producto> productos = productoRepository.findAll();

        for (Producto producto : productos) {
            lista.add(mapearProducto(producto));
        }
        return lista;
    }

    public HttpGlobalResponse<ProductoResponseDTO> obtenerProducto(Long id) {
        HttpGlobalResponse<ProductoResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Producto> encontrado = productoRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        response.setMessage("Producto encontrado");
        response.setData(mapearProducto(encontrado.get()));
        return response;
    }

    public HttpGlobalResponse<ProductoResponseDTO> actualizarProducto(Long id, ProductoRequestDTO request) {
        HttpGlobalResponse<ProductoResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Producto> encontrado = productoRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        Producto producto = encontrado.get();

        if (!producto.getCodigoBarras().equals(request.getCodigoBarras())) {
            Optional<Producto> duplicado = productoRepository.findByCodigoBarras(request.getCodigoBarras());
            if (duplicado.isPresent()) {
                response.setMessage("Ya existe un producto con ese código de barras");
                return response;
            }
        }

        Optional<Categoria> categoria = categoriaRepository.findById(request.getCategoriaId());
        if (categoria.isEmpty()) {
            response.setMessage("La categoría indicada no existe");
            return response;
        }

        producto.setNombre(request.getNombre());
        producto.setCodigoBarras(request.getCodigoBarras());
        producto.setPrecio(request.getPrecio());
        if (request.getStock() != null) {
            producto.setStock(request.getStock());
        }
        producto.setCategoria(categoria.get());
        productoRepository.save(producto);

        response.setMessage("Producto actualizado correctamente");
        response.setData(mapearProducto(producto));
        return response;
    }
    
    public MessageResponseDTO desactivarProducto(Long id) {
        MessageResponseDTO response = new MessageResponseDTO();
        Optional<Producto> encontrado = productoRepository.findById(id);

        if (encontrado.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        Producto producto = encontrado.get();
        producto.setActivo(false);
        productoRepository.save(producto);

        response.setMessage("Producto desactivado correctamente");
        return response;
    }

    private ProductoResponseDTO mapearProducto(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setActivo(producto.getActivo());
        if (producto.getCategoria() != null) {
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        }
        return dto;
    }
}
