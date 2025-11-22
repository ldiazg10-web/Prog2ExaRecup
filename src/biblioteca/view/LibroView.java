package biblioteca.view;

import biblioteca.model.Libro;
import biblioteca.model.Autor;
import biblioteca.model.Categoria;
import biblioteca.service.LibroService;
import biblioteca.service.AutorService;
import biblioteca.service.CategoriaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Vista CRUD para gestión de Libros
 */
public class LibroView extends JFrame {
    
    // Componentes del formulario
    private JTextField txtId;
    private JTextField txtTitulo;
    private JComboBox<Autor> cmbAutor;
    private JComboBox<Categoria> cmbCategoria;
    private JSpinner spnAnio;
    private JSpinner spnStock;
    private JTextField txtBuscar;
    
    // Botones
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnFavorito; // NUEVO
    private JButton btnBuscar;
    private JButton btnCerrar;
    
    // Tabla
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    
    // Services
    private LibroService libroService;
    private AutorService autorService;
    private CategoriaService categoriaService;
    
    // Estado
    private boolean modoEdicion = false;
    
    public LibroView() {
        libroService = new LibroService();
        autorService = new AutorService();
        categoriaService = new CategoriaService();
        initComponents();
        cargarComboAutores();
        cargarComboCategorias();
        cargarTabla(libroService.listarTodos());
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Gestión de Libros");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        
        // ===== PANEL SUPERIOR - TÍTULO =====
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(155, 89, 182));
        panelTitulo.setPreferredSize(new Dimension(1000, 50));
        
        JLabel lblTitulo = new JLabel("📚 GESTIÓN DE LIBROS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // ===== PANEL IZQUIERDO - FORMULARIO =====
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(350, 550));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));
        panelIzquierdo.setBackground(Color.WHITE);
        
        // ID
        JPanel panelId = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelId.setBackground(Color.WHITE);
        JLabel lblId = new JLabel("ID:");
        lblId.setForeground(Color.BLACK);
        panelId.add(lblId);
        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panelId.add(txtId);
        panelIzquierdo.add(panelId);
        
        // Título
        JPanel panelTitulo2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo2.setBackground(Color.WHITE);
        JLabel lblTitulo2 = new JLabel("Título: *");
        lblTitulo2.setForeground(Color.BLACK);
        panelTitulo2.add(lblTitulo2);
        txtTitulo = new JTextField(20);
        panelTitulo2.add(txtTitulo);
        panelIzquierdo.add(panelTitulo2);
        
        // Autor (ComboBox)
        JPanel panelAutor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAutor.setBackground(Color.WHITE);
        JLabel lblAutor = new JLabel("Autor: *");
        lblAutor.setForeground(Color.BLACK);
        panelAutor.add(lblAutor);
        cmbAutor = new JComboBox<>();
        cmbAutor.setPreferredSize(new Dimension(200, 25));
        panelAutor.add(cmbAutor);
        panelIzquierdo.add(panelAutor);
        
        // Categoría (ComboBox)
        JPanel panelCategoria = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCategoria.setBackground(Color.WHITE);
        JLabel lblCategoria = new JLabel("Categoría: *");
        lblCategoria.setForeground(Color.BLACK);
        panelCategoria.add(lblCategoria);
        cmbCategoria = new JComboBox<>();
        cmbCategoria.setPreferredSize(new Dimension(200, 25));
        panelCategoria.add(cmbCategoria);
        panelIzquierdo.add(panelCategoria);
        
        // Año (Spinner)
        JPanel panelAnio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAnio.setBackground(Color.WHITE);
        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setForeground(Color.BLACK);
        panelAnio.add(lblAnio);
        SpinnerNumberModel modelAnio = new SpinnerNumberModel(2024, 1900, 2100, 1);
        spnAnio = new JSpinner(modelAnio);
        spnAnio.setPreferredSize(new Dimension(100, 25));
        panelAnio.add(spnAnio);
        panelIzquierdo.add(panelAnio);
        
        // Stock (Spinner)
        JPanel panelStock = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStock.setBackground(Color.WHITE);
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setForeground(Color.BLACK);
        panelStock.add(lblStock);
        SpinnerNumberModel modelStock = new SpinnerNumberModel(0, 0, 1000, 1);
        spnStock = new JSpinner(modelStock);
        spnStock.setPreferredSize(new Dimension(100, 25));
        panelStock.add(spnStock);
        panelIzquierdo.add(panelStock);
        
        panelIzquierdo.add(Box.createVerticalStrut(20));
        
        // ===== BOTONES DE ACCIÓN =====
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 5, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBotones.setBackground(Color.WHITE);
        
        btnNuevo = crearBoton("Nuevo", new Color(52, 152, 219));
        btnNuevo.addActionListener(e -> nuevoLibro());
        
        btnGuardar = crearBoton("Guardar", new Color(46, 204, 113));
        btnGuardar.addActionListener(e -> guardarLibro());
        
        btnModificar = crearBoton("Modificar", new Color(241, 196, 15));
        btnModificar.addActionListener(e -> modificarLibro());
        
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnEliminar.addActionListener(e -> eliminarLibro());
        
        btnFavorito = crearBoton("⭐ Favorito", new Color(243, 156, 18));
        btnFavorito.addActionListener(e -> toggleFavorito());
        
        btnCerrar = crearBoton("Cerrar", new Color(52, 73, 94));
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnFavorito);
        panelBotones.add(btnCerrar);
        
        panelIzquierdo.add(panelBotones);
        
        // ===== PANEL DERECHO - TABLA Y BÚSQUEDA =====
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        panelDerecho.setBackground(Color.WHITE);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        panelBusqueda.setBackground(Color.WHITE);
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setForeground(Color.BLACK);
        panelBusqueda.add(lblBuscar);
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = crearBoton("🔍 Buscar", new Color(52, 152, 219));
        btnBuscar.addActionListener(e -> buscarLibro());
        panelBusqueda.add(btnBuscar);
        
        JButton btnListarTodos = crearBoton("📋 Listar Todos", new Color(52, 73, 94));
        btnListarTodos.addActionListener(e -> cargarTabla(libroService.listarTodos()));
        panelBusqueda.add(btnListarTodos);
        
        // Tabla
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Año", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.getTableHeader().setReorderingAllowed(false);
        tablaLibros.setRowHeight(25);
        tablaLibros.setBackground(Color.WHITE);
        tablaLibros.setForeground(Color.BLACK);
        
        // Evento de selección en tabla
        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
        
        scrollPane = new JScrollPane(tablaLibros);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Libros"));
        
        panelDerecho.add(panelBusqueda, BorderLayout.NORTH);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setBackground(Color.WHITE);
        JLabel lblTotal = new JLabel("Total de libros: " + libroService.obtenerTotal());
        lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotal.setForeground(Color.BLACK);
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
    
    private JButton crearBoton(String texto, Color fondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setPreferredSize(new Dimension(140, 35));
        boton.setBorderPainted(true);
        boton.setOpaque(true);
        return boton;
    }
    
    private void cargarComboAutores() {
        cmbAutor.removeAllItems();
        List<Autor> autores = autorService.listarTodos();
        for (Autor autor : autores) {
            cmbAutor.addItem(autor);
        }
    }
    
    private void cargarComboCategorias() {
        cmbCategoria.removeAllItems();
        List<Categoria> categorias = categoriaService.listarTodos();
        for (Categoria categoria : categorias) {
            cmbCategoria.addItem(categoria);
        }
    }
    
    private void limpiarCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        if (cmbAutor.getItemCount() > 0) cmbAutor.setSelectedIndex(0);
        if (cmbCategoria.getItemCount() > 0) cmbCategoria.setSelectedIndex(0);
        spnAnio.setValue(2024);
        spnStock.setValue(0);
        txtTitulo.requestFocus();
        modoEdicion = false;
        configurarEstadoBotones(false);
    }
    
    private void configurarEstadoBotones(boolean seleccionado) {
        btnGuardar.setEnabled(!seleccionado);
        btnModificar.setEnabled(seleccionado);
        btnEliminar.setEnabled(seleccionado);
        btnFavorito.setEnabled(seleccionado); // NUEVO
    }
    
    private void cargarTabla(List<Libro> libros) {
        modeloTabla.setRowCount(0);
        
        for (Libro libro : libros) {
            modeloTabla.addRow(new Object[]{
                libro.getId(),
                libro.getTitulo(),
                libro.getAutorNombre(),
                libro.getCategoriaNombre(),
                libro.getAnio(),
                libro.getStock()
            });
        }
    }
    
    private void nuevoLibro() {
        limpiarCampos();
        JOptionPane.showMessageDialog(this, 
            "Ingrese los datos del nuevo libro", 
            "Nuevo Libro", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void guardarLibro() {
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El título es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtTitulo.requestFocus();
            return;
        }
        
        if (cmbAutor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un autor", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (cmbCategoria.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar una categoría", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Libro libro = new Libro();
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setAutorId(((Autor) cmbAutor.getSelectedItem()).getId());
        libro.setCategoriaId(((Categoria) cmbCategoria.getSelectedItem()).getId());
        libro.setAnio((Integer) spnAnio.getValue());
        libro.setStock((Integer) spnStock.getValue());
        
        String resultado = libroService.crear(libro);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(libroService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarLibro() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un libro de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El título es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de modificar este libro?", 
            "Confirmar Modificación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        Libro libro = new Libro();
        libro.setId(Integer.parseInt(txtId.getText()));
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setAutorId(((Autor) cmbAutor.getSelectedItem()).getId());
        libro.setCategoriaId(((Categoria) cmbCategoria.getSelectedItem()).getId());
        libro.setAnio((Integer) spnAnio.getValue());
        libro.setStock((Integer) spnStock.getValue());
        
        String resultado = libroService.actualizar(libro);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(libroService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarLibro() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un libro de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este libro?\n" +
            "Esta acción no se puede deshacer.", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        int id = Integer.parseInt(txtId.getText());
        String resultado = libroService.eliminar(id);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla(libroService.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarLibro() {
        String busqueda = txtBuscar.getText().trim();
        List<Libro> resultados = libroService.buscarPorTitulo(busqueda);
        
        cargarTabla(resultados);
        
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron resultados", 
                "Búsqueda", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void seleccionarFila() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            
            // Buscar y seleccionar autor en ComboBox
            String nombreAutor = modeloTabla.getValueAt(filaSeleccionada, 2).toString();
            for (int i = 0; i < cmbAutor.getItemCount(); i++) {
                if (cmbAutor.getItemAt(i).getNombre().equals(nombreAutor)) {
                    cmbAutor.setSelectedIndex(i);
                    break;
                }
            }
            
            // Buscar y seleccionar categoría en ComboBox
            String nombreCategoria = modeloTabla.getValueAt(filaSeleccionada, 3).toString();
            for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                if (cmbCategoria.getItemAt(i).getNombre().equals(nombreCategoria)) {
                    cmbCategoria.setSelectedIndex(i);
                    break;
                }
            }
            
            spnAnio.setValue(Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString()));
            spnStock.setValue(Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 5).toString()));
            
            modoEdicion = true;
            configurarEstadoBotones(true);
            actualizarBotonFavorito(); // NUEVO
        }
    }
    
    // ===== MÉTODO PARA FAVORITOS =====
    
    /**
     * Marcar/desmarcar libro como favorito
     */
    private void toggleFavorito() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un libro de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = Integer.parseInt(txtId.getText());
        Libro libroActual = libroService.buscarPorId(id);
        
        if (libroActual == null) {
            JOptionPane.showMessageDialog(this, 
                "Libro no encontrado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Invertir estado de favorito
        boolean nuevoEstado = !libroActual.isDestacado();
        String resultado = libroService.marcarComoDestacado(id, nuevoEstado);
        
        if (resultado.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla(libroService.listarTodos());
            
            // Actualizar botón
            libroActual.setDestacado(nuevoEstado);
            actualizarBotonFavorito();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Actualizar texto del botón favorito según el estado
     */
    private void actualizarBotonFavorito() {
        if (txtId.getText().isEmpty()) {
            btnFavorito.setText("⭐ Favorito");
            return;
        }
        
        int id = Integer.parseInt(txtId.getText());
        Libro libro = libroService.buscarPorId(id);
        
        if (libro != null && libro.isDestacado()) {
            btnFavorito.setText("💔 Quitar Favorito");
            btnFavorito.setBackground(new Color(192, 57, 43));
        } else {
            btnFavorito.setText("⭐ Agregar Favorito");
            btnFavorito.setBackground(new Color(243, 156, 18));
        }
    }
}