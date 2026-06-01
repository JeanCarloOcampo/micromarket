package com.micromarket.service;

import com.micromarket.dto.*;
import com.micromarket.entity.Categoria;
import com.micromarket.entity.Producto;
import com.micromarket.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public MessageResponseDTO crearCategoria(CategoriaRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();

        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoriaRepository.save(categoria);

        response.setMessage("Categoría creada correctamente");
        return response;
    }

    public List<CategoriaResponseDTO> listarCategorias() {
        List<CategoriaResponseDTO> lista = new ArrayList<>();
        List<Categoria> categorias = categoriaRepository.findAll();

        for (Categoria categoria : categorias) {
            CategoriaResponseDTO dto = mapearCategoria(categoria);
            lista.add(dto);
        }
        return lista;
    }

    public HttpGlobalResponse<CategoriaResponseDTO> obtenerCategoria(Long id) {
        HttpGlobalResponse<CategoriaResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Categoria> encontrada = categoriaRepository.findById(id);

        if (encontrada.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        response.setMessage("Categoría encontrada");
        response.setData(mapearCategoria(encontrada.get()));
        return response;
    }

    public HttpGlobalResponse<CategoriaResponseDTO> actualizarCategoria(Long id, CategoriaRequestDTO request) {
        HttpGlobalResponse<CategoriaResponseDTO> response = new HttpGlobalResponse<>();
        Optional<Categoria> encontrada = categoriaRepository.findById(id);

        if (encontrada.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        Categoria categoria = encontrada.get();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoriaRepository.save(categoria);

        response.setMessage("Categoría actualizada correctamente");
        response.setData(mapearCategoria(categoria));
        return response;
    }

    public MessageResponseDTO eliminarCategoria(Long id) {
        MessageResponseDTO response = new MessageResponseDTO();
        Optional<Categoria> encontrada = categoriaRepository.findById(id);

        if (encontrada.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        categoriaRepository.deleteById(id);
        response.setMessage("Categoría eliminada correctamente");
        return response;
    }

    private CategoriaResponseDTO mapearCategoria(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());

        List<ProductoSimpleDTO> productosActivos = new ArrayList<>();
        if (categoria.getProductos() != null) {
            for (Producto producto : categoria.getProductos()) {
                if (Boolean.TRUE.equals(producto.getActivo())) {
                    ProductoSimpleDTO productoDTO = new ProductoSimpleDTO();
                    productoDTO.setId(producto.getId());
                    productoDTO.setNombre(producto.getNombre());
                    productoDTO.setCodigoBarras(producto.getCodigoBarras());
                    productoDTO.setPrecio(producto.getPrecio());
                    productoDTO.setStock(producto.getStock());
                    productosActivos.add(productoDTO);
                }
            }
        }
        dto.setProductos(productosActivos);
        return dto;
    }
}
