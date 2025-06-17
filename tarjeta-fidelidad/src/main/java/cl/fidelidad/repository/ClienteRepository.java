package cl.fidelidad.repository;

public class ClienteRepository {
    private final Map<String, Cliente> clientes = new HashMap<>();
    public void agregar(Cliente c) { clientes.put(c.getId(), c); }
    public Cliente obtener(String id) { return clientes.get(id); }
    public List<Cliente> listar() { return new ArrayList<>(clientes.values()); }
    public void eliminar(String id) { clientes.remove(id); }
    // Otros métodos: actualizar, buscar por correo
}
