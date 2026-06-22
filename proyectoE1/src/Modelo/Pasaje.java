package Modelo;

public class Pasaje {
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
        String nombreEmpresa = this.getViaje().getBus().getEmpresas().getNombre();
        String patenteBus = this.getViaje().getBus().getPatente();
        String terminalOrigen = this.getViaje().getTerminalSalida().getNombre();
        String terminalDestino = this.getViaje().getTerminalLlegada().getNombre();

        String fecha = this.getViaje().getFecha().toString();
        String hora = this.getViaje().getHora().toString();
        int valorPagado = this.getViaje().getPrecio();
        String nombrePasajero = this.getPasajero().getNomContacto().toString();
        String rutPasaporte = this.getPasajero().getIdPersona().toString();
        return "------------------- PASAJE ELECTRÓNICO -------------------\n" +
                "Nombre Empresa            Número de pasaje\n" +
                nombreEmpresa + "                 " + this.getNumero() + "\n\n" +
                "Nombre Pasajero                             RUT/Pasaporte\n" +
                nombrePasajero + "            " + rutPasaporte + "\n\n" +
                "Patente bus       Asiento         Valor Pagado\n" +
                patenteBus + "            " + this.getAsiento() + "              " + valorPagado + "\n\n" +
                "Terminal origen   Terminal destino    Fecha         Hora\n" +
                terminalOrigen + "    " + terminalDestino + "           " + fecha + "    " + hora + "\n" +
                "----------------------------------------------------------";
    }


}
