package cl.fidelidad.service;

import cl.fidelidad.model.Cliente;
import cl.fidelidad.model.Compra;
import cl.fidelidad.model.NivelFidelidad;
import cl.fidelidad.repository.ClienteRepository;
import cl.fidelidad.repository.CompraRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public class FidelidadService {

    private ClienteRepository clienteRepo;
    private CompraRepository compraRepo;

    public FidelidadService(ClienteRepository clienteRepo, CompraRepository compraRepo) {
        this.clienteRepo = clienteRepo;
        this.compraRepo = compraRepo;
    }

    // --- Gestión Clientes ---
    public void agregarCliente(String id, String nombre, String correo) {
        if (!correoValido(correo)) {
            throw new IllegalArgumentException("Correo inválido");
        }
        Optional<Cliente> existente = clienteRepo.buscarPorCorreo(correo);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Correo ya registrado");
        }
        Cliente cliente = new Cliente(id, nombre, correo);
        clienteRepo.agregar(cliente);
    }

    public Cliente obtenerCliente(String id) {
        return clienteRepo.obtener(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepo.listar();
    }

    public void actualizarCliente(Cliente clienteActualizado) {
        if (!correoValido(clienteActualizado.getCorreo())) {
            throw new IllegalArgumentException("Correo inválido");
        }
        clienteRepo.actualizar(clienteActualizado);
    }

    public void eliminarCliente(String id) {
        clienteRepo.eliminar(id);
    }

    // --- Registro de Compras ---
    public void registrarCompra(String idCliente, double monto, LocalDate fecha) {
        Cliente c = clienteRepo.obtener(idCliente);
        if (c == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        int puntosBase = (int) (monto / 100);
        double multiplicador = c.getNivel().getMultiplicador();
        int puntosTotales = (int) (puntosBase * multiplicador);

        List<Compra> comprasHoy = compraRepo.obtenerPorFechaYCliente(idCliente, fecha);

        if (comprasHoy.size() >= 2) {
            puntosTotales += 10; // bono por 3 compras en un día
            c.setStreakDias(c.getStreakDias() + 1);
        } else if (comprasHoy.isEmpty()) {
            c.setStreakDias(1);
        } else {
            c.setStreakDias(comprasHoy.size() + 1);
        }

        Compra compra = new Compra(UUID.randomUUID().toString(), idCliente, monto, fecha);
        compraRepo.agregar(compra);

        c.setPuntos(c.getPuntos() + puntosTotales);
        c.setNivel(calcularNivel(c.getPuntos()));

        clienteRepo.actualizar(c);
    }

    // --- Consultas ---
    public int obtenerPuntos(String idCliente) {
        Cliente cliente = clienteRepo.obtener(idCliente);
        return cliente != null ? cliente.getPuntos() : 0;
    }

    public NivelFidelidad obtenerNivel(String idCliente) {
        Cliente cliente = clienteRepo.obtener(idCliente);
        return cliente != null ? cliente.getNivel() : NivelFidelidad.BRONCE;
    }

    // --- Utilidades ---
    private NivelFidelidad calcularNivel(int puntos) {
        for (NivelFidelidad nivel : NivelFidelidad.values()) {
            if (puntos >= nivel.getMin() && puntos <= nivel.getMax()) {
                return nivel;
            }
        }
        return NivelFidelidad.BRONCE;
    }

    private boolean correoValido(String correo) {
        return correo != null && correo.contains("@");
    }
}
