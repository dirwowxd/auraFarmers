package Persistencia;

import Modelo.*;
import excepciones.SVPException;
import utilidades.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IOSVP {

    private static IOSVP instancia;

    private IOSVP() {
    }

    public static IOSVP getInstancia() {
        if (instancia == null) {
            instancia = new IOSVP();

        }
        return instancia;
    }

    public Object[] readDatosIniciales() {
        List<Object> lista = new ArrayList<>();
        String rutaArchivo = "SVPDatosIniciales.txt";
        try (FileReader fr = new FileReader(rutaArchivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linea;
            int contador = 1;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                if (linea.equals("+")) {
                    contador++;
                    continue;
                }
                String[] datosIniciales = linea.split(";");

                switch (contador) {
                    case 1:
                        parsearClienteOPasajero(datosIniciales, lista);
                        break;
                    case 2:
                        parsearEmpresa(datosIniciales, lista);
                        break;
                    case 3:
                        parsearTripulante(datosIniciales, lista);
                        break;
                    case 4:
                        parsearTerminal(datosIniciales, lista);
                        break;
                    case 5:
                        parsearBus(datosIniciales, lista);
                        break;
                    case 6:
                        parsearViaje(datosIniciales, lista);
                        break;

                }
            }
        } catch (IOException e) {
            throw new SVPException("Error al leer el archivo " + e.getMessage());
        }
        return lista.toArray();
    }

        public void saveControladores (Object[]controlador){

        }
        public Object[] readControladores () {

            return new Object[0];
        }
        public void savePasajesDeVenta (Pasaje[]pasajes, String nombreArchivo){

        }


    private void parsearClienteOPasajero(String[] datos, List<Object> listaTemporal) throws SVPException {
        try {
            String tipoRegistro = datos[0];

            Rut rutPersona = Rut.of(datos[1]);

            Nombre nombrePersona = new Nombre();
            nombrePersona.setTratamiento(Tratamiento.valueOf(datos[2]));
            nombrePersona.setNombres(datos[3]);
            nombrePersona.setApellidoPaterno(datos[4]);
            nombrePersona.setApellidoMaterno(datos[5]);

            String telefono = datos[6];

            if (tipoRegistro.equals("C")) {
                // Si es solo Cliente, el dato 7 es el email
                String email = datos[7];
                Cliente nuevoCliente = new Cliente(rutPersona, nombrePersona, email);
                nuevoCliente.setTelefono(telefono);
                listaTemporal.add(nuevoCliente);

            } else if (tipoRegistro.equals("P")) {

                Pasajero nuevoPasajero = new Pasajero(rutPersona, nombrePersona);
                nuevoPasajero.setNombreCompleto(nombrePersona);
                nuevoPasajero.setTelefono(telefono);

                Nombre nomContacto = new Nombre();
                nomContacto.setTratamiento(Tratamiento.valueOf(datos[7]));
                nomContacto.setNombres(datos[8]);
                nomContacto.setApellidoPaterno(datos[9]);
                nomContacto.setApellidoMaterno(datos[10]);

                nuevoPasajero.setNomContacto(nomContacto);
                nuevoPasajero.setFonoContacto(datos[11]);

                listaTemporal.add(nuevoPasajero);

            } else if (tipoRegistro.equals("CP")) {
                String email = datos[7];

                Cliente clientePasajero = new Cliente(rutPersona, nombrePersona, email);
                clientePasajero.setTelefono(telefono);

                Nombre nomContacto = new Nombre();
                nomContacto.setTratamiento(Tratamiento.valueOf(datos[8]));
                nomContacto.setNombres(datos[9]);
                nomContacto.setApellidoPaterno(datos[10]);
                nomContacto.setApellidoMaterno(datos[11]);

                clientePasajero.setNombreCompleto(nomContacto);
                clientePasajero.setTelefono(datos[12]);

                listaTemporal.add(clientePasajero);
            }

        } catch (Exception e) {
            throw new SVPException("Error al crear un Cliente/Pasajero en la lectura inicial: " + e.getMessage());
        }
    }

        private void parsearEmpresa (String[]datos, List < Object > listaTemporal){
        try {
            Rut rut= Rut.of(datos[0]);
            String nombre = datos[1];
            String url= datos[2];

            Empresa nuevaEmpresa= new Empresa(rut, nombre);
            nuevaEmpresa.setUrl(url);

            listaTemporal.add(nuevaEmpresa);

        } catch (SVPException e) {
            throw new SVPException("Error al leer el archivo " + e.getMessage());
        }

        }

        private void parsearTripulante (String[]datos,List <Object> listaTemporal) throws  SVPException {
        try{
            String tipoRegistro = datos[0];
            Rut rutTripulante = Rut.of(datos[1]);
            Nombre nomTripulante =  new Nombre();
            nomTripulante.setTratamiento(Tratamiento.valueOf(datos[2]));
            nomTripulante.setNombres(datos[3]);
            nomTripulante.setApellidoPaterno(datos[4]);
            nomTripulante.setApellidoMaterno(datos[5]);
            String calle = datos[6];
            int numero= Integer.parseInt(datos[7]);
            String direccion = datos[8];
            Direccion dirTripulante = new Direccion(calle,numero,direccion);
            String RutEmpresa = datos[9];
            Empresa empresaEmpleadora= findEmpresaEnLista(RutEmpresa, listaTemporal);
            if (empresaEmpleadora == null) {
                throw new SVPException("No existe Empresa con el rut : " + RutEmpresa);
            }
            if (tipoRegistro.equals("C")) {

                empresaEmpleadora.addConductor(rutTripulante, nomTripulante, dirTripulante);
                Conductor nuevoConductor = new Conductor(rutTripulante, nomTripulante, dirTripulante);
                listaTemporal.add(nuevoConductor);
            } else if (tipoRegistro.equals("A")) {
                empresaEmpleadora.addAuxiliar(rutTripulante, nomTripulante, dirTripulante);
                Auxiliar nuevoAuxiliar = new Auxiliar(rutTripulante, nomTripulante, dirTripulante);
                listaTemporal.add(nuevoAuxiliar);
            }

        }catch (SVPException e){
            throw new SVPException("Error al leer el archivo " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new SVPException("Error al leer el numero " + e.getMessage());
        }

        }

    private void parsearTerminal(String[] datos, List<Object> listaTemporal) throws SVPException {
        try {
            String nombreTerminal = datos[0];
            String calle = datos[1];
            int numero = Integer.parseInt(datos[2]);
            String comuna = datos[3];
            Direccion dir = new Direccion(calle, numero, comuna);
            Terminal terminal = new Terminal(nombreTerminal, dir);
            listaTemporal.add(terminal);
        } catch (SVPException e) {
            throw new SVPException("Error al leer el archivo " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new SVPException("Error al leer el numero " + e.getMessage());
        }
    }

    private void parsearBus(String[] datos, List<Object> listaTemporal) throws SVPException {
        try {
            String patente = datos[0];
            String marca = datos[1];
            String modelo = datos[2];
            int asientos = Integer.parseInt(datos[3]);

            String rutEmpresaBuscado = datos[4];

            Empresa empresaDuena = findEmpresaEnLista(rutEmpresaBuscado, listaTemporal);
            if (empresaDuena == null) {
                throw new SVPException("Inconsistencia: No se encontró la empresa con RUT " + rutEmpresaBuscado + " para asignar al bus patente " + patente);
            }

            Bus nuevoBus = new Bus(patente, asientos, empresaDuena.getRut());
            nuevoBus.setMarca(marca);
            nuevoBus.setModelo(modelo);
            empresaDuena.addBuses(nuevoBus);

            listaTemporal.add(nuevoBus);

        } catch (NumberFormatException e) {
            throw new SVPException("Error de formato: La cantidad de asientos del bus debe ser numérica.");
        } catch (Exception e) {
            throw new SVPException("Error al crear un Bus en la lectura inicial: " + e.getMessage());
        }
    }

    private void parsearViaje(String[] datos, List<Object> listaTemporal) throws SVPException {
        try {DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate fecha = LocalDate.parse(datos[0], formatoFecha);
            LocalTime hora = LocalTime.parse(datos[1], formatoHora);
            int precio = Integer.parseInt(datos[2]);
            int duracion = Integer.parseInt(datos[3]);
            String patenteBus = datos[4];
            String rutAuxiliar = datos[5];
            String rutConductor = datos[6];
            String nombreTerminalSalida = datos[7];
            String nombreTerminalLlegada = datos[8];
            Bus busAsignado = findBusEnLista(patenteBus, listaTemporal);
            Tripulante auxiliar = findTripulanteEnLista(rutAuxiliar, listaTemporal);
            Tripulante conductor = findTripulanteEnLista(rutConductor, listaTemporal);
            Terminal origen = findTerminalEnLista(nombreTerminalSalida, listaTemporal);
            Terminal destino = findTerminalEnLista(nombreTerminalLlegada, listaTemporal);

            if (busAsignado == null || auxiliar == null || conductor == null || origen == null || destino == null) {
                throw new SVPException("Inconsistencia: Faltan datos (Bus, Tripulante o Terminal) para armar este viaje");
            }
            Conductor[] conductores = new Conductor[1];
            conductores[0] = (Conductor) conductor;

            Viaje nuevoViaje = new Viaje(fecha, hora, precio, duracion, busAsignado, (Auxiliar)auxiliar, conductores, origen, destino);
            busAsignado.addViaje(nuevoViaje);
            listaTemporal.add(nuevoViaje);

        } catch (NumberFormatException e) {
            throw new SVPException("Error de formato numérico en viaje.");
        } catch (Exception e) {
            throw new SVPException("Error al crear un Viaje: " + e.getMessage());
        }
    }
        private Optional<Empresa> findEmpresa (List<Empresa> empresas, Rut rut){
        return empresas.stream()
                .filter(obj-> obj instanceof Empresa)
                .map(obj -> (Empresa) obj)
                .filter(empresa -> empresa.getRut().equals(rut))
                .findFirst();
        }
    private Empresa findEmpresaEnLista(String rut, List<Object> listaTemporal) {
        return listaTemporal.stream()
                .filter(obj -> obj instanceof Empresa)
                .map(obj -> (Empresa) obj)
                .filter(emp -> emp.getRut().equals(Rut.of(rut)))
                .findFirst()
                .orElse(null);
    }
    private Bus findBusEnLista(String patente, List<Object> listaTemporal) {
        return listaTemporal.stream()
                .filter(obj -> obj instanceof Bus)
                .map(obj -> (Bus) obj)
                .filter(bus -> bus.getPatente().equals(patente))
                .findFirst()
                .orElse(null);
    }
    private Tripulante findTripulanteEnLista(String id, List<Object> listaTemporal) {
        Rut rutBuscado = Rut.of(id);
        return listaTemporal.stream()
                .filter(obj -> obj instanceof Tripulante)
                .map(obj -> (Tripulante) obj)
                .filter(tripulante -> tripulante.getIdPersona().equals(rutBuscado))
                .findFirst()
                .orElse(null);
    }

    private Terminal findTerminalEnLista(String nombre, List<Object> listaTemporal) {
        return listaTemporal.stream()
                .filter(obj -> obj instanceof Terminal)
                .map(obj -> (Terminal) obj)
                .filter(terminal -> terminal.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }


    }

