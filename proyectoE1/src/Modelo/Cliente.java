package Modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.ArrayList;

public class Cliente extends Persona {
    private String email;
    private ArrayList<Venta> ventas;

    public Cliente(IdPersona id, Nombre nom, String email, String telefono) {
        super(id, nom, telefono);
        this.email = email;
        this.ventas = new ArrayList<>();


    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void addVenta(Venta venta){
        this.ventas.add(venta);

    }
    public Venta[] getVentas(){
        return this.ventas.toArray(new Venta[0]);
    }
}

