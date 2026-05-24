package Modelo;

import utilidades.Direccion;

public class Terminal {

    private String nombre;
    private Direccion direccion;

    public Terminal(String nombre, Direccion direccion) {

        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {

        return "----------------------------------------\n" +
                " DATOS TERMINAL\n" +
                "----------------------------------------\n" +
                " Nombre    : " + nombre + "\n" +
                " Dirección : " + direccion + "\n" +
                "----------------------------------------";
    }
}