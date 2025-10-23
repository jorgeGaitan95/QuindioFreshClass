package co.edu.uniquindio.SOLID.utils.Mappers;

import co.edu.uniquindio.SOLID.Model.Cliente;
import co.edu.uniquindio.SOLID.Model.DTO.ClienteDTO;

public class ClienteMapper {
    
    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;
        return new ClienteDTO(
            cliente.getCedula(),
            cliente.getNombre(),
            cliente.getCorreo(),
            cliente.getTelefono()
        );
    }
    
    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        return new Cliente(
            dto.getCedula(),
            dto.getNombre(),
            dto.getCorreo(),
            dto.getTelefono()
        );
    }
    
    public static void updateEntityFromDTO(Cliente entity, ClienteDTO dto) {
        if (entity == null || dto == null) return;
        entity.setNombre(dto.getNombre());
        entity.setCorreo(dto.getCorreo());
        entity.setTelefono(dto.getTelefono());
    }
}

