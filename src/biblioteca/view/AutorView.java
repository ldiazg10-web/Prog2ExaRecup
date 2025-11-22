package biblioteca.view;

import biblioteca.model.Autor;
import biblioteca.service.AutorService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Vista CRUD para gestión de Autores
 */
public class AutorView extends JFrame {
    
    // Componentes del formulario
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtNacionalidad;
    private JTextField txtBuscar;
    
    // Botones
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnCerrar;
    
    // Tabla
    private JTable tablaAutores;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    
    // Service
    private AutorService autorService;
    
    // Estado
    private boolean modoEdicion = false;
    
    public AutorView() {
        autorService = new AutorService();
        initComponents();
        cargarTabla(autorService.listarTodos());
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Gestión de Autores");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ===== PANEL SUPERIOR - TÍTULO =====
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(41, 128, 185));
        panelTitulo.setPreferredSize(new Dimension(900, 50));
        
        JLabel lblTitulo = new JLabel("📚 GESTIÓN DE AUTORES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // ===== PANEL IZQUIERDO - FORMULARIO =====
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(300, 500));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Datos del Autor"));
        
        // ID (oculto pero útil)
        JPanel panelId = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelId.add(new JLabel("ID:"));
        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panelId.add(txtId);
        panelIzquierdo.add(panelId);
        
        // Nombre
        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNombre.add(new JLabel("Nombre: *"));
        txtNombre = new JTextField(20);
        panelNombre.add(txtNombre);
        panelIzquierdo.add(panelNombre);
        
        // Nacionalidad
        JPanel panelNacionalidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNacionalidad.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField(20);
        panelNacionalidad.add(txtNacionalidad);
        panelIzquierdo.add(panelNacionalidad);
        
        panelIzquierdo.add(Box.createVerticalStrut(20));
        
        // ===== BOTONES DE ACCIÓN =====
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btnNuevo = crearBoton("Nuevo", new Color(52, 152, 219), Color.BLACK);
        btnNuevo.addActionListener(e -> nuevoAutor());
        
        btnGuardar = crearBoton("Guardar", new Color(46, 204, 113), Color.BLACK);
        btnGuardar.addActionListener(e -> guardarAutor());
        
        btnModificar = crearBoton("Modificar", new Color(241, 196, 15), Color.BLACK);
        btnModificar.addActionListener(e -> modificarAutor());
        
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60), Color.BLACK);
        btnEliminar.addActionListener(e -> eliminarAutor());
        
        btnCerrar = crearBoton("Cerrar", new Color(149, 165, 166), Color.BLACK);
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        panelIzquierdo.add(panelBotones);
        
        // ===== PANEL DERECHO - TABLA Y BÚSQUEDA =====
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = crearBoton("🔍 Buscar", new Color(52, 152, 219), Color.WHITE);
        btnBuscar.addActionListener(e -> buscarAutor());
        panelBusqueda.add(btnBuscar);
        
        JButton btnListarTodos = crearBoton("📋 Listar Todos", new Color(149, 165, 166), Color.WHITE);
        btnListarTodos.addActionListener(e -> cargarTabla(autorService.listarTodos()));
        panelBusqueda.add(btnListarTodos);
        
        // Tabla
        String[] columnas = {"ID", "Nombre", "Nacionalidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaAutores = new JTable(modeloTabla);
        tablaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAutores.getTableHeader().setReorderingAllowed(false);
        tablaAutores.setRowHeight(25);
        
        // Evento de selección en tabla
        tablaAutores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
        
        scrollPane = new JScrollPane(tablaAutores);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Autores"));
        
        panelDerecho.add(panelBusqueda, BorderLayout.NORTH);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel();
        JLabel lblTotal = new JLabel("Total de autores: " + autorService.obtenerTotal());
        lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
        panelEstadisticas.add(lblTotal);
        panelDerecho.add(panelEstadisticas, BorderLayout.SOUTH);
        
        // ===== AGREGAR PANELES AL FRAME =====
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);
        
        add(panelPrincipal);
        
        // Estado inicial
        configurarEstadoBotones(false);
    }
    
    /**
     * Crear botón con estilo
     */
    private JButton crearBoton(String texto, Color fondo, Color foreground) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(foreground);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setPreferredSize(new Dimension(120, 35));
        return boton;
    }
    
    /**
     * Limpiar campos del formulario
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtNacionalidad.setText("");
        txtNombre.requestFocus();
        modoEdicion = false;
        configurarEstadoBotones(false);
    }
    
    /**
     * Configurar estado de botones
     */
    private void configurarEstadoBotones(boolean seleccionado) {
        btnGuardar.setEnabled(!seleccionado);
        btnModificar.setEnabled(seleccionado);
        btnEliminar.setEnabled(seleccionado);
    }
    
    /**
     * Cargar datos en la tabla
     */
    private void cargarTabla(List<Autor> autores) {
        modeloTabla.setRowCount(0);
        
        for (Autor autor : autores) {
            modeloTabla.addRow(new Object[]{
                autor.getId(),
                autor.getNombre(),
                autor.getNacionalidad()
            });
        }
    }
    
    /**
     * Nuevo autor - Limpiar formulario
     */
    private void nuevoAutor() {
        limpiarCampos();
        JOptionPane.showMessageDialog(this, 
            "Ingrese los datos del nuevo autor", 
            "Nuevo Autor", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Guardar nuevo autor
     */
    private void guardarAutor() {
        // Validar campos
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
        // Crear objeto
        Autor autor = new Autor();
        autor.setNombre(txtNombre.getText().trim());
        autor.setNacionalidad(txtNacionalidad.getText().trim());
        
        // Guardar
        String resultado = autorService.crear(autor);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(autorService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Modificar autor existente
     */
    private void modificarAutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un autor de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar campos
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de modificar este autor?", 
            "Confirmar Modificación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Crear objeto
        Autor autor = new Autor();
        autor.setId(Integer.parseInt(txtId.getText()));
        autor.setNombre(txtNombre.getText().trim());
        autor.setNacionalidad(txtNacionalidad.getText().trim());
        
        // Actualizar
        String resultado = autorService.actualizar(autor);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(autorService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Eliminar autor
     */
    private void eliminarAutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un autor de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este autor?\n" +
            "Esta acción no se puede deshacer.", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        int id = Integer.parseInt(txtId.getText());
        String resultado = autorService.eliminar(id);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(autorService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Buscar autores
     */
    private void buscarAutor() {
        String busqueda = txtBuscar.getText().trim();
        List<Autor> resultados = autorService.buscarPorNombre(busqueda);
        
        cargarTabla(resultados);
        
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron resultados", 
                "Búsqueda", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Seleccionar fila de la tabla
     */
    private void seleccionarFila() {
        int filaSeleccionada = tablaAutores.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            
            Object nacionalidad = modeloTabla.getValueAt(filaSeleccionada, 2);
            txtNacionalidad.setText(nacionalidad != null ? nacionalidad.toString() : "");
            
            modoEdicion = true;
            configurarEstadoBotones(true);
        }
    }
    
    /**
     * Método main para pruebas independientes
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            AutorView vista = new AutorView();
            vista.setVisible(true);
        });
    }
}