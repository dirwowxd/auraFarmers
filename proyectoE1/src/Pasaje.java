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

    public int getNumero (){
        return  (int) numero;
    }

    public int getAsiento() {
        return asiento;
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


}
