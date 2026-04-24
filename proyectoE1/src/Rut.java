public class Rut implements idPersona {
    private int numero;
    private char dv;

    private Rut(int numero, char dv) {
        this.numero = numero;
        this.dv = dv;
    }
    public int getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    public static Rut of(int numero, char dv) {
        return new Rut(numero, dv);
    }
    @Override
    public String toString() {
        return "Rut{" + "numero=" + numero + ", dv=" + dv + '}';
    }
    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Rut rut = (Rut) otro;
        return numero == rut.numero && dv == rut.dv;
    }
}
