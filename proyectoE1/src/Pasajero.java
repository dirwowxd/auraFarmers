public class Pasajero extends Persona {
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(String fonoContacto, Nombre nomContacto, idPersona id, Nombre nom) { //agregue constructor auunque el uml no lo pedia, pero como es clase hijo lo agregue xd nose
        super(id, nom);
        this.fonoContacto = fonoContacto;
        this.nomContacto = nomContacto;

    }

    public Nombre getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(Nombre nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getFonoContacto() {
        return fonoContacto;
    }

    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}
