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
import java.util.Optional;

public class SistemaVentaPasajes {

    private static SistemaVentaPasajes instance;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Pasajero> pasajeros;
    private final ArrayList<Viaje> viajes;
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

        boolean viajeExiste = viajes.stream()
                .anyMatch(viaje -> viaje.getFecha().equals(fecha)
                        && viaje.getHora().equals(hora)
                        && viaje.getBus().getPatente().equals(patenteBus));

        if (viajeExiste) {
            throw new SVPException("Ya existe viaje con fecha, hora y patente de bus indicados ");
        }

        Bus bus = ControladorEmpresas.getInstance().findBus(patenteBus)
                .orElseThrow(() -> new SVPException("No existe un bus con la patente " + patenteBus));

        Rut rutEmpresa = bus.getRutEmpresa();

        Auxiliar auxiliar = ControladorEmpresas.getInstance().findAuxiliar(idTripulantes[0], rutEmpresa)
                .orElseThrow(() -> new SVPException("No existe auxiliar con el id indicado en la empresa con el rut indicado "));

        Conductor conductor = ControladorEmpresas.getInstance().findConductor(idTripulantes[1], rutEmpresa)
                .orElseThrow(() -> new SVPException("No existe conductor con el id indicado en la empresa con el rut indicado"));

        Terminal salida = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[0])
                .orElseThrow(() -> new SVPException("No existe terminal de salida en la comuna " + nomComunas[0]));

        Terminal llegada = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[1])
                .orElseThrow(() -> new SVPException("No existe terminal de llegada en la comuna " + nomComunas[1]));

        // 3. Si llegamos hasta aquí, todas las validaciones pasaron exitosamente
        Viaje nuevoViaje = new Viaje(fecha, hora, precio, duracion, bus, auxiliar, new Conductor[]{conductor}, salida, llegada);

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

    public String[][] getHorariosDisponibles(LocalDate fechaViaje) {
        return viajes.stream()
                .filter(viaje -> viaje.getFecha().equals(fechaViaje))
                .map(viaje -> new String[]{
                        viaje.getBus().getPatente(),
                        viaje.getHora().toString(),
                        String.valueOf(viaje.getPrecio()),
                        String.valueOf(viaje.getNroAsientosDisponibles())
                })
                .toArray(String[][]::new);
    }
    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        return viajes.stream()
                .filter(viaje -> viaje.getFecha().equals(fecha) &&
                        viaje.getHora().equals(hora) &&
                        viaje.getBus().getPatente().equals(patenteBus))
                .findFirst()
                .map(Viaje::getAsientos)
                .orElse(new String[0][0]);
    }


    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        return findVenta(idDocumento, tipo)
                .map(Venta::getMontoPagado)
                .orElse(0);
    }


    public Optional<String> getNombrePasajero(IdPersona id) {
        return pasajeros.stream()
                .filter(p -> p.getIdPersona().equals(id))
                .findFirst()
                .map(p -> p.getNombreCompleto().toString());
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
            return  viaje.getListaPasajeros();
        }

        return new String[0][0];
    }
    public void readDatosIniciales() throws SVPException {
        try {
            Object[] datos = IOSVP.getInstancia().readDatosIniciales();
            controladorEmpresas.setDatosIniciales(datos);
        } catch (SVPException e) {
            throw new SVPException("Error al leer datos iniciales : " + e.getMessage());
        }
    }

    public void saveDatosSistema() throws SVPException {
        IOSVP io = IOSVP.getInstancia();

        Object[] arregloControladores = { this, ControladorEmpresas.getInstance() };

        io.saveControladores(arregloControladores);
    }
    public void generatePasajesVenta(String idDoc, TipoDocumento tipo) throws SVPException {
        Optional<Venta> ventaOpt = findVenta(idDoc, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }
        String nombreArchivo = idDoc + tipo.name().toLowerCase() + ".txt";
        try {
            IOSVP.getInstancia().savePasajesDeVenta(ventaOpt.get().getPasajes(), nombreArchivo);
        } catch (SVPException e) {
            throw new SVPException(e.getMessage());
        }
    }


    public void readDatosSistemas() throws SVPException {
        try {
            Object[] ControladorGuardadoObjetos = IOSVP.getInstancia().readControladores();
            SistemaVentaPasajes.instance = (SistemaVentaPasajes) ControladorGuardadoObjetos[0];
            ControladorEmpresas empresaGuardada = (ControladorEmpresas) ControladorGuardadoObjetos[1];
            ControladorEmpresas.getInstance().setInstancePersistente(empresaGuardada);
        } catch (SVPException e){
            throw new SVPException("Error :" +e.getMessage());
        }
    }



    private Optional<Cliente> findCliente(IdPersona id) {
        return clientes.stream()
                .filter(cliente -> cliente.getIdPersona().equals(id))
                .findFirst();
    }
    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        return ventas.stream()
                .filter(v -> v.getIdDocumento().equals(idDocumento))
                .filter(venta -> venta.getTipo().equals(tipoDocumento))
                .findFirst();
    }
    private Optional<Bus> findBus(String patente) {
        return buses.stream()
                .filter(bus -> bus.getPatente().equals(patente))
                .findFirst();
    }

    //getFecha , getHora , getPatente
    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        return viajes.stream()
                .filter(viaje -> viaje.getFecha().equals(fecha))
                .filter(viaje -> viaje.getHora().equals(hora))
                .filter(viaje -> viaje.getBus().getPatente().equals(patenteBus))
                .findFirst();
    }
    private Optional<Pasajero> findPasajero(IdPersona idPasajero) {
        Optional<Pasajero> pasajeroBuscado = pasajeros.stream()
                .filter(p -> idPasajero.equals(p.getIdPersona()))
                .findFirst();

        if (pasajeroBuscado.isPresent()) {
            return pasajeroBuscado;
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