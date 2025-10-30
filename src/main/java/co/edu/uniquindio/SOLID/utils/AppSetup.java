package co.edu.uniquindio.SOLID.utils;

import co.edu.uniquindio.SOLID.Model.Cliente;
import co.edu.uniquindio.SOLID.Model.Empleado;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.Model.Producto;
import co.edu.uniquindio.SOLID.Model.Proveedor;

public class AppSetup {

    public AppSetup(){
        inicializarCatalogoProductos();
        inicializarClientes();
        inicializarProveedores();
        inicializarEmpleados();
    }

    public void inicializarCatalogoProductos (){
        Minimercado minimercado = Minimercado.getInstancia();
        
        // Cafés especiales del Quindío
        minimercado.addProducto(new Producto("SKU-001", "Café Quindío Premium", 25000));
        minimercado.addProducto(new Producto("SKU-002", "Café Orgánico Armenia", 32000));
        minimercado.addProducto(new Producto("SKU-003", "Café Tostado Montenegro", 28000));
        minimercado.addProducto(new Producto("SKU-004", "Café Descafeinado Calarcá", 30000));
        
        // Frutas frescas
        minimercado.addProducto(new Producto("SKU-101", "Banano Orgánico (kg)", 5000));
        minimercado.addProducto(new Producto("SKU-102", "Aguacate Hass (kg)", 8000));
        minimercado.addProducto(new Producto("SKU-103", "Mango Tommy (kg)", 6500));
        minimercado.addProducto(new Producto("SKU-104", "Guayaba Pera (kg)", 4500));
        minimercado.addProducto(new Producto("SKU-105", "Lulo (kg)", 7000));
        minimercado.addProducto(new Producto("SKU-106", "Mora (kg)", 8500));
        minimercado.addProducto(new Producto("SKU-107", "Papaya (unidad)", 12000));
        
        // Verduras y hortalizas
        minimercado.addProducto(new Producto("SKU-201", "Tomate Chonto (kg)", 3500));
        minimercado.addProducto(new Producto("SKU-202", "Cebolla Cabezona (kg)", 4000));
        minimercado.addProducto(new Producto("SKU-203", "Zanahoria (kg)", 3000));
        minimercado.addProducto(new Producto("SKU-204", "Lechuga Crespa (unidad)", 2500));
        minimercado.addProducto(new Producto("SKU-205", "Cilantro (atado)", 1500));
        minimercado.addProducto(new Producto("SKU-206", "Pimentón (kg)", 5500));
        
        // Productos lácteos locales
        minimercado.addProducto(new Producto("SKU-301", "Queso Campesino (kg)", 18000));
        minimercado.addProducto(new Producto("SKU-302", "Cuajada Fresca (kg)", 15000));
        minimercado.addProducto(new Producto("SKU-303", "Leche Entera (litro)", 4500));
        minimercado.addProducto(new Producto("SKU-304", "Yogurt Natural (litro)", 8000));
        
        // Panela y derivados
        minimercado.addProducto(new Producto("SKU-401", "Panela Redonda (kg)", 6000));
        minimercado.addProducto(new Producto("SKU-402", "Panela Pulverizada (kg)", 6500));
        minimercado.addProducto(new Producto("SKU-403", "Miel de Panela (litro)", 12000));
        
        // Huevos
        minimercado.addProducto(new Producto("SKU-501", "Huevos Campesinos (docena)", 9000));
        minimercado.addProducto(new Producto("SKU-502", "Huevos Orgánicos (docena)", 12000));
    }

    public void inicializarClientes(){
        Minimercado minimercado = Minimercado.getInstancia();
        
        // Clientes de Armenia
        minimercado.addCliente(new Cliente("1093142401", "María Fernanda López", "mflopez@gmail.com", "3104567890"));
        minimercado.addCliente(new Cliente("1093142402", "Carlos Andrés Gómez", "cagomez@hotmail.com", "3205678901"));
        minimercado.addCliente(new Cliente("1093142403", "Ana María Rodríguez", "amrodriguez@outlook.com", "3156789012"));
        
        // Clientes de Calarcá
        minimercado.addCliente(new Cliente("1094567801", "Juan Pablo Martínez", "jpmartinez@gmail.com", "3107890123"));
        minimercado.addCliente(new Cliente("1094567802", "Laura Cristina Pérez", "lcperez@yahoo.com", "3208901234"));
        minimercado.addCliente(new Cliente("1094567803", "Diego Fernando Silva", "dfsilva@gmail.com", "3159012345"));
        
        // Clientes de Montenegro
        minimercado.addCliente(new Cliente("1095234501", "Sandra Patricia Torres", "sptorres@hotmail.com", "3100123456"));
        minimercado.addCliente(new Cliente("1095234502", "Roberto Carlos Muñoz", "rcmunoz@gmail.com", "3201234567"));
        minimercado.addCliente(new Cliente("1095234503", "Claudia Marcela Vargas", "cmvargas@outlook.com", "3152345678"));
        
        // Clientes de Circasia
        minimercado.addCliente(new Cliente("1096789012", "Luis Eduardo Ramírez", "leramirez@gmail.com", "3103456789"));
        minimercado.addCliente(new Cliente("1096789013", "Patricia Elena Moreno", "pemoreno@hotmail.com", "3204567890"));
        
        // Clientes de Filandia
        minimercado.addCliente(new Cliente("1097345601", "Jorge Iván Castillo", "jicastillo@gmail.com", "3155678901"));
        minimercado.addCliente(new Cliente("1097345602", "Mónica Andrea Herrera", "maherrera@yahoo.com", "3106789012"));
        
        // Clientes de Salento
        minimercado.addCliente(new Cliente("1098901234", "Andrés Felipe Sánchez", "afsanchez@gmail.com", "3207890123"));
        minimercado.addCliente(new Cliente("1098901235", "Carolina Jiménez", "cjimenez@outlook.com", "3158901234"));
    }

    public void inicializarProveedores() {
        Minimercado minimercado = Minimercado.getInstancia();
        minimercado.agregarProveedor(new Proveedor("900111222-3", "Cafés del Quindío S.A.", "Diana Torres", "compras@cafesquindio.com", "606-7440001"));
        minimercado.agregarProveedor(new Proveedor("901234567-8", "Agrofrutas del Eje", "Luis Mejía", "ventas@agrofrutas.com", "606-7331122"));
        minimercado.agregarProveedor(new Proveedor("900765432-1", "Lácteos Andinos", "María Rojas", "contacto@lacteosandinos.co", "606-7213344"));
    }

    public void inicializarEmpleados() {
        Minimercado minimercado = Minimercado.getInstancia();
        minimercado.agregarEmpleado(new Empleado("EMP-001", "Julian", Empleado.Rol.CAJERO));
        minimercado.agregarEmpleado(new Empleado("EMP-002", "Andrea", Empleado.Rol.BODEGUERO));
        minimercado.agregarEmpleado(new Empleado("EMP-003", "Pablo", Empleado.Rol.CAJERO));
    }

}
