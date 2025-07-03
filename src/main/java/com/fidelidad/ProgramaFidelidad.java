package com.fidelidad;

import java.util.List;
import java.util.Scanner;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.repository.ClienteRepository;
import com.fidelidad.repository.CompraRepository;
import com.fidelidad.service.ClienteService;
import com.fidelidad.service.CompraService;
import com.fidelidad.service.PuntosService;

public class ProgramaFidelidad {
    private static ClienteService clienteService;
    private static CompraService compraService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        ClienteRepository clienteRepository = new ClienteRepository();
        CompraRepository compraRepository = new CompraRepository();
        
        clienteService = new ClienteService(clienteRepository);
        PuntosService puntosService = new PuntosService(compraRepository);
        compraService = new CompraService(compraRepository, clienteService, puntosService);
        
        scanner = new Scanner(System.in);
        
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    gestionClientes();
                    break;
                case 2:
                    gestionCompras();
                    break;
                case 3:
                    mostrarPuntosNivel();
                    break;
                case 4:
                    salir = true;
                    System.out.println("Vuelva pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- PROGRAMA DE FIDELIDAD ---");
        System.out.println("1. Gestión de Clientes");
        System.out.println("2. Gestión de Compras");
        System.out.println("3. Mostrar Puntos/Nivel de un Cliente");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private static void gestionClientes() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Actualizar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    actualizarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        }
    }
    
    private static void gestionCompras() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE COMPRAS ---");
            System.out.println("1. Registrar Compra");
            System.out.println("2. Listar Todas las Compras");
            System.out.println("3. Listar Compras por Cliente");
            System.out.println("4. Eliminar Compra");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    registrarCompra();
                    break;
                case 2:
                    listarCompras();
                    break;
                case 3:
                    listarComprasPorCliente();
                    break;
                case 4:
                    eliminarCompra();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        }
    }
    
    private static void agregarCliente() {
        System.out.println("\n--- AGREGAR CLIENTE ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        
        try {
            Cliente cliente = clienteService.crearCliente(nombre, correo);
            System.out.println("Cliente agregado exitosamente con ID: " + cliente.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> clientes = clienteService.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        System.out.printf("%-5s %-20s %-25s %-10s %-10s\n", "ID", "Nombre", "Correo", "Puntos", "Nivel");
        System.out.println("--------------------------------------------------------------------------");
        
        for (Cliente cliente : clientes) {
            System.out.printf("%-5d %-20s %-25s %-10d %-10s\n", 
                cliente.getId(), cliente.getNombre(), cliente.getCorreo(), 
                cliente.getPuntos(), cliente.getNivel());
        }
    }
    
    private static void actualizarCliente() {
        System.out.println("\n--- ACTUALIZAR CLIENTE ---");
        System.out.print("ID del cliente a actualizar: ");
        int id = leerOpcion();
        
        try {
            clienteService.buscarPorId(id).ifPresentOrElse(
                cliente -> {
                    System.out.println("Cliente encontrado: " + cliente.getNombre());
                    System.out.print("Nuevo nombre (dejar en blanco para mantener el actual): ");
                    String nombre = scanner.nextLine();
                    nombre = nombre.isEmpty() ? cliente.getNombre() : nombre;
                    
                    System.out.print("Nuevo correo (dejar en blanco para mantener el actual): ");
                    String correo = scanner.nextLine();
                    correo = correo.isEmpty() ? cliente.getCorreo() : correo;
                    
                    try {
                        clienteService.actualizarCliente(id, nombre, correo);
                        System.out.println("Cliente actualizado exitosamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                },
                () -> System.out.println("No se encontró un cliente con ID: " + id)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void eliminarCliente() {
        System.out.println("\n--- ELIMINAR CLIENTE ---");
        System.out.print("ID del cliente a eliminar: ");
        int id = leerOpcion();
        
        try {
            clienteService.buscarPorId(id).ifPresentOrElse(
                cliente -> {
                    System.out.println("Cliente encontrado: " + cliente.getNombre());
                    System.out.print("¿Está seguro de eliminar este cliente? (S/N): ");
                    String confirmacion = scanner.nextLine().trim().toUpperCase();
                    
                    if (confirmacion.equals("S")) {
                        clienteService.eliminarCliente(id);
                        System.out.println("Cliente eliminado exitosamente.");
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                },
                () -> System.out.println("No se encontró un cliente con ID: " + id)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void registrarCompra() {
        System.out.println("\n--- REGISTRAR COMPRA ---");
        
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados. Debe crear un cliente primero.");
            return;
        }
        
        System.out.print("ID del cliente: ");
        int clienteId = leerOpcion();
        
        try {
            clienteService.buscarPorId(clienteId).ifPresentOrElse(
                cliente -> {
                    System.out.println("Cliente: " + cliente.getNombre());
                    System.out.print("Monto de la compra ($): ");
                    
                    try {
                        double monto = Double.parseDouble(scanner.nextLine());
                        if (monto <= 0) {
                            System.out.println("El monto debe ser mayor que cero.");
                            return;
                        }
                        
                        Compra compra = compraService.registrarCompra(clienteId, monto);
                        System.out.println("Compra registrada exitosamente con ID: " + compra.getId());
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El monto debe ser un número válido.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                },
                () -> System.out.println("No se encontró un cliente con ID: " + clienteId)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void listarCompras() {
        System.out.println("\n--- LISTA DE COMPRAS ---");
        List<Compra> compras = compraService.listarCompras();
        
        if (compras.isEmpty()) {
            System.out.println("No hay compras registradas.");
            return;
        }
        
        System.out.printf("%-5s %-12s %-15s %-12s\n", "ID", "Cliente ID", "Monto ($)", "Fecha");
        System.out.println("--------------------------------------------------");
        
        for (Compra compra : compras) {
            System.out.printf("%-5d %-12d %-15.2f %-12s\n", 
                compra.getId(), compra.getIdCliente(), compra.getMonto(), compra.getFecha());
        }
    }
    
    private static void listarComprasPorCliente() {
        System.out.println("\n--- COMPRAS POR CLIENTE ---");
        System.out.print("ID del cliente: ");
        int clienteId = leerOpcion();
        
        try {
            clienteService.buscarPorId(clienteId).ifPresentOrElse(
                cliente -> {
                    System.out.println("Cliente: " + cliente.getNombre());
                    
                    List<Compra> compras = compraService.listarComprasPorCliente(clienteId);
                    
                    if (compras.isEmpty()) {
                        System.out.println("Este cliente no tiene compras registradas.");
                        return;
                    }
                    
                    System.out.printf("%-5s %-15s %-12s\n", "ID", "Monto ($)", "Fecha");
                    System.out.println("-------------------------------------");
                    
                    for (Compra compra : compras) {
                        System.out.printf("%-5d %-15.2f %-12s\n", 
                            compra.getId(), compra.getMonto(), compra.getFecha());
                    }
                },
                () -> System.out.println("No se encontró un cliente con ID: " + clienteId)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void eliminarCompra() {
        System.out.println("\n--- ELIMINAR COMPRA ---");
        System.out.print("ID de la compra a eliminar: ");
        int id = leerOpcion();
        
        try {
            compraService.buscarCompraPorId(id).ifPresentOrElse(
                compra -> {
                    System.out.printf("Compra encontrada - Cliente ID: %d, Monto: $%.2f, Fecha: %s\n", 
                        compra.getIdCliente(), compra.getMonto(), compra.getFecha());
                    
                    System.out.print("¿Está seguro de eliminar esta compra? (S/N): ");
                    String confirmacion = scanner.nextLine().trim().toUpperCase();
                    
                    if (confirmacion.equals("S")) {
                        compraService.eliminarCompra(id);
                        System.out.println("Compra eliminada exitosamente.");
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                },
                () -> System.out.println("No se encontró una compra con ID: " + id)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void mostrarPuntosNivel() {
        System.out.println("\n--- PUNTOS Y NIVEL DE CLIENTE ---");
        System.out.print("ID del cliente: ");
        int clienteId = leerOpcion();
        
        try {
            clienteService.buscarPorId(clienteId).ifPresentOrElse(
                cliente -> {
                    System.out.println("Cliente: " + cliente.getNombre());
                    System.out.println("Correo: " + cliente.getCorreo());
                    System.out.println("Puntos acumulados: " + cliente.getPuntos());
                    System.out.println("Nivel: " + cliente.getNivel());

                },
                () -> System.out.println("No se encontró un cliente con ID: " + clienteId)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}