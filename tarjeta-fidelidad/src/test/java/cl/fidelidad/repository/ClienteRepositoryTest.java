package cl.fidelidad.repository;

import cl.fidelidad.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest {

    private ClienteRepository repo;

    @BeforeEach
    void setUp() {
        repo = new ClienteRepository();
    }

    @Test
    void agregarClienteValido() {
        Cliente c = new Cliente("1", "Ana", "ana@mail.com");
        repo.agregar(c);

        assertEquals(c, repo.obtener("1"));
    }

    @Test
    void agregarClienteConCorreoInvalidoLanzaExcepcion() {
        Cliente c = new Cliente("2", "Pedro", "pedromail.com"); // sin @

        Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.agregar(c));
        assertTrue(ex.getMessage().contains("Correo inválido"));
    }

    @Test
    void actualizarClienteValido() {
        Cliente c = new Cliente("3", "Luis", "luis@mail.com");
        repo.agregar(c);

        c.setNombre("Luis Modificado");
        repo.actualizar(c);

        assertEquals("Luis Modificado", repo.obtener("3").getNombre());
    }

    @Test
    void actualizarClienteConCorreoInvalidoLanzaExcepcion() {
        Cliente c = new Cliente("4", "Ana", "ana@mail.com");
        repo.agregar(c);

        c.setCorreo("correoInvalido");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.actualizar(c));
        assertTrue(ex.getMessage().contains("Correo inválido"));
    }

    @Test
    void eliminarCliente() {
        Cliente c = new Cliente("5", "Carlos", "carlos@mail.com");
        repo.agregar(c);

        repo.eliminar("5");

        assertNull(repo.obtener("5"));
    }

    @Test
    void buscarPorCorreoExistente() {
        Cliente c = new Cliente("6", "Laura", "laura@mail.com");
        repo.agregar(c);

        Optional<Cliente> resultado = repo.buscarPorCorreo("laura@mail.com");

        assertTrue(resultado.isPresent());
        assertEquals("Laura", resultado.get().getNombre());
    }

    @Test
    void buscarPorCorreoInexistente() {
        Optional<Cliente> resultado = repo.buscarPorCorreo("noexiste@mail.com");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarClientes() {
        repo.agregar(new Cliente("7", "Uno", "uno@mail.com"));
        repo.agregar(new Cliente("8", "Dos", "dos@mail.com"));

        assertEquals(2, repo.listar().size());
    }

    @Test
    void existeClientePorId() {
        Cliente c = new Cliente("9", "Sofía", "sofia@mail.com");
        repo.agregar(c);

        assertTrue(repo.existe("9"));
        assertFalse(repo.existe("999"));
    }
}
