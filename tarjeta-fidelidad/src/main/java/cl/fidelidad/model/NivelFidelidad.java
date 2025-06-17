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

    // Método para determinar el nivel según puntos
    public static NivelFidelidad obtenerNivel(int puntos) {
        for (NivelFidelidad nivel : values()) {
            if (puntos >= nivel.min && puntos <= nivel.max) {
                return nivel;
            }
        }
        return BRONCE; // fallback por defecto
    }
}
