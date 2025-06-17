package cl.fidelidad.model;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private NivelFidelidad nivel;
    private int streakDias;

    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = NivelFidelidad.BRONCE;
        this.streakDias = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public int getPuntos() {
        return puntos;
    }

    public NivelFidelidad getNivel() {
        return nivel;
    }

    public int getStreakDias() {
        return streakDias;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPuntos(int puntos) {
        this.puntos = Math.max(0, puntos); // evitar puntos negativos
        this.nivel = NivelFidelidad.obtenerNivel(this.puntos); // actualizar nivel automáticamente
    }

    public void setNivel(NivelFidelidad nivel) {
        this.nivel = nivel;
    }

    public void setStreakDias(int streakDias) {
        this.streakDias = Math.max(0, streakDias);
    }

    // Métodos adicionales
    public void agregarPuntos(int puntosGanados) {
        if (puntosGanados > 0) {
            setPuntos(this.puntos + puntosGanados); // usa setPuntos para recalcular nivel
        }
    }

    public void reiniciarStreak() {
        this.streakDias = 0;
    }

    public void incrementarStreak() {
        this.streakDias++;
    }
}
