package cl.fidelidad.model;

public enum NivelFidelidad {
    BRONCE(0, 499, 1.0),
    PLATA(500, 1499, 1.2),
    ORO(1500, 2999, 1.5),
    PLATINO(3000, Integer.MAX_VALUE, 2.0);

    private final int min;
    private final int max;
    private final double multiplicador;
    // Constructor, getters
}
