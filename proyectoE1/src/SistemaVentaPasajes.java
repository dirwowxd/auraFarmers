import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    ArrayList<Viaje> viajes = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();

    public boolean createCliente(idPersona idPersona, Nombre nombre, String fono, String email) {
        for (Cliente cliente : clientes) {
            if (idPersona.equals(cliente.getIdPersona())) {
                System.out.println("No se puede tener el mismo ID de otro cliente...");
                return false;
            }

        }
        Cliente nuevocliente = new Cliente(idPersona, nombre, email); // los parametros son 4 pero cliente solo tiene 3 parametros
        nuevocliente.setTelefono(fono); // aca seteo el telefono para que la sheet no me de eror
        clientes.add(nuevocliente);// lo añado a la lista
        return true;

    }

    public boolean createPasajero(idPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        for (Pasajero pasajero : pasajeros) {
            if (id.equals(pasajero.getIdPersona())) {
                System.out.println("No se puede tener el mismo ID de otro cliente...");
                return false;
            }
        }
        Pasajero nuevoPasajero = new Pasajero(id, nom); // wea la wea webeada con las herencia ni habia caxaoooo
        nuevoPasajero.setTelefono(fono);
        nuevoPasajero.setFonoContacto(fonoContacto);
        nuevoPasajero.setNomContacto(nomContacto);
        pasajeros.add(nuevoPasajero);
        return true;
    }

    public boolean createBus(String patente, String marca, String modelo, int nroAsientos) {
        for (Bus bus : buses) {
            if (patente.equals(bus.getPatente())) {
                System.out.println("No se puede tener el mismo ID de otro Bus...");
                return false;
            }
        }
        Bus nuevoBus = new Bus(patente, nroAsientos);
        nuevoBus.setMarca(marca);
        nuevoBus.setModelo(modelo);
        buses.add(nuevoBus);
        return true;
    }

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patenteBus) {
        for (int i = 0; i < viajes.size(); i++) {
            //falta clase viaje
        }
        return true;
    }

    public boolean iniciaVenta(String idDocumento, TipoDocumento tipo, LocalDate fechaVenta,idPersona idCliente) {
        boolean existeCliente = false;
        Cliente clienteComprador=null;
        for (Cliente cliente : clientes) {
            if (idCliente.equals(cliente.getIdPersona())) {
                existeCliente = true;
                clienteComprador=cliente;
                break;
            }
        }
        if (!existeCliente) {
            return false;
        }
        for (Venta venta : ventas) {
            if (idDocumento.equals(venta.getIdDocumento()) &&  tipo.equals(venta.getTipo())) {
                return false;
            }
        }

        Venta nuevaVenta= new Venta(idDocumento, tipo, fechaVenta, clienteComprador);
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
    public String[][] listAsientosDeViaje (LocalDate fecha, LocalTime hora, String patenteBus){ //hecho por benja
        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patenteBus);
        if (viaje == null) {
            return new String[0][0];
        }
        return viaje.getAsientos();
    }


    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Venta ventaEncontrada = this.findVenta(idDocumento, tipo);

        if (ventaEncontrada != null) {
            return ventaEncontrada.getMonto();
        }
        return 0;
    }




    public String getNombrePasajero (idPersona idPasajero){ //hecho por benja
        Pasajero pasajero = findPasajero(idPasajero);
        if (pasajero == null) {
            return null;
        }
        return pasajero.getNombreCompleto().getNombres();
    }
    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patenteBus, int asiento, idPersona idPasajero) {
        return false; //hacer

    }
    public String[][] listVentas(){
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
        if(viajes.isEmpty()){
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
        return null; //hacer
    }
    private Cliente findCliente(idPersona id) {
        for (Cliente cliente : clientes) {
            if (id.equals(cliente.getIdPersona())) {
                System.out.println("No se puede tener el mismo ID de otro cliente...");
                return cliente;
            }
        }
        return null;
    }
    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (Venta ventaActual : ventas) {
            if (ventaActual.getIdDocumento().equals(idDocumento) && ventaActual.getTipo().equals(tipoDocumento)) {
                return  ventaActual;
            }
        }

        return null;
    }
    private Bus findBus(String patente){
        return null;//hacer
    }

    private Viaje findViaje(String fecha, String hora, String patenteBus) {
        for (Viaje ViajeActual : viajes) {
            if (ViajeActual.getFecha().equals(fecha) && ViajeActual.getHora().equals(hora) && ViajeActual.getBus().getPatente().equals(patenteBus)) {
                return  ViajeActual;
            }
        }
        return null;
    }
    private Pasajero findPasajero(idPersona idPasajero) {
        for (Pasajero pasajero : pasajeros) {
            if (idPasajero.equals(pasajero.getIdPersona())) {
            return  pasajero;
            }
        }
        return null;

        }


}