import java.time.LocalTime;
import java.time.LocalDate;

public class Viaje {
    private LocalDate Fecha;
    private LocalTime Hora;
    private int Precio;
    private Bus bus;
    //creare un arreglo privado para los datos de los asientos vendidos
    private Pasaje[] PasajesVendidos;
    private int ContadorPasajes;


    public Viaje(LocalDate Fecha, LocalTime Hora, int Precio, Bus bus) {
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Precio = Precio;
        this.bus = bus;
        this.PasajesVendidos = new Pasaje[bus.getNroAsientos()];
        this.ContadorPasajes = 0;
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

    public String[][] getAsientos() {
        int TotalAsientos = bus.getNroAsientos();
        int Columnas = 4;
        int filas = (int) Math.ceil((double) TotalAsientos/ Columnas);

        String[][] MapaAsientos = new String[filas][Columnas];
        // generamos el mapa del bus para ver la planilla de asientos
        // y verificamos asientos libres con el for

        for (int f = 0 ; f < filas; f++) {
            for (int c = 0; c < Columnas; c++) {
                MapaAsientos[f][c] = "Asiento Libre";
            }
        }

        for (int i=0; i<ContadorPasajes; i++) {
            int NroAsiento = PasajesVendidos[i].getAsiento();
            int f = (NroAsiento - 1) / Columnas;
            int c = (NroAsiento - 1) % Columnas;
            if (f < filas) {
                MapaAsientos[f][c] = "Asiento Ocupado";
            }
            return MapaAsientos;//mod a futuro
        }

        return  MapaAsientos;
    }


    public void addPasaje(Pasaje pasaje) {
        if (ContadorPasajes < PasajesVendidos.length) {
            this.PasajesVendidos[ContadorPasajes] = pasaje;
            ContadorPasajes++;
        } else {
            System.out.println("Error: Ya no quedan asientos disponibles");
        }
    }

    public String[][] getListaPasajeros() {
        String[][] lista = new String[ContadorPasajes][2];

        for (int i = 0; i < ContadorPasajes; i++) {
            Pasajero pa = PasajesVendidos[i].getPasajero();

            lista[i][0] = pa.getNomContacto().toString();
            lista[i][1] = pa.getIdPersona().toString();
        }
        return lista;
    }

    //verificar que el contador sea menor al limite del bus
    public boolean ExisteDisponibilidad() {
        return ContadorPasajes < bus.getNroAsientos();
    }

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - ContadorPasajes;
    }

//lol comit




}
