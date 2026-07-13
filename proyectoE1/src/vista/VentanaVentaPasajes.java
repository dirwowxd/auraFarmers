package vista;

import Modelo.Terminal;
import Modelo.TipoDocumento;
import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import excepciones.SVPException;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Pasaporte;
import utilidades.Rut;
import utilidades.Tratamiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashSet;
import java.util.Set;

public class VentanaVentaPasajes extends JFrame {

    private final SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
    private final ControladorEmpresas controladorEmpresas = ControladorEmpresas.getInstance();

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --- Estado de la venta en curso ---
    private String idDocumentoVenta;
    private TipoDocumento tipoDocumentoVenta;
    private LocalDate fechaVenta;
    private LocalTime horaViaje;
    private String patenteViaje;
    private boolean ventaPagada;

    // --- Sección A: iniciar venta ---
    private JTextField txtIdDocVenta;
    private JComboBox<TipoDocumento> cbTipoDocVenta;
    private JTextField txtFechaVenta;
    private JComboBox<String> cbTipoIdCliente;
    private JTextField txtIdCliente;
    private JTextField txtNacionalidadCliente;
    private JComboBox<String> cbComunaSalida;
    private JComboBox<String> cbComunaLlegada;
    private JButton btnIniciarVenta;
    private JLabel lblEstadoVenta;

    // --- Sección B: selección de viaje ---
    private JTable tablaHorarios;
    private DefaultTableModel modeloHorarios;
    private JButton btnSeleccionarViaje;
    private JLabel lblViajeSeleccionado;

    // --- Sección C: venta de pasajes ---
    private JComboBox<String> cbTipoIdPasajero;
    private JTextField txtIdPasajero;
    private JTextField txtNacionalidadPasajero;
    private JComboBox<String> cbAsiento;
    private JButton btnVenderPasaje;
    private JButton btnCrearPasajero;

    // --- Sección D: pago ---
    private JComboBox<String> cbMetodoPago;
    private JTextField txtNroTarjeta;
    private JButton btnPagar;
    private JLabel lblMonto;

    // --- Sección E: generar pasajes ---
    private JButton btnGenerarPasajes;

    public VentanaVentaPasajes() {
        super("Venta de pasajes");
        construirInterfaz();
        cargarComunas();
        actualizarEstadoBotones();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                volverAlMenu();
            }
        });
        setSize(700, 800);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contenedor.add(construirPanelIniciarVenta());
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(construirPanelSeleccionViaje());
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(construirPanelVentaPasajes());
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(construirPanelPago());
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(construirPanelGenerarPasajes());

        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> volverAlMenu());
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(btnCerrar);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel construirPanelIniciarVenta() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("1. Iniciar venta"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        int fila = 0;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("N° documento (boleta/factura):"), gbc);
        txtIdDocVenta = new JTextField();
        gbc.gridx = 1; panel.add(txtIdDocVenta, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Tipo documento:"), gbc);
        cbTipoDocVenta = new JComboBox<>(TipoDocumento.values());
        gbc.gridx = 1; panel.add(cbTipoDocVenta, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Fecha del viaje (dd/MM/yyyy):"), gbc);
        txtFechaVenta = new JTextField();
        gbc.gridx = 1; panel.add(txtFechaVenta, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Tipo identificación cliente:"), gbc);
        cbTipoIdCliente = new JComboBox<>(new String[]{"RUT", "PASAPORTE"});
        cbTipoIdCliente.addActionListener(e -> actualizarCamposIdentificacion(cbTipoIdCliente, txtNacionalidadCliente));
        gbc.gridx = 1; panel.add(cbTipoIdCliente, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("N° identificación cliente:"), gbc);
        txtIdCliente = new JTextField();
        gbc.gridx = 1; panel.add(txtIdCliente, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Nacionalidad (si es pasaporte):"), gbc);
        txtNacionalidadCliente = new JTextField();
        txtNacionalidadCliente.setEnabled(false);
        gbc.gridx = 1; panel.add(txtNacionalidadCliente, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Comuna salida:"), gbc);
        cbComunaSalida = new JComboBox<>();
        gbc.gridx = 1; panel.add(cbComunaSalida, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Comuna llegada:"), gbc);
        cbComunaLlegada = new JComboBox<>();
        gbc.gridx = 1; panel.add(cbComunaLlegada, gbc);
        fila++;

        btnIniciarVenta = new JButton("Iniciar venta");
        btnIniciarVenta.addActionListener(e -> iniciarVenta());
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(btnIniciarVenta, gbc);
        fila++;

        lblEstadoVenta = new JLabel(" ");
        lblEstadoVenta.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(lblEstadoVenta, gbc);

        return panel;
    }

    private JPanel construirPanelSeleccionViaje() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("2. Seleccionar viaje"));

        modeloHorarios = new DefaultTableModel(new String[]{"Patente", "Hora", "Precio", "Asientos disponibles"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) { return false; }
        };
        tablaHorarios = new JTable(modeloHorarios);
        tablaHorarios.setAutoCreateRowSorter(true);
        panel.add(new JScrollPane(tablaHorarios), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        btnSeleccionarViaje = new JButton("Seleccionar viaje");
        btnSeleccionarViaje.addActionListener(e -> seleccionarViaje());
        lblViajeSeleccionado = new JLabel("Ningún viaje seleccionado.");
        panelSur.add(btnSeleccionarViaje, BorderLayout.WEST);
        panelSur.add(lblViajeSeleccionado, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);

        panel.setPreferredSize(new Dimension(650, 180));
        return panel;
    }

    private JPanel construirPanelVentaPasajes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("3. Vender pasajes"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        int fila = 0;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Tipo identificación pasajero:"), gbc);
        cbTipoIdPasajero = new JComboBox<>(new String[]{"RUT", "PASAPORTE"});
        cbTipoIdPasajero.addActionListener(e -> actualizarCamposIdentificacion(cbTipoIdPasajero, txtNacionalidadPasajero));
        gbc.gridx = 1; panel.add(cbTipoIdPasajero, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("N° identificación pasajero:"), gbc);
        txtIdPasajero = new JTextField();
        gbc.gridx = 1; panel.add(txtIdPasajero, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Nacionalidad (si es pasaporte):"), gbc);
        txtNacionalidadPasajero = new JTextField();
        txtNacionalidadPasajero.setEnabled(false);
        gbc.gridx = 1; panel.add(txtNacionalidadPasajero, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Asiento:"), gbc);
        cbAsiento = new JComboBox<>();
        gbc.gridx = 1; panel.add(cbAsiento, gbc);
        fila++;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnCrearPasajero = new JButton("Crear pasajero nuevo");
        btnCrearPasajero.addActionListener(e -> crearPasajero());
        btnVenderPasaje = new JButton("Vender pasaje");
        btnVenderPasaje.addActionListener(e -> venderPasaje());
        panelBotones.add(btnCrearPasajero);
        panelBotones.add(btnVenderPasaje);
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        return panel;
    }

    private JPanel construirPanelPago() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("4. Pagar venta"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        int fila = 0;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Método de pago:"), gbc);
        cbMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta"});
        cbMetodoPago.addActionListener(e -> txtNroTarjeta.setEnabled("Tarjeta".equals(cbMetodoPago.getSelectedItem())));
        gbc.gridx = 1; panel.add(cbMetodoPago, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("N° tarjeta:"), gbc);
        txtNroTarjeta = new JTextField();
        txtNroTarjeta.setEnabled(false);
        gbc.gridx = 1; panel.add(txtNroTarjeta, gbc);
        fila++;

        btnPagar = new JButton("Pagar venta");
        btnPagar.addActionListener(e -> pagarVenta());
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(btnPagar, gbc);
        fila++;

        lblMonto = new JLabel(" ");
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(lblMonto, gbc);

        return panel;
    }

    private JPanel construirPanelGenerarPasajes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("5. Generar pasajes electrónicos"));
        btnGenerarPasajes = new JButton("Generar pasajes electrónicos");
        btnGenerarPasajes.addActionListener(e -> generarPasajes());
        panel.add(btnGenerarPasajes);
        return panel;
    }

    private void actualizarCamposIdentificacion(JComboBox<String> cbTipo, JTextField txtNacionalidad) {
        txtNacionalidad.setEnabled("PASAPORTE".equals(cbTipo.getSelectedItem()));
    }

    private void cargarComunas() {
        Terminal[] terminales = controladorEmpresas.getTerminales();
        Set<String> comunas = new LinkedHashSet<>();
        for (Terminal t : terminales) {
            comunas.add(t.getDireccion().getComuna());
        }
        cbComunaSalida.removeAllItems();
        cbComunaLlegada.removeAllItems();
        for (String comuna : comunas) {
            cbComunaSalida.addItem(comuna);
            cbComunaLlegada.addItem(comuna);
        }
        if (comunas.isEmpty()) {
            lblEstadoVenta.setForeground(Color.RED);
            lblEstadoVenta.setText("No hay terminales cargados. Cargue los datos iniciales antes de vender.");
        }
    }

    private IdPersona construirIdPersona(String tipo, String valor, String nacionalidad, String etiqueta) {
        if (valor == null || valor.trim().isEmpty()) {
            error("Debe ingresar el número de identificación " + etiqueta + ".");
            return null;
        }
        if ("RUT".equals(tipo)) {
            Rut rut = Rut.of(valor.trim());
            if (rut == null) {
                error("El RUT " + etiqueta + " no tiene un formato válido (ej: 12345678-9).");
                return null;
            }
            return rut;
        } else {
            if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
                error("Debe ingresar la nacionalidad del pasaporte " + etiqueta + ".");
                return null;
            }
            Pasaporte pasaporte = Pasaporte.of(valor.trim(), nacionalidad.trim());
            if (pasaporte == null) {
                error("El número de pasaporte " + etiqueta + " no es válido.");
                return null;
            }
            return pasaporte;
        }
    }

    private void iniciarVenta() {
        String idDoc = txtIdDocVenta.getText().trim();
        if (idDoc.isEmpty()) {
            error("Debe ingresar el número de documento de la venta.");
            return;
        }
        TipoDocumento tipo = (TipoDocumento) cbTipoDocVenta.getSelectedItem();

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(txtFechaVenta.getText().trim(), FORMATO_FECHA);
        } catch (DateTimeParseException ex) {
            error("La fecha debe tener el formato dd/MM/yyyy.");
            return;
        }

        IdPersona idCliente = construirIdPersona(
                (String) cbTipoIdCliente.getSelectedItem(),
                txtIdCliente.getText(),
                txtNacionalidadCliente.getText(),
                "del cliente");
        if (idCliente == null) return;

        String comunaSalida = (String) cbComunaSalida.getSelectedItem();
        String comunaLlegada = (String) cbComunaLlegada.getSelectedItem();
        if (comunaSalida == null || comunaLlegada == null) {
            error("Debe seleccionar comuna de salida y de llegada.");
            return;
        }
        if (comunaSalida.equals(comunaLlegada)) {
            error("La comuna de salida y la de llegada no pueden ser la misma.");
            return;
        }

        try {
            sistema.iniciaVenta(idDoc, tipo, fecha, idCliente, comunaSalida, comunaLlegada);

            this.idDocumentoVenta = idDoc;
            this.tipoDocumentoVenta = tipo;
            this.fechaVenta = fecha;
            this.horaViaje = null;
            this.patenteViaje = null;
            this.ventaPagada = false;

            cargarHorarios(fecha, comunaSalida, comunaLlegada);
            lblEstadoVenta.setForeground(new Color(0, 100, 0));
            lblEstadoVenta.setText("Venta iniciada correctamente. Seleccione un viaje.");
            actualizarEstadoBotones();

        } catch (SVPException ex) {
            error(ex.getMessage());
        } catch (Exception ex) {
            error("Ocurrió un error inesperado: " + ex.getMessage());
        }
    }

    private void cargarHorarios(LocalDate fecha, String comunaSalida, String comunaLlegada) {
        modeloHorarios.setRowCount(0);
        try {
            String[][] horarios = sistema.getHorariosDisponibles(fecha, comunaSalida, comunaLlegada, 1);
            for (String[] fila : horarios) {
                modeloHorarios.addRow(fila);
            }
            if (horarios.length == 0) {
                JOptionPane.showMessageDialog(this,
                        "No hay viajes disponibles para la fecha indicada.",
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SVPException ex) {
            error(ex.getMessage());
        }
    }

    private void seleccionarViaje() {
        int fila = tablaHorarios.getSelectedRow();
        if (fila == -1) {
            error("Debe seleccionar un viaje de la tabla.");
            return;
        }
        int filaModelo = tablaHorarios.convertRowIndexToModel(fila);
        String patente = (String) modeloHorarios.getValueAt(filaModelo, 0);
        String horaTexto = (String) modeloHorarios.getValueAt(filaModelo, 1);
        String precioTexto = (String) modeloHorarios.getValueAt(filaModelo, 2);

        try {
            this.horaViaje = LocalTime.parse(horaTexto);
            this.patenteViaje = patente;
        } catch (Exception ex) {
            error("No fue posible interpretar los datos del viaje seleccionado.");
            return;
        }

        lblViajeSeleccionado.setText("Viaje seleccionado: bus " + patente + " a las " + horaTexto + " ($" + precioTexto + ")");
        cargarAsientos();
        actualizarEstadoBotones();
    }

    private void cargarAsientos() {
        cbAsiento.removeAllItems();
        if (fechaVenta == null || horaViaje == null || patenteViaje == null) return;
        try {
            String[][] asientos = sistema.listAsientosDeViaje(fechaVenta, horaViaje, patenteViaje);
            for (String[] asiento : asientos) {
                if ("Libre".equals(asiento[1])) {
                    cbAsiento.addItem(asiento[0]);
                }
            }
        } catch (SVPException ex) {
            error(ex.getMessage());
        }
    }

    private void crearPasajero() {
        JTextField txtNombres = new JTextField();
        JTextField txtApPaterno = new JTextField();
        JTextField txtApMaterno = new JTextField();
        JTextField txtFono = new JTextField();
        JTextField txtNomContacto = new JTextField();
        JTextField txtFonoContacto = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Nombres:")); panel.add(txtNombres);
        panel.add(new JLabel("Apellido paterno:")); panel.add(txtApPaterno);
        panel.add(new JLabel("Apellido materno:")); panel.add(txtApMaterno);
        panel.add(new JLabel("Teléfono:")); panel.add(txtFono);
        panel.add(new JLabel("Nombre contacto de emergencia:")); panel.add(txtNomContacto);
        panel.add(new JLabel("Teléfono contacto de emergencia:")); panel.add(txtFonoContacto);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Crear pasajero nuevo (usa la identificación ingresada en la sección 3)",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) return;

        IdPersona idPasajero = construirIdPersona(
                (String) cbTipoIdPasajero.getSelectedItem(),
                txtIdPasajero.getText(),
                txtNacionalidadPasajero.getText(),
                "del pasajero");
        if (idPasajero == null) return;

        if (txtNombres.getText().trim().isEmpty() || txtApPaterno.getText().trim().isEmpty()
                || txtApMaterno.getText().trim().isEmpty()) {
            error("Debe completar nombres y apellidos del pasajero.");
            return;
        }

        Nombre nombre = new Nombre(Tratamiento.SR, txtNombres.getText().trim(),
                txtApPaterno.getText().trim(), txtApMaterno.getText().trim());
        Nombre nomContacto = txtNomContacto.getText().trim().isEmpty() ? null :
                new Nombre(Tratamiento.SR, txtNomContacto.getText().trim(), "", "");

        try {
            sistema.createPasajero(idPasajero, nombre, txtFono.getText().trim(),
                    nomContacto, txtFonoContacto.getText().trim());
            JOptionPane.showMessageDialog(this, "Pasajero creado exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SVPException ex) {
            error(ex.getMessage());
        }
    }

    private void venderPasaje() {
        if (patenteViaje == null || horaViaje == null) {
            error("Debe seleccionar un viaje antes de vender pasajes.");
            return;
        }

        IdPersona idPasajero = construirIdPersona(
                (String) cbTipoIdPasajero.getSelectedItem(),
                txtIdPasajero.getText(),
                txtNacionalidadPasajero.getText(),
                "del pasajero");
        if (idPasajero == null) return;

        String asientoSeleccionado = (String) cbAsiento.getSelectedItem();
        if (asientoSeleccionado == null) {
            error("No hay asientos disponibles o no se ha seleccionado uno.");
            return;
        }
        int nroAsiento;
        try {
            nroAsiento = Integer.parseInt(asientoSeleccionado);
        } catch (NumberFormatException ex) {
            error("El asiento seleccionado no es válido.");
            return;
        }

        try {
            sistema.vendePasaje(idDocumentoVenta, tipoDocumentoVenta, fechaVenta, horaViaje,
                    patenteViaje, idPasajero, nroAsiento);
            JOptionPane.showMessageDialog(this, "Pasaje vendido exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarAsientos();
        } catch (SVPException ex) {
            error(ex.getMessage());
        }
    }

    private void pagarVenta() {
        if (idDocumentoVenta == null) {
            error("Debe iniciar una venta antes de pagarla.");
            return;
        }

        try {
            if ("Tarjeta".equals(cbMetodoPago.getSelectedItem())) {
                long nroTarjeta;
                try {
                    nroTarjeta = Long.parseLong(txtNroTarjeta.getText().trim());
                } catch (NumberFormatException ex) {
                    error("El número de tarjeta debe ser numérico.");
                    return;
                }
                sistema.pagaVenta(idDocumentoVenta, tipoDocumentoVenta, nroTarjeta);
            } else {
                sistema.pagaVenta(idDocumentoVenta, tipoDocumentoVenta);
            }

            ventaPagada = true;
            int monto = sistema.getMontoVenta(idDocumentoVenta, tipoDocumentoVenta);
            lblMonto.setText("Monto pagado: $" + monto);
            JOptionPane.showMessageDialog(this, "Venta pagada exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarEstadoBotones();
        } catch (SVPException ex) {
            error(ex.getMessage());
        }
    }

    private void generarPasajes() {
        if (!ventaPagada) {
            error("Debe pagar la venta antes de generar los pasajes electrónicos.");
            return;
        }
        try {
            sistema.generatePasajesVenta(idDocumentoVenta, tipoDocumentoVenta);
            JOptionPane.showMessageDialog(this,
                    "Pasajes electrónicos generados exitosamente en archivo de texto.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SVPException ex) {
            error(ex.getMessage());
        }
    }

    private void actualizarEstadoBotones() {
        boolean ventaIniciada = idDocumentoVenta != null;
        boolean viajeSeleccionado = patenteViaje != null && horaViaje != null;

        btnSeleccionarViaje.setEnabled(ventaIniciada);
        btnVenderPasaje.setEnabled(ventaIniciada && viajeSeleccionado);
        btnPagar.setEnabled(ventaIniciada && !ventaPagada);
        btnGenerarPasajes.setEnabled(ventaPagada);
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void volverAlMenu() {
        dispose();
        Menu menu = new Menu();
        menu.setTitle("Sistema Venta Pasajes");
        menu.pack();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SistemaVentaPasajes.getInstance().readDatosIniciales();
            } catch (SVPException e) {
                System.out.println("Aviso: no se pudieron cargar datos iniciales (" + e.getMessage() + ")");
            }
            new VentanaVentaPasajes().setVisible(true);
        });
    }
}