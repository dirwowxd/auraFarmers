package Modelo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;

public class Viaje {
    private final LocalDate Fecha;
    private final LocalTime Hora;
    private int Precio;
    private final Bus bus;
    private final Pasaje[] PasajesVendidos;
    private int ContadorPasajes;
    private final int duracion;
    private final Auxiliar auxiliar;
    private final Conductor[] conductores;
    private final Terminal terminalSalida;
    private final Terminal terminalLlegada;


    public Viaje(LocalDate Fecha, LocalTime Hora, int Precio, int duracion, Bus bus, Auxiliar auxiliar, Conductor[] conductores, Terminal terminalSalida, Terminal terminalLlegada) {
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Precio = Precio;
        this.bus = bus;
        this.duracion = duracion;
        this.auxiliar = auxiliar;
        this.conductores = conductores;
        this.terminalSalida = terminalSalida;
        this.terminalLlegada = terminalLlegada;
        this.PasajesVendidos = new Pasaje[bus.getNroAsientos()];
        this.ContadorPasajes = 0;
    }

    public int getDuracion() {
        return duracion;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public Conductor[] getConductores() {
        return conductores;
    }

    public Terminal getTerminalSalida() {
        return terminalSalida;
    }

    public Terminal getTerminalLlegada() {
        return terminalLlegada;
    }


    public LocalDate getFecha() {
        return Fecha;
    }

    public LocalTime getHora() {
        return Hora;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int Precio) {
        this.Precio = Precio;
    }

    public Bus getBus() {
        return bus;
    }

    public String[][] getAsientos() { //modificar ya que ahora es unidimensional
        int totalAsientos = bus.getNroAsientos();
        String[][] mapaAsientos = new String[totalAsientos][2];

        for (int i = 0; i < totalAsientos; i++) {
            mapaAsientos[i][0] = String.valueOf(i + 1); // Número de asiento
            mapaAsientos[i][1] = "Libre"; // Estado inicial por defecto
        }


        for (int i = 0; i < ContadorPasajes; i++) {
            if (PasajesVendidos[i] != null) {
                int nro = PasajesVendidos[i].getAsiento();
                mapaAsientos[nro - 1][1] = "Ocupado";
            }
        }
        return mapaAsientos;
    }


    public void addPasaje(Pasaje pasaje) {
        if (ContadorPasajes < PasajesVendidos.length) {
            this.PasajesVendidos[ContadorPasajes] = pasaje;
            ContadorPasajes++;
        } else {
            System.out.println("Error: Ya no quedan asientos disponibles");
        }
    }

    public String[][] getListaPasajeros() {//modificar
        String[][] lista = new String[this.ContadorPasajes][5];

        for (int i = 0; i < this.ContadorPasajes; i++) {
            Pasaje ps = this.PasajesVendidos[i];
            Pasajero pa = ps.getPasajero();

            lista[i][0] = String.valueOf(ps.getAsiento());
            lista[i][1] = pa.getIdPersona().toString();
            lista[i][2] = pa.getNombreCompleto().toString();

            if (pa.getNomContacto() != null) {
                lista[i][3] = pa.getNomContacto().toString();
            } else {
                lista[i][3] = "No registrado";
            }

            // [4] TELEFONO CONTACTO
            lista[i][4] = (pa.getFonoContacto() != null) ? pa.getFonoContacto() : "S/N";
        }
        return lista;
    }

    //verificar que el contador sea menor al limite del bus
    public boolean ExisteDisponibilidad() { //modificar
        return ContadorPasajes < bus.getNroAsientos();
    }

    public int getNroAsientosDisponibles() {// se debe borrar segun yo ya que no aparece en el uml
        return bus.getNroAsientos() - ContadorPasajes;
    }

    public LocalDateTime getFechaHoraTermino() {
        return null;//HACER
    }
    public Venta[] getVentas() {
        return null; //hacer
    }
    public Tripulante[] getTripulantes(){
        return null; //hacer
    }
    public void addConductor(Conductor conductor){
        //hacer
    }
    public boolean existeDisponibilidad(int nroAsientos){
        return false; //hacer
    }


}
