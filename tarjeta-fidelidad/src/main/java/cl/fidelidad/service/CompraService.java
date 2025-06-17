package cl.fidelidad.service;

import cl.fidelidad.model.Cliente;
import cl.fidelidad.model.Compra;
import cl.fidelidad.repository.CompraRepository;

import java.time.LocalDate;
import java.util.List;

public class CompraService {

    private final CompraRepository compraRepo;
    private final ClienteService clienteService;

    public CompraService(CompraRepository compraRepo, ClienteService clienteService) {
        this.compraRepo = compraRepo;
        this.clienteService = clienteService;
    }

    public void registrarCompra(Compra compra) {
        Cliente cliente = clienteService.obtenerCliente(compra.getIdCliente());
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        LocalDate fechaCompra = compra.getFecha();
        List<Compra> comprasHoy = compraRepo.obtenerPorFechaYCliente(cliente.getId(), fechaCompra);

        // Puntos base: 1 punto por cada $100 (parte entera)
        int puntosBase = (int) (compra.getMonto() / 100);

        // Aplicar multiplicador por nivel
        double multiplicador = cliente.getNivel().getMultiplicador();
        int puntosConMultiplicador = (int) Math.floor(puntosBase * multiplicador);

        // Bono por 3 compras consecutivas en el mismo día
        int bono = 0;
        if (comprasHoy.size() >= 2) { // Compra actual no incluida aún
            bono = 10;
            cliente.setStreakDias(cliente.getStreakDias() + 1);
        } else {
            cliente.setStreakDias(1);
        }

        int totalPuntos = puntosConMultiplicador + bono;

        // Actualizar puntos
        cliente.setPuntos(cliente.getPuntos() + totalPuntos);

        // Guardar compra
        compraRepo.agregar(compra);

        // Actualizar nivel
        clienteService.actualizarNivel(cliente);
    }
}
