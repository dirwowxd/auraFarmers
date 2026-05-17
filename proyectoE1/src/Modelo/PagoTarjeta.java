package Modelo;

public class PagoTarjeta extends Pago{

    private long NroTarjeta;

    public PagoTarjeta(int Monto, long NroTarjeta){
        super(Monto);
        this.NroTarjeta = NroTarjeta;
    }

    public long getNroTarjeta(){
        return NroTarjeta;
    }

}
