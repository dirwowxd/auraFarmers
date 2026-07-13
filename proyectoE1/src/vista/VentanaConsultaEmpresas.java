package vista;


import controlador.ControladorEmpresas;
import excepciones.SVPException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaConsultaEmpresas extends JDialog { //hecho por nicolas

    private JPanel panelPrincipal;
    private JTable tablaEmpresas;
    private JButton botonActualizar;
    private JButton botonCerrar;
    private JLabel etiquetaCantidad;

    private final ControladorEmpresas controlador;

    public VentanaConsultaEmpresas(Window ventanaPadre) {
        super(ventanaPadre, "Listado de empresas", ModalityType.MODELESS);

        controlador = ControladorEmpresas.getInstance();

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(850, 480));
        setLocationRelativeTo(ventanaPadre);

        tablaEmpresas.setFillsViewportHeight(true);
        tablaEmpresas.setAutoCreateRowSorter(true);

        botonActualizar.addActionListener(e -> cargarEmpresas());
        botonCerrar.addActionListener(e -> volverAlMenu());

        cargarEmpresas();
    }

    public VentanaConsultaEmpresas() {
        this(null);
    }

    private void cargarEmpresas() {
        try {
            String[][] datos = controlador.listEmpresas();

            String[] columnas = {
                    "RUT empresa",
                    "Nombre",
                    "URL",
                    "Nro. tripulantes",
                    "Nro. buses"
            };

            DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            tablaEmpresas.setModel(modelo);
            etiquetaCantidad.setText("Empresas encontradas: " + datos.length);

            if (datos.length == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "No existen empresas registradas en el sistema.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SVPException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error al consultar empresas",
                    JOptionPane.ERROR_MESSAGE
            );
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
}
