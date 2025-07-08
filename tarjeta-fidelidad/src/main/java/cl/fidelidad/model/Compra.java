package cl.fidelidad.model;

import java.time.LocalDate;

public class Compra {
    private String idCompra;
    private String idCliente;
    private double monto;
    private LocalDate fecha;
    private int puntosGanados; 

    public Compra(String idCompra, String idCliente, double monto, LocalDate fecha, int puntosGanados) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
        this.puntosGanados = puntosGanados;
    }

    // Constructor alternativo si aún usas el antiguo en algunos lados
    public Compra(String idCompra, String idCliente, double monto, LocalDate fecha) {
        this(idCompra, idCliente, monto, fecha, 0);
    }

    // Getters
    public String getIdCompra() {
        return idCompra;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getPuntosGanados() {
        return puntosGanados;
    }

    // Setters
    public void setMonto(double monto) {
        if (monto >= 0) {
            this.monto = monto;
        }
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setPuntosGanados(int puntosGanados) {
        this.puntosGanados = Math.max(0, puntosGanados);
    }
}
