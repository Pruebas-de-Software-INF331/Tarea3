package cl.fidelidad.repository;

import cl.fidelidad.model.Cliente;

import java.util.*;

public class ClienteRepository {

    private final Map<String, Cliente> clientes = new HashMap<>();

    /** Agrega un nuevo cliente si el correo es válido */
    public void agregar(Cliente c) {
        validarCorreo(c.getCorreo());
        clientes.put(c.getId(), c);
    }

    /** Obtiene un cliente por ID */
    public Cliente obtener(String id) {
        return clientes.get(id);
    }

    /** Retorna una lista con todos los clientes registrados */
    public List<Cliente> listar() {
        return new ArrayList<>(clientes.values());
    }

    /** Elimina un cliente por ID */
    public void eliminar(String id) {
        clientes.remove(id);
    }

    /** Actualiza un cliente existente, validando el correo */
    public void actualizar(Cliente clienteActualizado) {
        validarCorreo(clienteActualizado.getCorreo());
        clientes.put(clienteActualizado.getId(), clienteActualizado);
    }

    /** Busca un cliente por su correo electrónico (ignorando mayúsculas/minúsculas) */
    public Optional<Cliente> buscarPorCorreo(String correo) {
        return clientes.values().stream()
                .filter(c -> c.getCorreo().equalsIgnoreCase(correo))
                .findFirst();
    }

    /** Verifica si existe un cliente con un ID dado */
    public boolean existe(String id) {
        return clientes.containsKey(id);
    }

    /** Valida que el correo contenga el carácter '@' */
    private void validarCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Correo inválido: debe contener '@'");
        }
    }
}
