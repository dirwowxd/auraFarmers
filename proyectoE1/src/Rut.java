public class Rut implements IdPersona {
    private  int numero;
    private  char dv;

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

    public static Rut of(String rutConDv) {
        if (!rutConDv.matches("^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[0-9kK]$")) {
            System.out.println("Error: El formato del RUT ingresado no es válido.");
            return null;
        }
        String rutLimpio = rutConDv.replace(".", "");
        String[] partes = rutLimpio.split("-");
        int numero = Integer.parseInt(partes[0]);
        char dv = partes[1].charAt(0);
        return new Rut(numero, dv);
    }
    @Override
    public String toString() {
        return numero + "-" + dv;
    }
    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Rut rut = (Rut) otro;
        return numero == rut.numero && dv == rut.dv;
    }
}
