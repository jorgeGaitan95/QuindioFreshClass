package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.Model.Empleado;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class EmpleadosController implements Initializable {

    @FXML private TextField txtEmpId;
    @FXML private TextField txtEmpNombre;
    @FXML private ComboBox<String> cmbEmpRol;
    @FXML private TableView<Empleado> tblEmpleados;
    @FXML private TableColumn<Empleado, String> colEmpId;
    @FXML private TableColumn<Empleado, String> colEmpNombre;
    @FXML private TableColumn<Empleado, String> colEmpRol;
    @FXML private TableColumn<Empleado, String> colEmpEstado;

    private ObservableList<Empleado> empleados;
    private Minimercado minimercado = Minimercado.getInstancia();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empleados = FXCollections.observableArrayList(minimercado.getEmpleados());
        
        if (cmbEmpRol != null) {
            cmbEmpRol.setItems(FXCollections.observableArrayList("CAJERO", "BODEGUERO"));
            cmbEmpRol.setValue("CAJERO");
        }
        if (tblEmpleados != null) {
            colEmpId.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getId()));
            colEmpNombre.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getNombre()));
            colEmpRol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getRol().name()));
            colEmpEstado.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().isActivo() ? "Activo" : "Inactivo"));
            tblEmpleados.setItems(empleados);
        }
    }

    @FXML
    void crearEmpleado() {
        String id = txtEmpId != null ? txtEmpId.getText() : null;
        String nombre = txtEmpNombre != null ? txtEmpNombre.getText() : null;
        String rol = cmbEmpRol != null ? cmbEmpRol.getValue() : null;
        
        // Validaciones de campos
        if (id == null || id.trim().isEmpty()) {
            mostrarError("El ID es obligatorio");
            return;
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarError("El nombre es obligatorio");
            return;
        }
        if (rol == null) {
            mostrarError("El rol es obligatorio");
            return;
        }
        
        try {
            Empleado emp = minimercado.crearEmpleado(id, nombre, rol);
            empleados.add(emp);
            if (tblEmpleados != null) tblEmpleados.refresh();
            if (txtEmpId != null) txtEmpId.clear();
            if (txtEmpNombre != null) txtEmpNombre.clear();
            if (cmbEmpRol != null) cmbEmpRol.setValue("CAJERO");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void actualizarEmpleado() {
        String id = txtEmpId != null ? txtEmpId.getText() : null;
        String nombre = txtEmpNombre != null ? txtEmpNombre.getText() : null;
        String rol = cmbEmpRol != null ? cmbEmpRol.getValue() : null;
        if (id == null || id.trim().isEmpty()) { mostrarError("El ID es obligatorio"); return; }
        try {
            Empleado actualizado = minimercado.actualizarEmpleado(id, nombre, rol, null);
            for (int i = 0; i < empleados.size(); i++) {
                if (empleados.get(i).getId().equals(id)) { empleados.set(i, actualizado); break; }
            }
            if (tblEmpleados != null) tblEmpleados.refresh();
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void eliminarEmpleado() {
        String id = txtEmpId != null ? txtEmpId.getText() : null;
        if (id == null || id.trim().isEmpty()) { mostrarError("El ID es obligatorio"); return; }
        try {
            minimercado.eliminarEmpleado(id);
            empleados.removeIf(e -> e.getId().equals(id));
            if (tblEmpleados != null) tblEmpleados.refresh();
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void activarEmpleado() {
        String id = txtEmpId != null ? txtEmpId.getText() : null;
        if (id == null || id.trim().isEmpty()) { mostrarError("El ID es obligatorio"); return; }
        try {
            Empleado actualizado = minimercado.actualizarEmpleado(id, null, null, true);
            for (int i = 0; i < empleados.size(); i++) { if (empleados.get(i).getId().equals(id)) { empleados.set(i, actualizado); break; } }
            if (tblEmpleados != null) tblEmpleados.refresh();
        } catch (IllegalArgumentException e) { mostrarError(e.getMessage()); }
    }

    @FXML
    void inactivarEmpleado() {
        String id = txtEmpId != null ? txtEmpId.getText() : null;
        if (id == null || id.trim().isEmpty()) { mostrarError("El ID es obligatorio"); return; }
        try {
            Empleado actualizado = minimercado.actualizarEmpleado(id, null, null, false);
            for (int i = 0; i < empleados.size(); i++) { if (empleados.get(i).getId().equals(id)) { empleados.set(i, actualizado); break; } }
            if (tblEmpleados != null) tblEmpleados.refresh();
        } catch (IllegalArgumentException e) { mostrarError(e.getMessage()); }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


