package utilidades;

import Modelo.Persona;

import java.util.Objects;

public class Direccion {

    private String calle;
    private int numero;
    private String comuna;

    public Direccion(String calle, int numero, String comuna) {

        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getComuna() {
        return comuna;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    @Override
    public String toString() {
        return calle + " " + numero + ", " + comuna;
    }
    @Override
    public boolean equals(Object otro) {
        if (otro == this) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Direccion direccion = (Direccion) otro;
        return this.numero == direccion.getNumero() && this.calle.equals(direccion.getCalle()) && this.comuna.equals(direccion.getComuna());
    }
}

