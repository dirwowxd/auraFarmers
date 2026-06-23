package controlador;

import Modelo.*;
import excepciones.SVPException;
import utilidades.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorEmpresas {
    private static ControladorEmpresas instance;

    private ArrayList<Empresa> empresas;
    private ArrayList<Terminal> terminales;
    private ArrayList<Bus> buses;
    private ArrayList<Venta> ventas;


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
            throw new SVPException("Ya existe un empresa con el rut indicado: " + rut);
        }
        Empresa empresa= new Empresa(rut, nombre);
        empresa.setUrl(url);
        empresas.add(empresa);
    }

    public void createBus(String patente, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Optional<Bus> buscarBusPat = findBus(patente);
        if (buscarBusPat.isPresent()) {
            throw new SVPException("Ya existe bus con la patente indicada " + patente);
        }
        Optional<Empresa> buscarBusRut = findEmpresa(rutEmp);
        if (buscarBusRut.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado "+ rutEmp);
        }

        Empresa empresaDuena = buscarBusRut.get();
        Bus nuevoBus = new Bus(patente, nroAsientos, empresaDuena);
        nuevoBus.setMarca(marca);
        nuevoBus.setModelo(modelo);
        buses.add(nuevoBus);
    }



    public void createTerminal(String nombre, Direccion direccion) {
        findTerminal(nombre).ifPresent(t -> {
            throw new SVPException("Ya existe terminal con ese nombre");
        });
        boolean existeEnComuna = terminales.stream()
                .anyMatch(ter -> ter.getDireccion().getComuna().equals(direccion.getComuna()));

        if (existeEnComuna) {
            throw new SVPException("Ya existe terminal en la comuna");
        }
        Terminal terminalNuevo = new Terminal(nombre, direccion);
        terminales.add(terminalNuevo);
    }


    public String[][] listVentasEmpresa(Rut rut) {
        Empresa empresa = findEmpresa(rut)
                .orElseThrow(() -> new SVPException("No existe empresa con el rut indicado " + rut));

        return Arrays.stream(empresa.getBuses())
                .flatMap(bus -> Arrays.stream(bus.getViajes()))
                .flatMap(viaje -> Arrays.stream(viaje.getVentas()))
                .map(venta -> new String[]{
                        venta.getFecha().toString(),
                        venta.getTipo().toString(),
                        String.valueOf(venta.getMontoPagado()),
                        venta.getPago().toString()
                })
                .toArray(String[][]::new);
    }

    protected void setDatosIniciales(Object[] datos) throws SVPException {
        List<Object> listaDatos = Arrays.asList(datos);
        try {
            this.empresas = listaDatos.stream()
                    .filter(Empresa.class::isInstance) //Deja pasar solo los objetos que son Empresa
                    .map(Empresa.class::cast) //el objeto de Object a Empresa
                    .collect(Collectors.toCollection(ArrayList::new)); //Recoge todo denuevo en un Arraylist (verde?)
            this.buses = listaDatos.stream()
                    .filter(Bus.class::isInstance)
                    .map(Bus.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            this.ventas=listaDatos.stream()
                    .filter(Venta.class::isInstance)
                    .map(Venta.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            this.terminales = listaDatos.stream()
                    .filter(Terminal.class::isInstance)
                    .map(Terminal.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));

        } catch (Exception e) {
            throw new SVPException("Error : " + e.getMessage());
        }
    }

    public Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {
        return findEmpresa(rutEmpresa)
                .flatMap(empresa -> Arrays.stream(empresa.getTripulantes())
                        .filter(Objects::nonNull)
                        .filter(Auxiliar.class::isInstance)
                        .filter(tripulante -> tripulante.getIdPersona().equals(id))
                        .map(Auxiliar.class::cast)
                        .findFirst()
                );
    }

    public void hireConductorForEmpresa(Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {

        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);

        if (EmpresaBuscada.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado.");
        }

        Empresa EmpresaContratada = EmpresaBuscada.get();
        boolean ContratacionExitosa = EmpresaContratada.addConductor(Id, nombre, direccion);

        if (!ContratacionExitosa) {
            throw new SVPException("El conductor con el id dado por la empresa ya se encuentra contratado.");
        }

    }

    public void hireAuxiliarForEmpresa(Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {
        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);
        if (EmpresaBuscada.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado.");
        }
        Empresa EmpresaContratada = EmpresaBuscada.get();
        boolean ContratacionExitosa = EmpresaContratada.addAuxiliar(Id, nombre, direccion);
        if (!ContratacionExitosa) {
            throw new SVPException("El Auxiliar con el id dado por la empresa ya se encuentra contratado.");
        }
    }

    protected void setInstancePersistente(ControladorEmpresas InstanciaPersistente) {
            instance= InstanciaPersistente;

    }
    Optional<Conductor> findConductor(IdPersona id, Rut rutEmp) {
        return findEmpresa(rutEmp)
                .flatMap(empresa -> Arrays.stream(empresa.getTripulantes())
                        .filter(Objects::nonNull)
                        .filter(Conductor.class::isInstance)
                        .filter(tripulante -> tripulante.getIdPersona().equals(id))
                        .map(Conductor.class::cast)
                        .findFirst()
                );
    }
    protected Optional<Empresa> findEmpresa(Rut rut) {
        return empresas.stream() // Abrimos el flujo de la lista
                .filter(empresa -> empresa.getRut().equals(rut)) //Filtramos el que coincida
                .findFirst(); //Atrapamos el primero que cumpla (devuelve Optional automático)
    }

    protected Optional<Bus> findBus(String patente) {
        return buses.stream()
                .filter(bus -> bus.getPatente().equals(patente))
                .findFirst();
    }

    protected Optional<Terminal> findTerminal(String nombre) {
        return terminales.stream()
                .filter(terminal -> terminal.getNombre().equals(nombre))
                .findFirst();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna) {
        return terminales.stream()
                .filter(terminal -> terminal.getDireccion().getComuna().equals(comuna))
                .findFirst();
    }
    public String[][] listEmpresas() {
        if (empresas.isEmpty()) {
            return new String[0][0];
        }

        return empresas.stream()
                .map(empresa -> new String[]{
                        (empresa.getRut() != null) ? empresa.getRut().toString() : "N/A",
                        empresa.getNombre(),
                        empresa.getUrl(),
                        String.valueOf(empresa.getTripulantes().length),
                        String.valueOf(empresa.getBuses().length),
                        String.valueOf(empresa.getVentas().length)
                })
                .toArray(String[][]::new);
    }
    public String[][] listLlegadasSalidasTerminal(String nombreTerminal, LocalDate fecha) {

        Terminal terminal = findTerminal(nombreTerminal)
                .orElseThrow(() -> new SVPException("No existe terminal con el nombre indicado"));

        return buses.stream()
                .flatMap(bus -> Arrays.stream(bus.getViajes())
                        .filter(viaje -> viaje.getFecha().equals(fecha))
                        .flatMap(viaje -> {
                            List<String[]> filas = new ArrayList<>();

                            if (viaje.getTerminalSalida().equals(terminal)) {
                                filas.add(new String[]{
                                        "Salida",
                                        viaje.getHora().toString(),
                                        viaje.getTerminalLlegada().getNombre(),
                                        bus.getPatente(),
                                        String.valueOf(viaje.getPrecio())
                                });
                            }

                            if (viaje.getTerminalLlegada().equals(terminal)) {
                                LocalTime horaLlegada = viaje.getHora().plusHours(viaje.getDuracion());
                                filas.add(new String[]{
                                        "Llegada",
                                        horaLlegada.toString(),
                                        viaje.getTerminalSalida().getNombre(),
                                        bus.getPatente(),
                                        String.valueOf(viaje.getPrecio())
                                });
                            }

                            return filas.stream();
                        })
                )
                .toArray(String[][]::new);
    }



}