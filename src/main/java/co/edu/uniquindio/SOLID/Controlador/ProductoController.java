package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.model.Producto;
import co.edu.uniquindio.SOLID.model.DTO.ProductoDTO;
import co.edu.uniquindio.SOLID.model.Minimercado;
import co.edu.uniquindio.SOLID.utils.Mappers.ProductoMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductoController implements Initializable {

    @FXML private TableView<ProductoDTO> tblProductos;
    @FXML private TableColumn<ProductoDTO, String> colSku;
    @FXML private TableColumn<ProductoDTO, String> colNombre;
    @FXML private TableColumn<ProductoDTO, Double> colPrecio;
    
    @FXML private TextField txtSku;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    
    @FXML private Label lblMensaje;

    private Minimercado minimercado;
    private ObservableList<ProductoDTO> productosDTO;
    private ProductoDTO productoSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercado = Minimercado.getInstancia();
        productosDTO = FXCollections.observableArrayList();
        
        configurarTabla();
        cargarProductos();
        configurarSeleccionTabla();
        
        System.out.println("ProductoController inicializado correctamente");
    }

    private void configurarTabla() {
        colSku.setCellValueFactory(cellData -> cellData.getValue().skuProperty());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        
        tblProductos.setItems(productosDTO);
    }

    private void configurarSeleccionTabla() {
        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                productoSeleccionado = newSelection;
                cargarDatosEnFormulario(newSelection);
            }
        });
    }

    private void cargarProductos() {
        productosDTO.clear();
        productosDTO.addAll(
            minimercado.getProductos().stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList())
        );
        mostrarMensaje("Productos cargados: " + productosDTO.size(), false);
    }

    private void cargarDatosEnFormulario(ProductoDTO producto) {
        txtSku.setText(producto.getSku());
        txtSku.setDisable(true); // No se puede modificar el SKU
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
    }

    @FXML
    void agregarProducto(ActionEvent event) {
        try {
            // Validar campos
            if (!validarCampos()) {
                return;
            }
            
            String sku = txtSku.getText().trim();
            
            // Verificar si ya existe un producto con ese SKU
            if (buscarProductoPorSku(sku) != null) {
                mostrarMensaje("Error: Ya existe un producto con el SKU " + sku, true);
                return;
            }
            
            // Crear DTO
            ProductoDTO nuevoProductoDTO = new ProductoDTO(
                sku,
                txtNombre.getText().trim(),
                Double.parseDouble(txtPrecio.getText().trim())
            );
            
            // Convertir a entidad y agregar al minimercado
            Producto nuevoProducto = ProductoMapper.toEntity(nuevoProductoDTO);
            minimercado.addProducto(nuevoProducto);
            
            // Recargar tabla
            cargarProductos();
            limpiarFormulario(null);
            
            mostrarMensaje("Producto agregado exitosamente", false);
            System.out.println("Producto agregado: " + nuevoProducto);
            
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: El precio debe ser un número válido", true);
        } catch (Exception e) {
            mostrarMensaje("Error al agregar producto: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void actualizarProducto(ActionEvent event) {
        try {
            if (productoSeleccionado == null) {
                mostrarMensaje("Seleccione un producto de la tabla para actualizar", true);
                return;
            }
            
            if (!validarCampos()) {
                return;
            }
            
            // Actualizar DTO
            productoSeleccionado.setNombre(txtNombre.getText().trim());
            productoSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            
            // Buscar entidad y actualizar
            Producto productoEntity = buscarProductoPorSku(productoSeleccionado.getSku());
            if (productoEntity != null) {
                ProductoMapper.updateEntityFromDTO(productoEntity, productoSeleccionado);
            }
            
            // Refrescar tabla
            tblProductos.refresh();
            
            mostrarMensaje("Producto actualizado exitosamente", false);
            System.out.println("Producto actualizado: " + productoSeleccionado);
            
            limpiarFormulario(null);
            
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: El precio debe ser un número válido", true);
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar producto: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void eliminarProducto(ActionEvent event) {
        try {
            if (productoSeleccionado == null) {
                mostrarMensaje("Seleccione un producto de la tabla para eliminar", true);
                return;
            }
            
            // Confirmar eliminación
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este producto?");
            confirmacion.setContentText("Producto: " + productoSeleccionado.getNombre() + 
                                       "\nSKU: " + productoSeleccionado.getSku() +
                                       "\nPrecio: $" + productoSeleccionado.getPrecio());
            
            Optional<ButtonType> resultado = confirmacion.showAndWait();
            
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Buscar y eliminar entidad
                Producto productoEntity = buscarProductoPorSku(productoSeleccionado.getSku());
                if (productoEntity != null) {
                    minimercado.getProductos().remove(productoEntity);
                }
                
                // Recargar tabla
                cargarProductos();
                limpiarFormulario(null);
                
                mostrarMensaje("Producto eliminado exitosamente", false);
                System.out.println("Producto eliminado: " + productoSeleccionado.getSku());
            }
            
        } catch (Exception e) {
            mostrarMensaje("Error al eliminar producto: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void limpiarFormulario(ActionEvent event) {
        txtSku.clear();
        txtSku.setDisable(false);
        txtNombre.clear();
        txtPrecio.clear();
        
        productoSeleccionado = null;
        tblProductos.getSelectionModel().clearSelection();
        
        mostrarMensaje("Formulario limpio - Listo para nuevo producto", false);
    }

    @FXML
    void volverAPedidos(ActionEvent event) {
        try {
            // Cerrar ventana actual
            Stage stage = (Stage) lblMensaje.getScene().getWindow();
            stage.close();
            
            System.out.println("Volviendo a PedidoView");
            
        } catch (Exception e) {
            mostrarMensaje("Error al navegar: " + e.getMessage(), true);
        }
    }

    private boolean validarCampos() {
        if (txtSku.getText().trim().isEmpty()) {
            mostrarMensaje("El SKU es obligatorio", true);
            return false;
        }
        
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre es obligatorio", true);
            return false;
        }
        
        if (txtPrecio.getText().trim().isEmpty()) {
            mostrarMensaje("El precio es obligatorio", true);
            return false;
        }
        
        // Validar que el precio sea un número válido
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio <= 0) {
                mostrarMensaje("El precio debe ser mayor a 0", true);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("El precio debe ser un número válido", true);
            return false;
        }
        
        return true;
    }

    private Producto buscarProductoPorSku(String sku) {
        for (Producto producto : minimercado.getProductos()) {
            if (producto.getSku().equals(sku)) {
                return producto;
            }
        }
        return null;
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("ProductoController: " + mensaje);
    }
}
