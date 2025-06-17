package cl.fidelidad.repository;

import cl.fidelidad.model.Compra;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CompraRepository {
    private final Map<String, Compra> compras = new HashMap<>();

    public void agregar(Compra compra) {
        compras.put(compra.getIdCompra(), compra);
    }

    public Compra obtener(String idCompra) {
        return compras.get(idCompra);
    }

    public List<Compra> listar() {
        return new ArrayList<>(compras.values());
    }

    public void eliminar(String idCompra) {
        compras.remove(idCompra);
    }

    public List<Compra> obtenerPorCliente(String idCliente) {
        return compras.values().stream()
                .filter(c -> c.getIdCliente().equals(idCliente))
                .collect(Collectors.toList());
    }

    public List<Compra> obtenerPorFechaYCliente(String idCliente, LocalDate fecha) {
        return compras.values().stream()
                .filter(c -> c.getIdCliente().equals(idCliente) && c.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }
}
