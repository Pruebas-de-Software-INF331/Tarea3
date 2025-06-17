package cl.fidelidad.model;

public enum NivelFidelidad {
    BRONCE(0, 499, 1.0),
    PLATA(500, 1499, 1.2),
    ORO(1500, 2999, 1.5),
    PLATINO(3000, Integer.MAX_VALUE, 2.0);

    private final int min;
    private final int max;
    private final double multiplicador;

    NivelFidelidad(int min, int max, double multiplicador) {
        this.min = min;
        this.max = max;
        this.multiplicador = multiplicador;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    // Método para determinar el nivel según puntos (asume orden ascendente)
    public static NivelFidelidad obtenerNivel(int puntos) {
        NivelFidelidad nivelActual = BRONCE;
        for (NivelFidelidad nivel : values()) {
            if (puntos >= nivel.min) {
                nivelActual = nivel;
            } else {
                break; // Como están ordenados, ya no hay que seguir
            }
        }
        return nivelActual;
    }
}
