package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.Model.Cliente;
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

public class ClienteController implements Initializable {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colCedula;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colCorreo;
    @FXML private TableColumn<Cliente, String> colTelefono;
    
    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    
    @FXML private Label lblMensaje;

    private Minimercado minimercado;
    private ObservableList<Cliente> clientes;
    private Cliente clienteSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercado = Minimercado.getInstancia();
        clientes = FXCollections.observableArrayList();
        
        configurarTabla();
        cargarClientes();
        configurarSeleccionTabla();
        
        System.out.println("ClienteController inicializado correctamente");
    }

    private void configurarTabla() {
        colCedula.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCedula()));
        colNombre.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colCorreo.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCorreo()));
        colTelefono.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
        
        tblClientes.setItems(clientes);
    }

    private void configurarSeleccionTabla() {
        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                clienteSeleccionado = newSelection;
                cargarDatosEnFormulario(newSelection);
            }
        });
    }

    private void cargarClientes() {
        clientes.clear();
        clientes.addAll(minimercado.getClientes());
        mostrarMensaje("Clientes cargados: " + clientes.size(), false);
    }

    private void cargarDatosEnFormulario(Cliente cliente) {
        txtCedula.setText(cliente.getCedula());
        txtCedula.setDisable(true);
        txtNombre.setText(cliente.getNombre());
        txtCorreo.setText(cliente.getCorreo());
        txtTelefono.setText(cliente.getTelefono());
    }

    @FXML
    void agregarCliente(ActionEvent event) {
        try {
            if (!validarCampos()) {
                return;
            }
            
            Cliente nuevoCliente = new Cliente(
                txtNombre.getText().trim(),
                txtCedula.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim()
            );
            
            minimercado.agregarCliente(nuevoCliente);
            cargarClientes();
            limpiarFormulario(null);
            mostrarMensaje("Cliente agregado exitosamente", false);
            
        } catch (Exception e) {
            mostrarMensaje("Error al agregar cliente: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void actualizarCliente(ActionEvent event) {
        try {
            if (clienteSeleccionado == null) {
                mostrarMensaje("Seleccione un cliente de la tabla para actualizar", true);
                return;
            }
            
            if (!validarCampos()) {
                return;
            }
            
            boolean actualizado = minimercado.actualizarCliente(
                clienteSeleccionado.getCedula(),
                txtNombre.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim()
            );
            
            if (actualizado) {
                tblClientes.refresh();
                mostrarMensaje("Cliente actualizado exitosamente", false);
                limpiarFormulario(null);
            } else {
                mostrarMensaje("Error: No se pudo actualizar el cliente", true);
            }
            
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar cliente: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void eliminarCliente(ActionEvent event) {
        try {
            if (clienteSeleccionado == null) {
                mostrarMensaje("Seleccione un cliente de la tabla para eliminar", true);
                return;
            }
            
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este cliente?");
            confirmacion.setContentText("Cliente: " + clienteSeleccionado.getNombre() + 
                                       "\nCédula: " + clienteSeleccionado.getCedula());
            
            Optional<ButtonType> resultado = confirmacion.showAndWait();
            
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                boolean eliminado = minimercado.eliminarCliente(clienteSeleccionado.getCedula());
                if (eliminado) {
                    cargarClientes();
                    limpiarFormulario(null);
                    mostrarMensaje("Cliente eliminado exitosamente", false);
                } else {
                    mostrarMensaje("Error: No se pudo eliminar el cliente", true);
                }
            }
            
        } catch (Exception e) {
            mostrarMensaje("Error al eliminar cliente: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void limpiarFormulario(ActionEvent event) {
        txtCedula.clear();
        txtCedula.setDisable(false);
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        
        clienteSeleccionado = null;
        tblClientes.getSelectionModel().clearSelection();
        
        mostrarMensaje("Formulario limpio - Listo para nuevo cliente", false);
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
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarMensaje("La cédula es obligatoria", true);
            return false;
        }
        
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre es obligatorio", true);
            return false;
        }
        
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarMensaje("El correo es obligatorio", true);
            return false;
        }
        
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("El teléfono es obligatorio", true);
            return false;
        }
        
        String correo = txtCorreo.getText().trim();
        if (!correo.contains("@") || !correo.contains(".")) {
            mostrarMensaje("El formato del correo no es válido", true);
            return false;
        }
        
        return true;
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("ClienteController: " + mensaje);
    }
}
