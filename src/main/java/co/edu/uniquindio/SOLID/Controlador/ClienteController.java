package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.model.Cliente;
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

public class ClienteController implements Initializable {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colCedula;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colCorreo;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private Label lblMensaje;

    private Minimercado minimercado;
    private ObservableList<Cliente> clientes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercado = Minimercado.getInstancia();
        clientes = FXCollections.observableArrayList();
        
        configurarTabla();
        cargarClientes();
        
        System.out.println("ClienteController inicializado correctamente");
    }

    private void configurarTabla() {
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        
        tblClientes.setItems(clientes);
    }

    private void cargarClientes() {
        clientes.clear();
        clientes.addAll(minimercado.getClientes());
        mostrarMensaje("Clientes cargados: " + clientes.size());
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
        System.out.println("ClienteController: " + mensaje);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}