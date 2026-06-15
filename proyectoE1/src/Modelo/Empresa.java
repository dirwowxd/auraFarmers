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
                throw new SVPException("Ya existe un bus con la patente " + busAux.getPatente());
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

    public Venta[] getVentas() {
        ArrayList<Venta> todasLasVentas = new ArrayList<>();

        for (Bus bus : buses) {
            Arrays.stream(bus.getViajes())
                    .filter(viaje -> viaje != null)
                    .forEach(viaje -> {
                        Venta[] ventas = viaje.getVentas();
                        if (ventas != null) {
                            Arrays.stream(ventas)
                                    .filter(venta -> venta != null)
                                    .forEach(todasLasVentas::add);
                        }
                    });
        }
        return todasLasVentas.toArray(new Venta[0]);
    }
    @Override // temporal
    public String toString() {
        return "Empresa{" +
                "rut=" + rut +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                ", buses=" + buses.size() +
                ", tripulantes=" + tripulantes.size() +
                '}';
    }
}
