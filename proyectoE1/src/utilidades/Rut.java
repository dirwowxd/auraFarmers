package utilidades;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Rut implements IdPersona, Serializable {
    private int numero;
    private char dv;
    private static final long serialVersionUID = 1L;

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

    public static Rut of(String rutStr) {
        if (rutStr == null || !rutStr.matches("^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[0-9Kk]$")) {
            return null;
        }
        String formato = rutStr.replace(".", "").trim();
        String[] rut = formato.split("-");
        int numero = Integer.parseInt(rut[0]);
        char dv = Character.toUpperCase(rut[1].charAt(0));
        return new Rut(numero, dv);
    }

    public static Rut of(int numero, char dv) {
        return new Rut(numero, Character.toUpperCase(dv));
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(numero) + "-" + dv;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Rut rut = (Rut) otro;
        return numero == rut.numero && Character.toUpperCase(dv) == Character.toUpperCase(rut.dv);
    }
}