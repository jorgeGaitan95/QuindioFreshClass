package co.edu.uniquindio.SOLID;

import co.edu.uniquindio.SOLID.Model.DTO.ItemPedidoDTO;
import co.edu.uniquindio.SOLID.Model.DTO.PedidoDTO;
import co.edu.uniquindio.SOLID.Service.Fachadas.PedidoFacade;
import co.edu.uniquindio.SOLID.utils.AppSetup;
import co.edu.uniquindio.SOLID.utils.JFXPaths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane load = FXMLLoader.load(getClass().getResource(JFXPaths.HOLA_MUNDO));
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
//        AppSetup app = new AppSetup();
//        PedidoFacade pedidoFacade = new PedidoFacade();
//
//        List<ItemPedidoDTO> ProductosPedido = new ArrayList<>();
//        ProductosPedido.add(new ItemPedidoDTO("SKU-01",1));
//        ProductosPedido.add(new ItemPedidoDTO("SKU-02",2));
//        ProductosPedido.add(new ItemPedidoDTO("SKU-03",4));
//
//        PedidoDTO pedidoDTO = new PedidoDTO();
//        pedidoDTO.codigo = "OO1";
//        pedidoDTO.idCliente = "1093";
//        pedidoDTO.itemsPedido = ProductosPedido;
//        pedidoDTO.direccionEnvio = "Cra 5c - 27";
//
//        pedidoFacade.crearPedido(pedidoDTO);
    }
}