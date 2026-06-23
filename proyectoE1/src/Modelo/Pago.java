package Modelo;

import java.io.Serializable;

public class Pago implements Serializable {
    private int Monto;

    public Pago(int Monto){
        this.Monto = Monto;
    }

    public int getMonto(){
        return Monto;
    }

}
