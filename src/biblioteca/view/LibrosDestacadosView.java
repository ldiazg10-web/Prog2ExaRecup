package biblioteca.view;

import biblioteca.model.Libro;
import biblioteca.service.LibroService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Vista para mostrar Libros Destacados/Favoritos
 */
public class LibrosDestacadosView extends JFrame {
    
    // Componentes
    private JTable tablaDestacados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private JButton btnQuitarFavorito;
    private JButton btnActualizar;
    private JButton btnCerrar;
    private JLabel lblTotal;
    
    // Service
    private LibroService libroService;
    
    public LibrosDestacadosView() {
        libroService = new LibroService();
        initComponents();
        cargarTabla();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Mis Libros Destacados / Favoritos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        
        // ===== PANEL SUPERIOR - TÍTULO =====
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(243, 156, 18));
        panelTitulo.setPreferredSize(new Dimension(900, 60));
        
        JLabel lblTitulo = new JLabel("⭐ MIS LIBROS FAVORITOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // ===== PANEL CENTRAL - TABLA =====
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.setBackground(Color.WHITE);
        
        // Info superior
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelInfo.setBackground(new Color(255, 243, 224));
        panelInfo.setBorder(BorderFactory.createLineBorder(new Color(243, 156, 18), 2));
        
        JLabel lblInfoIcon = new JLabel("ℹ️");
        lblInfoIcon.setFont(new Font("Arial", Font.BOLD, 18));
        panelInfo.add(lblInfoIcon);
        
        JLabel lblInfo = new JLabel("Estos son tus libros marcados como favoritos. Puedes quitar un libro de esta lista seleccionándolo.");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInfo.setForeground(Color.BLACK);
        panelInfo.add(lblInfo);
        
        // Tabla
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Año", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaDestacados = new JTable(modeloTabla);
        tablaDestacados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDestacados.getTableHeader().setReorderingAllowed(false);
        tablaDestacados.setRowHeight(30);
        tablaDestacados.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaDestacados.setBackground(Color.WHITE);
        tablaDestacados.setForeground(Color.BLACK);
        tablaDestacados.setSelectionBackground(new Color(255, 235, 205));
        tablaDestacados.setSelectionForeground(Color.BLACK);
        
        scrollPane = new JScrollPane(tablaDestacados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Libros Destacados"));
        
        panelCentral.add(panelInfo, BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        // ===== PANEL INFERIOR - BOTONES Y ESTADÍSTICAS =====
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.WHITE);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnQuitarFavorito = crearBoton("💔 Quitar de Favoritos", new Color(231, 76, 60));
        btnQuitarFavorito.addActionListener(e -> quitarDeFavoritos());
        
        btnActualizar = crearBoton("🔄 Actualizar Lista", new Color(52, 152, 219));
        btnActualizar.addActionListener(e -> cargarTabla());
        
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnQuitarFavorito);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);
        
        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelEstadisticas.setBackground(Color.WHITE);
        
        lblTotal = new JLabel("Total de favoritos: 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(243, 156, 18));
        panelEstadisticas.add(lblTotal);
        
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(panelEstadisticas, BorderLayout.SOUTH);
        
        // ===== AGREGAR PANELES AL FRAME =====
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, Color fondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setPreferredSize(new Dimension(200, 40));
        boton.setBorderPainted(true);
        boton.setOpaque(true);
        return boton;
    }
    
    /**
     * Cargar libros destacados en la tabla
     */
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        List<Libro> librosDestacados = libroService.listarDestacados();
        
        for (Libro libro : librosDestacados) {
            modeloTabla.addRow(new Object[]{
                libro.getId(),
                libro.getTitulo(),
                libro.getAutorNombre(),
                libro.getCategoriaNombre(),
                libro.getAnio(),
                libro.getStock()
            });
        }
        
        // Actualizar contador
        int total = libroService.contarDestacados();
        lblTotal.setText("Total de favoritos: " + total);
        
        // Mensaje si no hay favoritos
        if (total == 0) {
            JOptionPane.showMessageDialog(this,
                "No tienes libros marcados como favoritos.\n" +
                "Ve al módulo de Libros y marca algunos libros con la estrella ⭐",
                "Sin Favoritos",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Quitar libro de favoritos
     */
    private void quitarDeFavoritos() {
        int filaSeleccionada = tablaDestacados.getSelectedRow();
        
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un libro de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String titulo = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Desea quitar \"" + titulo + "\" de favoritos?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String resultado = libroService.marcarComoDestacado(id, false);
            
            if (resultado.contains("exitosamente")) {
                JOptionPane.showMessageDialog(this,
                    "Libro quitado de favoritos",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarTabla(); // Recargar tabla
            } else {
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}