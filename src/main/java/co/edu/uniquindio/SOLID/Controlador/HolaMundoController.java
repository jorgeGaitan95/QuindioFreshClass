package co.edu.uniquindio.SOLID.Controlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class HolaMundoController {

    @FXML
    private Label lblMensaje;

    @FXML
    void click(ActionEvent event) {
        lblMensaje.setText("Hola Mundo");
        System.out.println("Hola mundo click");
    }
}
