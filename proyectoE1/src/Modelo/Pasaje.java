package Modelo;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Pasaje implements Serializable {
    private long numero;
    private int asiento;
    private Viaje viaje;
    private Venta venta;
    private Pasajero pasajero;

    public Pasaje(int asiento, Viaje viaje, Pasajero pasajero, Venta venta) {
        this.asiento = asiento;
        this.viaje = viaje;
        this.venta = venta;
        this.pasajero = pasajero;
        this.numero = System.currentTimeMillis();
        viaje.addPasaje(this);
    }


    public int getAsiento() {
        return asiento;
    }
    public int getNumero(){
        return (int) numero;
    }

    public Viaje getViaje (){
        return viaje;
    }

    public Pasajero getPasajero (){
        return pasajero;
    }

    public Venta getVenta ( ) {
        return venta;
    }

    @Override
    public String toString() {
        String nombreEmpresa = this.viaje.getBus().getEmpresas().getNombre().toUpperCase();
        String nombrePasajero = this.pasajero.getNombreCompleto().toString().toUpperCase();
        String rutPasaporte = this.pasajero.getIdPersona().toString();
        String patenteBus = this.viaje.getBus().getPatente();
        String terminalOrigen = this.viaje.getTerminalSalida().getNombre().toUpperCase();
        String terminalDestino = this.viaje.getTerminalLlegada().getNombre().toUpperCase();
        String fecha = this.viaje.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = this.viaje.getHora().toString();
        int valorPagado = this.viaje.getPrecio();

        return "-------------------- PASAJE ELECTRÓNICO --------------------\n" +
                String.format("%-24s%s%n", "Nombre Empresa", "Número de pasaje") +
                String.format("%-24s%s%n", nombreEmpresa, this.numero) +
                "\n" +
                String.format("%-44s%s%n", "Nombre Pasajero", "RUT/Pasaporte") +
                String.format("%-44s%s%n", nombrePasajero, rutPasaporte) +
                "\n" +
                String.format("%-16s%-16s%s%n", "Patente bus", "Asiento", "Valor Pagado") +
                String.format("%-16s%-16s%s%n", patenteBus, this.asiento, valorPagado) +
                "\n" +
                String.format("%-16s%-18s%-14s%s%n", "Terminal origen", "Terminal destino", "Fecha", "Hora") +
                String.format("%-16s%-18s%-14s%s%n", terminalOrigen, terminalDestino, fecha, hora) +
                "------------------------------------------------------------";
    }


}
