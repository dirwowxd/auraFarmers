package Modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import java.util.Objects;

public abstract class Persona {
    private IdPersona idPersona;
    private Nombre nombreCompleto;
    // El atributo telefono se elimina de aquí //modificar (Se va a Pasajero según el nuevo UML)

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

    public void setNombreCompleto(Nombre nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    // Los métodos getTelefono y setTelefono se eliminan //modificar

    public String toString() { // //modificar (Limpiar la salida para que no imprima el teléfono que ya no existe)
        return "----------------------------------------\n" +
                " DATOS DE LA PERSONA\n" +
                "----------------------------------------\n" +
                "  ID       : " + idPersona + "\n" +
                "  Nombre   : " + nombreCompleto + "\n" +
                "----------------------------------------";
    }

    public boolean equals(Object otro) {
        if (otro == this) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Persona persona = (Persona) otro;
        return Objects.equals(idPersona, persona.idPersona);
    }
}