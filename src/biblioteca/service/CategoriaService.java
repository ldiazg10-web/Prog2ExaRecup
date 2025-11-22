package biblioteca.service;

import biblioteca.dao.CategoriaDAO;
import biblioteca.model.Categoria;
import java.util.List;


public class CategoriaService {
    
    private CategoriaDAO categoriaDAO;
    
    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }
    
    private String validarCategoria(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            return "El nombre de la categoría es obligatorio";
        }
        
        if (categoria.getNombre().length() < 3) {
            return "El nombre debe tener al menos 3 caracteres";
        }
        
        if (categoria.getNombre().length() > 50) {
            return "El nombre no puede exceder 50 caracteres";
        }
        
        if (categoria.getDescripcion() != null && categoria.getDescripcion().length() > 200) {
            return "La descripción no puede exceder 200 caracteres";
        }
        
        return null;
    }
    
    public String crear(Categoria categoria) {
        String errorValidacion = validarCategoria(categoria);
        if (errorValidacion != null) {
            return errorValidacion;
        }
        
        if (categoriaDAO.existeNombre(categoria.getNombre(), 0)) {
            return "Ya existe una categoría con ese nombre";
        }
        
        if (categoriaDAO.crear(categoria)) {
            return "Categoría creada exitosamente";
        } else {
            return "Error al crear la categoría";
        }
    }
    
    public String actualizar(Categoria categoria) {
        String errorValidacion = validarCategoria(categoria);
        if (errorValidacion != null) {
            return errorValidacion;
        }
        
        if (categoriaDAO.existeNombre(categoria.getNombre(), categoria.getId())) {
            return "Ya existe otra categoría con ese nombre";
        }
        
        if (categoriaDAO.actualizar(categoria)) {
            return "Categoría actualizada exitosamente";
        } else {
            return "Error al actualizar la categoría";
        }
    }
    
    public String eliminar(int id) {
        if (id <= 0) {
            return "ID inválido";
        }
        
        Categoria categoria = categoriaDAO.buscarPorId(id);
        if (categoria == null) {
            return "Categoría no encontrada";
        }
        
        if (categoriaDAO.eliminar(id)) {
            return "Categoría eliminada exitosamente";
        } else {
            return "No se puede eliminar: existen libros en esta categoría";
        }
    }
    
    public List<Categoria> listarTodos() {
        return categoriaDAO.listarTodos();
    }
    
    public List<Categoria> buscarPorNombre(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return listarTodos();
        }
        return categoriaDAO.buscarPorNombre(busqueda);
    }
    
    public Categoria buscarPorId(int id) {
        return categoriaDAO.buscarPorId(id);
    }
    
    public int obtenerTotal() {
        return categoriaDAO.contar();
    }
}