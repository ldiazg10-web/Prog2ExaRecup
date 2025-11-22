package biblioteca.view;

import biblioteca.model.AcercaDe;
import biblioteca.dao.AcercaDeDAO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Vista de Información del Proyecto (Acerca De)
 */
public class AcercaDeView extends JFrame {
    
    // Componentes
    private JTextField txtCarne;
    private JTextField txtNombres;
    private JTextField txtNumeroCarne;
    private JTextField txtProyecto;
    private JTextField txtVersion;
    private JTextField txtFecha;
    private JLabel lblFoto;
    private JButton btnCargarFoto;
    private JButton btnGuardar;
    private JButton btnCerrar;
    
    // DAO
    private AcercaDeDAO acercaDeDAO;
    
    // Variables
    private String rutaFotoSeleccionada;
    private AcercaDe infoActual;
    
    public AcercaDeView() {
        acercaDeDAO = new AcercaDeDAO();
        initComponents();
        cargarDatos();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Acerca De - Información del Proyecto");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
        
        // ===== PANEL SUPERIOR - TÍTULO =====
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(241, 196, 15));
        panelTitulo.setPreferredSize(new Dimension(700, 60));
        
        JLabel lblTitulo = new JLabel("ℹ️ INFORMACIÓN DEL PROYECTO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // ===== PANEL CENTRAL =====
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(Color.WHITE);
        
        // Panel izquierdo - Foto
        JPanel panelFoto = new JPanel();
        panelFoto.setLayout(new BoxLayout(panelFoto, BoxLayout.Y_AXIS));
        panelFoto.setBorder(BorderFactory.createTitledBorder("Fotografía"));
        panelFoto.setPreferredSize(new Dimension(200, 450));
        panelFoto.setBackground(Color.WHITE);
        
        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(180, 180));
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setVerticalAlignment(SwingConstants.CENTER);
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        lblFoto.setBackground(Color.LIGHT_GRAY);
        lblFoto.setOpaque(true);
        lblFoto.setText("Sin foto");
        lblFoto.setForeground(Color.DARK_GRAY);
        lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnCargarFoto = new JButton("📁 Cargar Foto");
        btnCargarFoto.setFont(new Font("Arial", Font.BOLD, 12));
        btnCargarFoto.setBackground(new Color(52, 152, 219));
        btnCargarFoto.setForeground(Color.BLACK);
        btnCargarFoto.setFocusPainted(false);
        btnCargarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCargarFoto.addActionListener(e -> cargarFoto());
        
        panelFoto.add(Box.createVerticalStrut(10));
        panelFoto.add(lblFoto);
        panelFoto.add(Box.createVerticalStrut(15));
        panelFoto.add(btnCargarFoto);
        
        // Panel derecho - Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Carné
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCarne = new JLabel("Carné:");
        lblCarne.setForeground(Color.BLACK);
        panelFormulario.add(lblCarne, gbc);
        
        gbc.gridx = 1;
        txtCarne = new JTextField(20);
        panelFormulario.add(txtCarne, gbc);
        
        // Nombres
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNombres = new JLabel("Nombres Completos:");
        lblNombres.setForeground(Color.BLACK);
        panelFormulario.add(lblNombres, gbc);
        
        gbc.gridx = 1;
        txtNombres = new JTextField(20);
        panelFormulario.add(txtNombres, gbc);
        
        // Número de Carné
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblNumeroCarne = new JLabel("Número de Carné:");
        lblNumeroCarne.setForeground(Color.BLACK);
        panelFormulario.add(lblNumeroCarne, gbc);
        
        gbc.gridx = 1;
        txtNumeroCarne = new JTextField(20);
        panelFormulario.add(txtNumeroCarne, gbc);
        
        // Separador
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JSeparator separador = new JSeparator();
        panelFormulario.add(separador, gbc);
        
        gbc.gridwidth = 1;
        
        // Proyecto
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblProyecto = new JLabel("Nombre del Proyecto:");
        lblProyecto.setForeground(Color.BLACK);
        panelFormulario.add(lblProyecto, gbc);
        
        gbc.gridx = 1;
        txtProyecto = new JTextField(20);
        panelFormulario.add(txtProyecto, gbc);
        
        // Versión
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblVersion = new JLabel("Versión:");
        lblVersion.setForeground(Color.BLACK);
        panelFormulario.add(lblVersion, gbc);
        
        gbc.gridx = 1;
        txtVersion = new JTextField(20);
        panelFormulario.add(txtVersion, gbc);
        
        // Fecha
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(Color.BLACK);
        panelFormulario.add(lblFecha, gbc);
        
        gbc.gridx = 1;
        txtFecha = new JTextField(20);
        panelFormulario.add(txtFecha, gbc);
        
        panelCentral.add(panelFoto, BorderLayout.WEST);
        panelCentral.add(panelFormulario, BorderLayout.CENTER);
        
        // ===== PANEL INFERIOR - BOTONES =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(150, 40));
        btnGuardar.addActionListener(e -> guardar());
        
        btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(149, 165, 166));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setPreferredSize(new Dimension(150, 40));
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCerrar);
        
        // ===== AGREGAR AL FRAME =====
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Cargar datos desde la base de datos
     */
    private void cargarDatos() {
        infoActual = acercaDeDAO.obtenerInfo();
        
        if (infoActual != null) {
            txtCarne.setText(infoActual.getCarne());
            txtNombres.setText(infoActual.getNombres());
            txtNumeroCarne.setText(infoActual.getNumeroCarne());
            txtProyecto.setText(infoActual.getProyecto());
            txtVersion.setText(infoActual.getVersion());
            txtFecha.setText(infoActual.getFecha());
            
            // Cargar foto si existe
            if (infoActual.getFotoPath() != null && !infoActual.getFotoPath().isEmpty()) {
                rutaFotoSeleccionada = infoActual.getFotoPath();
                mostrarFoto(rutaFotoSeleccionada);
            }
        } else {
            // Valores por defecto
            txtProyecto.setText("Sistema de Gestión de Biblioteca");
            txtVersion.setText("1.0");
            txtFecha.setText("2024");
        }
    }
    
    /**
     * Cargar foto usando JFileChooser
     */
    private void cargarFoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Fotografía");
        
        // Filtro para imágenes
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
            "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"
        );
        fileChooser.setFileFilter(filtro);
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            rutaFotoSeleccionada = archivoSeleccionado.getAbsolutePath();
            mostrarFoto(rutaFotoSeleccionada);
        }
    }
    
    /**
     * Mostrar foto en el JLabel
     */
    private void mostrarFoto(String rutaFoto) {
        try {
            ImageIcon iconoOriginal = new ImageIcon(rutaFoto);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                180, 180, Image.SCALE_SMOOTH
            );
            lblFoto.setIcon(new ImageIcon(imagenEscalada));
            lblFoto.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar la imagen",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Guardar información
     */
    private void guardar() {
        // Validaciones
        if (txtCarne.getText().trim().isEmpty() || txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El carné y los nombres son obligatorios",
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Crear o actualizar objeto
        if (infoActual == null) {
            infoActual = new AcercaDe();
        }
        
        infoActual.setCarne(txtCarne.getText().trim());
        infoActual.setNombres(txtNombres.getText().trim());
        infoActual.setNumeroCarne(txtNumeroCarne.getText().trim());
        infoActual.setFotoPath(rutaFotoSeleccionada);
        infoActual.setProyecto(txtProyecto.getText().trim());
        infoActual.setVersion(txtVersion.getText().trim());
        infoActual.setFecha(txtFecha.getText().trim());
        
        // Guardar en BD
        boolean exito = acercaDeDAO.actualizar(infoActual);
        
        if (exito) {
            JOptionPane.showMessageDialog(this,
                "Información guardada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al guardar la información",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}