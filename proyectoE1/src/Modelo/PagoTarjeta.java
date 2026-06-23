package Modelo;

import java.io.Serializable;

public class PagoTarjeta extends Pago implements Serializable {

    private long NroTarjeta;

    public PagoTarjeta(int Monto, long NroTarjeta){
        super(Monto);
        this.NroTarjeta = NroTarjeta;
    }

    public long getNroTarjeta(){
        return NroTarjeta;
    }

}
