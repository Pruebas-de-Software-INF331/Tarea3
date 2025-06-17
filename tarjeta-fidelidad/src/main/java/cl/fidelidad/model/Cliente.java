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

    // Getters y setters
}
