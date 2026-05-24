package controlador;

import Modelo.*;
import excepciones.SistemaVentaPasajesException;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentaPasajes {

    private static SistemaVentaPasajes instance;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Pasajero> pasajeros;
    private final ArrayList<Viaje> viajes;
    private final ArrayList<Venta> ventas;

    private SistemaVentaPasajes(){
       this.viajes = new ArrayList<>();
       this.clientes = new ArrayList<>();
       this.pasajeros = new ArrayList<>();
       this.ventas = new ArrayList<>();

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
            throw new SistemaVentaPasajesException("Ya existe un cliente con el id " + id);
        }
        Cliente nuevoCliente= new Cliente(id, nom, email, fono);
        nuevoCliente.setTelefono(fono);
        clientes.add(nuevoCliente);


    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        Optional <Pasajero> buscarPasajero=findPasajero(id);
           if (buscarPasajero.isPresent()) {
               throw new SistemaVentaPasajesException("Ya existe un pasajero con el id " + id);
           }

        Pasajero nuevoPasajero = new Pasajero(id, nom,fono);
        nuevoPasajero.setFonoContacto(fonoContacto);
        nuevoPasajero.setNomContacto(nomContacto);
        pasajeros.add(nuevoPasajero);
    }



    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patenteBus, IdPersona[] idTripulantes, String[] nomComunas) {
        Optional<Bus>busEncontrado= ControladorEmpresas.getInstance().findBus(patenteBus);
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fecha) && viaje.getHora().equals(hora) && viaje.getBus().getPatente().equals(patenteBus)) {
                throw new SistemaVentaPasajesException("Ya existe viaje con fecha, hora y patente de bus indicados ");
            }
        }
        if (busEncontrado.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe un bus con la patente " + patenteBus);
        }
        Bus bus= busEncontrado.get();
        Rut rutEmpresa= bus.getEmpresa().getRut();
       Optional<Auxiliar> auxEncontrado=ControladorEmpresas.getInstance().findAuxiliar(idTripulantes[0], rutEmpresa);
        if (auxEncontrado.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe auxiliar con el id indicado en la empresa con el rut indicado ");
        }
        Optional<Conductor> conductorEncontrado= ControladorEmpresas.getInstance().findConductor(idTripulantes[1], rutEmpresa);
        if (conductorEncontrado.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe conductor con el id indicado en la empresa con el rut indicado");
        }
        Optional<Terminal> salidaEncontrada = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[0]);
        Optional<Terminal> llegadaEncontrada = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[1]);
        if (salidaEncontrada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe terminal de salida en la comuna " + nomComunas[0]);
        }
        if (llegadaEncontrada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe terminal de llegada en la comuna " + nomComunas[1]);
        }
        Viaje nuevoViaje = new Viaje(fecha, hora, precio, duracion, bus, auxEncontrado.get(), conductorEncontrado.get(), salidaEncontrada.get(), llegadaEncontrada.get());

        bus.addViaje(nuevoViaje);
        viajes.add(nuevoViaje);

        System.out.println("...:::: Viaje guardado exitosamente ::::...");


    }

    public boolean iniciaVenta(String idDocumento, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        boolean existeCliente = false;
        Cliente clienteComprador = null;
        for (Cliente cliente : clientes) {
            if (idCliente.equals(cliente.getIdPersona())) {
                existeCliente = true;
                clienteComprador = cliente;
                break;
            }
        }
        if (!existeCliente) {
            return false;
        }
        for (Venta venta : ventas) {
            if (idDocumento.equals(venta.getIdDocumento()) && tipo.equals(venta.getTipo())) {
                return false;
            }
        }

        Venta nuevaVenta = new Venta(idDocumento, tipo, fechaVenta, clienteComprador);
        ventas.add(nuevaVenta);
        return true;
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
            return Optional.of(ventaEncontrada.get().getMonto());
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
    public boolean vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fecha, LocalTime hora, String patenteBus, IdPersona idPasajero, int asiento) {
        Venta ventaEncontrada = findVenta(idDoc, tipo);

        if (ventaEncontrada == null) {
            return false;
        }

        Viaje viajeEncontrado = null;

        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fecha) &&
                    viaje.getHora().equals(hora) &&
                    viaje.getBus().getPatente().equals(patenteBus)) {
                viajeEncontrado = viaje;
                break;
            }
        }

        if (viajeEncontrado == null) {
            return false;
        }

        Pasajero pasajeroEncontrado = findPasajero(idPasajero);

        if (pasajeroEncontrado == null) {
            return false;
        }

        if (!viajeEncontrado.ExisteDisponibilidad()) {
            return false;
        }

        ventaEncontrada.createPasaje(asiento, viajeEncontrado, pasajeroEncontrado);

        return true;
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
            matrizVentas[i][6] = "Monto $:" + venta.getMonto();

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

            String[] asientos = v.getAsientos();
            int asientosLibres = 0;
            for (int j = 0; j < asientos.length; j++) {
                if (!asientos[j].equals("*")) {
                    asientosLibres++;
                }
            }
            viajes[i][4] = String.valueOf(asientosLibres);
            viajes[i][5] = v.getBus().getPatente();
            viajes[i][6] = v.getTerminalSalida().getComuna();
            viajes[i][7] = v.getTerminalLlegada().getComuna();
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
            Pasajero nuevo= new Pasajero(clienteEncontrado.getIdPersona(), clienteEncontrado.getNombreCompleto(), clienteEncontrado.getTelefono(), clienteEncontrado.);
            nuevo.setTelefono(clienteEncontrado.getTelefono());
            pasajeros.add(nuevo);
            return Optional.of(nuevo);
        }
        return Optional.empty();
    }
}