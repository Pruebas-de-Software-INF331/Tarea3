package cl.fidelidad.service;

import cl.fidelidad.model.Cliente;
import cl.fidelidad.model.Compra;
import cl.fidelidad.model.NivelFidelidad;
import cl.fidelidad.repository.ClienteRepository;
import cl.fidelidad.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FidelidadServiceTest {

    private FidelidadService service;
    private ClienteRepository clienteRepo;
    private CompraRepository compraRepo;

    @BeforeEach
    void setUp() {
        clienteRepo = new ClienteRepository();
        compraRepo = new CompraRepository();
        service = new FidelidadService(clienteRepo, compraRepo);

        // Agregar cliente base
        Cliente cliente = new Cliente("1", "Cliente Prueba", "cliente@mail.com");
        clienteRepo.agregar(cliente);
    }

    @Test
    void registrarCompraDebeSumarPuntosBase() {
        service.registrarCompra("1", 1000, LocalDate.now());

        Cliente cliente = clienteRepo.obtener("1");
        assertTrue(cliente.getPuntos() > 0);
        assertEquals(NivelFidelidad.BRONCE, cliente.getNivel());
    }

    @Test
    void registrarCompraDebeActualizarNivel() {
        // Realizamos múltiples compras para subir de nivel
        for (int i = 0; i < 5; i++) {
            service.registrarCompra("1", 500.0, LocalDate.now());
        }

        Cliente cliente = clienteRepo.obtener("1");

        assertTrue(cliente.getPuntos() >= 500); // umbral mínimo de PLATA
        assertEquals(NivelFidelidad.PLATA, cliente.getNivel());
    }

    @Test
    void registrarTresComprasEnMismoDiaAplicaBono() {
        LocalDate hoy = LocalDate.now();
        service.registrarCompra("1", 100.0, hoy);
        service.registrarCompra("1", 150.0, hoy);
        service.registrarCompra("1", 200.0, hoy);

        Cliente cliente = clienteRepo.obtener("1");

        // El bono es +10 puntos extra si hay 3 compras en el mismo día
        // Los puntos base serían: (100+150+200) * 1.0 = 450
        // Más 10 de bono: 460
        assertEquals(460, cliente.getPuntos());
    }

    @Test
    void registrarCompraIncrementaStreakSiEsDiaConsecutivo() {
        LocalDate ayer = LocalDate.now().minusDays(1);
        service.registrarCompra("1", 100.0, ayer);
        service.registrarCompra("1", 100.0, LocalDate.now());

        Cliente cliente = clienteRepo.obtener("1");

        // Después de 2 días consecutivos de compra, el streak debe ser 2
        assertEquals(2, cliente.getStreakDias());
    }

    @Test
    void registrarCompraReiniciaStreakSiNoEsConsecutivo() {
        LocalDate antesDeAyer = LocalDate.now().minusDays(2);
        service.registrarCompra("1", 100.0, antesDeAyer);
        service.registrarCompra("1", 100.0, LocalDate.now());

        Cliente cliente = clienteRepo.obtener("1");

        // El streak debe ser 1 porque hubo un día sin compras
        assertEquals(1, cliente.getStreakDias());
    }

    @Test
    void registrarCompraEnDiaYaConCompraNoDuplicaStreak() {
        LocalDate hoy = LocalDate.now();
        service.registrarCompra("1", 100.0, hoy);
        service.registrarCompra("1", 200.0, hoy);

        Cliente cliente = clienteRepo.obtener("1");

        // Aunque haya 2 compras, el streak no debe subir más de 1
        assertEquals(1, cliente.getStreakDias());
    }

    @Test
    void obtenerPuntosYNivelCliente() {
        service.registrarCompra("1", 1000.0, LocalDate.now());

        Cliente cliente = clienteRepo.obtener("1");

        assertEquals(cliente.getPuntos(), service.obtenerPuntos("1"));
        assertEquals(cliente.getNivel(), service.obtenerNivel("1"));
    }

    @Test
    void registrarCompraConClienteInexistenteLanzaExcepcion() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                service.registrarCompra("no-existe", 100, LocalDate.now())
        );
        assertTrue(ex.getMessage().contains("Cliente no existe"));
    }
}
