package Vista;

import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Modelo.Auxiliar;
import Modelo.Bus;
import Modelo.Conductor;
import Modelo.*;
import utilidades.IdPersona;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class crearViaje extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fechaViajeTexto;
    private JTextField textField1;
    private JComboBox patenteBusComboBox;
    private JComboBox primerConductor;
    private JComboBox segundoConductor;
    private JComboBox listaEmpresas;
    private JComboBox listaAuxiliares;
    private JTextField horaViaje;
    private JTextField minutoViaje;
    private JTextField duracionViaje;
    private JTextField comunaOrigenNombre;
    private JTextField comunaDestinoNombre;
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

    //DEBO BORRAR LA LLAMADA AL METODO DE READ DATOS INICIALES CUANDO EL GUI ESTE LISTO
    public crearViaje() throws FileNotFoundException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        primerConductor.addItem("N/A");
        segundoConductor.addItem("N/A");
        listaAuxiliares.addItem("N/A");
        patenteBusComboBox.addItem("N/A");
        SistemaVentaPasajes.getInstance().readDatosIniciales();
        cargarEmpresas();
        truquito();

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
        String fechaViaje = fechaViajeTexto.getText();
        String tiempoViaje = horaViaje.getText() + ":" + minutoViaje.getText();
        String precio = textField1.getText().toString();
        String duracion = duracionViaje.getText().toString();
        String patenteBus = patenteBusComboBox.getSelectedItem().toString();
        String nombrePrimer = primerConductor.getSelectedItem().toString();
        String nombreSegundo = segundoConductor.getSelectedItem().toString();
        String nombreAuxiliar = listaAuxiliares.getSelectedItem().toString();
        String comunaDestino = comunaDestinoNombre.getText().toString();
        String comunaOrigen = comunaOrigenNombre.getText().toString();
        String[] comunas = {comunaOrigen, comunaDestino};
        IdPersona[] idPersonas;
        if (!nombreSegundo.equals("N/A")) idPersonas = new IdPersona[3];
        else idPersonas = new IdPersona[2];
        Object[][] array = ControladorEmpresas.getInstance().listEmpresasGUI();
        Tripulante[] tripulantes = new Tripulante[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i][1].equals(listaEmpresas.getSelectedItem().toString())) {
                tripulantes = (Tripulante[]) array[i][3];
            }
        }
        for (Tripulante t : tripulantes) {
            if (idPersonas.length == 2) {
                if (t.getNombreCompleto().toString().equals(nombreAuxiliar)) idPersonas[0] = t.getIdPersona();
                if (t.getNombreCompleto().toString().equals(nombrePrimer)) idPersonas[1] = t.getIdPersona();
            }
            if (idPersonas.length == 3) {
                if (t.getNombreCompleto().toString().equals(nombreAuxiliar)) idPersonas[0] = t.getIdPersona();
                if (t.getNombreCompleto().toString().equals(nombreSegundo)) idPersonas[2] = t.getIdPersona();
                if (t.getNombreCompleto().toString().equals(nombrePrimer)) idPersonas[1] = t.getIdPersona();
            }
        }



        SistemaVentaPasajes.getInstance()
                .createViaje(LocalDate.parse(fechaViaje, formatterDate),
                        LocalTime.parse(tiempoViaje, formatterTime),
                        Integer.parseInt(precio),
                        Integer.parseInt(duracion), patenteBus, idPersonas, comunas
                        );


        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void cargarEmpresas(){
        listaEmpresas.addItem("N/A");
        Object[][] empresas = ControladorEmpresas.getInstance().listEmpresasGUI();
        for (int i = 0; i<empresas.length;i++){
            System.out.println("empresas: "+empresas[i][0]);
            System.out.println(empresas[i][1].toString());
            listaEmpresas.addItem(empresas[i][1]);
        }
    }

    public void truquito(){
        listaEmpresas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTablas();
            }
        });
    }

    private void actualizarTablas(){
        primerConductor.removeAllItems();
        segundoConductor.removeAllItems();
        listaAuxiliares.removeAllItems();
        patenteBusComboBox.removeAllItems();
        primerConductor.addItem("N/A");
        segundoConductor.addItem("N/A");
        listaAuxiliares.addItem("N/A");
        patenteBusComboBox.addItem("N/A");
        Object[][] array = ControladorEmpresas.getInstance().listEmpresasGUI();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                System.out.println(i);
                if (array[i][1].equals(listaEmpresas.getSelectedItem())) {
                    rellenarTablas(i, array);
                }
            }
        }
    }

    private void rellenarTablas(int i, Object[][] array){
        Tripulante[] tripulantes = (Tripulante[]) array[i][3];
        Bus[] buses = (Bus[]) array[i][4];
        for (Tripulante t : tripulantes){
            System.out.println(t.toString());
            if (t instanceof Conductor){
                primerConductor.addItem(t.getNombreCompleto().toString());
                segundoConductor.addItem(t.getNombreCompleto().toString());
            }
            if (t instanceof Auxiliar) listaAuxiliares.addItem(t.getNombreCompleto().toString());
        }
        for (Bus b : buses){
            patenteBusComboBox.addItem(b.getPatente().toString());
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
