import java.util.ArrayList;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();

    public boolean createCliente(idPersona idPersona, Nombre nombre, String fono, String email) {
        for (int i = 0; i < clientes.size(); i++) {
            if (idPersona.equals(clientes.get(i).getIdPersona())) {
                System.out.println("No se puede tener el mismo ID de otro cliente...");
                return false;
            }

        }
        Cliente nuevocliente = new Cliente(idPersona, nombre, email); // los parametros son 4 pero cliente solo tiene 3 parametros
        nuevocliente.setTelefono(fono); // aca seteo el telefono para que la sheet no me de eror
        clientes.add(nuevocliente);// lo añado a la lista
        return true;

    }
    public boolean createPasajero(idPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        for (int i = 0; i < pasajeros.size(); i++) {
            if (id.equals(pasajeros.get(i).getIdPersona())) {
                System.out.println("No se puede tener el mismo ID de otro cliente...");
                return false;
            }
        }
        Pasajero nuevoPasajero= new Pasajero(id, nom); // wea la wea webeada con las herencia ni habia caxaoooo
        nuevoPasajero.setTelefono(fono);
        nuevoPasajero.setFonoContacto(fonoContacto);
        nuevoPasajero.setNomContacto(nomContacto);
        pasajeros.add(nuevoPasajero);
        return true;
    }
}

