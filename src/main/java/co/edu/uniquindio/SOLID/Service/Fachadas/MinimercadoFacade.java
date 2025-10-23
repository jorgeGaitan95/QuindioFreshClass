package co.edu.uniquindio.SOLID.Service.Fachadas;

import co.edu.uniquindio.SOLID.model.DTO.ClienteDTO;
import co.edu.uniquindio.SOLID.model.DTO.ProductoDTO;
import co.edu.uniquindio.SOLID.model.DTO.PedidoDTO;
import co.edu.uniquindio.SOLID.model.DTO.ItemPedidoDTO;
import co.edu.uniquindio.SOLID.model.DTO.ResumenPedidoDTO;
import co.edu.uniquindio.SOLID.model.Pedido;
import co.edu.uniquindio.SOLID.Service.ClienteService;
import co.edu.uniquindio.SOLID.Service.ProductoService;
import co.edu.uniquindio.SOLID.Service.PedidoService;
import co.edu.uniquindio.SOLID.Service.CatalogoProductosService;

import java.util.List;

/**
 * Facade principal que simplifica el acceso a todos los servicios del sistema
 * Los controladores solo interactúan con este Facade
 */
public class MinimercadoFacade {
    
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final PedidoService pedidoService;
    
    public MinimercadoFacade() {
        this.clienteService = new ClienteService();
        this.productoService = new ProductoService();
        
        // Crear PedidoService con sus dependencias
        CatalogoProductosService catalogoProductosService = new CatalogoProductosService();
        this.pedidoService = new PedidoService(catalogoProductosService);
    }
    
    // ========== OPERACIONES DE CLIENTES ==========
    
    /**
     * Obtiene todos los clientes
     */
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }
    
    /**
     * Busca un cliente por cédula
     */
    public ClienteDTO buscarClientePorCedula(String cedula) {
        return clienteService.buscarClientePorCedula(cedula);
    }
    
    /**
     * Agrega un nuevo cliente
     */
    public boolean agregarCliente(ClienteDTO clienteDTO) {
        return clienteService.agregarCliente(clienteDTO);
    }
    
    /**
     * Actualiza un cliente existente
     */
    public boolean actualizarCliente(ClienteDTO clienteDTO) {
        return clienteService.actualizarCliente(clienteDTO);
    }
    
    /**
     * Elimina un cliente por cédula
     */
    public boolean eliminarCliente(String cedula) {
        return clienteService.eliminarCliente(cedula);
    }
    
    /**
     * Valida si existe un cliente
     */
    public boolean existeCliente(String cedula) {
        return clienteService.existeCliente(cedula);
    }
    
    // ========== OPERACIONES DE PRODUCTOS ==========
    
    /**
     * Obtiene todos los productos
     */
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }
    
    /**
     * Busca un producto por SKU
     */
    public ProductoDTO buscarProductoPorSku(String sku) {
        return productoService.buscarProductoPorSku(sku);
    }
    
    /**
     * Agrega un nuevo producto
     */
    public boolean agregarProducto(ProductoDTO productoDTO) {
        return productoService.agregarProducto(productoDTO);
    }
    
    /**
     * Actualiza un producto existente
     */
    public boolean actualizarProducto(ProductoDTO productoDTO) {
        return productoService.actualizarProducto(productoDTO);
    }
    
    /**
     * Elimina un producto por SKU
     */
    public boolean eliminarProducto(String sku) {
        return productoService.eliminarProducto(sku);
    }
    
    /**
     * Valida si existe un producto
     */
    public boolean existeProducto(String sku) {
        return productoService.existeProducto(sku);
    }
    
    // ========== OPERACIONES DE PEDIDOS ==========
    
    /**
     * Procesa un pedido completo
     */
    public ResumenPedidoDTO procesarPedido(PedidoDTO pedidoDTO) {
        // Usar PedidoService directamente para crear el pedido
        Pedido pedidoCreado = pedidoService.crearPedido(pedidoDTO);
        
        // Crear resumen del pedido
        ResumenPedidoDTO resumen = new ResumenPedidoDTO();
        resumen.codigo = pedidoCreado.getCodigo();
        resumen.nombreCliente = pedidoCreado.getCliente().getNombre();
        resumen.items = pedidoDTO.itemsPedido; // Usar los items del DTO original
        resumen.direccionEnvio = pedidoDTO.direccionEnvio;
        resumen.notas = pedidoDTO.notas;
        resumen.codigoDescuento = pedidoDTO.codigoDescuento;
        resumen.subtotal = calcularSubtotal(pedidoDTO.itemsPedido);
        resumen.costoEnvio = calcularCostoEnvio("ESTANDAR"); // Por defecto
        resumen.total = calcularTotal(resumen.subtotal, resumen.costoEnvio);
        resumen.estado = "PROCESADO";
        
        return resumen;
    }
    
    /**
     * Calcula el subtotal de items de pedido
     */
    public double calcularSubtotal(List<ItemPedidoDTO> items) {
        double subtotal = 0;
        for (ItemPedidoDTO item : items) {
            ProductoDTO producto = buscarProductoPorSku(item.skuProducto);
            if (producto != null) {
                subtotal += producto.getPrecio() * item.cantidad;
            }
        }
        return subtotal;
    }
    
    /**
     * Calcula el costo de envío
     */
    public double calcularCostoEnvio(String tipoEnvio) {
        if (tipoEnvio != null && tipoEnvio.equals("EXPRESS")) {
            return 15000; // EXPRESS
        } else {
            return 7000; // ESTANDAR
        }
    }
    
    /**
     * Calcula el total del pedido
     */
    public double calcularTotal(double subtotal, double costoEnvio) {
        return subtotal + costoEnvio;
    }
}
