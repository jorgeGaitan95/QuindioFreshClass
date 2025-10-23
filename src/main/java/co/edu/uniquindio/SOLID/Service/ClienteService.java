package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.model.Cliente;
import co.edu.uniquindio.SOLID.model.Minimercado;
import co.edu.uniquindio.SOLID.model.Producto;

public class ClienteService {

    public Cliente buscarCliente (String cedula) {
        for (Cliente cliente : Minimercado.getInstancia().getClientes()) {
            if (cliente.getCedula().equalsIgnoreCase(cedula)) {
                return cliente;
            }
        }
        return null;
    }
}
