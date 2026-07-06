package Vista;

import Controlador.SistemaVentaPasajes;
import Excepciones.SistemaVentaPasajesException;
import Modelo.TipoDocumento;
import utilidades.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VentanaVentadePasajes extends JDialog {

    private SistemaVentaPasajes sv = SistemaVentaPasajes.getInstance();
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private String idDocumentoActual;
    private TipoDocumento tipoDocumentoActual;
    private String fechaViajeSeleccionada;
    private String patenteBusSeleccionada;
    private String horaViajeSeleccionada;
    private List<Integer> asientosSeleccionados = new ArrayList<>();
    private List<IdPersona> pasajerosAgregados = new ArrayList<>();

    // Layout
    private CardLayout cardLayout;
    private JPanel panelPrincipal;

    // Paneles
    private JPanel panelInicio;
    private JPanel panelViaje;
    private JPanel panelAsientos;
    private JPanel panelPasajero;
    private JPanel panelPago;

    // Componentes - Panel Inicio
    private JTextField txtIdDocumento;
    private JComboBox<TipoDocumento> cmbTipoDocumento;
    private JTextField txtFechaVenta;
    private JTextField txtRutCliente;
    private JTextField txtNombresCliente;
    private JTextField txtApellidoPaternoCliente;
    private JTextField txtApellidoMaternoCliente;
    private JTextField txtTelefonoCliente;
    private JTextField txtEmailCliente;
    private JComboBox<Tratamiento> cmbTratamientoCliente;
    private JButton btnIniciarVenta;

    // Componentes - Panel Viaje
    private JTextField txtFechaViaje;
    private JComboBox<String> cmbOrigen;
    private JComboBox<String> cmbDestino;
    private JButton btnBuscarViajes;
    private JTable tblViajes;
    private DefaultTableModel modelViajes;
    private JButton btnSeleccionarViaje;

    // Componentes - Panel Asientos
    private JLabel lblInfoViaje;
    private JPanel panelAsientosGrid;
    private JButton[] btnAsientos;
    private JButton btnConfirmarAsientos;

    // Componentes - Panel Pasajero
    private JComboBox<String> cmbTipoIdPasajero;
    private JTextField txtNumeroIdPasajero;
    private JTextField txtNacionalidadPasajero;
    private JTextField txtNombresPasajero;
    private JTextField txtApellidoPaternoPasajero;
    private JTextField txtApellidoMaternoPasajero;
    private JTextField txtTelefonoPasajero;
    private JTextField txtNombresContacto;
    private JTextField txtTelefonoContacto;
    private JComboBox<Tratamiento> cmbTratamientoPasajero;
    private JButton btnGuardarPasajero;
    private JButton btnFinalizarPasajeros;

    // Componentes - Panel Pago
    private JLabel lblMontoTotal;
    private JComboBox<String> cmbTipoPago;
    private JTextField txtNumeroTarjeta;
    private JButton btnPagar;
    private JButton btnGenerarPasajes;

    public VentanaVentadePasajes() {
        super(new JFrame(), "Venta de Pasajes", true);
        initComponents();
        setSize(950, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("VENTA DE PASAJES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);


        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(new Color(30, 30, 40));

        crearPanelInicio();
        crearPanelViaje();
        crearPanelAsientos();
        crearPanelPasajero();
        crearPanelPago();

        panelPrincipal.add(panelInicio, "INICIO");
        panelPrincipal.add(panelViaje, "VIAJE");
        panelPrincipal.add(panelAsientos, "ASIENTOS");
        panelPrincipal.add(panelPasajero, "PASAJERO");
        panelPrincipal.add(panelPago, "PAGO");

        add(panelPrincipal, BorderLayout.CENTER);


        JPanel panelNavegacion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNavegacion.setBackground(new Color(30, 30, 40));
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.addActionListener(e -> dispose());
        panelNavegacion.add(btnVolver);
        add(panelNavegacion, BorderLayout.SOUTH);

        cardLayout.show(panelPrincipal, "INICIO");
    }

    private void crearPanelInicio() {
        panelInicio = new JPanel();
        panelInicio.setBackground(new Color(30, 30, 40));
        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
        panelInicio.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        java.util.function.BiFunction<String, JComponent, JPanel> crearFila = (labelText, componente) -> {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fila.setBackground(new Color(30, 30, 40));
            JLabel label = new JLabel(labelText);
            label.setForeground(Color.WHITE);
            label.setPreferredSize(new Dimension(200, 25));
            fila.add(label);
            componente.setPreferredSize(new Dimension(200, 25));
            fila.add(componente);
            return fila;
        };

        txtIdDocumento = new JTextField(15);
        txtIdDocumento.setBackground(new Color(50, 50, 60));
        txtIdDocumento.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("ID Documento:", txtIdDocumento));

        cmbTipoDocumento = new JComboBox<>(TipoDocumento.values());
        cmbTipoDocumento.setBackground(new Color(50, 50, 60));
        cmbTipoDocumento.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Tipo Documento:", cmbTipoDocumento));

        txtFechaVenta = new JTextField(15);
        txtFechaVenta.setText(LocalDate.now().format(formatterDate));
        txtFechaVenta.setBackground(new Color(50, 50, 60));
        txtFechaVenta.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Fecha Venta (dd/MM/yyyy):", txtFechaVenta));

        JLabel separador = new JLabel("--- DATOS DEL CLIENTE ---", SwingConstants.CENTER);
        separador.setForeground(Color.WHITE);
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInicio.add(Box.createVerticalStrut(10));
        panelInicio.add(separador);
        panelInicio.add(Box.createVerticalStrut(10));

        txtRutCliente = new JTextField(15);
        txtRutCliente.setBackground(new Color(50, 50, 60));
        txtRutCliente.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("RUT Cliente (xx.xxx.xxx-x):", txtRutCliente));

        cmbTratamientoCliente = new JComboBox<>(Tratamiento.values());
        cmbTratamientoCliente.setBackground(new Color(50, 50, 60));
        cmbTratamientoCliente.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Tratamiento:", cmbTratamientoCliente));

        txtNombresCliente = new JTextField(15);
        txtNombresCliente.setBackground(new Color(50, 50, 60));
        txtNombresCliente.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Nombres:", txtNombresCliente));

        txtApellidoPaternoCliente = new JTextField(15);
        txtApellidoPaternoCliente.setBackground(new Color(50, 50, 60));
        txtApellidoPaternoCliente.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Apellido Paterno:", txtApellidoPaternoCliente));

        txtApellidoMaternoCliente = new JTextField(15);
        txtApellidoMaternoCliente.setBackground(new Color(50, 50, 60));
        txtApellidoMaternoCliente.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Apellido Materno:", txtApellidoMaternoCliente));

        txtTelefonoCliente = new JTextField(15);
        txtTelefonoCliente.setBackground(new Color(50, 50, 60));
        txtTelefonoCliente.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Teléfono:", txtTelefonoCliente));

        txtEmailCliente = new JTextField(15);
        txtEmailCliente.setBackground(new Color(50, 50, 60));
        txtEmailCliente.setForeground(Color.WHITE);
        panelInicio.add(crearFila.apply("Email:", txtEmailCliente));

        panelInicio.add(Box.createVerticalStrut(15));
        btnIniciarVenta = new JButton("Iniciar Venta");
        btnIniciarVenta.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciarVenta.setBackground(new Color(70, 130, 180));
        btnIniciarVenta.setForeground(Color.WHITE);
        btnIniciarVenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciarVenta.addActionListener(e -> iniciarVenta());
        panelInicio.add(btnIniciarVenta);
    }

    private void crearPanelViaje() {
        panelViaje = new JPanel(new BorderLayout(10, 10));
        panelViaje.setBackground(new Color(30, 30, 40));
        panelViaje.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filtros.setBackground(new Color(30, 30, 40));

        JLabel lblFecha = new JLabel("Fecha (dd/MM/yyyy):");
        lblFecha.setForeground(Color.WHITE);
        filtros.add(lblFecha);

        txtFechaViaje = new JTextField(12);
        txtFechaViaje.setBackground(new Color(50, 50, 60));
        txtFechaViaje.setForeground(Color.WHITE);
        filtros.add(txtFechaViaje);

        JLabel lblOrigen = new JLabel("Origen:");
        lblOrigen.setForeground(Color.WHITE);
        filtros.add(lblOrigen);

        cmbOrigen = new JComboBox<>();
        cmbOrigen.setBackground(new Color(50, 50, 60));
        cmbOrigen.setForeground(Color.WHITE);
        filtros.add(cmbOrigen);

        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setForeground(Color.WHITE);
        filtros.add(lblDestino);

        cmbDestino = new JComboBox<>();
        cmbDestino.setBackground(new Color(50, 50, 60));
        cmbDestino.setForeground(Color.WHITE);
        filtros.add(cmbDestino);

        btnBuscarViajes = new JButton("Buscar Viajes");
        btnBuscarViajes.setBackground(new Color(70, 130, 180));
        btnBuscarViajes.setForeground(Color.WHITE);
        btnBuscarViajes.addActionListener(e -> buscarViajes());
        filtros.add(btnBuscarViajes);

        panelViaje.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"N°", "Patente", "Hora", "Precio", "Asientos"};
        modelViajes = new DefaultTableModel(columnas, 0);
        tblViajes = new JTable(modelViajes);
        tblViajes.setBackground(new Color(50, 50, 60));
        tblViajes.setForeground(Color.WHITE);
        tblViajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tblViajes);
        scroll.setBackground(new Color(30, 30, 40));
        panelViaje.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(30, 30, 40));

        btnSeleccionarViaje = new JButton("Seleccionar Viaje");
        btnSeleccionarViaje.setFont(new Font("Arial", Font.BOLD, 14));
        btnSeleccionarViaje.setBackground(new Color(70, 130, 180));
        btnSeleccionarViaje.setForeground(Color.WHITE);
        btnSeleccionarViaje.addActionListener(e -> seleccionarViaje());
        btnPanel.add(btnSeleccionarViaje);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "INICIO"));
        btnPanel.add(btnVolver);

        panelViaje.add(btnPanel, BorderLayout.SOUTH);

        cargarComunas();
    }

    private void crearPanelAsientos() {
        panelAsientos = new JPanel(new BorderLayout(10, 10));
        panelAsientos.setBackground(new Color(30, 30, 40));
        panelAsientos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblInfoViaje = new JLabel("Viaje seleccionado: ");
        lblInfoViaje.setFont(new Font("Arial", Font.BOLD, 14));
        lblInfoViaje.setForeground(Color.WHITE);
        panelAsientos.add(lblInfoViaje, BorderLayout.NORTH);

        panelAsientosGrid = new JPanel(new GridLayout(0, 4, 10, 10));
        panelAsientosGrid.setBackground(new Color(30, 30, 40));
        panelAsientosGrid.setBorder(BorderFactory.createTitledBorder("Seleccione sus asientos"));
        JScrollPane scroll = new JScrollPane(panelAsientosGrid);
        scroll.setBackground(new Color(30, 30, 40));
        panelAsientos.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(30, 30, 40));

        btnConfirmarAsientos = new JButton("Confirmar Asientos");
        btnConfirmarAsientos.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmarAsientos.setBackground(new Color(70, 130, 180));
        btnConfirmarAsientos.setForeground(Color.WHITE);
        btnConfirmarAsientos.addActionListener(e -> confirmarAsientos());
        btnPanel.add(btnConfirmarAsientos);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            asientosSeleccionados.clear();
            cardLayout.show(panelPrincipal, "VIAJE");
        });
        btnPanel.add(btnVolver);

        panelAsientos.add(btnPanel, BorderLayout.SOUTH);
    }

    private void crearPanelPasajero() {
        panelPasajero = new JPanel();
        panelPasajero.setBackground(new Color(30, 30, 40));
        panelPasajero.setLayout(new BoxLayout(panelPasajero, BoxLayout.Y_AXIS));
        panelPasajero.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        java.util.function.BiFunction<String, JComponent, JPanel> crearFila = (labelText, componente) -> {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fila.setBackground(new Color(30, 30, 40));
            JLabel label = new JLabel(labelText);
            label.setForeground(Color.WHITE);
            label.setPreferredSize(new Dimension(200, 25));
            fila.add(label);
            componente.setPreferredSize(new Dimension(200, 25));
            fila.add(componente);
            return fila;
        };

        cmbTipoIdPasajero = new JComboBox<>(new String[]{"RUT", "Pasaporte"});
        cmbTipoIdPasajero.setBackground(new Color(50, 50, 60));
        cmbTipoIdPasajero.setForeground(Color.WHITE);
        cmbTipoIdPasajero.addActionListener(e -> actualizarCamposId());
        panelPasajero.add(crearFila.apply("Tipo ID:", cmbTipoIdPasajero));

        txtNumeroIdPasajero = new JTextField(15);
        txtNumeroIdPasajero.setBackground(new Color(50, 50, 60));
        txtNumeroIdPasajero.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Número ID:", txtNumeroIdPasajero));

        txtNacionalidadPasajero = new JTextField(15);
        txtNacionalidadPasajero.setBackground(new Color(50, 50, 60));
        txtNacionalidadPasajero.setForeground(Color.WHITE);
        txtNacionalidadPasajero.setEnabled(false);
        panelPasajero.add(crearFila.apply("Nacionalidad:", txtNacionalidadPasajero));

        cmbTratamientoPasajero = new JComboBox<>(Tratamiento.values());
        cmbTratamientoPasajero.setBackground(new Color(50, 50, 60));
        cmbTratamientoPasajero.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Tratamiento:", cmbTratamientoPasajero));

        txtNombresPasajero = new JTextField(15);
        txtNombresPasajero.setBackground(new Color(50, 50, 60));
        txtNombresPasajero.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Nombres:", txtNombresPasajero));

        txtApellidoPaternoPasajero = new JTextField(15);
        txtApellidoPaternoPasajero.setBackground(new Color(50, 50, 60));
        txtApellidoPaternoPasajero.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Apellido Paterno:", txtApellidoPaternoPasajero));

        txtApellidoMaternoPasajero = new JTextField(15);
        txtApellidoMaternoPasajero.setBackground(new Color(50, 50, 60));
        txtApellidoMaternoPasajero.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Apellido Materno:", txtApellidoMaternoPasajero));

        txtTelefonoPasajero = new JTextField(15);
        txtTelefonoPasajero.setBackground(new Color(50, 50, 60));
        txtTelefonoPasajero.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Teléfono:", txtTelefonoPasajero));

        JLabel separador = new JLabel("--- CONTACTO EMERGENCIA ---", SwingConstants.CENTER);
        separador.setForeground(Color.WHITE);
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPasajero.add(Box.createVerticalStrut(10));
        panelPasajero.add(separador);
        panelPasajero.add(Box.createVerticalStrut(10));

        txtNombresContacto = new JTextField(15);
        txtNombresContacto.setBackground(new Color(50, 50, 60));
        txtNombresContacto.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Nombre Contacto:", txtNombresContacto));

        txtTelefonoContacto = new JTextField(15);
        txtTelefonoContacto.setBackground(new Color(50, 50, 60));
        txtTelefonoContacto.setForeground(Color.WHITE);
        panelPasajero.add(crearFila.apply("Teléfono Contacto:", txtTelefonoContacto));

        panelPasajero.add(Box.createVerticalStrut(15));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(30, 30, 40));

        btnGuardarPasajero = new JButton("Guardar Pasajero");
        btnGuardarPasajero.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardarPasajero.setBackground(new Color(70, 130, 180));
        btnGuardarPasajero.setForeground(Color.WHITE);
        btnGuardarPasajero.addActionListener(e -> guardarPasajero());
        btnPanel.add(btnGuardarPasajero);

        btnFinalizarPasajeros = new JButton("Finalizar Pasajeros");
        btnFinalizarPasajeros.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizarPasajeros.setBackground(new Color(70, 130, 180));
        btnFinalizarPasajeros.setForeground(Color.WHITE);
        btnFinalizarPasajeros.addActionListener(e -> finalizarPasajeros());
        btnFinalizarPasajeros.setEnabled(false);
        btnPanel.add(btnFinalizarPasajeros);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "ASIENTOS"));
        btnPanel.add(btnVolver);

        panelPasajero.add(btnPanel);
    }

    private void crearPanelPago() {
        panelPago = new JPanel();
        panelPago.setBackground(new Color(30, 30, 40));
        panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.Y_AXIS));
        panelPago.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel tituloPago = new JLabel("RESUMEN DE VENTA", SwingConstants.CENTER);
        tituloPago.setFont(new Font("Arial", Font.BOLD, 16));
        tituloPago.setForeground(Color.WHITE);
        tituloPago.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPago.add(tituloPago);
        panelPago.add(Box.createVerticalStrut(20));

        lblMontoTotal = new JLabel("Monto Total: $0", SwingConstants.CENTER);
        lblMontoTotal.setFont(new Font("Arial", Font.BOLD, 24));
        lblMontoTotal.setForeground(new Color(100, 200, 100));
        lblMontoTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPago.add(lblMontoTotal);
        panelPago.add(Box.createVerticalStrut(30));

        JPanel pagoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pagoPanel.setBackground(new Color(30, 30, 40));
        JLabel lblTipo = new JLabel("Tipo de Pago:");
        lblTipo.setForeground(Color.WHITE);
        pagoPanel.add(lblTipo);

        cmbTipoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta"});
        cmbTipoPago.setBackground(new Color(50, 50, 60));
        cmbTipoPago.setForeground(Color.WHITE);
        cmbTipoPago.addActionListener(e -> actualizarCampoTarjeta());
        pagoPanel.add(cmbTipoPago);
        panelPago.add(pagoPanel);

        JPanel tarjetaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tarjetaPanel.setBackground(new Color(30, 30, 40));
        JLabel lblTarjeta = new JLabel("Número de Tarjeta:");
        lblTarjeta.setForeground(Color.WHITE);
        tarjetaPanel.add(lblTarjeta);

        txtNumeroTarjeta = new JTextField(20);
        txtNumeroTarjeta.setBackground(new Color(50, 50, 60));
        txtNumeroTarjeta.setForeground(Color.WHITE);
        txtNumeroTarjeta.setEnabled(false);
        tarjetaPanel.add(txtNumeroTarjeta);
        panelPago.add(tarjetaPanel);

        panelPago.add(Box.createVerticalStrut(30));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(30, 30, 40));

        btnPagar = new JButton("Pagar Venta");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPagar.setBackground(new Color(0, 150, 0));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.addActionListener(e -> pagarVenta());
        btnPanel.add(btnPagar);

        btnGenerarPasajes = new JButton("Generar Pasajes");
        btnGenerarPasajes.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerarPasajes.setBackground(new Color(70, 130, 180));
        btnGenerarPasajes.setForeground(Color.WHITE);
        btnGenerarPasajes.addActionListener(e -> generarPasajes());
        btnGenerarPasajes.setEnabled(false);
        btnPanel.add(btnGenerarPasajes);

        JButton btnVolver = new JButton("Volver al Inicio");
        btnVolver.addActionListener(e -> reiniciarVenta());
        btnPanel.add(btnVolver);

        panelPago.add(btnPanel);
    }

    private void iniciarVenta() {
        try {
            idDocumentoActual = txtIdDocumento.getText().trim();
            if (idDocumentoActual.isEmpty()) {
                throw new SistemaVentaPasajesException("Ingrese ID del documento");
            }

            tipoDocumentoActual = (TipoDocumento) cmbTipoDocumento.getSelectedItem();
            LocalDate fechaVenta = LocalDate.parse(txtFechaVenta.getText().trim(), formatterDate);

            String rutCliente = txtRutCliente.getText().trim();
            if (rutCliente.isEmpty()) {
                throw new SistemaVentaPasajesException("Ingrese RUT del cliente");
            }

            IdPersona idCliente = crearIdPersonaRut(rutCliente);
            Nombre nombreCliente = new Nombre();
            nombreCliente.setTratamiento((Tratamiento) cmbTratamientoCliente.getSelectedItem());
            nombreCliente.setNombres(txtNombresCliente.getText().trim());
            nombreCliente.setApellidoPaterno(txtApellidoPaternoCliente.getText().trim());
            nombreCliente.setApellidoMaterno(txtApellidoMaternoCliente.getText().trim());

            try {
                sv.createCliente(idCliente, nombreCliente,
                        txtTelefonoCliente.getText().trim(),
                        txtEmailCliente.getText().trim());
            } catch (SistemaVentaPasajesException e) {
                if (!e.getMessage().contains("Ya existe cliente")) throw e;
            }

            sv.iniciaVenta(idDocumentoActual, tipoDocumentoActual, fechaVenta, idCliente);

            JOptionPane.showMessageDialog(this, "Venta iniciada exitosamente");
            cardLayout.show(panelPrincipal, "VIAJE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void cargarComunas() {
        cmbOrigen.removeAllItems();
        cmbDestino.removeAllItems();
        cmbOrigen.addItem("Seleccione...");
        cmbDestino.addItem("Seleccione...");
        String[] comunas = {"Chillan", "Concepcion", "Santiago", "Talcahuano", "Pinto", "Alhué"};
        for (String c : comunas) {
            cmbOrigen.addItem(c);
            cmbDestino.addItem(c);
        }
    }

    private void buscarViajes() {
        try {
            String fechaStr = txtFechaViaje.getText().trim();
            if (fechaStr.isEmpty()) {
                throw new SistemaVentaPasajesException("Ingrese fecha del viaje");
            }
            LocalDate fecha = LocalDate.parse(fechaStr, formatterDate);
            fechaViajeSeleccionada = fechaStr;

            String[][] viajes = sv.getHorariosDisponibles(fecha);
            modelViajes.setRowCount(0);

            if (viajes.length == 0) {
                JOptionPane.showMessageDialog(this, "No hay viajes para esta fecha");
                return;
            }

            for (int i = 0; i < viajes.length; i++) {
                modelViajes.addRow(new Object[]{
                        i + 1,
                        viajes[i][0],
                        viajes[i][1],
                        "$" + viajes[i][2],
                        viajes[i][3]
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void seleccionarViaje() {
        int fila = tblViajes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un viaje");
            return;
        }

        patenteBusSeleccionada = (String) modelViajes.getValueAt(fila, 1);
        horaViajeSeleccionada = (String) modelViajes.getValueAt(fila, 2);

        cargarAsientos();
        lblInfoViaje.setText("Viaje: Bus " + patenteBusSeleccionada + " | Hora: " + horaViajeSeleccionada);
        cardLayout.show(panelPrincipal, "ASIENTOS");
    }

    private void cargarAsientos() {
        panelAsientosGrid.removeAll();
        asientosSeleccionados.clear();

        try {
            String[][] asientos = sv.listAsientosDelViaje(
                    LocalDate.parse(fechaViajeSeleccionada, formatterDate),
                    LocalTime.parse(horaViajeSeleccionada, formatterTime),
                    patenteBusSeleccionada
            );

            int total = 0;
            for (String[] fila : asientos) {
                for (String a : fila) {
                    if (a != null && !a.isEmpty()) total++;
                }
            }

            btnAsientos = new JButton[total];
            int idx = 0;
            for (String[] fila : asientos) {
                for (String a : fila) {
                    if (a != null && !a.isEmpty()) {
                        int num = Integer.parseInt(a);
                        btnAsientos[idx] = new JButton(String.valueOf(num));
                        btnAsientos[idx].setPreferredSize(new Dimension(60, 40));
                        btnAsientos[idx].setBackground(Color.GREEN);
                        btnAsientos[idx].addActionListener(new AsientoListener(num));
                        panelAsientosGrid.add(btnAsientos[idx]);
                        idx++;
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar asientos: " + e.getMessage());
        }

        panelAsientosGrid.revalidate();
        panelAsientosGrid.repaint();
    }

    private class AsientoListener implements ActionListener {
        private int numero;

        public AsientoListener(int numero) {
            this.numero = numero;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            if (asientosSeleccionados.contains(numero)) {
                asientosSeleccionados.remove(Integer.valueOf(numero));
                btn.setBackground(Color.GREEN);
            } else {
                asientosSeleccionados.add(numero);
                btn.setBackground(Color.ORANGE);
            }
        }
    }

    private void confirmarAsientos() {
        if (asientosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un asiento");
            return;
        }

        JOptionPane.showMessageDialog(this, "Asientos: " + asientosSeleccionados);
        cardLayout.show(panelPrincipal, "PASAJERO");
    }

    private void guardarPasajero() {
        try {
            IdPersona idPasajero = crearIdPasajero();
            Nombre nombre = new Nombre();
            nombre.setTratamiento((Tratamiento) cmbTratamientoPasajero.getSelectedItem());
            nombre.setNombres(txtNombresPasajero.getText().trim());
            nombre.setApellidoPaterno(txtApellidoPaternoPasajero.getText().trim());
            nombre.setApellidoMaterno(txtApellidoMaternoPasajero.getText().trim());

            Nombre contacto = new Nombre();
            contacto.setNombres(txtNombresContacto.getText().trim());

            sv.createPasajero(idPasajero, nombre, txtTelefonoPasajero.getText().trim(),
                    contacto, txtTelefonoContacto.getText().trim());

            int asiento = asientosSeleccionados.get(pasajerosAgregados.size());
            sv.vendePasaje(idDocumentoActual,
                    LocalDate.parse(fechaViajeSeleccionada, formatterDate),
                    LocalTime.parse(horaViajeSeleccionada, formatterTime),
                    patenteBusSeleccionada, asiento, idPasajero, tipoDocumentoActual);

            pasajerosAgregados.add(idPasajero);
            limpiarCamposPasajero();

            JOptionPane.showMessageDialog(this, "Pasajero agregado");

            if (pasajerosAgregados.size() >= asientosSeleccionados.size()) {
                btnFinalizarPasajeros.setEnabled(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void finalizarPasajeros() {
        try {
            java.util.Optional<Integer> monto = sv.getMontoVenta(idDocumentoActual, tipoDocumentoActual);
            if (monto.isPresent()) {
                lblMontoTotal.setText("Monto Total: $" + monto.get());
            }
        } catch (Exception e) {}

        cardLayout.show(panelPrincipal, "PAGO");
    }

    private IdPersona crearIdPasajero() {
        String tipo = (String) cmbTipoIdPasajero.getSelectedItem();
        String numero = txtNumeroIdPasajero.getText().trim();

        if (tipo.equals("RUT")) {
            return crearIdPersonaRut(numero);
        } else {
            return new Pasaporte(numero, txtNacionalidadPasajero.getText().trim());
        }
    }

    private IdPersona crearIdPersonaRut(String rutStr) {
        rutStr = rutStr.replace(".", "");
        String[] partes = rutStr.split("-");
        if (partes.length != 2) {
            throw new SistemaVentaPasajesException("Formato RUT inválido");
        }
        return new Rut(Integer.parseInt(partes[0]), partes[1].charAt(0));
    }

    private void actualizarCamposId() {
        boolean esPasaporte = cmbTipoIdPasajero.getSelectedItem().equals("Pasaporte");
        txtNacionalidadPasajero.setEnabled(esPasaporte);
        if (!esPasaporte) txtNacionalidadPasajero.setText("");
    }

    private void actualizarCampoTarjeta() {
        boolean esTarjeta = cmbTipoPago.getSelectedItem().equals("Tarjeta");
        txtNumeroTarjeta.setEnabled(esTarjeta);
        if (!esTarjeta) txtNumeroTarjeta.setText("");
    }

    private void limpiarCamposPasajero() {
        txtNumeroIdPasajero.setText("");
        txtNacionalidadPasajero.setText("");
        txtNombresPasajero.setText("");
        txtApellidoPaternoPasajero.setText("");
        txtApellidoMaternoPasajero.setText("");
        txtTelefonoPasajero.setText("");
        txtNombresContacto.setText("");
        txtTelefonoContacto.setText("");
    }

    private void pagarVenta() {
        try {
            String tipoPago = (String) cmbTipoPago.getSelectedItem();

            if (tipoPago.equals("Efectivo")) {
                sv.pagaVenta(idDocumentoActual, tipoDocumentoActual);
            } else {
                long tarjeta = Long.parseLong(txtNumeroTarjeta.getText().trim());
                sv.pagaVenta(idDocumentoActual, tipoDocumentoActual, tarjeta);
            }

            btnPagar.setEnabled(false);
            btnGenerarPasajes.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Venta pagada exitosamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al pagar: " + e.getMessage());
        }
    }

    private void generarPasajes() {
        JOptionPane.showMessageDialog(this, "Pasajes electrónicos generados");
        btnGenerarPasajes.setEnabled(false);
    }

    private void reiniciarVenta() {
        idDocumentoActual = null;
        tipoDocumentoActual = null;
        fechaViajeSeleccionada = null;
        patenteBusSeleccionada = null;
        horaViajeSeleccionada = null;
        asientosSeleccionados.clear();
        pasajerosAgregados.clear();

        txtIdDocumento.setText("");
        txtRutCliente.setText("");
        txtNombresCliente.setText("");
        txtApellidoPaternoCliente.setText("");
        txtApellidoMaternoCliente.setText("");
        txtTelefonoCliente.setText("");
        txtEmailCliente.setText("");
        txtFechaViaje.setText("");
        modelViajes.setRowCount(0);

        btnPagar.setEnabled(true);
        btnGenerarPasajes.setEnabled(false);
        btnFinalizarPasajeros.setEnabled(false);

        cardLayout.show(panelPrincipal, "INICIO");
    }


    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            VentanaVentadePasajes ventana = new VentanaVentadePasajes();
            ventana.setVisible(true);
        });
    }
}