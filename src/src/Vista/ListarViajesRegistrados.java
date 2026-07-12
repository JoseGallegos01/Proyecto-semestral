package Vista;

import Controlador.SistemaVentaPasajes;
import Excepciones.SVPException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarViajesRegistrados extends JDialog {
    private JPanel Mish;
    private JLabel Titulo;
    private JTable TablaViajesRegistrados;
    private JButton Ok;


    public ListarViajesRegistrados() {
        setContentPane(Mish);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Ok.addActionListener(e -> dispose());

        cargarTabla();
    }

    public void cargarTabla() {
        try {
            SistemaVentaPasajes controlador = SistemaVentaPasajes.getInstance();
            String[][] viajes = controlador.listViajes();

            String[] columnas = {"FECHA", "HORA", "PRECIO", "DISPONIBLES", "PATENTE"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            for (String[] v : viajes) {
                modelo.addRow(v);
            }

            TablaViajesRegistrados.setModel(modelo);

        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
