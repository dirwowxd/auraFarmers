package persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import excepciones.SistemaVentaPasajesException;

public class IOSVP {

    public Object[] readDatosIniciales() {
        return null;
    }

    public void saveControladores(Object[] controladores) {

    }

    public Object[] readControladores() {
        return null;
    }

    public void savePasajesDeVenta(Object[] pasajes, String nombreArchivo)
            throws SistemaVentaPasajesException {

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {

            for (Object pasaje : pasajes) {
                pw.println(pasaje.toString());
                pw.println();
            }

        } catch (IOException e) {
            throw new SistemaVentaPasajesException(
                    "No se puede crear el archivo " + nombreArchivo);
        }
    }
}