package biblioteca.view;

import biblioteca.model.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana del Menú Principal del Sistema
 */
public class MenuPrincipalView extends JFrame {
    
    private Usuario usuarioActual;
    
    public MenuPrincipalView(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Sistema de Biblioteca - Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(236, 240, 241));
        
        // ===== PANEL SUPERIOR - BARRA DE TÍTULO =====
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(52, 73, 94));
        panelSuperior.setPreferredSize(new Dimension(800, 80));
        
        // Título
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panelTitulo.setBackground(new Color(52, 73, 94));
        
        JLabel lblTitulo = new JLabel("📚 SISTEMA DE GESTIÓN DE BIBLIOTECA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel de usuario
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        panelUsuario.setBackground(new Color(52, 73, 94));
        
        JLabel lblUsuario = new JLabel("👤 Usuario: " + usuarioActual.getUsername() + 
                                       " (" + usuarioActual.getRol() + ")");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);
        panelUsuario.add(lblUsuario);
        
        panelSuperior.add(panelTitulo, BorderLayout.WEST);
        panelSuperior.add(panelUsuario, BorderLayout.EAST);
        
        // ===== PANEL CENTRAL - OPCIONES DEL MENÚ =====
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Título de bienvenida
        JLabel lblBienvenida = new JLabel("Seleccione una opción:");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelCentral.add(lblBienvenida, gbc);
        
        // Resetear gridwidth
        gbc.gridwidth = 1;
        
        // ===== BOTONES DEL MENÚ =====
        
        // Botón Autores
        JButton btnAutores = crearBotonMenu("📖 GESTIÓN DE AUTORES", 
                                            "Administrar autores de libros",
                                            new Color(52, 152, 219));
        btnAutores.addActionListener(e -> abrirAutores());
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentral.add(btnAutores, gbc);
        
        // Botón Categorías
        JButton btnCategorias = crearBotonMenu("📂 GESTIÓN DE CATEGORÍAS", 
                                               "Administrar categorías de libros",
                                               new Color(46, 204, 113));
        btnCategorias.addActionListener(e -> abrirCategorias());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCentral.add(btnCategorias, gbc);
        
        // Botón Libros
        JButton btnLibros = crearBotonMenu("📚 GESTIÓN DE LIBROS", 
                                          "Administrar inventario de libros",
                                          new Color(155, 89, 182));
        btnLibros.addActionListener(e -> abrirLibros());
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCentral.add(btnLibros, gbc);
        
        // Botón Acerca De
        JButton btnAcercaDe = crearBotonMenu("ℹ️ ACERCA DE", 
                                            "Información del proyecto",
                                            new Color(241, 196, 15));
        btnAcercaDe.addActionListener(e -> abrirAcercaDe());
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCentral.add(btnAcercaDe, gbc);
        
        // Botón Libros Destacados (NUEVO)
        JButton btnDestacados = crearBotonMenu("⭐ LIBROS FAVORITOS", 
                                               "Mis libros destacados",
                                               new Color(243, 156, 18));
        btnDestacados.addActionListener(e -> abrirLibrosDestacados());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelCentral.add(btnDestacados, gbc);
        
        // ===== PANEL INFERIOR - OPCIONES DE SESIÓN =====
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelInferior.setBackground(new Color(236, 240, 241));
        
        JButton btnCerrarSesion = new JButton("🚪 Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrarSesion.setBackground(new Color(231, 76, 60));
        btnCerrarSesion.setForeground(Color.BLACK);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setPreferredSize(new Dimension(180, 40));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        
        JButton btnSalir = new JButton("❌ Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setBackground(new Color(149, 165, 166));
        btnSalir.setForeground(Color.BLACK);
        btnSalir.setFocusPainted(false);
        btnSalir.setPreferredSize(new Dimension(180, 40));
        btnSalir.addActionListener(e -> salir());
        
        panelInferior.add(btnCerrarSesion);
        panelInferior.add(btnSalir);
        
        // ===== AGREGAR PANELES AL FRAME =====
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        // ===== CREAR BARRA DE MENÚ =====
        crearBarraMenu();
    }
    
    /**
     * Crear botón del menú con estilo
     */
    private JButton crearBotonMenu(String titulo, String descripcion, Color color) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(320, 120));
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Panel interno del botón
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.setBackground(color);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelBoton.add(Box.createVerticalGlue());
        panelBoton.add(lblTitulo);
        panelBoton.add(Box.createVerticalStrut(10));
        panelBoton.add(lblDescripcion);
        panelBoton.add(Box.createVerticalGlue());
        
        boton.add(panelBoton, BorderLayout.CENTER);
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
                panelBoton.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
                panelBoton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    /**
     * Crear barra de menú
     */
    private void crearBarraMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> salir());
        
        menuArchivo.add(itemCerrarSesion);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
        
        // Menú Gestión
        JMenu menuGestion = new JMenu("Gestión");
        
        JMenuItem itemAutores = new JMenuItem("Autores");
        itemAutores.addActionListener(e -> abrirAutores());
        
        JMenuItem itemCategorias = new JMenuItem("Categorías");
        itemCategorias.addActionListener(e -> abrirCategorias());
        
        JMenuItem itemLibros = new JMenuItem("Libros");
        itemLibros.addActionListener(e -> abrirLibros());
        
        menuGestion.add(itemAutores);
        menuGestion.add(itemCategorias);
        menuGestion.add(itemLibros);
        
        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> abrirAcercaDe());
        
        menuAyuda.add(itemAcercaDe);
        
        // Agregar menús a la barra
        menuBar.add(menuArchivo);
        menuBar.add(menuGestion);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Abrir ventana de Autores
     */
    private void abrirAutores() {
        AutorView vistaAutores = new AutorView();
        vistaAutores.setVisible(true);
    }
    
    /**
     * Abrir ventana de Categorías
     */
    private void abrirCategorias() {
        CategoriaView vistaCategorias = new CategoriaView();
        vistaCategorias.setVisible(true);
    }
    
    /**
     * Abrir ventana de Libros
     */
    private void abrirLibros() {
        LibroView vistaLibros = new LibroView();
        vistaLibros.setVisible(true);
    }
    
    /**
     * Abrir ventana Acerca De
     */
    private void abrirAcercaDe() {
        AcercaDeView vistaAcercaDe = new AcercaDeView();
        vistaAcercaDe.setVisible(true);
    }
        /**
     * Abrir ventana de Libros Destacados
     */
    private void abrirLibrosDestacados() {
        LibrosDestacadosView vistaDestacados = new LibrosDestacadosView();
        vistaDestacados.setVisible(true);
    }
    /**
     * Cerrar sesión
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de cerrar sesión?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Volver al login
            LoginView login = new LoginView();
            login.setVisible(true);
            dispose();
        }
    }
    
    /**
     * Salir del sistema
     */
    private void salir() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de salir del sistema?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}