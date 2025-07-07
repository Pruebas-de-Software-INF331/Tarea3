package cl.fidelidad;

import cl.fidelidad.model.Cliente;
import cl.fidelidad.repository.ClienteRepository;
import cl.fidelidad.repository.CompraRepository;
import cl.fidelidad.service.FidelidadService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteRepository clienteRepo = new ClienteRepository();
    private static final CompraRepository compraRepo = new CompraRepository();
    private static final FidelidadService fidelidadService = new FidelidadService(clienteRepo, compraRepo);

    public static void main(String[] args) {
        System.out.println("=== Sistema Tarjeta Fidelidad ===");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> mostrarPuntosYNivel();
                case "2" -> registrarCompra();
                case "3" -> agregarCliente();
                case "4" -> listarClientes();
                case "5" -> eliminarCliente();
                case "6" -> verHistorialDeCompras();
                case "7" -> {
                    continuar = false;
                    System.out.println("¡Hasta luego!");
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Mostrar puntos y nivel de cliente");
        System.out.println("2. Registrar compra");
        System.out.println("3. Agregar cliente");
        System.out.println("4. Listar clientes");
        System.out.println("5. Eliminar cliente");
        System.out.println("6. Ver historial de compras");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void agregarCliente() {
        System.out.print("ID cliente: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("El ID no puede estar vacío.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        if (!correo.contains("@")) {
            System.out.println("Correo no válido.");
            return;
        }

        if (clienteRepo.buscarPorCorreo(correo).isPresent()) {
            System.out.println("Ya existe un cliente con ese correo.");
            return;
        }

        Cliente nuevo = new Cliente(id, nombre, correo);
        clienteRepo.agregar(nuevo);
        System.out.println("Cliente agregado exitosamente.");
    }

    private static void registrarCompra() {
        System.out.print("ID cliente: ");
        String idCliente = scanner.nextLine().trim();

        Cliente cliente = clienteRepo.obtener(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Monto de compra: ");
        String montoStr = scanner.nextLine().trim();

        double monto;
        try {
            monto = Double.parseDouble(montoStr);
            if (monto <= 0) {
                System.out.println("El monto debe ser un número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido. Ingrese un número válido.");
            return;
        }

        try {
            fidelidadService.registrarCompra(idCliente, monto, LocalDate.now());
            System.out.println("Compra registrada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar compra: " + e.getMessage());
        }
    }

    private static void mostrarPuntosYNivel() {
        System.out.print("ID cliente: ");
        String idConsulta = scanner.nextLine().trim();

        Cliente c = clienteRepo.obtener(idConsulta);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Cliente: " + c.getNombre());
        System.out.println("Puntos: " + c.getPuntos());
        System.out.println("Nivel: " + c.getNivel());
        System.out.println("Streak días: " + c.getStreakDias());
    }

    private static void listarClientes() {
    var lista = fidelidadService.listarClientes();
    if (lista.isEmpty()) {
        System.out.println("No hay clientes registrados.");
    } else {
        System.out.println("--- Clientes ---");
        for (Cliente c : lista) {
            System.out.println(c.getId() + " - " + c.getNombre() + " - " + c.getCorreo());
        }
    }
    }

    private static void eliminarCliente() {
        System.out.print("ID del cliente a eliminar: ");
        String id = scanner.nextLine().trim();
        Cliente c = clienteRepo.obtener(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        fidelidadService.eliminarCliente(id);
        System.out.println("Cliente eliminado.");
    }

    private static void verHistorialDeCompras() {
    System.out.print("ID del cliente: ");
    String id = scanner.nextLine().trim();
    var historial = fidelidadService.obtenerHistorialCompras(id);
    if (historial.isEmpty()) {
        System.out.println("Este cliente no tiene compras registradas.");
    } else {
        System.out.println("--- Historial de Compras ---");
        historial.forEach(compra -> System.out.println(
            compra.getFecha() + " - $" + compra.getMonto() + " - ID: " + compra.getIdCompra()
        ));
    }
}
}
