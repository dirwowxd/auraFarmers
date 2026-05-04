import Modelo.Pasajero;

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

    public String[][] getListaPasajeros() {
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
    public boolean ExisteDisponibilidad() {
        return ContadorPasajes < bus.getNroAsientos();
    }

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - ContadorPasajes;
    }

//lol comit


}
