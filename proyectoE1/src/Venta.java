import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
    private String idDocumento;
    private TipoDocumento Tipo;
    private LocalDate Fecha;

    ArrayList<Pasaje> pasaje = new ArrayList<>();

    public Venta (String idDocumento, TipoDocumento Tipo, LocalDate Fecha, Cliente cliente)  {
      this.idDocumento = idDocumento;
      this.Tipo = Tipo;
      this.Fecha = Fecha;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public TipoDocumento getTipo() {
        return Tipo;
    }











}
