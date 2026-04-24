import java.util.Objects;

public class Pasaporte implements idPersona {
    private String numero;
    private String nacionalidad;

    private Pasaporte(String numero, String nacionalidad) {
        this.numero = numero;
        this.nacionalidad = nacionalidad;
    }

    public String getNumero() {
        return numero;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }

    public static Pasaporte of(String numero, String nacionalidad) {
        return new Pasaporte(numero, nacionalidad);
    }

    @Override
    public String toString() {
        return "Pasaporte{" +
                "numero='" + numero + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pasaporte pasaporte = (Pasaporte) o;
        return Objects.equals(numero, pasaporte.numero) && Objects.equals(nacionalidad, pasaporte.nacionalidad);
    }


}
