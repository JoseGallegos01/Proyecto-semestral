package Vista;

import Controlador.SistemaVentaPasajes;
import Excepciones.SVPException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarVentasRealizadas extends JDialog {
    private JPanel Mish;
    private JTable TablaVentasRealizadas;
    private JLabel Titulo;
    private JButton Ok;

    public ListarVentasRealizadas() {
        setContentPane(Mish);
        setModal(true);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Ok.addActionListener(e -> dispose());

        cargarTabla();
    }

    public void cargarTabla() {
        try {
            SistemaVentaPasajes controlador = SistemaVentaPasajes.getInstance();
            String[][] ventas = controlador.listVentas();

            String[] columnas = {"ID DOCUMENTO", "TIPO DOCU", "FECHA", "RUT/PASAPORTE", "CLIENTE", "CANT BOLETOS", "TOTAL VENTA"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            for (String[] v : ventas) {
                modelo.addRow(v);
            }

            TablaVentasRealizadas.setModel(modelo);

        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}