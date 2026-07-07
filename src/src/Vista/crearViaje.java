package Vista;

import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Modelo.Auxiliar;
import Modelo.Bus;
import Modelo.Conductor;
import Modelo.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class crearViaje extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fechaViajeTexto;
    private JComboBox horaComboBox;
    private JComboBox minutoComboBox;
    private JTextField textField1;
    private JComboBox patenteBusComboBox;
    private JComboBox primerConductor;
    private JComboBox segundoConductor;
    private JComboBox listaEmpresas;
    private JComboBox listaAuxiliares;

    public crearViaje() throws FileNotFoundException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        SistemaVentaPasajes.getInstance().readDatosIniciales();
        System.out.println("e");
        cargarEmpresas();
        System.out.println("e");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void cargarEmpresas(){
        System.out.println("r");
        Object[][] empresas = ControladorEmpresas.getInstance().listEmpresasGUI();
        System.out.println("t");
        for (int i = 0; i<empresas.length;i++){
            System.out.println("empresas: "+empresas[i][0]);
            System.out.println(empresas[i][1].toString());
            listaEmpresas.addItem(empresas[i][1]);
        }
    }

    public void truquito(){
        listaEmpresas.addFocusListener(new FocusAdapter() {
            private void actualizarTablas(){
                Object[][] array = ControladorEmpresas.getInstance().listEmpresasGUI();
                if (array != null){
                    for (int i = 0; i<array.length;i++){
                        if (array[i][1] == listaEmpresas.getSelectedItem()){
                            rellenarTablas(i, array);
                        }
                    }

                }
            }
        });
    }

    private void rellenarTablas(int i, Object[][] array){
        ArrayList<Object> listas = new ArrayList<>();
        listas.add(array[i][3]);
        listas.add(array[i][4]);
        for (Object l : listas) {
            if (l instanceof Conductor){
                primerConductor.addItem(l.toString());
                segundoConductor.addItem(l.toString());
            }
            if (l instanceof Auxiliar) listaAuxiliares.addItem(l.toString());
            if (l instanceof Bus) patenteBusComboBox.addItem(((Bus) l).getPatente());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        crearViaje dialog = new crearViaje();
        dialog.pack();
        dialog.setTitle("Creacion de un Viaje");
        dialog.setVisible(true);
        System.exit(0);
        SistemaVentaPasajes.getInstance().readDatosSistema();
    }
}
