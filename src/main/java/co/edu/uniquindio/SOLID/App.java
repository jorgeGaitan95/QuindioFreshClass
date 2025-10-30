package co.edu.uniquindio.SOLID;

import co.edu.uniquindio.SOLID.utils.AppSetup;
import co.edu.uniquindio.SOLID.utils.JFXPaths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Inicializar datos del sistema
        AppSetup app = new AppSetup();
        
        // Cargar vista principal de pedidos
        BorderPane load = FXMLLoader.load(getClass().getResource(JFXPaths.PEDIDO_VIEW));
        Scene scene = new Scene(load, 900, 700);
        stage.setTitle("Sistema Quindío Fresh - Gestión de Pedidos");
        stage.setScene(scene);
        stage.show();
    }
}