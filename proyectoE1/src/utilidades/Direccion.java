package utilidades;

public class Direccion {
    private String calle;
    private int numero;

    public Direccion(String calle, int numero) {
        this.calle = calle;
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return calle + " " + numero;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }

        if (otro == null || getClass() != otro.getClass()) {
            return false;
        }

        Direccion direccion = (Direccion) otro;

        return numero == direccion.numero &&
                calle.equals(direccion.calle);
    }
}