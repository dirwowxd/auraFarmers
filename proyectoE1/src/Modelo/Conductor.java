package Modelo;

import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.io.Serializable;
import java.util.ArrayList;

public class Conductor extends Tripulante implements Serializable {

    private ArrayList<Viaje> viajes;

    public Conductor(IdPersona idPersona, Nombre nombreCompleto, Direccion direccion) {
        super(idPersona, nombreCompleto,direccion);
    }

    public void addViaje(Viaje viaje) {
        this.viajes.add(viaje);
    }
    public int getNroViajes() {
        return this.viajes.size();
    }
}
