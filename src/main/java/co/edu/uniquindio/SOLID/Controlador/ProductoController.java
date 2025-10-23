package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.model.Producto;
import co.edu.uniquindio.SOLID.model.Minimercado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductoController implements Initializable {

    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, String> colSku;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private Label lblMensaje;

    private Minimercado minimercado;
    private ObservableList<Producto> productos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercado = Minimercado.getInstancia();
        productos = FXCollections.observableArrayList();
        
        configurarTabla();
        cargarProductos();
        
        System.out.println("ProductoController inicializado correctamente");
    }

    private void configurarTabla() {
        colSku.setCellValueFactory(new PropertyValueFactory<>("sku"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
        tblProductos.setItems(productos);
    }

    private void cargarProductos() {
        productos.clear();
        productos.addAll(minimercado.getProductos());
        mostrarMensaje("Productos cargados: " + productos.size());
    }

    @FXML
    void volverAPedidos(ActionEvent event) {
        try {
            // Cerrar ventana actual y volver a PedidoView
            Stage stage = (Stage) lblMensaje.getScene().getWindow();
            stage.close();
            
            // Abrir PedidoView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pedidoView.fxml"));
            Parent root = loader.load();
            Stage pedidoStage = new Stage();
            pedidoStage.setTitle("Sistema Quindío Fresh - Gestión de Pedidos");
            pedidoStage.setScene(new Scene(root, 900, 700));
            pedidoStage.show();
            
            System.out.println("Navegando a PedidoView");
        } catch (Exception e) {
            mostrarError("Error al navegar: " + e.getMessage());
        }
    }

    private void mostrarMensaje(String mensaje) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("ProductoController: " + mensaje);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}