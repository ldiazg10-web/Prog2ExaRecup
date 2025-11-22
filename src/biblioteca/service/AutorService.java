package biblioteca.service;

import biblioteca.dao.AutorDAO;
import biblioteca.model.Autor;
import java.util.List;

/**
 * Capa de servicio para Autor
 * Contiene la lógica de negocio y validaciones
 */
public class AutorService {
    
    private AutorDAO autorDAO;
    
    public AutorService() {
        this.autorDAO = new AutorDAO();
    }
    
    /**
     * Validar datos del autor
     * @param autor objeto a validar
     * @return mensaje de error o null si es válido
     */
    private String validarAutor(Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            return "El nombre del autor es obligatorio";
        }
        
        if (autor.getNombre().length() < 3) {
            return "El nombre debe tener al menos 3 caracteres";
        }
        
        if (autor.getNombre().length() > 100) {
            return "El nombre no puede exceder 100 caracteres";
        }
        
        if (autor.getNacionalidad() != null && autor.getNacionalidad().length() > 50) {
            return "La nacionalidad no puede exceder 50 caracteres";
        }
        
        return null; // Válido
    }
    
    /**
     * Crear un nuevo autor con validaciones
     * @param autor objeto Autor
     * @return mensaje de resultado
     */
    public String crear(Autor autor) {
        // Validar datos
        String errorValidacion = validarAutor(autor);
        if (errorValidacion != null) {
            return errorValidacion;
        }
        
        // Verificar si ya existe
        if (autorDAO.existeNombre(autor.getNombre(), 0)) {
            return "Ya existe un autor con ese nombre";
        }
        
        // Intentar crear
        if (autorDAO.crear(autor)) {
            return "Autor creado exitosamente";
        } else {
            return "Error al crear el autor";
        }
    }
    
    /**
     * Actualizar autor con validaciones
     * @param autor objeto Autor
     * @return mensaje de resultado
     */
    public String actualizar(Autor autor) {
        // Validar datos
        String errorValidacion = validarAutor(autor);
        if (errorValidacion != null) {
            return errorValidacion;
        }
        
        // Verificar si ya existe otro con el mismo nombre
        if (autorDAO.existeNombre(autor.getNombre(), autor.getId())) {
            return "Ya existe otro autor con ese nombre";
        }
        
        // Intentar actualizar
        if (autorDAO.actualizar(autor)) {
            return "Autor actualizado exitosamente";
        } else {
            return "Error al actualizar el autor";
        }
    }
    
    /**
     * Eliminar autor con validaciones
     * @param id identificador del autor
     * @return mensaje de resultado
     */
    public String eliminar(int id) {
        if (id <= 0) {
            return "ID inválido";
        }
        
        // Verificar si existe
        Autor autor = autorDAO.buscarPorId(id);
        if (autor == null) {
            return "Autor no encontrado";
        }
        
        // Intentar eliminar
        if (autorDAO.eliminar(id)) {
            return "Autor eliminado exitosamente";
        } else {
            return "No se puede eliminar: existen libros de este autor";
        }
    }
    
    /**
     * Listar todos los autores
     * @return lista de autores
     */
    public List<Autor> listarTodos() {
        return autorDAO.listarTodos();
    }
    
    /**
     * Buscar autores por nombre
     * @param busqueda texto a buscar
     * @return lista de autores
     */
    public List<Autor> buscarPorNombre(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return listarTodos();
        }
        return autorDAO.buscarPorNombre(busqueda);
    }
    
    /**
     * Buscar autor por ID
     * @param id identificador
     * @return objeto Autor o null
     */
    public Autor buscarPorId(int id) {
        return autorDAO.buscarPorId(id);
    }
    
    /**
     * Obtener estadísticas
     * @return total de autores
     */
    public int obtenerTotal() {
        return autorDAO.contar();
    }
}