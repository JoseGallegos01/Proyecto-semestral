package Vista;

// Asegurate de importar los controladores y la clase de persistencia correctos
import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Modelo.*;
import Persistencia.PersistenciaClase;

import javax.swing.*;
import java.awt.event.*;

public class MenuDeOpciones extends JFrame {

    private JPanel contentPane;
    private JButton btnVentaPasajes;
    private JButton btnCrearViaje;
    private JButton btnListarEmpresas;
    private JButton btnListarVentas;
    private JButton btnListarViajes;
    private JButton btnCargarDatos;
    private JButton btnGuardarDatos;
    private JButton btnSalir;

    public MenuDeOpciones() {
        setTitle("SVP - MENU DE OPCIONES");
        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSalidaYGuardar();
            }
        });

        btnVentaPasajes.addActionListener(e -> abrirVentanaVentaPasajes());
        btnCrearViaje.addActionListener(e -> abrirVentanaCrearViaje());
        btnListarEmpresas.addActionListener(e -> abrirListarEmpresas());
        btnListarVentas.addActionListener(e -> abrirListarVentas());
        btnListarViajes.addActionListener(e -> abrirListarViajes());
        btnGuardarDatos.addActionListener(e -> guardarDatosSistema());
        btnCargarDatos.addActionListener(e -> cargarDatosSistema());
        btnSalir.addActionListener(e -> confirmarSalidaYGuardar());
    }

    private void abrirVentanaVentaPasajes() {
        try {
            VentanaVentadePasajes dialog = new VentanaVentadePasajes();
            dialog.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al abrir la ventana: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirVentanaCrearViaje() {
        try {
            crearViaje.display();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al abrir la ventana: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirListarEmpresas() {
        ListarEmpresas dialog = new ListarEmpresas();
        dialog.setVisible(true);
    }

    private void abrirListarVentas() {
        ListarVentasRealizadas dialog = new ListarVentasRealizadas();
        dialog.setVisible(true);
    }

    private void abrirListarViajes() {
        ListarViajesRegistrados dialog = new ListarViajesRegistrados();
        dialog.setVisible(true);
    }

    private void guardarDatosSistema() {
        try {
            SistemaVentaPasajes.getInstance().saveDatosSistema();
            JOptionPane.showMessageDialog(this, "Datos guardados exitosamente.", "Persistencia", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosSistema() {
        try {
            Object[] controladoresLeidos = PersistenciaClase.getInstance().readControladores();
            if (controladoresLeidos != null) {
                JOptionPane.showMessageDialog(this, "Datos recuperados exitosamente.", "Persistencia", JOptionPane.INFORMATION_MESSAGE);
                SistemaVentaPasajes.getInstance().readDatosSistema();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron recuperar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void confirmarSalidaYGuardar() {
        int respuesta = JOptionPane.showConfirmDialog(this, "Desea guardar los datos antes de salir?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                Object[] controladores = {SistemaVentaPasajes.getInstance(), ControladorEmpresas.getInstance()};
                PersistenciaClase.getInstance().saveControladores(controladores);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (respuesta == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    public static void mostrar() {
        SwingUtilities.invokeLater(() -> {
            MenuDeOpciones frame = new MenuDeOpciones();
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
        });
    }
}