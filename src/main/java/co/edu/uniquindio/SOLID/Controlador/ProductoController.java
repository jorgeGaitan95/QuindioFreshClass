package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.Model.Producto;
import co.edu.uniquindio.SOLID.Model.Minimercado;
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

public class ProductoController implements Initializable {

    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, String> colSku;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    
    @FXML private TextField txtSku;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    
    @FXML private Label lblMensaje;

    private Minimercado minimercado;
    private ObservableList<Producto> productos;
    private Producto productoSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercado = Minimercado.getInstancia();
        productos = FXCollections.observableArrayList();
        
        configurarTabla();
        cargarProductos();
        configurarSeleccionTabla();
        
        System.out.println("ProductoController inicializado correctamente");
    }

    private void configurarTabla() {
        colSku.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSku()));
        colNombre.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colPrecio.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());
        
        tblProductos.setItems(productos);
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
        productos.clear();
        productos.addAll(minimercado.getProductos());
        mostrarMensaje("Productos cargados: " + productos.size(), false);
    }

    private void cargarDatosEnFormulario(Producto producto) {
        txtSku.setText(producto.getSku());
        txtSku.setDisable(true);
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
    }

    @FXML
    void agregarProducto(ActionEvent event) {
        try {
            if (!validarCampos()) {
                return;
            }
            
            Producto nuevoProducto = new Producto(
                txtSku.getText().trim(),
                txtNombre.getText().trim(),
                Double.parseDouble(txtPrecio.getText().trim())
            );
            
            minimercado.agregarProducto(nuevoProducto);
            cargarProductos();
            limpiarFormulario(null);
            mostrarMensaje("Producto agregado exitosamente", false);
            
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
            
            boolean actualizado = minimercado.actualizarProducto(
                productoSeleccionado.getSku(),
                txtNombre.getText().trim(),
                Double.parseDouble(txtPrecio.getText().trim())
            );
            
            if (actualizado) {
                tblProductos.refresh();
                mostrarMensaje("Producto actualizado exitosamente", false);
                limpiarFormulario(null);
            } else {
                mostrarMensaje("Error: No se pudo actualizar el producto", true);
            }
            
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
            
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este producto?");
            confirmacion.setContentText("Producto: " + productoSeleccionado.getNombre() + 
                                       "\nSKU: " + productoSeleccionado.getSku() +
                                       "\nPrecio: $" + productoSeleccionado.getPrecio());
            
            Optional<ButtonType> resultado = confirmacion.showAndWait();
            
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                boolean eliminado = minimercado.eliminarProducto(productoSeleccionado.getSku());
                if (eliminado) {
                    cargarProductos();
                    limpiarFormulario(null);
                    mostrarMensaje("Producto eliminado exitosamente", false);
                } else {
                    mostrarMensaje("Error: No se pudo eliminar el producto", true);
                }
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

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("ProductoController: " + mensaje);
    }
}
