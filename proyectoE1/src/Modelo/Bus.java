package Modelo;
import java.util.ArrayList;

public class Bus {
    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private Empresa empresa;

    public Bus(String patente, int nroAsientos, Empresa empresa) {
        this.patente = patente;
        this.nroAsientos = nroAsientos;
        this.empresa = empresa;
    }

    public String getPatente(){
        return patente;
    }
    public String getMarca(){
        return marca;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }
    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    public int getNroAsientos(){
        return nroAsientos;
    }
    public void addViaje(Viaje viaje) {
        if (viaje != null) {
            this.viajes.add(viaje);
        }
    }
    public Empresa getEmpresa() {
        // hacer
        return null;
    }
    public Viaje[] getViajes() {
        // hacer
        return null;
    }
}