package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.model.Cliente;
import co.edu.uniquindio.SOLID.model.DTO.ClienteDTO;
import co.edu.uniquindio.SOLID.model.Minimercado;
import co.edu.uniquindio.SOLID.utils.Mappers.ClienteMapper;
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

public class ClienteController implements Initializable {

    @FXML private TableView<ClienteDTO> tblClientes;
    @FXML private TableColumn<ClienteDTO, String> colCedula;
    @FXML private TableColumn<ClienteDTO, String> colNombre;
    @FXML private TableColumn<ClienteDTO, String> colCorreo;
    @FXML private TableColumn<ClienteDTO, String> colTelefono;
    
    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    
    @FXML private Label lblMensaje;

    private Minimercado minimercado;
    private ObservableList<ClienteDTO> clientesDTO;
    private ClienteDTO clienteSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercado = Minimercado.getInstancia();
        clientesDTO = FXCollections.observableArrayList();
        
        configurarTabla();
        cargarClientes();
        configurarSeleccionTabla();
        
        System.out.println("ClienteController inicializado correctamente");
    }

    private void configurarTabla() {
        colCedula.setCellValueFactory(cellData -> cellData.getValue().cedulaProperty());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colCorreo.setCellValueFactory(cellData -> cellData.getValue().correoProperty());
        colTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        
        tblClientes.setItems(clientesDTO);
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
        clientesDTO.clear();
        clientesDTO.addAll(
            minimercado.getClientes().stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList())
        );
        mostrarMensaje("Clientes cargados: " + clientesDTO.size(), false);
    }

    private void cargarDatosEnFormulario(ClienteDTO cliente) {
        txtCedula.setText(cliente.getCedula());
        txtCedula.setDisable(true); // No se puede modificar la cédula
        txtNombre.setText(cliente.getNombre());
        txtCorreo.setText(cliente.getCorreo());
        txtTelefono.setText(cliente.getTelefono());
    }

    @FXML
    void agregarCliente(ActionEvent event) {
        try {
            // Validar campos
            if (!validarCampos()) {
                return;
            }
            
            String cedula = txtCedula.getText().trim();
            
            // Verificar si ya existe un cliente con esa cédula
            if (buscarClientePorCedula(cedula) != null) {
                mostrarMensaje("Error: Ya existe un cliente con la cédula " + cedula, true);
                return;
            }
            
            // Crear DTO
            ClienteDTO nuevoClienteDTO = new ClienteDTO(
                cedula,
                txtNombre.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim()
            );
            
            // Convertir a entidad y agregar al minimercado
            Cliente nuevoCliente = ClienteMapper.toEntity(nuevoClienteDTO);
            minimercado.addCliente(nuevoCliente);
            
            // Recargar tabla
            cargarClientes();
            limpiarFormulario(null);
            
            mostrarMensaje("Cliente agregado exitosamente", false);
            System.out.println("Cliente agregado: " + nuevoCliente);
            
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
            
            // Actualizar DTO
            clienteSeleccionado.setNombre(txtNombre.getText().trim());
            clienteSeleccionado.setCorreo(txtCorreo.getText().trim());
            clienteSeleccionado.setTelefono(txtTelefono.getText().trim());
            
            // Buscar entidad y actualizar
            Cliente clienteEntity = buscarClientePorCedula(clienteSeleccionado.getCedula());
            if (clienteEntity != null) {
                ClienteMapper.updateEntityFromDTO(clienteEntity, clienteSeleccionado);
            }
            
            // Refrescar tabla
            tblClientes.refresh();
            
            mostrarMensaje("Cliente actualizado exitosamente", false);
            System.out.println("Cliente actualizado: " + clienteSeleccionado);
            
            limpiarFormulario(null);
            
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
            
            // Confirmar eliminación
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este cliente?");
            confirmacion.setContentText("Cliente: " + clienteSeleccionado.getNombre() + 
                                       "\nCédula: " + clienteSeleccionado.getCedula());
            
            Optional<ButtonType> resultado = confirmacion.showAndWait();
            
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Buscar y eliminar entidad
                Cliente clienteEntity = buscarClientePorCedula(clienteSeleccionado.getCedula());
                if (clienteEntity != null) {
                    minimercado.getClientes().remove(clienteEntity);
                }
                
                // Recargar tabla
                cargarClientes();
                limpiarFormulario(null);
                
                mostrarMensaje("Cliente eliminado exitosamente", false);
                System.out.println("Cliente eliminado: " + clienteSeleccionado.getCedula());
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
            // Cerrar ventana actual
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
        
        // Validar formato de correo básico
        String correo = txtCorreo.getText().trim();
        if (!correo.contains("@") || !correo.contains(".")) {
            mostrarMensaje("El formato del correo no es válido", true);
            return false;
        }
        
        return true;
    }

    private Cliente buscarClientePorCedula(String cedula) {
        for (Cliente cliente : minimercado.getClientes()) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("ClienteController: " + mensaje);
    }
}
