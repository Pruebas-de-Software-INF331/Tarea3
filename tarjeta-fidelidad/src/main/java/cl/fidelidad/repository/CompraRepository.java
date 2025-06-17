package cl.fidelidad.repository;

import cl.fidelidad.model.Compra;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CompraRepository {

    private final Map<String, Compra> compras = new HashMap<>();

    /** Agrega una nueva compra al repositorio */
    public void agregar(Compra compra) {
        compras.put(compra.getIdCompra(), compra);
    }

    /** Obtiene una compra por su ID */
    public Compra obtener(String idCompra) {
        return compras.get(idCompra);
    }

    /** Lista todas las compras registradas */
    public List<Compra> listar() {
        return new ArrayList<>(compras.values());
    }

    /** Elimina una compra según su ID */
    public void eliminar(String idCompra) {
        compras.remove(idCompra);
    }

    /** Devuelve todas las compras hechas por un cliente específico */
    public List<Compra> obtenerPorCliente(String idCliente) {
        return compras.values().stream()
                .filter(c -> c.getIdCliente().equals(idCliente))
                .collect(Collectors.toList());
    }

    /** Obtiene las compras de un cliente realizadas en una fecha específica */
    public List<Compra> obtenerPorFechaYCliente(String idCliente, LocalDate fecha) {
        return compras.values().stream()
                .filter(c -> c.getIdCliente().equals(idCliente) && c.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }

    /** Cuenta la cantidad de compras que ha hecho un cliente en una fecha específica */
    public int contarComprasEnFecha(String idCliente, LocalDate fecha) {
        return (int) compras.values().stream()
                .filter(c -> c.getIdCliente().equals(idCliente) && c.getFecha().equals(fecha))
                .count();
    }

    /** Devuelve las compras de un cliente ordenadas cronológicamente (ascendente) */
    public List<Compra> obtenerComprasOrdenadasPorFecha(String idCliente) {
        return compras.values().stream()
                .filter(c -> c.getIdCliente().equals(idCliente))
                .sorted(Comparator.comparing(Compra::getFecha))
                .collect(Collectors.toList());
    }
}
