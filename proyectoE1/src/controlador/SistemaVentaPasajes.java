package controlador;

import Modelo.Cliente;
import Modelo.Pasajero;
import excepciones.SistemaVentaPasajesException;
import utilidades.IdPersona;
import utilidades.Nombre;
import Modelo.Bus;
import Modelo.Viaje;
import Modelo.Venta;
import Modelo.TipoDocumento;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    ArrayList<Viaje> viajes = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();

    public void createCliente(IdPersona id, Nombre nom, String fono, String email) {
        Optional<Cliente> buscarCliente=findCliente(id);
        if (buscarCliente.isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe un cliente con el id " + id);
        }
        Cliente nuevoCliente= new Cliente(id, nom, email);
        nuevoCliente.setTelefono(email);
        clientes.add(nuevoCliente);


    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        Optional <Pasajero> buscarPasajero=findPasajero(id);
           if (buscarPasajero.isPresent()) {
               throw new SistemaVentaPasajesException("Ya existe un pasajero con el id " + id);
           }

        Pasajero nuevoPasajero = new Pasajero(id, nom); 
        nuevoPasajero.setTelefono(fono);
        nuevoPasajero.setFonoContacto(fonoContacto);
        nuevoPasajero.setNomContacto(nomContacto);
        pasajeros.add(nuevoPasajero);
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

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, String patenteBus) {
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fecha) && viaje.getHora().equals(hora) && viaje.getBus().equals(patenteBus)) {
                throw new SistemaVentaPasajesException("Ya existe viaje con fecha, hora y patente de bus indicados ");
            }
        }
        //seguir modificando 05/05/2026

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


    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Venta ventaEncontrada = this.findVenta(idDocumento, tipo);

        if (ventaEncontrada != null) {
            return ventaEncontrada.getMonto();
        }
        return 0;
    }


    public String getNombrePasajero(IdPersona id) {

        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(id)) {
                return p.getNombreCompleto().toString();
            }
        }
        return null;
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
        if (viajes.isEmpty()) {
            System.out.println("No hay viajes registrados.");
            return new String[0][0];
        }
        String[][] listadosViajes = new String[viajes.size()][5];
        for (int i = 0; i < viajes.size(); i++) {
            Viaje viaje = viajes.get(i);
            listadosViajes[i][0] = String.valueOf(viaje.getFecha());
            listadosViajes[i][1] = String.valueOf(viaje.getHora());
            listadosViajes[i][2] = String.valueOf(viaje.getPrecio());
            listadosViajes[i][3] = String.valueOf(viaje.getNroAsientosDisponibles());
            listadosViajes[i][4] = viaje.getBus().getPatente();
        }
        return listadosViajes;
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
            Pasajero nuevo= new Pasajero(clienteEncontrado.getIdPersona(), clienteEncontrado.getNombreCompleto());
            nuevo.setTelefono(clienteEncontrado.getTelefono());
            pasajeros.add(nuevo);
            return Optional.of(nuevo);
        }
        return Optional.empty();
    }



}