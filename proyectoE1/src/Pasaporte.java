public class Pasaporte {
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
}
