package controlador;

import Modelo.*;
import excepciones.SistemaVentaPasajesException;
import utilidades.*;

import java.util.ArrayList;
import java.util.Optional;

import Modelo.Conductor;
import Modelo.Empresa;
import Modelo.Tripulante;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.util.Optional;

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

    public void createTerminal(String nombre, Direccion direccion) {

        Optional<Terminal> terminal = findTerminal(nombre);

        if (terminal.isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe terminal con ese nombre");
        }

        for (int i = 0; i < terminales.size(); i++) {
            Terminal ter = terminales.get(i);

            String comunaExistente = ter.getDireccion().getComuna();
            String comunaNueva = direccion.getComuna();

            if (comunaExistente.equals(comunaNueva)) {
                throw new SistemaVentaPasajesException("Ya existe terminal en la comuna");
            }
        }

        Terminal Terminalnuevo = new Terminal(nombre, direccion);
        terminales.add(Terminalnuevo);
    }

    public Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {

        Optional<Empresa> empresatemp = findEmpresa(rutEmpresa);

        if (empresatemp.isEmpty()) {
            return Optional.empty();
        }

        Empresa empresa = empresatemp.get();
        Tripulante[] tripulantes = empresa.getTripulantes();

        for (int i = 0; i < tripulantes.length; i++) {
            Tripulante t = tripulantes[i];
            if (t != null && t.getIdPersona().equals(id)) {
                if (t instanceof Auxiliar) {
                    Auxiliar aux = (Auxiliar) t;
                    return Optional.of(aux);
                }
            }
        }

        return Optional.empty();
    }

}


    //dejo guardado esto aca ya que no habia notado que se habia cambiado de SistemaVentaPasaje a esta clase



    public void hireConductorForEmpresa (Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {

        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);

        if (EmpresaBuscada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado.");
        }

        Empresa EmpresaContratada =  EmpresaBuscada.get();
        boolean ContratacionExitosa = empresa.addConductor(Id,nombre,direccion);

        if(!ContratacionExitosa){
            throw new SistemaVentaPasajesException("El conductor con el id dado por la empresa ya se encuentra contratado.");
        }

    }

    public void hireAuxiliarForEmpresa (Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {

        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);

        if (EmpresaBuscada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado.");
        }

        Empresa EmpresaContratada =  EmpresaBuscada.get();
        boolean ContratacionExitosa = empresa.addAuxiliar(Id,nombre,direccion);

        if(!ContratacionExitosa){
            throw new SistemaVentaPasajesException("El Auxiliar con el id dado por la empresa ya se encuentra contratado.");
        }

    }

    Optional <Conductor> findConductor(IdPersona Id, Rut RutEmp) {
        Optional<Empresa> EmpresaOpcion = findEmpresa(rutEmpresa);

        if (EmpresaOpcion.isEmpty()) {
            return Optional.empty();
        }

        Empresa EmpresaContratada =  EmpresaOpcion.get();
        Tripulante[] tripulantesEmpresa = EmpresaContratada.getTripulantes();

        for (Tripulante tripulante : tripulantesEmpresa) {

            if (tripulante != null) {
                if (tripulante instanceof Conductor) {
                    if (tripulante.getIdPersona().equals(IdConductor)) {
                        return Optional.of((Conductor) tripulante);
                    }
                }
            }
        }
            return Optional.empty();

    }

}
