package cl.fidelidad;

import cl.fidelidad.model.Cliente;
import cl.fidelidad.repository.ClienteRepository;
import cl.fidelidad.repository.CompraRepository;
import cl.fidelidad.service.FidelidadService;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteRepository clienteRepo = new ClienteRepository();
        CompraRepository compraRepo = new CompraRepository();
        FidelidadService fidelidadService = new FidelidadService(clienteRepo, compraRepo);

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Registrar compra");
            System.out.println("3. Mostrar puntos y nivel de cliente");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("ID cliente: ");
                    String id = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();

                    if (!correo.contains("@")) {
                        System.out.println("Correo no válido.");
                        break;
                    }

                    Cliente nuevo = new Cliente(id, nombre, correo);
                    clienteRepo.agregar(nuevo);
                    System.out.println("Cliente agregado.");
                    break;

                case "2":
                    System.out.print("ID cliente: ");
                    String idCliente = scanner.nextLine();
                    System.out.print("Monto de compra: ");
                    double monto = Double.parseDouble(scanner.nextLine());

                    fidelidadService.registrarCompra(idCliente, monto, LocalDate.now());
                    System.out.println("Compra registrada.");
                    break;

                case "3":
                    System.out.print("ID cliente: ");
                    String idConsulta = scanner.nextLine();
                    Cliente c = clienteRepo.obtener(idConsulta);
                    if (c != null) {
                        System.out.println("Puntos: " + c.getPuntos());
                        System.out.println("Nivel: " + c.getNivel());
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case "4":
                    continuar = false;
                    System.out.println("¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
