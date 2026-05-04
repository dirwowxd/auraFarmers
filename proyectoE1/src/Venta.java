import Modelo.Cliente;
import Modelo.Pasajero;

import java.time.LocalDate;

public class Venta {
    private String idDocumento;
    private TipoDocumento Tipo;
    private LocalDate Fecha;
    private Cliente cliente;
    private Pasaje[] pasajes;
    private int CantidadPasajes;

    public Venta (String idDocumento, TipoDocumento Tipo, LocalDate Fecha, Cliente cliente)  {
      this.idDocumento = idDocumento;
      this.Tipo = Tipo;
      this.Fecha = Fecha;
      this.cliente = cliente;

      this.pasajes = new Pasaje[40];
      this.CantidadPasajes = 0;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public TipoDocumento getTipo() {
        return Tipo;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {
        if (this.CantidadPasajes < this.pasajes.length) {

            Pasaje nuevoPasaje = new Pasaje(asiento,viaje,pasajero, this);

            pasajes[CantidadPasajes] = nuevoPasaje;
            this.CantidadPasajes++;
        } else {
            System.out.println("//ERROR//");
            System.out.println("La cantidad de pasajes de la venta supera el limite del bus");
        }
    }

    public Pasaje[] getPasajes() {
        Pasaje[] pasajesVendidos = new Pasaje[CantidadPasajes];

        for (int i = 0; i < CantidadPasajes; i++) {
            pasajesVendidos[i] = pasajes[i];
        }
        return pasajesVendidos;
    }

    public int getMonto () {
        int montoTotal = 0;
        for (int i = 0; i < CantidadPasajes; i++) {
            montoTotal += pasajes[i].getViaje().getPrecio();
        }
        return montoTotal;
    }

}
