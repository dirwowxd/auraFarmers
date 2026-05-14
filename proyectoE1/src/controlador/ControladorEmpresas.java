package controlador;

import Modelo.Bus;
import Modelo.Empresa;
import Modelo.Terminal;
import excepciones.SistemaVentaPasajesException;
import utilidades.Rut;

import java.util.ArrayList;
import java.util.Optional;
import utilidades.Rut;

public class ControladorEmpresas {
    private static ControladorEmpresas instance;

    private final ArrayList<Empresa> empresas;
    private final ArrayList<Terminal> terminales;
    private final ArrayList<Bus> buses;

    private ControladorEmpresas() {
        this.empresas = new ArrayList<>();
        this.terminales = new ArrayList<>();
        this.buses = new ArrayList<>();
    }

    public static ControladorEmpresas getInstance() {
        if (instance == null) {
            instance = new ControladorEmpresas();
        }
        return instance;
    }
    public void createEmpresa(Rut rut, String nombre, String url) {
        Optional<Empresa>buscarEmpresa= findEmpresa()
    }
    public void createBus(String patente, String marca, String modelo, int nroAsientos) {
        Optional<Bus> buscarBus= findBus(patente);
        if (buscarBus.isPresent()) {
            throw new SistemaVentaPasajesException("No existe un Bus con la patente indicada " + patente);
        }
        Bus nuevoBus= new Bus(patente, nroAsientos);
        nuevoBus.setMarca(marca);
        nuevoBus.setModelo(modelo);
        buses.add(nuevoBus);
    }
    protected Optional<Empresa>findEmpresa(Rut rut) {
        for (Empresa empresa : empresas) {
            if (empresa.get) {
                return Optional.of(empresa);
            }
        }
        return Optional.empty();
    }

    protected Optional<Bus> findBus(String patente) {
        for (Bus bus : buses) {
            if (bus.getPatente().equals(patente)) {
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }

}


    //dejo guardado esto aca ya que no habia notado que se habia cambiado de SistemaVentaPasaje a esta clase

