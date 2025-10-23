package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.Model.*;
import co.edu.uniquindio.SOLID.Model.Pedido.ItemPedido;
import co.edu.uniquindio.SOLID.Model.Pedido.Pedido;
import co.edu.uniquindio.SOLID.utils.JFXPaths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador simplificado que usa directamente las entidades del modelo
 */
public class PedidoController implements Initializable {

    @FXML private ComboBox<Cliente> cmbClientes;
    @FXML private ComboBox<Producto> cmbProductos;
    @FXML private Spinner<Integer> spnCantidad;
    @FXML private TableView<ItemPedido> tblItemsPedido;
    @FXML private TableColumn<ItemPedido, String> colProducto;
    @FXML private TableColumn<ItemPedido, Integer> colCantidad;
    @FXML private TableColumn<ItemPedido, Double> colPrecio;
    @FXML private TableColumn<ItemPedido, Double> colSubtotal;
    @FXML private TextField txtDireccionEnvio;
    @FXML private TextArea txtNotas;
    @FXML private TextField txtCodigoDescuento;
    @FXML private ComboBox<String> cmbMetodoPago;
    @FXML private ComboBox<String> cmbTipoEnvio;
    @FXML private ComboBox<String> cmbTipoNotificacion;
    @FXML private Label lblSubtotal;
    @FXML private Label lblCostoEnvio;
    @FXML private Label lblTotal;
    @FXML private TextArea txtResultado;

    private Minimercado minimercado;
    private ObservableList<ItemPedido> itemsPedido;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercado = Minimercado.getInstancia();
        itemsPedido = FXCollections.observableArrayList();
        
        configurarTabla();
        cargarClientes();
        cargarProductos();
        configurarComboBoxes();
        configurarSpinner();
        
        System.out.println("PedidoController inicializado correctamente");
    }

    private void configurarTabla() {
        if (tblItemsPedido != null) {
            colProducto.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty(
                    cellData.getValue().getProducto().getNombre()));
            colCantidad.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleIntegerProperty(
                    cellData.getValue().getCantidad()).asObject());
            colPrecio.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleDoubleProperty(
                    cellData.getValue().getProducto().getPrecio()).asObject());
            colSubtotal.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleDoubleProperty(
                    cellData.getValue().calcularSubtotal()).asObject());
            
            tblItemsPedido.setItems(itemsPedido);
        }
    }

    private void cargarClientes() {
        if (cmbClientes != null) {
            List<Cliente> clientes = minimercado.getClientes();
            cmbClientes.setItems(FXCollections.observableArrayList(clientes));
            
            cmbClientes.setButtonCell(new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " (" + item.getCedula() + ")");
                    }
                }
            });

            cmbClientes.setCellFactory(param -> new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " (" + item.getCedula() + ")");
                    }
                }
            });
        }
    }

    private void cargarProductos() {
        if (cmbProductos != null) {
            List<Producto> productos = minimercado.getProductos();
            cmbProductos.setItems(FXCollections.observableArrayList(productos));
            
            cmbProductos.setButtonCell(new ListCell<Producto>() {
                @Override
                protected void updateItem(Producto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " - $" + item.getPrecio());
                    }
                }
            });

            cmbProductos.setCellFactory(param -> new ListCell<Producto>() {
                @Override
                protected void updateItem(Producto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " - $" + item.getPrecio());
                    }
                }
            });
        }
    }

    private void configurarComboBoxes() {
        if (cmbMetodoPago != null) {
            cmbMetodoPago.setItems(FXCollections.observableArrayList("PSE", "TARJETA_CREDITO"));
            cmbMetodoPago.setValue("PSE");
        }
        
        if (cmbTipoEnvio != null) {
            cmbTipoEnvio.setItems(FXCollections.observableArrayList("ESTANDAR", "EXPRESS"));
            cmbTipoEnvio.setValue("ESTANDAR");
        }
        
        if (cmbTipoNotificacion != null) {
            cmbTipoNotificacion.setItems(FXCollections.observableArrayList("EMAIL", "SMS"));
            cmbTipoNotificacion.setValue("EMAIL");
        }
    }

    private void configurarSpinner() {
        if (spnCantidad != null) {
            spnCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        }
    }

    @FXML
    void agregarItem(ActionEvent event) {
        Producto producto = cmbProductos.getValue();
        Integer cantidad = spnCantidad.getValue();

        if (producto == null) {
            mostrarError("Seleccione un producto");
            return;
        }

        if (cantidad == null || cantidad <= 0) {
            mostrarError("Ingrese una cantidad válida");
            return;
        }

        ItemPedido item = new ItemPedido(producto, cantidad);
        itemsPedido.add(item);

        actualizarTotales();
        System.out.println("Item agregado: " + producto.getNombre() + " x" + cantidad);
    }

    @FXML
    void eliminarItem(ActionEvent event) {
        ItemPedido itemSeleccionado = tblItemsPedido.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            itemsPedido.remove(itemSeleccionado);
            actualizarTotales();
            System.out.println("Item eliminado: " + itemSeleccionado.getProducto().getNombre());
        } else {
            mostrarError("Seleccione un item de la tabla para eliminar");
        }
    }

    @FXML
    void procesarPedido(ActionEvent event) {
        try {
            // Validaciones
            if (cmbClientes.getValue() == null) {
                mostrarError("Seleccione un cliente");
                return;
            }

            if (itemsPedido.isEmpty()) {
                mostrarError("Agregue al menos un producto al pedido");
                return;
            }

            if (txtDireccionEnvio.getText().trim().isEmpty()) {
                mostrarError("Ingrese la dirección de envío");
                return;
            }

            // Obtener datos del formulario
            Cliente cliente = cmbClientes.getValue();
            List<ItemPedido> items = new ArrayList<>(itemsPedido);
            String direccionEnvio = txtDireccionEnvio.getText();
            String notas = txtNotas.getText();
            String codigoDescuento = txtCodigoDescuento.getText();
            String tipoEnvio = cmbTipoEnvio.getValue();
            String metodoPago = cmbMetodoPago.getValue();
            String tipoNotificacion = cmbTipoNotificacion.getValue();
            
            // Procesar pedido usando Minimercado (que usa los patrones Factory)
            Pedido pedido = minimercado.procesarPedido(
                cliente, items, direccionEnvio, notas, codigoDescuento,
                tipoEnvio, metodoPago, tipoNotificacion
            );
            
            // Mostrar resultado
            txtResultado.setText(
                "✅ PEDIDO PROCESADO EXITOSAMENTE\n\n" +
                "Código: " + pedido.getCodigo() + "\n" +
                "Cliente: " + cliente.getNombre() + "\n" +
                "Items: " + pedido.getItems().size() + "\n" +
                "Subtotal: $" + String.format("%.2f", pedido.calcularSubtotal()) + "\n" +
                "Envío " + tipoEnvio + ": $" + String.format("%.2f", pedido.calcularCostoEnvio()) + "\n" +
                "Total: $" + String.format("%.2f", pedido.calcularTotal()) + "\n" +
                "Método de pago: " + metodoPago + "\n" +
                "Notificación enviada por: " + tipoNotificacion
            );
            
            System.out.println("Pedido procesado exitosamente: " + pedido.getCodigo());
            
            // Limpiar formulario después de procesar
            limpiarFormulario(event);
            
        } catch (Exception e) {
            txtResultado.setText("❌ ERROR AL PROCESAR PEDIDO:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void limpiarFormulario(ActionEvent event) {
        itemsPedido.clear();
        if (cmbClientes != null) cmbClientes.setValue(null);
        if (cmbProductos != null) cmbProductos.setValue(null);
        if (spnCantidad != null) spnCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        if (txtDireccionEnvio != null) txtDireccionEnvio.clear();
        if (txtNotas != null) txtNotas.clear();
        if (txtCodigoDescuento != null) txtCodigoDescuento.clear();
        if (cmbMetodoPago != null) cmbMetodoPago.setValue("PSE");
        if (cmbTipoEnvio != null) cmbTipoEnvio.setValue("ESTANDAR");
        if (cmbTipoNotificacion != null) cmbTipoNotificacion.setValue("EMAIL");
        if (txtResultado != null) txtResultado.clear();
        
        actualizarTotales();
        System.out.println("Formulario limpiado");
    }

    @FXML
    void gestionarClientes(ActionEvent event) {
        try {
            System.out.println("Navegando a gestión de clientes");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(JFXPaths.CLIENTE_VIEW));
            Parent root = loader.load();
            
            Stage clienteStage = new Stage();
            clienteStage.setTitle("Gestión de Clientes");
            clienteStage.setScene(new Scene(root));
            
            clienteStage.setOnHidden(e -> {
                cargarClientes();
                System.out.println("Clientes recargados después de cerrar ventana");
            });
            
            clienteStage.show();
            
        } catch (Exception e) {
            mostrarError("Error al abrir gestión de clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void gestionarProductos(ActionEvent event) {
        try {
            System.out.println("Navegando a gestión de productos");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(JFXPaths.PRODUCTO_VIEW));
            Parent root = loader.load();
            
            Stage productoStage = new Stage();
            productoStage.setTitle("Gestión de Productos");
            productoStage.setScene(new Scene(root));
            
            productoStage.setOnHidden(e -> {
                cargarProductos();
                System.out.println("Productos recargados después de cerrar ventana");
            });
            
            productoStage.show();
            
        } catch (Exception e) {
            mostrarError("Error al abrir gestión de productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void actualizarTotales() {
        double subtotal = 0;
        for (ItemPedido item : itemsPedido) {
            subtotal += item.calcularSubtotal();
        }
        
        double costoEnvio = calcularCostoEnvio();
        double total = subtotal + costoEnvio;

        if (lblSubtotal != null) lblSubtotal.setText(String.format("$%.2f", subtotal));
        if (lblCostoEnvio != null) lblCostoEnvio.setText(String.format("$%.2f", costoEnvio));
        if (lblTotal != null) lblTotal.setText(String.format("$%.2f", total));
    }

    private double calcularCostoEnvio() {
        String tipoEnvio = cmbTipoEnvio != null ? cmbTipoEnvio.getValue() : "ESTANDAR";
        if (tipoEnvio != null && tipoEnvio.equals("EXPRESS")) {
            return 15000;
        } else {
            return 7000; // ESTANDAR
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
