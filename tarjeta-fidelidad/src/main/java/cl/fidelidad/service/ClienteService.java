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
        if (!correoValido(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo inválido");
        }
        if (clienteRepo.buscarPorCorreo(cliente.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Correo ya registrado");
        }
        clienteRepo.agregar(cliente);
    }

    public Cliente obtenerCliente(String id) {
        return clienteRepo.obtener(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepo.listar();
    }

    public void eliminarCliente(String id) {
        clienteRepo.eliminar(id);
    }

    public void actualizarCliente(Cliente cliente) {
        if (!correoValido(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo inválido");
        }
        clienteRepo.actualizar(cliente);
    }

    /** Actualiza el nivel según puntos actuales */
    public void actualizarNivel(Cliente cliente) {
        NivelFidelidad nuevoNivel = NivelFidelidad.obtenerNivel(cliente.getPuntos());
        cliente.setNivel(nuevoNivel);
        clienteRepo.actualizar(cliente);
    }

    private boolean correoValido(String correo) {
        return correo != null && correo.contains("@");
    }
}
