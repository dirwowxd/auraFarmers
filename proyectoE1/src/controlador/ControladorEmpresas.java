package controlador;

import Modelo.Conductor;
import Modelo.Empresa;
import Modelo.Tripulante;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.util.Optional;

public class ControladorEmpresas {


    public void hireConductorForEmpresa (Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {

        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);

        if (EmpresaBuscada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado.");
        }

        Empresa EmpresaContratada =  EmpresaBuscada.get();
        boolean ContratacionExitosa = empresa.addConductor(Id,nombre,direccion);

        if(!ContratacionExitosa){
            throw new SistemaVentaPasajesException("El conductor con el id dado por la empresa ya se encuentra contratado.");
        }

    }

    public void hireAuxiliarForEmpresa (Rut RutEmp, IdPersona Id, Nombre nombre, Direccion direccion) {

        Optional<Empresa> EmpresaBuscada = findEmpresa(RutEmp);

        if (EmpresaBuscada.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado.");
        }

        Empresa EmpresaContratada =  EmpresaBuscada.get();
        boolean ContratacionExitosa = empresa.addAuxiliar(Id,nombre,direccion);

        if(!ContratacionExitosa){
            throw new SistemaVentaPasajesException("El Auxiliar con el id dado por la empresa ya se encuentra contratado.");
        }

    }

    Optional <Conductor> findConductor(IdPersona Id, Rut RutEmp) {
        Optional<Empresa> EmpresaOpcion = findEmpresa(rutEmpresa);

        if (EmpresaOpcion.isEmpty()) {
            return Optional.empty();
        }

        Empresa EmpresaContratada =  EmpresaOpcion.get();
        Tripulante[] tripulantesEmpresa = EmpresaContratada.getTripulantes();

        for (Tripulante tripulante : tripulantesEmpresa) {

            if (tripulante != null) {
                if (tripulante instanceof Conductor) {
                    if (tripulante.getIdPersona().equals(IdConductor)) {
                        return Optional.of((Conductor) tripulante);
                    }
                }
            }
        }
            return Optional.empty();

    }

}
