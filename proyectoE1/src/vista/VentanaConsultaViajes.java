package vista;


import controlador.SistemaVentaPasajes;
import excepciones.SVPException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class VentanaConsultaViajes extends JDialog { //hecho por nicolas

    private JPanel panelPrincipal;
    private JTextField campoFecha;
    private JTextField campoComunaSalida;
    private JTextField campoComunaLlegada;
    private JSpinner spinnerPasajes;
    private JButton botonBuscar;
    private JButton botonLimpiar;
    private JButton botonCerrar;
    private JTable tablaViajes;
    private JLabel etiquetaCantidad;

    private final SistemaVentaPasajes sistema;
    private final DateTimeFormatter formatoFecha;

    public VentanaConsultaViajes(Window ventanaPadre) {
        super(ventanaPadre, "Viajes disponibles por fecha", ModalityType.MODELESS);

        sistema = SistemaVentaPasajes.getInstance();
        formatoFecha = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(ventanaPadre);

        spinnerPasajes.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        tablaViajes.setFillsViewportHeight(true);
        tablaViajes.setAutoCreateRowSorter(true);

        botonBuscar.addActionListener(e -> buscarViajes());
        botonLimpiar.addActionListener(e -> limpiarConsulta());
        botonCerrar.addActionListener(e -> volverAlMenu());

        limpiarTabla();
    }

    public VentanaConsultaViajes() {
        this(null);
    }

    private void buscarViajes() {
        String textoFecha = campoFecha.getText().trim();
        String comunaSalida = campoComunaSalida.getText().trim();
        String comunaLlegada = campoComunaLlegada.getText().trim();
        int cantidadPasajes = (Integer) spinnerPasajes.getValue();

        if (textoFecha.isEmpty() || comunaSalida.isEmpty() || comunaLlegada.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar la fecha, la comuna de salida y la comuna de llegada.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!comunaValida(comunaSalida) || !comunaValida(comunaLlegada)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Las comunas solo pueden contener letras, espacios, puntos, apostrofes o guiones.",
                    "Comuna no valida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (comunaSalida.equalsIgnoreCase(comunaLlegada)) {
            JOptionPane.showMessageDialog(
                    this,
                    "La comuna de salida y la comuna de llegada deben ser diferentes.",
                    "Ruta no valida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            LocalDate fecha = LocalDate.parse(textoFecha, formatoFecha);

            String[][] datos = sistema.getHorariosDisponibles(
                    fecha,
                    comunaSalida,
                    comunaLlegada,
                    cantidadPasajes
            );

            cargarTabla(datos);
            etiquetaCantidad.setText("Viajes encontrados: " + datos.length);

            if (datos.length == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "No existen viajes disponibles para los datos ingresados.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La fecha debe ser valida y tener el formato dd/MM/yyyy.",
                    "Fecha no valida",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (SVPException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error al consultar viajes",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cargarTabla(String[][] datos) {
        String[] columnas = {
                "Patente bus",
                "Hora de salida",
                "Precio",
                "Asientos disponibles"
        };

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaViajes.setModel(modelo);
    }

    private void limpiarConsulta() {
        campoFecha.setText("");
        campoComunaSalida.setText("");
        campoComunaLlegada.setText("");
        spinnerPasajes.setValue(1);
        limpiarTabla();
        campoFecha.requestFocusInWindow();
    }

    private void limpiarTabla() {
        cargarTabla(new String[0][4]);
        etiquetaCantidad.setText("Viajes encontrados: 0");
    }

    private boolean comunaValida(String comuna) {
        return comuna.matches("[\\p{L} .'-]+");
    }
    private void volverAlMenu() {
        dispose();
        Menu menu = new Menu();
        menu.setTitle("Sistema Venta Pasajes");
        menu.pack();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
