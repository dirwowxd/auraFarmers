public class Nombre {
    private Tratamiento tratamiento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    public String toString() {
        return "Tratamiento: "+tratamiento+" Nombre: "+nombres+ " Apellido paterno: "+ apellidoPaterno+ " Apellido materno: "+ apellidoMaterno;

    }
    public boolean equals(Object o) {
        if (this == o) return true; //aqui comparo el objeto con el otro
        if (o == null || getClass() != o.getClass()) return false; // si el objeto esta null o no son de la misma clase se retorna falso
        Nombre otro = (Nombre) o; // se guarda la variable (o)  en otro
        return nombres.equals(otro.nombres) && apellidoPaterno.equals(otro.apellidoPaterno) && apellidoMaterno.equals(otro.apellidoMaterno); //aqui comparo lo que pidio el uml xd
    }
}
