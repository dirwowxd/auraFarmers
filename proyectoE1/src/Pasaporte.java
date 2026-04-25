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
        if (numero == null || nacionalidad == null) {
            System.out.println("Error: La ruta no puede estar vacia");
            return null;
        }
        if (!numero.matches("^[a-zA-Z0-9]+$")){
            System.out.println("Error el numero del pasaporte es invalido");
            return null;
        }
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
