package controlador;

import Modelo.*;
import Persistencia.IOSVP;
import excepciones.SVPException;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class SistemaVentaPasajes {

    private static SistemaVentaPasajes instance;
    private ArrayList<Cliente> clientes;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Viaje> viajes;
    private final ArrayList<Venta> ventas;
    private final ArrayList<Bus>buses;
    private ControladorEmpresas controladorEmpresas;

    private SistemaVentaPasajes(){
       this.viajes = new ArrayList<>();
       this.clientes = new ArrayList<>();
       this.pasajeros = new ArrayList<>();
       this.ventas = new ArrayList<>();
       this.buses = new ArrayList<>();
       this.controladorEmpresas = ControladorEmpresas.getInstance();

    }
    public static SistemaVentaPasajes getInstance(){
        if(instance == null){
            instance = new SistemaVentaPasajes();

        }
        return instance;
    }



    public void createCliente(IdPersona id, Nombre nom, String fono, String email) {
        Optional<Cliente> buscarCliente=findCliente(id);
        if (buscarCliente.isPresent()) {
            throw new SVPException("Ya existe un cliente con el id " + id);
        }
        Cliente nuevoCliente= new Cliente(id, nom, email);
        nuevoCliente.setTelefono(fono);
        clientes.add(nuevoCliente);


    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        Optional <Pasajero> buscarPasajero=findPasajero(id);
           if (buscarPasajero.isPresent()) {
               throw new SVPException("Ya existe un pasajero con el id " + id);
           }

        Pasajero nuevoPasajero = new Pasajero(id, nom);
        nuevoPasajero.setFonoContacto(fonoContacto);
        nuevoPasajero.setNomContacto(nomContacto);
        pasajeros.add(nuevoPasajero);
    }



    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patenteBus, IdPersona[] idTripulantes, String[] nomComunas) {
        Optional<Bus>busEncontrado= ControladorEmpresas.getInstance().findBus(patenteBus);
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fecha) && viaje.getHora().equals(hora) && viaje.getBus().getPatente().equals(patenteBus)) {
                throw new SVPException("Ya existe viaje con fecha, hora y patente de bus indicados ");
            }
        }
        if (busEncontrado.isEmpty()) {
            throw new SVPException("No existe un bus con la patente " + patenteBus);
        }
        Bus bus= busEncontrado.get();
        Rut rutEmpresa= bus.getRutEmpresa();
       Optional<Auxiliar> auxEncontrado=ControladorEmpresas.getInstance().findAuxiliar(idTripulantes[0], rutEmpresa);
        if (auxEncontrado.isEmpty()) {
            throw new SVPException("No existe auxiliar con el id indicado en la empresa con el rut indicado ");
        }
        Optional<Conductor> conductorEncontrado= ControladorEmpresas.getInstance().findConductor(idTripulantes[1], rutEmpresa);
        if (conductorEncontrado.isEmpty()) {
            throw new SVPException("No existe conductor con el id indicado en la empresa con el rut indicado");
        }
        Optional<Terminal> salidaEncontrada = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[0]);
        Optional<Terminal> llegadaEncontrada = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[1]);
        if (salidaEncontrada.isEmpty()) {
            throw new SVPException("No existe terminal de salida en la comuna " + nomComunas[0]);
        }
        if (llegadaEncontrada.isEmpty()) {
            throw new SVPException("No existe terminal de llegada en la comuna " + nomComunas[1]);
        }
        Viaje nuevoViaje = new Viaje(fecha, hora, precio, duracion, bus, auxEncontrado.get(), new Conductor[]{conductorEncontrado.get()}, salidaEncontrada.get(), llegadaEncontrada.get());

        bus.addViaje(nuevoViaje);
        viajes.add(nuevoViaje);

    }

    public void iniciaVenta(String idDocumento, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        Optional<Cliente> clienteOpt = findCliente(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new SVPException("No existe cliente con id indicado");
        }

        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isPresent()) {
            throw new SVPException("Ya existe venta con el id y tipo de documento indicados");
        }

        Venta nuevaVenta = new Venta(idDocumento, tipo, fechaVenta, clienteOpt.get());
        ventas.add(nuevaVenta);
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje) {//hecho por benja
        int contador = 0;
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fechaViaje)) {
                contador++;
            }
        }

        String[][] datos = new String[contador][4];
        int i = 0;


        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fechaViaje)) {
                datos[i][0] = viaje.getBus().getPatente();
                datos[i][1] = viaje.getHora().toString();
                datos[i][2] = String.valueOf(viaje.getPrecio());
                datos[i][3] = String.valueOf(viaje.getNroAsientosDisponibles());
                i++;
            }
        }
        return datos;
    }
    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fecha) &&
                    viaje.getHora().equals(hora) &&
                    viaje.getBus().getPatente().equals(patenteBus)) {
                return viaje.getAsientos();
            }
        }

        return new String[0][0];
    }


    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Optional<Venta> ventaEncontrada = findVenta(idDocumento, tipo);

        if (ventaEncontrada.isPresent()) {
            return Optional.of(ventaEncontrada.get().getMontoPagado());
        }
        return Optional.empty();
    }


    public Optional<String> getNombrePasajero(IdPersona id) {

        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(id)) {
                return Optional.of(p.getNombreCompleto().toString());
            }
        }
        return Optional.empty();
    }
    public void vendePasaje(String idDocumento, TipoDocumento tipo, LocalDate fecha, LocalTime hora, String patenteBus, IdPersona idPersona, int nroAsiento) {

        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }

        Optional<Pasajero> pasajeroOpt = findPasajero(idPersona);
        if (pasajeroOpt.isEmpty()) {
            throw new SVPException("No existe pasajero con el id indicado");
        }

        Optional<Viaje> viajeOpt = findViaje(fecha, hora, patenteBus);
        if (viajeOpt.isEmpty()) {
            throw new SVPException("No existe viaje con la fecha, hora y patente de bus indicados");
        }

        Viaje viaje = viajeOpt.get();
        ventaOpt.get().createPasaje(nroAsiento, viaje, pasajeroOpt.get());
    }
    public String[][] listVentas() {
        int CantidadVentas = ventas.size();

        if (CantidadVentas == 0) {
            return null;
        }

        String[][] matrizVentas = new String[CantidadVentas][7];
        for (int i = 0; i < CantidadVentas; i++) {
            Venta venta = ventas.get(i);
            Cliente cliente = venta.getCliente();

            matrizVentas[i][0] = venta.getIdDocumento();
            matrizVentas[i][1] = venta.getTipo().toString();
            matrizVentas[i][2] = venta.getFecha().toString();
            matrizVentas[i][3] = cliente.getIdPersona().toString();
            matrizVentas[i][4] = cliente.getNombreCompleto().toString();
            matrizVentas[i][5] = String.valueOf(venta.getPasajes().length);
            matrizVentas[i][6] = "Monto $:" + venta.getMontoPagado();

        }
        return matrizVentas;
    }

    public String[][] listViajes() {
        if (this.viajes.isEmpty()) {
            return new String[0][0];
        }

        String[][] viajes = new String[this.viajes.size()][8];

        for (int i = 0; i < this.viajes.size(); i++) {
            Viaje v = this.viajes.get(i);

            viajes[i][0] = v.getFecha().toString();
            viajes[i][1] = v.getHora().toString();
            viajes[i][2] = v.getFechaHoraTermino().toLocalTime().toString();
            viajes[i][3] = "$" + v.getPrecio();

            String[][] asientos = v.getAsientos();
            int asientosLibres = 0;
            for (int j = 0; j < asientos.length; j++) {
                if (!asientos[j].equals("*")) {
                    asientosLibres++;
                }
            }
            viajes[i][4] = String.valueOf(asientosLibres);
            viajes[i][5] = v.getBus().getPatente();
            viajes[i][6] = v.getTerminalSalida().getDireccion().toString();
            viajes[i][7] = v.getTerminalLlegada().getDireccion().toString();
        }
        return viajes;
    }


    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patenteBus) {

        Optional<Viaje> viajeEncontrado = findViaje(fecha, hora, patenteBus);

        if (viajeEncontrado.isPresent()) {
            Viaje viaje = viajeEncontrado.get();
            viaje.getListaPasajeros();
            return  viaje.getListaPasajeros();
        }

        return new String[0][0];
    }
    public void readDatosIniciales() throws SVPException {
        try {

            Object[] datos = IOSVP.getInstancia().readDatosIniciales();
            controladorEmpresas.setDatosIniciales(datos);
            this.clientes= Arrays.stream(datos)
                    .filter(Cliente.class::isInstance)
                    .map(Cliente.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            this.pasajeros= Arrays.stream(datos)
                    .filter(Pasajero.class::isInstance)
                    .map(Pasajero.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            this.viajes= Arrays.stream(datos)
                    .filter(Viaje.class::isInstance)
                    .map(Viaje.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));

            System.out.println("\n=== RESUMEN DE CARGA ===");
            System.out.println("Clientes: " + clientes.size());
            System.out.println("Pasajeros: " + pasajeros.size());
            System.out.println("Viajes: " + viajes.size());
            System.out.println("\n=== CLIENTES ===");
            clientes.forEach(System.out::println);
            System.out.println("\n=== PASAJEROS ===");
            pasajeros.forEach(System.out::println);
            System.out.println("\n=== VIAJES ===");
            viajes.forEach(System.out::println);

        } catch (SVPException e) {
            throw new SVPException("Error al leer datos iniciales: " + e.getMessage());
        }
    }


    private Optional<Cliente> findCliente(IdPersona id) {
        for (Cliente cliente : clientes) {
            if (id.equals(cliente.getIdPersona())) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }
    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (Venta ventaActual : ventas) {
            if (ventaActual.getIdDocumento().equals(idDocumento) && ventaActual.getTipo().equals(tipoDocumento)) {
                return Optional.of(ventaActual);
            }
        }
        return Optional.empty();
    }
    private Optional<Bus> findBus(String patente) {
        for (Bus bus: buses){
            if(bus.getPatente().equals(patente)){
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        for (Viaje v : viajes) {
            if (v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patenteBus)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }
    private Optional<Pasajero> findPasajero(IdPersona idPasajero) {
        for (Pasajero p : pasajeros) {
            if (idPasajero.equals(p.getIdPersona())) return Optional.of(p);
        }
        Optional<Cliente> c = findCliente(idPasajero);
        if (c.isPresent()) {
            Cliente clienteEncontrado = c.get();
            Pasajero nuevo= new Pasajero(clienteEncontrado.getIdPersona(), clienteEncontrado.getNombreCompleto());
            nuevo.setTelefono(clienteEncontrado.getTelefono());
            pasajeros.add(nuevo);
            return Optional.of(nuevo);
        }
        return Optional.empty();
    }
    public void pagaVenta(String idDocumento, TipoDocumento tipo, long nroTarjeta) { //tarjeta
        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }

        if (!ventaOpt.get().pagaMonto(nroTarjeta)) {
            throw new SVPException("La venta ya fue pagada");
        }
    }
    public void pagaVenta(String idDocumento, TipoDocumento tipo) { //efectivo
        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }

        if (!ventaOpt.get().pagaMonto()) {
            throw new SVPException("La venta ya fue pagada");
        }
    }
}