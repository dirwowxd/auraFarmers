package Modelo;

import utilidades.Direccion;

public class Terminal {

    private String nombre;
    private String comuna;
    private Direccion direccion;

    public Terminal(String nombre, String comuna, Direccion direccion) {

        this.nombre = nombre;
        this.comuna = comuna;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getComuna() {
        return comuna;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
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
                " Comuna    : " + comuna + "\n" +
                " Dirección : " + direccion + "\n" +
                "----------------------------------------";
    }

    @Override
    public boolean equals(Object otro) {

        if (this == otro) return true;

        if (otro == null || getClass() != otro.getClass()) {
            return false;
        }

        Terminal terminal = (Terminal) otro;

        return nombre.equals(terminal.nombre);
    }
}