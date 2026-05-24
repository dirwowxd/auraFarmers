package Modelo;

import java.util.ArrayList;

public class Bus {

    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private Empresa empresa;
    private ArrayList<Viaje> viajes = new ArrayList<>();

    public Bus(String patente, String marca, String modelo,
               int nroAsientos, Empresa empresa) {

        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.nroAsientos = nroAsientos;
        this.empresa = empresa;
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getNroAsientos() {
        return nroAsientos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public ArrayList<Viaje> getViajes() {
        return viajes;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void addViaje(Viaje viaje) {

        if (viaje != null && !viajes.contains(viaje)) {
            viajes.add(viaje);
        }
    }

    @Override
    public String toString() {
        return patente + " " + marca + " " + modelo;
    }
}