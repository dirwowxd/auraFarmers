package vista;

import Modelo.Terminal;
import controlador.ControladorEmpresas;
import excepciones.SVPException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaConsultaTerminales extends JFrame { //hecho por Benajmin

    private final ControladorEmpresas controladorEmpresas = ControladorEmpresas.getInstance();

    private JTable tablaTerminales;
    private DefaultTableModel modeloTabla;
    private JLabel lblCantidad;

    public VentanaConsultaTerminales() {
        super("Listado de terminales");
        construirInterfaz();
        cargarTerminales();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                volverAlMenu();
            }
        });
        setSize(600, 420);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(8, 8));

        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Calle", "NÃºmero", "Comuna"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaTerminales = new JTable(modeloTabla);
        tablaTerminales.setFillsViewportHeight(true);
        tablaTerminales.setAutoCreateRowSorter(true);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        lblCantidad = new JLabel(" ");
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarTerminales());
        panelSuperior.add(lblCantidad, BorderLayout.WEST);
        panelSuperior.add(btnActualizar, BorderLayout.EAST);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> volverAlMenu());
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panelInferior.add(btnCerrar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaTerminales), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarTerminales() {
        try {
            Terminal[] terminales = controladorEmpresas.getTerminales();
            modeloTabla.setRowCount(0);

            for (Terminal t : terminales) {
                modeloTabla.addRow(new String[]{
                        t.getNombre(),
                        t.getDireccion().getCalle(),
                        String.valueOf(t.getDireccion().getNumero()),
                        t.getDireccion().getComuna()
                });
            }

            lblCantidad.setText("Terminales encontrados: " + terminales.length);

            if (terminales.length == 0) {
                JOptionPane.showMessageDialog(this,
                        "No existen terminales registrados en el sistema.",
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error al consultar terminales", JOptionPane.ERROR_MESSAGE);
        }
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
                controlador.SistemaVentaPasajes.getInstance().readDatosIniciales();
            } catch (SVPException e) {
                System.out.println("Aviso: no se pudieron cargar datos iniciales (" + e.getMessage() + ")");
            }
            new VentanaConsultaTerminales().setVisible(true);
        });
    }
}