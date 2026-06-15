package Modelo;

import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import java.util.ArrayList;

public class Auxiliar extends Tripulante {

    private ArrayList<Viaje> viajes;

    public Auxiliar(IdPersona idPersona, Nombre nombreCompleto, Direccion dir) {
        super(idPersona, nombreCompleto, dir);
        this.viajes = new ArrayList<>();
    }

    @Override
    public void addViaje(Viaje viaje) {
        this.viajes.add(viaje);
    }

    @Override
    public int getNroViajes() {
        return this.viajes.size();
    }
}