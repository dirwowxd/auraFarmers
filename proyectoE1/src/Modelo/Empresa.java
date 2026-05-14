package Modelo;

import excepciones.SistemaVentaPasajesException;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;



import java.util.ArrayList;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;
    private final ArrayList<Bus> buses;
    private final ArrayList<Conductor> conductores;
    private final ArrayList<Tripulante> tripulantes;

    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        this.buses= new ArrayList<>();
        this.conductores= new ArrayList<>();
        this.tripulantes= new ArrayList<>();
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
                throw new SistemaVentaPasajesException("Ya existe un bus con la patente " + busAux);
            }
        }
        buses.add(bus);
    }

    public Bus[] getBuses() {
        return buses.toArray(new Bus[0]);
    }


    public boolean addConductor(IdPersona id, Nombre nom, Direccion direccion ) {
        for (Tripulante tripulante: tripulantes){// primero recorro para saber los tripulantes
            if (tripulante.getIdPersona().equals(id)){ //aqui comparo los id's de los tripulantes si son iguales
                return false;
            }
        }
        Conductor conductor= new Conductor(id, nom, direccion); // esta erronea hasta que Benja haga la clase de Conductor 14/05/2026
        tripulantes.add(conductor); //tambien da error debido a que aun no se termina la clase tripulante
        return true;

    }
}
