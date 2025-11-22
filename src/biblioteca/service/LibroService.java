package biblioteca.service;

import biblioteca.dao.LibroDAO;
import biblioteca.model.Libro;
import java.util.List;

public class LibroService {
    
    private LibroDAO libroDAO;
    
    public LibroService() {
        this.libroDAO = new LibroDAO();
    }
    
    private String validarLibro(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            return "El título es obligatorio";
        }
        
        if (libro.getTitulo().length() < 3) {
            return "El título debe tener al menos 3 caracteres";
        }
        
        if (libro.getAutorId() <= 0) {
            return "Debe seleccionar un autor";
        }
        
        if (libro.getCategoriaId() <= 0) {
            return "Debe seleccionar una categoría";
        }
        
        if (libro.getAnio() < 1900 || libro.getAnio() > 2100) {
            return "El año debe estar entre 1900 y 2100";
        }
        
        if (libro.getStock() < 0) {
            return "El stock no puede ser negativo";
        }
        
        return null;
    }
    
    public String crear(Libro libro) {
        String errorValidacion = validarLibro(libro);
        if (errorValidacion != null) {
            return errorValidacion;
        }
        
        if (libroDAO.crear(libro)) {
            return "Libro creado exitosamente";
        } else {
            return "Error al crear el libro";
        }
    }
    
    public String actualizar(Libro libro) {
        String errorValidacion = validarLibro(libro);
        if (errorValidacion != null) {
            return errorValidacion;
        }
        
        if (libroDAO.actualizar(libro)) {
            return "Libro actualizado exitosamente";
        } else {
            return "Error al actualizar el libro";
        }
    }
    
    public String eliminar(int id) {
        if (id <= 0) {
            return "ID inválido";
        }
        
        Libro libro = libroDAO.buscarPorId(id);
        if (libro == null) {
            return "Libro no encontrado";
        }
        
        if (libroDAO.eliminar(id)) {
            return "Libro eliminado exitosamente";
        } else {
            return "Error al eliminar el libro";
        }
    }
    
    public List<Libro> listarTodos() {
        return libroDAO.listarTodos();
    }
    
    public List<Libro> buscarPorTitulo(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return listarTodos();
        }
        return libroDAO.buscarPorTitulo(busqueda);
    }
    
    public Libro buscarPorId(int id) {
        return libroDAO.buscarPorId(id);
    }
    
    public int obtenerTotal() {
        return libroDAO.contar();
    }
    
    // ===== MÉTODOS PARA FAVORITOS =====
    
    public String marcarComoDestacado(int id, boolean destacado) {
        if (id <= 0) {
            return "ID inválido";
        }
        
        Libro libro = libroDAO.buscarPorId(id);
        if (libro == null) {
            return "Libro no encontrado";
        }
        
        if (libroDAO.marcarDestacado(id, destacado)) {
            String accion = destacado ? "agregado a" : "quitado de";
            return "Libro " + accion + " favoritos exitosamente";
        } else {
            return "Error al actualizar el estado de favoritos";
        }
    }
    
    public List<Libro> listarDestacados() {
        return libroDAO.listarDestacados();
    }
    
    public int contarDestacados() {
        return libroDAO.contarDestacados();
    }
}