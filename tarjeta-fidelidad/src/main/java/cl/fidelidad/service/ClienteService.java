package cl.fidelidad.service;

import cl.fidelidad.model.Cliente;
import cl.fidelidad.model.NivelFidelidad;
import cl.fidelidad.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        if (!correoValido(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo inválido");
        }
        Optional<Cliente> existente = clienteRepo.buscarPorCorreo(cliente.getCorreo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Correo ya registrado");
        }
        clienteRepo.agregar(cliente);
    }

    public Cliente obtenerCliente(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID de cliente inválido");
        }
        Cliente cliente = clienteRepo.obtener(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }
        return cliente;
    }

    public List<Cliente> listarClientes() {
        return clienteRepo.listar();
    }

    public void eliminarCliente(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID de cliente inválido");
        }
        clienteRepo.eliminar(id);
    }

    public void actualizarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        if (!correoValido(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo inválido");
        }
        clienteRepo.actualizar(cliente);
    }

    /** Actualiza el nivel según puntos actuales */
    public void actualizarNivel(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        NivelFidelidad nuevoNivel = NivelFidelidad.obtenerNivel(cliente.getPuntos());
        cliente.setNivel(nuevoNivel);
        clienteRepo.actualizar(cliente);
    }

    private boolean correoValido(String correo) {
        return correo != null && correo.contains("@") && correo.indexOf("@") != 0 && correo.indexOf("@") != correo.length() - 1;
    }
}
