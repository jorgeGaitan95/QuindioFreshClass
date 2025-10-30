package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.Model.*;
import co.edu.uniquindio.SOLID.Model.DTO.*;
import co.edu.uniquindio.SOLID.Service.Fachadas.MinimercadoFacade;
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

public class PedidoController implements Initializable {

    @FXML private ComboBox<ClienteDTO> cmbClientes;
    @FXML private ComboBox<ProductoDTO> cmbProductos;
    @FXML private Spinner<Integer> spnCantidad;
    @FXML private TableView<ItemPedidoDTO> tblItemsPedido;
    @FXML private TableColumn<ItemPedidoDTO, String> colProducto;
    @FXML private TableColumn<ItemPedidoDTO, Integer> colCantidad;
    @FXML private TableColumn<ItemPedidoDTO, Double> colPrecio;
    @FXML private TableColumn<ItemPedidoDTO, Double> colSubtotal;
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

    private MinimercadoFacade minimercadoFacade;
    private ObservableList<ItemPedidoDTO> itemsPedido;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimercadoFacade = new MinimercadoFacade();
        itemsPedido = FXCollections.observableArrayList();
        
        // Configurar tabla
        configurarTabla();
        
        // Cargar datos
        cargarClientes();
        cargarProductos();
        
        // Configurar spinner
        if (spnCantidad != null) {
            spnCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        }
        
        // Configurar combos
        if (cmbMetodoPago != null) {
            cmbMetodoPago.setItems(FXCollections.observableArrayList("TARJETA", "PSE"));
            cmbMetodoPago.setValue("TARJETA");
        }
        
        if (cmbTipoEnvio != null) {
            cmbTipoEnvio.setItems(FXCollections.observableArrayList("ESTANDAR", "EXPRESS"));
            cmbTipoEnvio.setValue("ESTANDAR");
            cmbTipoEnvio.setOnAction(e -> actualizarTotales());
        }
        
        if (cmbTipoNotificacion != null) {
            cmbTipoNotificacion.setItems(FXCollections.observableArrayList("EMAIL", "SMS"));
            cmbTipoNotificacion.setValue("EMAIL");
        }
        
        actualizarTotales();
        System.out.println("PedidoController inicializado correctamente");
    }

    private void configurarTabla() {
        if (tblItemsPedido != null) {
            colProducto.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().skuProducto));
            colCantidad.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().cantidad).asObject());
            colPrecio.setCellValueFactory(cellData -> {
                ProductoDTO p = buscarProductoPorSku(cellData.getValue().skuProducto);
                return new javafx.beans.property.SimpleDoubleProperty(p != null ? p.getPrecio() : 0).asObject();
            });
            colSubtotal.setCellValueFactory(cellData -> {
                ProductoDTO p = buscarProductoPorSku(cellData.getValue().skuProducto);
                double subtotal = p != null ? p.getPrecio() * cellData.getValue().cantidad : 0;
                return new javafx.beans.property.SimpleDoubleProperty(subtotal).asObject();
            });
            
            tblItemsPedido.setItems(itemsPedido);
        }
    }

    private void cargarClientes() {
        if (cmbClientes != null) {
            List<ClienteDTO> clientesDTO = minimercadoFacade.obtenerTodosLosClientes();
            cmbClientes.setItems(FXCollections.observableArrayList(clientesDTO));
            
            // Configurar cómo se muestra el cliente
            cmbClientes.setButtonCell(new ListCell<ClienteDTO>() {
                @Override
                protected void updateItem(ClienteDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            });
            
            cmbClientes.setCellFactory(param -> new ListCell<ClienteDTO>() {
                @Override
                protected void updateItem(ClienteDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            });
        }
    }

    private void cargarProductos() {
        if (cmbProductos != null) {
            List<ProductoDTO> productosDTO = minimercadoFacade.obtenerTodosLosProductos();
            cmbProductos.setItems(FXCollections.observableArrayList(productosDTO));
            
            // Configurar cómo se muestra el producto
            cmbProductos.setButtonCell(new ListCell<ProductoDTO>() {
                @Override
                protected void updateItem(ProductoDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            });
            
            cmbProductos.setCellFactory(param -> new ListCell<ProductoDTO>() {
                @Override
                protected void updateItem(ProductoDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            });
        }
    }

    @FXML
    void agregarItem(ActionEvent event) {
        ProductoDTO productoDTO = cmbProductos.getValue();
        Integer cantidad = spnCantidad.getValue();

        if (productoDTO == null) {
            mostrarError("Seleccione un producto");
            return;
        }

        if (cantidad == null || cantidad <= 0) {
            mostrarError("Ingrese una cantidad válida");
            return;
        }

        ItemPedidoDTO item = new ItemPedidoDTO(productoDTO.getSku(), cantidad);
        itemsPedido.add(item);
        
        actualizarTotales();
        System.out.println("Item agregado: " + productoDTO.getNombre() + " x" + cantidad);
    }

    @FXML
    void eliminarItem(ActionEvent event) {
        ItemPedidoDTO itemSeleccionado = tblItemsPedido.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            itemsPedido.remove(itemSeleccionado);
            actualizarTotales();
            System.out.println("Item eliminado");
        } else {
            mostrarError("Seleccione un item para eliminar");
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

            // Crear DTO del pedido
            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.codigo = "PED-" + System.currentTimeMillis();
            pedidoDTO.idCliente = cmbClientes.getValue().getCedula();
            pedidoDTO.itemsPedido = new ArrayList<>(itemsPedido);
            pedidoDTO.direccionEnvio = txtDireccionEnvio.getText();
            pedidoDTO.notas = txtNotas.getText();
            pedidoDTO.codigoDescuento = txtCodigoDescuento.getText();
            
            // Procesar pedido usando el Facade
            ResumenPedidoDTO resultado = minimercadoFacade.procesarPedido(pedidoDTO);
            
            // Calcular totales
            double subtotal = calcularSubtotal();
            double costoEnvio = calcularCostoEnvio();
            double total = minimercadoFacade.calcularTotal(subtotal, costoEnvio);
            
            // Mostrar resultado
            txtResultado.setText(
                "PEDIDO PROCESADO EXITOSAMENTE\n\n" +
                "Código: " + resultado.codigo + "\n" +
                "Cliente: " + cmbClientes.getValue().getNombre() + "\n" +
                "Items: " + itemsPedido.size() + "\n" +
                "Subtotal: $" + String.format("%.2f", subtotal) + "\n" +
                "Envío " + cmbTipoEnvio.getValue() + ": $" + String.format("%.2f", costoEnvio) + "\n" +
                "Total: $" + String.format("%.2f", total) + "\n" +
                "Método de pago: " + cmbMetodoPago.getValue() + "\n" +
                "Notificación enviada por: " + cmbTipoNotificacion.getValue()
            );
            
            System.out.println("Pedido procesado exitosamente: " + resultado.codigo);
            
            // Limpiar formulario después de procesar
            limpiarFormulario(event);
            
        } catch (IllegalArgumentException e) {
            // Errores de negocio (por ejemplo, stock insuficiente)
            mostrarError(e.getMessage());
            txtResultado.setText("");
        } catch (Exception e) {
            txtResultado.setText("ERROR AL PROCESAR PEDIDO:\n" + e.getMessage());
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
        if (cmbMetodoPago != null) cmbMetodoPago.setValue("TARJETA");
        if (cmbTipoEnvio != null) cmbTipoEnvio.setValue("ESTANDAR");
        if (cmbTipoNotificacion != null) cmbTipoNotificacion.setValue("EMAIL");
        actualizarTotales();
        System.out.println("Formulario limpiado");
    }

    @FXML
    void gestionarClientes(ActionEvent event) {
        try {
            System.out.println("Navegando a gestión de clientes");
            
            // Cargar ClienteView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clienteView.fxml"));
            Parent root = loader.load();
            Stage clienteStage = new Stage();
            clienteStage.setTitle("Sistema Quindío Fresh - Gestión de Clientes");
            clienteStage.setScene(new Scene(root, 800, 600));
            
            // Agregar listener para recargar clientes cuando se cierre la ventana
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
            
            // Cargar ProductoView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/productoView.fxml"));
            Parent root = loader.load();
            Stage productoStage = new Stage();
            productoStage.setTitle("Sistema Quindío Fresh - Gestión de Productos");
            productoStage.setScene(new Scene(root, 800, 600));
            
            // Agregar listener para recargar productos cuando se cierre la ventana
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
        double subtotal = calcularSubtotal();
        double costoEnvio = calcularCostoEnvio();
        double total = minimercadoFacade.calcularTotal(subtotal, costoEnvio);

        if (lblSubtotal != null) lblSubtotal.setText(String.format("$%.2f", subtotal));
        if (lblCostoEnvio != null) lblCostoEnvio.setText(String.format("$%.2f", costoEnvio));
        if (lblTotal != null) lblTotal.setText(String.format("$%.2f", total));
    }

    private double calcularSubtotal() {
        return minimercadoFacade.calcularSubtotal(itemsPedido);
    }

    private double calcularCostoEnvio() {
        String tipoEnvio = cmbTipoEnvio != null ? cmbTipoEnvio.getValue() : "ESTANDAR";
        return minimercadoFacade.calcularCostoEnvio(tipoEnvio);
    }

    private ProductoDTO buscarProductoPorSku(String sku) {
        return minimercadoFacade.buscarProductoPorSku(sku);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}