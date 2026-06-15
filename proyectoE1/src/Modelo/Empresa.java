package Modelo;

import excepciones.SistemaVentaPasajesException;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;



import java.util.ArrayList;
import java.util.Collections;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;
    private final ArrayList<Bus> buses;
    private final ArrayList<Conductor> conductores;
    private final ArrayList<Tripulante> tripulantes;
    private final ArrayList<Auxiliar> auxiliars;

    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        this.buses= new ArrayList<>();
        this.conductores= new ArrayList<>();
        this.tripulantes= new ArrayList<>();
        this.auxiliars= new ArrayList<>();
    }

    public Rut getRut() {
        return rut;
    }

    public void setRut(Rut rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addBuses(Bus bus) {
        for (Bus busAux : buses) {
            if (busAux.getPatente().equals(bus.getPatente())) {
                throw new SistemaVentaPasajesException("Ya existe un bus con la patente " + busAux.getPatente());
            }
        }
        buses.add(bus);
    }

    public Bus[] getBuses() {
        return buses.toArray(new Bus[0]);
    }


    public boolean addConductor(IdPersona id, Nombre nom, Direccion direccion) {
        for (Tripulante tripulante : tripulantes) {
            if (tripulante.getIdPersona().equals(id)) {
                return false;
            }
        }
        Conductor conductor = new Conductor(id, nom, direccion);
        this.tripulantes.add(conductor);
        return true;
    }
    public boolean addAuxiliar(IdPersona id, Nombre nom, Direccion direccion) {
        for (Tripulante tripulante : tripulantes) {
            if (tripulante.getIdPersona().equals(id)) {
                return false;
            }
        }
        Auxiliar auxiliar = new Auxiliar(id, nom, direccion);
        this.tripulantes.add(auxiliar);
        return true;
    }
    public Tripulante[] getTripulantes() {
        return tripulantes.toArray(new Tripulante[0]);
    }
    public Venta[] getventas() {
        ArrayList<Venta> ventas= new ArrayList<>();
        for (Bus busAux : buses) {
            for (Viaje viajeAux : busAux.getViajes()) {
                Collections.addAll(ventas, viajeAux.getVentas()); // intellij lo hizo y basicamente ahorra mas tiempo que con el .add al ser un ciclo for de 3
            }
        }
        return ventas.toArray(new Venta[0]);
    }
}
