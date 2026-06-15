package Modelo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;

public class Viaje {
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private final Bus bus;
    private final Pasaje[] pasajesVendidos;
    private int contadorPasajes;
    private int duracion;
    private final ArrayList<Auxiliar> auxiliares;
    private final Conductor conductor;
    private final Terminal terminalSalida;
    private final Terminal terminalLlegada;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, int duracion, Bus bus,
                 Auxiliar auxiliar, Conductor[] conductor, Terminal terminalSalida, Terminal terminalLlegada) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        this.duracion = duracion;
        this.auxiliares = new ArrayList<>();
        if (auxiliar != null) this.auxiliares.add(auxiliar);
        this.conductor = conductor[0];
        this.terminalSalida = terminalSalida;
        this.terminalLlegada = terminalLlegada;
        this.pasajesVendidos = new Pasaje[bus.getNroAsientos()];
        this.contadorPasajes = 0;
    }
    public LocalDate getFecha() {
        return fecha; }
    public LocalTime getHora() {
        return hora; }
    public int getPrecio() {
        return precio; }
    public int getDuracion() {
        return duracion; }
    public Bus getBus() {
        return bus; }
    public Terminal getTerminalSalida() {
        return terminalSalida; }
    public Terminal getTerminalLlegada() {
        return terminalLlegada; }
    public Conductor getConductor() {
        return conductor; }




    public LocalDateTime getFechaHoraTermino() {
        return LocalDateTime.of(this.fecha, this.hora).plusMinutes(this.duracion);
    }

    public boolean existeDisponibilidad(int nroAsiento) {
        if (nroAsiento < 1 || nroAsiento > bus.getNroAsientos()) {
            return false;
        }
        return pasajesVendidos[nroAsiento - 1] == null;
    }

    public void addPasaje(Pasaje pasaje) {
        if (contadorPasajes < pasajesVendidos.length) {
            this.pasajesVendidos[pasaje.getAsiento() - 1] = pasaje;
            contadorPasajes++;
        }
    }

    public Tripulante[] getTripulantes() {
        int total = (conductor != null ? 1 : 0) + auxiliares.size();
        Tripulante[] tripulantes = new Tripulante[total];
        int index = 0;
        if (conductor != null) {
            tripulantes[index++] = conductor;
        }
        for (Auxiliar a : auxiliares) {
            tripulantes[index++] = a;
        }
        return tripulantes;
    }
    public String[][] getAsientos() {
        String[][] mapa = new String[bus.getNroAsientos()][2];
        for (int i = 0; i < bus.getNroAsientos(); i++) {
            mapa[i][0] = String.valueOf(i + 1);
            mapa[i][1] = (pasajesVendidos[i] == null) ? "Libre" : "Ocupado";
        }
        return mapa;
    }
    public String[][] getListaPasajeros() {
        String[][] lista = new String[this.contadorPasajes][5];
        int fila = 0;
        for (Pasaje ps : pasajesVendidos) {
            if (ps != null) {
                Pasajero pa = ps.getPasajero();
                lista[fila][0] = String.valueOf(ps.getAsiento());
                lista[fila][1] = pa.getIdPersona().toString();
                lista[fila][2] = pa.getNombreCompleto().toString();
                lista[fila][3] = (pa.getNomContacto() != null) ? pa.getNomContacto().toString() : "No registrado";
                lista[fila][4] = (pa.getFonoContacto() != null) ? pa.getFonoContacto() : "S/N";
                fila++;
            }
        }
        return lista;
    }
    public Venta[] getVentas() {
        ArrayList<Venta> ventasUnicas = new ArrayList<>();

        for (Pasaje pasaje : pasajesVendidos) {
            if (pasaje != null) {
                Venta venta = pasaje.getVenta();
                if (venta != null && !ventasUnicas.contains(venta)) {
                    ventasUnicas.add(venta);
                }
            }
        }

        return ventasUnicas.toArray(new Venta[0]);
    }
    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - contadorPasajes;
    }
}
