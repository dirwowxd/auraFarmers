package Modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.Objects;

public abstract class Persona {
    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    public Persona(IdPersona idPersona, Nombre nombreCompleto) {
        this.idPersona = idPersona;
        this.nombreCompleto = nombreCompleto;

    }

    public IdPersona getIdPersona() {
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
                "  utilidades.Nombre   : " + nombreCompleto + "\n" +
                "  Teléfono : " + telefono + "\n" +
                "----------------------------------------";
    }

    public boolean equals(Object otro) {
       if (otro == this) return true;
       if (otro == null || getClass() != otro.getClass()) return false;
       Persona persona = (Persona) otro;
        return Objects.equals(idPersona, persona.idPersona);
       }


    }

