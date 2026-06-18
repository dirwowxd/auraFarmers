package Modelo;

import excepciones.SVPException;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;



import java.util.ArrayList;
import java.util.Arrays;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;
    private final ArrayList<Bus> buses;
    private final ArrayList<Tripulante> tripulantes;

    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        this.buses = new ArrayList<>();
        this.tripulantes = new ArrayList<>();
    }

    public Rut getRut() {
        return rut;
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
                throw new SVPException("Ya existe un bus con la patente " + busAux.getPatente());
            }
        }
        buses.add(bus);
    }

    public Bus[] getBuses() {
        return buses.toArray(new Bus[0]);
    }


    public boolean addConductor(IdPersona id, Nombre nom, Direccion direccion) {
        return tripulantes.stream().noneMatch(t -> t.getIdPersona().equals(id))
                && this.tripulantes.add(new Conductor(id, nom, direccion));
    }


    public boolean addAuxiliar(IdPersona id, Nombre nom, Direccion direccion) {
        return tripulantes.stream().noneMatch(t -> t.getIdPersona().equals(id))
                && this.tripulantes.add(new Auxiliar(id, nom, direccion));
    }

    public Tripulante[] getTripulantes() {
        return tripulantes.toArray(new Tripulante[0]);
    }

    public Venta[] getVentas() {
        return buses.stream().flatMap(bus -> Arrays.stream(bus.getViajes()))
                .filter(viaje -> viaje != null && viaje.getVentas() != null)
                .flatMap(viaje -> Arrays.stream(viaje.getVentas()))
                .filter(venta -> venta != null)
                .toArray(Venta[]::new);
    }
}
