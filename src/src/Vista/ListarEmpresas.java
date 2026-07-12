package Vista;

import Controlador.ControladorEmpresas;
import Excepciones.SVPException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarEmpresas extends JDialog {

    private JPanel Mish;
    private JLabel Titulo;
    private JTable TablaEmpresas;
    private JButton Ok;


    public ListarEmpresas() {
        setContentPane(Mish);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Ok.addActionListener(e -> dispose());

        cargarTabla();
    }

    public void cargarTabla() {
        try {
            ControladorEmpresas controlador = ControladorEmpresas.getInstance();
            String[][] empresas = controlador.listEmpresas();

            String[] columnas = {"RUT EMPRESA", "NOMBRE", "URL", "NRO. TRIPULANTES", "NRO. BUSES", "NRO. VENTAS"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            for (String[] emp : empresas) {
                modelo.addRow(emp);
            }

            TablaEmpresas.setModel(modelo);

        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}