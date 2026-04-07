public class Persona {
    private idPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    public Persona(idPersona idPersona, Nombre nombreCompleto) {
        this.idPersona = idPersona;
        this.nombreCompleto = nombreCompleto;

    }

    public idPersona getIdPersona() {
        return idPersona;
    }

    public Nombre getNombreCompleto() {
        return nombreCompleto;
    }

    public void  setNombreCompleto(Nombre nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return "----------------------------------------\n" +
                " DATOS DE LA PERSONA\n" +
                "----------------------------------------\n" +
                "  ID       : " + idPersona + "\n" +
                "  Nombre   : " + nombreCompleto + "\n" +
                "  Teléfono : " + telefono + "\n" +
                "----------------------------------------";
    }

    public boolean equals(Persona otro) {
        if (this.idPersona.equals(otro.getIdPersona())) {
            return true;
        }

        return false;
    }



}
