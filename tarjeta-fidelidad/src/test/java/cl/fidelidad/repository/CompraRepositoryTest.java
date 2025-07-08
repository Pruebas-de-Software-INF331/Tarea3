package cl.fidelidad.repository;

import cl.fidelidad.model.Compra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompraRepositoryTest {

    private CompraRepository repo;
    private final String clienteId = "cliente1";
    private final LocalDate hoy = LocalDate.now();

    @BeforeEach
    void setUp() {
        repo = new CompraRepository();
    }

    @Test
    void agregarYObtenerCompra() {
        Compra compra = new Compra("c1", clienteId, 1000, hoy);
        repo.agregar(compra);

        assertEquals(compra, repo.obtener("c1"));
    }

    @Test
    void eliminarCompra() {
        Compra compra = new Compra("c2", clienteId, 200, hoy);
        repo.agregar(compra);
        repo.eliminar("c2");

        assertNull(repo.obtener("c2"));
    }

    @Test
    void obtenerPorCliente() {
        repo.agregar(new Compra("c3", clienteId, 300, hoy));
        repo.agregar(new Compra("c4", "otroCliente", 400, hoy));

        List<Compra> compras = repo.obtenerPorCliente(clienteId);
        assertEquals(1, compras.size());
    }

    @Test
    void obtenerPorFechaYCliente() {
        repo.agregar(new Compra("c5", clienteId, 150, hoy));
        repo.agregar(new Compra("c6", clienteId, 150, hoy.minusDays(1)));

        List<Compra> compras = repo.obtenerPorFechaYCliente(clienteId, hoy);
        assertEquals(1, compras.size());
    }

    @Test
    void contarComprasEnFecha() {
        repo.agregar(new Compra("c7", clienteId, 100, hoy));
        repo.agregar(new Compra("c8", clienteId, 200, hoy));

        int count = repo.contarComprasEnFecha(clienteId, hoy);
        assertEquals(2, count);
    }

    @Test
    void obtenerComprasOrdenadasPorFecha() {
        repo.agregar(new Compra("c9", clienteId, 100, hoy.plusDays(1)));
        repo.agregar(new Compra("c10", clienteId, 100, hoy));
        repo.agregar(new Compra("c11", clienteId, 100, hoy.minusDays(1)));

        List<Compra> ordenadas = repo.obtenerComprasOrdenadasPorFecha(clienteId);
        assertEquals("c11", ordenadas.get(0).getIdCompra());
        assertEquals("c10", ordenadas.get(1).getIdCompra());
        assertEquals("c9", ordenadas.get(2).getIdCompra());
    }

    @Test
    void listarCompras() {
        repo.agregar(new Compra("c12", clienteId, 150, hoy));
        repo.agregar(new Compra("c13", clienteId, 200, hoy));

        assertEquals(2, repo.listar().size());
    }

    @Test
    void obtenerPorClienteSinComprasDevuelveListaVacia() {
        List<Compra> compras = repo.obtenerPorCliente("inexistente");
        assertTrue(compras.isEmpty());
    }
}
