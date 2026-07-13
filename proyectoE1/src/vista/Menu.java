package vista;

import controlador.SistemaVentaPasajes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;

public class Menu extends JDialog {
    private JPanel contentPane;
    private JButton leerDatosButton;
    private JButton guardarDatosButton;
    private JButton recuperarDatosButton;
    private JButton ventaDePasajesButton;
    private JButton creacionDeViajeButton;
    private JButton ListarEmpresasButton;
    private JButton ListarTerminalesButton;
    private JButton ViajesDisponiblesporFechaButton;
    private JButton salirDelSistemaButton;
    private SistemaVentaPasajes svp = SistemaVentaPasajes.getInstance();


    public Menu() {
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        salirDelSistemaButton.addActionListener(e -> onCancel());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        aplicarEstilos();
        leerDatosButton.setPreferredSize(new Dimension(150, 40));
        guardarDatosButton.setPreferredSize(new Dimension(150, 40));
        recuperarDatosButton.setPreferredSize(new Dimension(150, 40));
        //datos

        //opciones
        ventaDePasajesButton.setPreferredSize(new Dimension(125, 30));
        creacionDeViajeButton.setPreferredSize(new Dimension(125, 30));
        ListarEmpresasButton.setPreferredSize(new Dimension(125, 30));
        ListarTerminalesButton.setPreferredSize(new Dimension(125, 30));
        ViajesDisponiblesporFechaButton.setPreferredSize(new Dimension(125, 30));

        //boton de salir entero grande pa presionarlo altiro
        salirDelSistemaButton.setPreferredSize(new Dimension(200, 60));

        leerDatosButton.addActionListener(e -> {
            try {
                svp.readDatosIniciales();
                JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        guardarDatosButton.addActionListener(e -> {
            try {
                svp.saveDatosSistema();
                JOptionPane.showMessageDialog(this, "Datos guardados exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        recuperarDatosButton.addActionListener(e -> {
            try {
                svp.readDatosSistemas();
                JOptionPane.showMessageDialog(this, "Datos leisods exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        creacionDeViajeButton.addActionListener(e -> {
            VentanaCrearViaje ventana = new VentanaCrearViaje();
            ventana.setVisible(true);
            dispose();
        });
        ListarEmpresasButton.addActionListener(e -> {
            VentanaConsultaEmpresas ventanaConsultaEmpresas = new VentanaConsultaEmpresas();
            ventanaConsultaEmpresas.setVisible(true);
            dispose();
        });
        ViajesDisponiblesporFechaButton.addActionListener(e -> {
            VentanaConsultaViajes ventana = new VentanaConsultaViajes(this);
            ventana.setVisible(true);
        });
        ventaDePasajesButton.addActionListener(e -> {
            VentaDePasajes ventaDePasajes = new VentaDePasajes();
            ventaDePasajes.setVisible(true);
        });
        ListarTerminalesButton.addActionListener(e -> {
            ListarTerminales listarTerminales = new ListarTerminales();
            listarTerminales.setVisible(true);
        });
    }

    private void aplicarEstilos() {
        Font fuenteBoton = new Font("Segoe UI", Font.PLAIN, 12);
        Color colorFondo = new Color(248, 248, 252);
        Color colorBoton = new Color(255, 255, 255);
        Color colorBorde = new Color(210, 210, 220);

        contentPane.setBackground(colorFondo);

        JButton[] botones = {
                leerDatosButton,
                guardarDatosButton,
                recuperarDatosButton,
                ventaDePasajesButton,
                creacionDeViajeButton,
                ListarEmpresasButton,
                ListarTerminalesButton,
                ViajesDisponiblesporFechaButton
        };

        for (JButton btn : botones) {
            btn.setFont(fuenteBoton);
            btn.setBackground(colorBoton);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(colorBorde, 1, true));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // Botón salir
        salirDelSistemaButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        salirDelSistemaButton.setBackground(new Color(254, 226, 226));
        salirDelSistemaButton.setForeground(new Color(185, 28, 28));
        salirDelSistemaButton.setFocusPainted(false);
        salirDelSistemaButton.setBorder(BorderFactory.createLineBorder(new Color(252, 165, 165), 1, true));
        salirDelSistemaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void main(String[] args) {
        Menu dialog = new Menu();
        dialog.setTitle("Sistema Venta Pasajes");
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }
}
