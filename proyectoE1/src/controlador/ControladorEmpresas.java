package controlador;

import Modelo.*;
import excepciones.SistemaVentaPasajesException;
import utilidades.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

public class ControladorEmpresas {
    private static ControladorEmpresas instance;

    private final ArrayList<Empresa> empresas;
    private final ArrayList<Terminal> terminales;
    private final ArrayList<Bus> buses;
    private final ArrayList<Venta> ventas;

    private ControladorEmpresas() {
        this.empresas = new ArrayList<>();
        this.terminales = new ArrayList<>();
        this.buses = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public static ControladorEmpresas getInstance() {
        if (instance == null) {
            instance = new ControladorEmpresas();
        }
        return instance;
    }

    public void createEmpresa(Rut rut, String nombre, String url) {
        Optional<Empresa> buscarEmpresa = findEmpresa(rut);
        if (buscarEmpresa.isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe un empresa con el rut indicado: " + rut);
        }
        Empresa empresa= new Empresa(rut, nombre);
        empresa.setUrl(url);
        empresas.add(empresa);
    }

    public void createBus(String patente, String marca, String modelo, int nroAsientos,Rut rutEmp) {
        Optional<Bus> buscarBusPat = findBus(patente);
        if (buscarBusPat.isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe bus con la patente indicada " + patente);
        }
        Optional<Empresa>buscarBusRut= findEmpresa(rutEmp);
        if (buscarBusRut.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado "+ rutEmp);
        }

        Bus nuevoBus = new Bus(patente, nroAsientos, rutEmp);
        nuevoBus.setMarca(marca);
        nuevoBus.setModelo(modelo);
        buses.add(nuevoBus);
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


    public String[][] listVentasEmpresa(Rut rut) {
        if (findEmpresa(rut).isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado " + rut);
        }
        Empresa empresa = findEmpresa(rut).get();
        ArrayList<Venta> listaVentas = new ArrayList<>();

        for (Bus b : empresa.getBuses()) {
            for (Viaje v : b.getViajes()) {
                listaVentas.addAll(List.of(v.getVentas()));
            }
        }

        if (listaVentas.isEmpty()) {
            return new String[0][0];
        }
        String[][] ventasEmpresas = new String[listaVentas.size()][4];

        for (int i = 0; i < listaVentas.size(); i++) {
            Venta venta = listaVentas.get(i);
            ventasEmpresas[i][0] = venta.getFecha().toString();
            ventasEmpresas[i][1] = venta.getTipo().toString();
            ventasEmpresas[i][2] = String.valueOf(venta.getMonto());
            ventasEmpresas[i][3] = venta.getPago().toString();
        }
        return ventasEmpresas;
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

    public void hireConductorForEmpresa(Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {

        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);

        if (EmpresaBuscada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado.");
        }

        Empresa EmpresaContratada = EmpresaBuscada.get();
        boolean ContratacionExitosa = empresa.addConductor(Id, nombre, direccion);

        if (!ContratacionExitosa) {
            throw new SistemaVentaPasajesException("El conductor con el id dado por la empresa ya se encuentra contratado.");
        }

    }

    public void hireAuxiliarForEmpresa(Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {

        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);

        if (EmpresaBuscada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado.");
        }

        Empresa EmpresaContratada = EmpresaBuscada.get();
        boolean ContratacionExitosa = empresa.addAuxiliar(Id, nombre, direccion);

        if (!ContratacionExitosa) {
            throw new SistemaVentaPasajesException("El Auxiliar con el id dado por la empresa ya se encuentra contratado.");
        }

    }

    Optional<Conductor> findConductor(IdPersona Id, Rut RutEmp) {
        Optional<Empresa> EmpresaOpcion = findEmpresa(rutEmpresa);

        if (EmpresaOpcion.isEmpty()) {
            return Optional.empty();
        }

        Empresa EmpresaContratada = EmpresaOpcion.get();
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
    protected Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa empresa : empresas) {
            if (empresa.getRut().equals(rut)) {
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
    protected Optional<Terminal> findTerminal(String nombre) {

        for (Terminal terminal : terminales) {

            if (terminal.getNombre().equals(nombre)) {
                return Optional.of(terminal);
            }
        }

        return Optional.empty();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna) {

        for (Terminal terminal : terminales) {

            if (terminal.getDireccion().getComuna().equals(comuna)) {
                return Optional.of(terminal);
            }
        }

        return Optional.empty();
    }

    public String[][] listEmpresas() {

        if (empresas.isEmpty()) {
            return new String[0][0];
        }

        String[][] listaEmpresas = new String[empresas.size()][3];

        for (int i = 0; i < empresas.size(); i++) {

            Empresa empresa = empresas.get(i);

            listaEmpresas[i][0] = empresa.getRut().toString();
            listaEmpresas[i][1] = empresa.getNombre();
            listaEmpresas[i][2] = empresa.getUrl();
        }

        return listaEmpresas;
    }

    public String[][] listLlegadasSalidasTerminal(String nombreTerminal, LocalDate fecha) {

        Optional<Terminal> terminalBuscado = findTerminal(nombreTerminal);

        if (terminalBuscado.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe terminal con el nombre indicado");
        }

        Terminal terminal = terminalBuscado.get();

        ArrayList<String[]> listaViajes = new ArrayList<>();

        for (Bus bus : buses) {

            for (Viaje viaje : bus.getViajes()) {

                if (viaje.getTerminalSalida().equals(terminal)
                        && viaje.getFecha().equals(fecha)) {

                    String[] fila = new String[5];

                    fila[0] = "Salida";
                    fila[1] = viaje.getHora().toString();
                    fila[2] = viaje.getTerminalLlegada().getNombre();
                    fila[3] = bus.getPatente();
                    fila[4] = String.valueOf(viaje.getPrecio());

                    listaViajes.add(fila);
                }

                LocalTime horaLlegada = viaje.getHora().plusHours(viaje.getDuracion());

                if (viaje.getTerminalLlegada().equals(terminal)
                        && viaje.getFecha().equals(fecha)) {

                    String[] fila = new String[5];

                    fila[0] = "Llegada";
                    fila[1] = horaLlegada.toString();
                    fila[2] = viaje.getTerminalSalida().getNombre();
                    fila[3] = bus.getPatente();
                    fila[4] = String.valueOf(viaje.getPrecio());

                    listaViajes.add(fila);
                }
            }
        }

        if (listaViajes.isEmpty()) {
            return new String[0][0];
        }

        String[][] resultado = new String[listaViajes.size()][5];

        for (int i = 0; i < listaViajes.size(); i++) {
            resultado[i] = listaViajes.get(i);
        }

        return resultado;
    }

}