package Modelo;

import java.time.LocalDate;

public class Venta {
        private String idDocumento;
        private TipoDocumento Tipo;
        private LocalDate Fecha;
        private Cliente cliente;
        private Pasaje[] pasajes;
        private int CantidadPasajes;
        private Pago pago;

    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {
        this.idDocumento = idDocumento;
        this.Tipo = tipo;
        this.Fecha = fecha;
        this.cliente = cliente;
        this.pago = null; // corregido por vicente
        this.pasajes = new Pasaje[38];
        this.CantidadPasajes = 0;
        this.cliente.addVenta(this);
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

        public Pago getPago() {
            return pago;
        }

        public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) { // //modificar (Revisar que la lógica use el nuevo límite de 5)
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

        public int getMontoPagado () {
            int montoTotal = 0;
            for (int i = 0; i < CantidadPasajes; i++) {
                montoTotal += pasajes[i].getViaje().getPrecio();
            }
            return montoTotal;
        }

    public boolean pagaMonto() {

        if (this.pago != null) {
            return false;
        }

        this.pago = new PagoEfectivo(this.getMontoPagado());
        return true;
    }

    public boolean pagaMonto(long nroTarjeta) {

        if (this.pago != null) {
            return false;
        }

        this.pago = new PagoTarjeta(this.getMontoPagado(), nroTarjeta);
        return true;

    }

    public String getTipoPago() {
        if (this.pago == null) {
            return null;
        }
        if (this.pago instanceof PagoEfectivo) {
            return "Pago Efectivo";
        } else if (this.pago instanceof PagoTarjeta) {
            return "Pago Tarjeta";
        }

        return null;
    }
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (otro == null || this.getClass() != otro.getClass()) {
            return false;
        }
        Venta otraVenta = (Venta) otro;
        return this.idDocumento.equals(otraVenta.idDocumento) &&
                this.Tipo.equals(otraVenta.Tipo);
    }
}


