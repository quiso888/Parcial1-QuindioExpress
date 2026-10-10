package co.edu.uniquindio.quindioexpress.modelo;

/**
 * Paquete a entregar: código, destino, peso, prioridad y tiempo estimado.
 *
 * <p>Responsable: Dev 1.
 */


public class Paquete {

    private String codigo;
    private String municipio;
    private double peso;
    private int prioridad;
    private int tiempo;


    public Paquete(String codigo, String municipio, double peso, int prioridad, int tiempo) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempo = tiempo;
    }


    public Paquete() {
    }


    public String getCodigo() {
        return codigo;
    }

    public String getMunicipio() {
        return municipio;
    }

    public double getPeso() {
        return peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempo() {
        return tiempo;
    }
}
