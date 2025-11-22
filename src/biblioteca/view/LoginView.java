/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package biblioteca.view;


import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class LoginView extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnSalir;
    private JLabel lblMensaje;
    
    private boolean destacado;


    private UsuarioDAO usuarioDAO;
    
    public LoginView() {
        usuarioDAO = new UsuarioDAO();
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Sistema de Biblioteca - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 240, 240));
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(41, 128, 185));
        panelTitulo.setPreferredSize(new Dimension(400, 60));
        
        JLabel lblTitulo = new JLabel("SISTEMA DE BIBLIOTECA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(lblUsuario, gbc);
        
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelFormulario.add(txtUsuario, gbc);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelFormulario.add(txtPassword, gbc);
        
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelFormulario.add(lblMensaje, gbc);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(240, 240, 240));
        
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(46, 204, 113));
        btnIngresar.setForeground(Color.BLACK);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setPreferredSize(new Dimension(120, 35));
        btnIngresar.addActionListener(e -> login());
        
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.BLACK);
        btnSalir.setFocusPainted(false);
        btnSalir.setPreferredSize(new Dimension(120, 35));
        btnSalir.addActionListener(e -> System.exit(0));
        
        panelBotones.add(btnIngresar);
        panelBotones.add(Box.createHorizontalStrut(10));
        panelBotones.add(btnSalir);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(new Color(52, 73, 94));
        panelInfo.setPreferredSize(new Dimension(400, 30));
        JLabel lblInfo = new JLabel("Proyecto Final - Programación II");
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        panelInfo.add(lblInfo);
        panelPrincipal.add(panelInfo, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        // Eventos de teclado
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
    }
    

    private void login() {
        // Obtener datos
        String username = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }
        
        Usuario usuario = usuarioDAO.autenticar(username, password);
        
        if (usuario != null) {
            // Login exitoso
            lblMensaje.setForeground(new Color(46, 204, 113));
            lblMensaje.setText("✓ Login exitoso");
            
            Timer timer = new Timer(500, e -> {
                MenuPrincipalView menuPrincipal = new MenuPrincipalView(usuario);
                menuPrincipal.setVisible(true);
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            // Login fallido
            mostrarError("Usuario o contraseña incorrectos");
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
    
 void mostrarError(String mensaje) {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText("✗ " + mensaje);
    }
    

    public static void main(String[] args) {
        // Establecer Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}
