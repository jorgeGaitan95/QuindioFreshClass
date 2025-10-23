package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.Model.Producto;
import co.edu.uniquindio.SOLID.Model.DTO.ProductoDTO;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.utils.Mappers.ProductoMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    
    private final Minimercado minimercado;
    
    public ProductoService() {
        this.minimercado = Minimercado.getInstancia();
    }
    
    /**
     * Obtiene todos los productos como DTOs
     */
    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<ProductoDTO> productosDTO = new ArrayList<>();
        for (Producto producto : minimercado.getProductos()) {
            productosDTO.add(ProductoMapper.toDTO(producto));
        }
        return productosDTO;
    }
    
    /**
     * Busca un producto por SKU
     */
    public ProductoDTO buscarProductoPorSku(String sku) {
        Producto producto = buscarProductoEntity(sku);
        return producto != null ? ProductoMapper.toDTO(producto) : null;
    }
    
    /**
     * Agrega un nuevo producto
     */
    public boolean agregarProducto(ProductoDTO productoDTO) {
        // Validar que no exista
        if (buscarProductoEntity(productoDTO.getSku()) != null) {
            return false;
        }
        
        // Convertir a entidad y agregar
        Producto producto = ProductoMapper.toEntity(productoDTO);
        minimercado.addProducto(producto);
        return true;
    }
    
    /**
     * Actualiza un producto existente
     */
    public boolean actualizarProducto(ProductoDTO productoDTO) {
        Producto producto = buscarProductoEntity(productoDTO.getSku());
        if (producto == null) {
            return false;
        }
        
        ProductoMapper.updateEntityFromDTO(producto, productoDTO);
        return true;
    }
    
    /**
     * Elimina un producto por SKU
     */
    public boolean eliminarProducto(String sku) {
        Producto producto = buscarProductoEntity(sku);
        if (producto == null) {
            return false;
        }
        
        minimercado.getProductos().remove(producto);
        return true;
    }
    
    /**
     * Valida si existe un producto con el SKU dado
     */
    public boolean existeProducto(String sku) {
        return buscarProductoEntity(sku) != null;
    }
    
    /**
     * Busca un producto entity por SKU (para uso interno de servicios)
     */
    public Producto buscarProductoEntity(String sku) {
        for (Producto producto : minimercado.getProductos()) {
            if (producto.getSku().equals(sku)) {
                return producto;
            }
        }
        return null;
    }
}
