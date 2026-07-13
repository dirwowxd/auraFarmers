
package vista;

import Modelo.Auxiliar;
import Modelo.Bus;
import Modelo.Conductor;
import Modelo.Empresa;
import Modelo.Terminal;
import Modelo.Tripulante;
import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import excepciones.SVPException;
import utilidades.IdPersona;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VentanaCrearViaje extends JFrame {

    private final SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
    private final ControladorEmpresas controladorEmpresas = ControladorEmpresas.getInstance();

    private JComboBox<Empresa> cbEmpresa;
    private JComboBox<Bus> cbBus;
    private JComboBox<Auxiliar> cbAuxiliar;
    private JComboBox<Conductor> cbConductor;
    private JComboBox<Terminal> cbTerminalSalida;
    private JComboBox<Terminal> cbTerminalLlegada;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtPrecio;
    private JTextField txtDuracion;
    private JButton btnCrear;
    private JButton btnCerrar;
    private JLabel lblEstado;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public VentanaCrearViaje() {
        super("Crear nuevo viaje");
        construirInterfaz();
        cargarEmpresas();
        cargarTerminales();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480, 460);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int fila = 0;

        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1;
        panel.add(new JLabel("Fecha (dd/MM/yyyy):"), gbc);
        txtFecha = new JTextField();
        gbc.gridx = 1; panel.add(txtFecha, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Hora (HH:mm):"), gbc);
        txtHora = new JTextField();
        gbc.gridx = 1; panel.add(txtHora, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Precio ($):"), gbc);
        txtPrecio = new JTextField();
        gbc.gridx = 1; panel.add(txtPrecio, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Duración (min):"), gbc);
        txtDuracion = new JTextField();
        gbc.gridx = 1; panel.add(txtDuracion, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Empresa:"), gbc);
        cbEmpresa = new JComboBox<>();
        cbEmpresa.setRenderer(crearRenderer(item -> {
            Empresa e = (Empresa) item;
            return e.getNombre() + "  (" + e.getRut() + ")";
        }));
        cbEmpresa.addActionListener(e -> onEmpresaSeleccionada());
        gbc.gridx = 1; panel.add(cbEmpresa, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Bus (patente):"), gbc);
        cbBus = new JComboBox<>();
        cbBus.setRenderer(crearRenderer(item -> {
            Bus b = (Bus) item;
            String marcaModelo = (b.getMarca() != null ? " - " + b.getMarca() : "")
                    + (b.getModelo() != null ? " " + b.getModelo() : "");
            return b.getPatente() + marcaModelo + " (" + b.getNroAsientos() + " asientos)";
        }));
        gbc.gridx = 1; panel.add(cbBus, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Auxiliar:"), gbc);
        cbAuxiliar = new JComboBox<>();
        cbAuxiliar.setRenderer(crearRenderer(item -> formatoTripulante((Tripulante) item)));
        gbc.gridx = 1; panel.add(cbAuxiliar, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Conductor:"), gbc);
        cbConductor = new JComboBox<>();
        cbConductor.setRenderer(crearRenderer(item -> formatoTripulante((Tripulante) item)));
        gbc.gridx = 1; panel.add(cbConductor, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Terminal salida:"), gbc);
        cbTerminalSalida = new JComboBox<>();
        cbTerminalSalida.setRenderer(crearRenderer(item -> formatoTerminal((Terminal) item)));
        gbc.gridx = 1; panel.add(cbTerminalSalida, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Terminal llegada:"), gbc);
        cbTerminalLlegada = new JComboBox<>();
        cbTerminalLlegada.setRenderer(crearRenderer(item -> formatoTerminal((Terminal) item)));
        gbc.gridx = 1; panel.add(cbTerminalLlegada, gbc);
        fila++;

        lblEstado = new JLabel(" ");
        lblEstado.setForeground(new Color(150, 0, 0, 0));
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(lblEstado, gbc);
        fila++;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCrear = new JButton("Crear viaje");
        btnCerrar = new JButton("Cerrar");
        btnCrear.addActionListener(e -> crearViaje());
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCrear);
        panelBotones.add(btnCerrar);

        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    private void cargarEmpresas() {
        cbEmpresa.removeAllItems();
        Empresa[] empresas = controladorEmpresas.getEmpresas();
        if (empresas == null || empresas.length == 0) {
            mostrarEstado("No hay empresas registradas. Cargue los datos iniciales antes de crear un viaje.");
            btnCrear.setEnabled(false);
            return;
        }
        for (Empresa emp : empresas) {
            cbEmpresa.addItem(emp);
        }
        onEmpresaSeleccionada();
    }

    private void cargarTerminales() {
        cbTerminalSalida.removeAllItems();
        cbTerminalLlegada.removeAllItems();
        Terminal[] terminales = controladorEmpresas.getTerminales();
        if (terminales == null || terminales.length == 0) {
            mostrarEstado("No hay terminales registrados. Cargue los datos iniciales antes de crear un viaje.");
            btnCrear.setEnabled(false);
            return;
        }
        for (Terminal t : terminales) {
            cbTerminalSalida.addItem(t);
            cbTerminalLlegada.addItem(t);
        }
    }

    private void onEmpresaSeleccionada() {
        cbBus.removeAllItems();
        cbAuxiliar.removeAllItems();
        cbConductor.removeAllItems();

        Empresa empresa = (Empresa) cbEmpresa.getSelectedItem();
        if (empresa == null) return;

        for (Bus bus : empresa.getBuses()) {
            cbBus.addItem(bus);
        }
        for (Tripulante t : empresa.getTripulantes()) {
            if (t instanceof Auxiliar) {
                cbAuxiliar.addItem((Auxiliar) t);
            } else if (t instanceof Conductor) {
                cbConductor.addItem((Conductor) t);
            }
        }

        if (cbBus.getItemCount() == 0 || cbAuxiliar.getItemCount() == 0 || cbConductor.getItemCount() == 0) {
            mostrarEstado("La empresa seleccionada no tiene bus, auxiliar y/o conductor suficientes para crear un viaje.");
        } else {
            mostrarEstado(" ");
        }
    }

    private void crearViaje() {
        mostrarEstado(" ");

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(txtFecha.getText().trim(), FORMATO_FECHA);
        } catch (DateTimeParseException ex) {
            error("La fecha debe tener el formato dd/MM/yyyy.");
            return;
        }

        LocalTime hora;
        try {
            hora = LocalTime.parse(txtHora.getText().trim(), FORMATO_HORA);
        } catch (DateTimeParseException ex) {
            error("La hora debe tener el formato HH:mm.");
            return;
        }

        int precio;
        try {
            precio = Integer.parseInt(txtPrecio.getText().trim());
            if (precio <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            error("El precio debe ser un número entero mayor a 0.");
            return;
        }

        int duracion;
        try {
            duracion = Integer.parseInt(txtDuracion.getText().trim());
            if (duracion <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            error("La duración debe ser un número entero mayor a 0 (minutos).");
            return;
        }

        Bus bus = (Bus) cbBus.getSelectedItem();
        Auxiliar auxiliar = (Auxiliar) cbAuxiliar.getSelectedItem();
        Conductor conductor = (Conductor) cbConductor.getSelectedItem();
        Terminal terminalSalida = (Terminal) cbTerminalSalida.getSelectedItem();
        Terminal terminalLlegada = (Terminal) cbTerminalLlegada.getSelectedItem();

        if (bus == null || auxiliar == null || conductor == null
                || terminalSalida == null || terminalLlegada == null) {
            error("Debe seleccionar empresa, bus, auxiliar, conductor y ambos terminales.");
            return;
        }

        if (terminalSalida.equals(terminalLlegada)) {
            error("El terminal de salida y el de llegada no pueden ser el mismo.");
            return;
        }

        String[] nomComunas = {
                terminalSalida.getDireccion().getComuna(),
                terminalLlegada.getDireccion().getComuna()
        };

        try {
            sistema.createViaje(fecha, hora, precio, duracion, bus.getPatente(),
                    new IdPersona[]{auxiliar.getIdPersona(), conductor.getIdPersona()},
                    nomComunas);

            JOptionPane.showMessageDialog(this, "Viaje creado exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (SVPException ex) {
            error(ex.getMessage());
        } catch (Exception ex) {
            error("Ocurrió un error inesperado: " + ex.getMessage());
        }
    }

    private String formatoTripulante(Tripulante t) {
        return t.getNombreCompleto().toString() + "  (" + t.getIdPersona() + ")";
    }

    private String formatoTerminal(Terminal t) {
        return t.getNombre() + "  -  " + t.getDireccion().getComuna();
    }


    private javax.swing.ListCellRenderer<Object> crearRenderer(java.util.function.Function<Object, String> formateador) {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                String texto = (value == null) ? "" : formateador.apply(value);
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        };
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error al crear el viaje", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarEstado(String msg) {
        lblEstado.setText(msg);
    }

    private void limpiarFormulario() {
        txtFecha.setText("");
        txtHora.setText("");
        txtPrecio.setText("");
        txtDuracion.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SistemaVentaPasajes.getInstance().readDatosIniciales();
            } catch (SVPException e) {
                System.out.println("Aviso: no se pudieron cargar datos iniciales (" + e.getMessage() + ")");
            }
            new VentanaCrearViaje().setVisible(true);
        });
    }
}
