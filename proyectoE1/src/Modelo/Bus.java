package Modelo;


import utilidades.Rut;


import java.util.ArrayList;


public class Bus {


    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private Rut rutEmpresa;
    private ArrayList<Viaje> viajes = new ArrayList<>();


    public Bus(String patente, int nroAsientos, Rut rutEmpresa) {


        this.patente = patente;
        this.nroAsientos = nroAsientos;
        this.rutEmpresa = rutEmpresa;
    }


    public String getPatente() {
        return patente;
    }


    public String getMarca() {
        return marca;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }


    public String getModelo() {
        return modelo;
    }


    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public int getNroAsientos() {
        return nroAsientos;
    }


    public Rut getRutEmpresa() {
        return rutEmpresa;
    }


    public void addViaje(Viaje viaje) {


        if (viaje != null) {
            viajes.add(viaje);
        }
    }


    public Viaje[] getViajes() {
        return viajes.toArray(new Viaje[0]);
    }


    @Override
    public String toString() {


        return "----------------------------------------\n" +
                " DATOS BUS\n" +
                "----------------------------------------\n" +
                " Patente     : " + patente + "\n" +
                " Marca       : " + marca + "\n" +
                " Modelo      : " + modelo + "\n" +
                " NroAsientos : " + nroAsientos + "\n" +
                "----------------------------------------";
    }
}
