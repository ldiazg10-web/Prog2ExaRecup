package biblioteca.view;

import biblioteca.model.Categoria;
import biblioteca.service.CategoriaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class CategoriaView extends JFrame {
    
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtBuscar;
    
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnCerrar;
    
    private JTable tablaCategorias;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    
    private CategoriaService categoriaService;
    
    private boolean modoEdicion = false;
    
    public CategoriaView() {
        categoriaService = new CategoriaService();
        initComponents();
        cargarTabla(categoriaService.listarTodos());
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Gestión de Categorías");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ===== PANEL SUPERIOR - TÍTULO =====
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(46, 204, 113));
        panelTitulo.setPreferredSize(new Dimension(900, 50));
        
        JLabel lblTitulo = new JLabel("📂 GESTIÓN DE CATEGORÍAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // ===== PANEL IZQUIERDO - FORMULARIO =====
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(320, 500));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Datos de la Categoría"));
        
        // ID
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
        
        // Descripción
        JPanel panelDescripcion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDescripcion.add(new JLabel("Descripción:"));
        panelIzquierdo.add(panelDescripcion);
        
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(280, 80));
        JPanel panelTextArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTextArea.add(scrollDescripcion);
        panelIzquierdo.add(panelTextArea);
        
        panelIzquierdo.add(Box.createVerticalStrut(20));
        
        // ===== BOTONES DE ACCIÓN =====
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btnNuevo = crearBoton("Nuevo", new Color(52, 152, 219), Color.BLACK);
        btnNuevo.addActionListener(e -> nuevaCategoria());
        
        btnGuardar = crearBoton("Guardar", new Color(46, 204, 113), Color.BLACK);
        btnGuardar.addActionListener(e -> guardarCategoria());
        
        btnModificar = crearBoton("Modificar", new Color(241, 196, 15), Color.BLACK);
        btnModificar.addActionListener(e -> modificarCategoria());
        
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60), Color.BLACK);
        btnEliminar.addActionListener(e -> eliminarCategoria());
        
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
        btnBuscar.addActionListener(e -> buscarCategoria());
        panelBusqueda.add(btnBuscar);
        
        JButton btnListarTodos = crearBoton("📋 Listar Todos", new Color(149, 165, 166), Color.WHITE);
        btnListarTodos.addActionListener(e -> cargarTabla(categoriaService.listarTodos()));
        panelBusqueda.add(btnListarTodos);
        
        // Tabla
        String[] columnas = {"ID", "Nombre", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCategorias = new JTable(modeloTabla);
        tablaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCategorias.getTableHeader().setReorderingAllowed(false);
        tablaCategorias.setRowHeight(25);
        
        // Evento de selección en tabla
        tablaCategorias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
        
        scrollPane = new JScrollPane(tablaCategorias);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Categorías"));
        
        panelDerecho.add(panelBusqueda, BorderLayout.NORTH);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel();
        JLabel lblTotal = new JLabel("Total de categorías: " + categoriaService.obtenerTotal());
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
    
    private JButton crearBoton(String texto, Color fondo, Color foreground) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(foreground);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setPreferredSize(new Dimension(120, 35));
        return boton;
    }
    
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtNombre.requestFocus();
        modoEdicion = false;
        configurarEstadoBotones(false);
    }
    
    private void configurarEstadoBotones(boolean seleccionado) {
        btnGuardar.setEnabled(!seleccionado);
        btnModificar.setEnabled(seleccionado);
        btnEliminar.setEnabled(seleccionado);
    }
    
    private void cargarTabla(List<Categoria> categorias) {
        modeloTabla.setRowCount(0);
        
        for (Categoria categoria : categorias) {
            modeloTabla.addRow(new Object[]{
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
            });
        }
    }
    
    private void nuevaCategoria() {
        limpiarCampos();
        JOptionPane.showMessageDialog(this, 
            "Ingrese los datos de la nueva categoría", 
            "Nueva Categoría", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void guardarCategoria() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
        Categoria categoria = new Categoria();
        categoria.setNombre(txtNombre.getText().trim());
        categoria.setDescripcion(txtDescripcion.getText().trim());
        
        String resultado = categoriaService.crear(categoria);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(categoriaService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarCategoria() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una categoría de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de modificar esta categoría?", 
            "Confirmar Modificación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(txtId.getText()));
        categoria.setNombre(txtNombre.getText().trim());
        categoria.setDescripcion(txtDescripcion.getText().trim());
        
        String resultado = categoriaService.actualizar(categoria);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(categoriaService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarCategoria() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una categoría de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar esta categoría?\n" +
            "Esta acción no se puede deshacer.", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        int id = Integer.parseInt(txtId.getText());
        String resultado = categoriaService.eliminar(id);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(categoriaService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarCategoria() {
        String busqueda = txtBuscar.getText().trim();
        List<Categoria> resultados = categoriaService.buscarPorNombre(busqueda);
        
        cargarTabla(resultados);
        
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron resultados", 
                "Búsqueda", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void seleccionarFila() {
        int filaSeleccionada = tablaCategorias.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            
            Object descripcion = modeloTabla.getValueAt(filaSeleccionada, 2);
            txtDescripcion.setText(descripcion != null ? descripcion.toString() : "");
            
            modoEdicion = true;
            configurarEstadoBotones(true);
        }
    }
}