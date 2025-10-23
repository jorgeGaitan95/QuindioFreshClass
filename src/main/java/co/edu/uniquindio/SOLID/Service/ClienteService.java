package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.Model.Cliente;
import co.edu.uniquindio.SOLID.Model.DTO.ClienteDTO;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.utils.Mappers.ClienteMapper;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    
    private final Minimercado minimercado;
    
    public ClienteService() {
        this.minimercado = Minimercado.getInstancia();
    }

    /**
     * Obtiene todos los clientes como DTOs
     */
    public List<ClienteDTO> obtenerTodosLosClientes() {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : minimercado.getClientes()) {
            clientesDTO.add(ClienteMapper.toDTO(cliente));
        }
        return clientesDTO;
    }
    
    /**
     * Busca un cliente por cédula
     */
    public ClienteDTO buscarClientePorCedula(String cedula) {
        Cliente cliente = buscarClienteEntity(cedula);
        return cliente != null ? ClienteMapper.toDTO(cliente) : null;
    }
    
    /**
     * Agrega un nuevo cliente
     */
    public boolean agregarCliente(ClienteDTO clienteDTO) {
        // Validar que no exista
        if (buscarClienteEntity(clienteDTO.getCedula()) != null) {
            return false;
        }
        
        // Convertir a entidad y agregar
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);
        minimercado.addCliente(cliente);
        return true;
    }
    
    /**
     * Actualiza un cliente existente
     */
    public boolean actualizarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = buscarClienteEntity(clienteDTO.getCedula());
        if (cliente == null) {
            return false;
        }
        
        ClienteMapper.updateEntityFromDTO(cliente, clienteDTO);
        return true;
    }
    
    /**
     * Elimina un cliente por cédula
     */
    public boolean eliminarCliente(String cedula) {
        Cliente cliente = buscarClienteEntity(cedula);
        if (cliente == null) {
            return false;
        }
        
        minimercado.getClientes().remove(cliente);
        return true;
    }
    
    /**
     * Valida si existe un cliente con la cédula dada
     */
    public boolean existeCliente(String cedula) {
        return buscarClienteEntity(cedula) != null;
    }
    
    /**
     * Busca un cliente entity por cédula (para uso interno de servicios)
     */
    public Cliente buscarClienteEntity(String cedula) {
        for (Cliente cliente : minimercado.getClientes()) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }
}
