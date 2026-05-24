package Modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.ArrayList;

public class Conductor extends Tripulante {

    private ArrayList<Viaje> viajes;

    public Conductor(IdPersona idPersona, Nombre nombreCompleto, String telefono) {
        super(idPersona, nombreCompleto, telefono);
    }

    public void addViaje(Viaje viaje) {
        this.viajes.add(viaje);
    }
    public int getNroViajes() {
        return this.viajes.size();
    }
}
