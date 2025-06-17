package cl.fidelidad.service;

public class FidelidadService {
    private final ClienteRepository clienteRepo;
    private final CompraRepository compraRepo;

    public void registrarCompra(String idCliente, double monto, LocalDate fecha) {
        Cliente c = clienteRepo.obtener(idCliente);
        int puntosBase = (int)(monto / 100);
        double multiplicador = c.getNivel().getMultiplicador();
        int puntosTotales = (int)(puntosBase * multiplicador);

        // Verifica streak
        List<Compra> comprasHoy = compraRepo.obtenerPorFechaYCliente(idCliente, fecha);
        if (comprasHoy.size() >= 2) puntosTotales += 10; // tercera compra hoy

        // Guarda la compra y actualiza cliente
        Compra compra = new Compra(UUID.randomUUID().toString(), idCliente, monto, fecha);
        compraRepo.agregar(compra);
        c.setPuntos(c.getPuntos() + puntosTotales);
        c.setNivel(calcularNivel(c.getPuntos()));
    }

    private NivelFidelidad calcularNivel(int puntos) {
        for (NivelFidelidad nivel : NivelFidelidad.values()) {
            if (puntos >= nivel.getMin() && puntos <= nivel.getMax()) {
                return nivel;
            }
        }
        return NivelFidelidad.BRONCE;
    }
}
