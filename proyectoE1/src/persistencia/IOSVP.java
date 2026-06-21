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

    public void savePasajesDeVenta(Pasaje[] pasajes, String nombreArchivo) throws SVPException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Pasaje pasaje : pasajes) {
                pw.println(pasaje.toString());
                pw.println();
            }
        } catch (IOException e) {
            throw new SVPException("No se puede abrir o crear el archivo " + nombreArchivo);
        }
    }
}