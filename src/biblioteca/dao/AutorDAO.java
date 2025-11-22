package biblioteca.dao;

import biblioteca.config.DatabaseConfig;
import biblioteca.model.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Autor
 * Implementa operaciones CRUD usando PreparedStatement
 */
public class AutorDAO {
    
    private Connection connection;
    
    public AutorDAO() {
        this.connection = DatabaseConfig.getConnection();
    }
    
    /**
     * Crear un nuevo autor
     * @param autor objeto Autor a insertar
     * @return true si se insertó correctamente
     */
    public boolean crear(Autor autor) {
        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        autor.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear autor: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Leer todos los autores
     * @return Lista de autores
     */
    public List<Autor> listarTodos() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT id, nombre, nacionalidad FROM autores ORDER BY nombre";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Autor autor = new Autor();
                autor.setId(rs.getInt("id"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                autores.add(autor);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar autores: " + e.getMessage());
            e.printStackTrace();
        }
        return autores;
    }
    
    /**
     * Buscar autor por ID
     * @param id identificador del autor
     * @return objeto Autor o null si no existe
     */
    public Autor buscarPorId(int id) {
        String sql = "SELECT id, nombre, nacionalidad FROM autores WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Autor autor = new Autor();
                    autor.setId(rs.getInt("id"));
                    autor.setNombre(rs.getString("nombre"));
                    autor.setNacionalidad(rs.getString("nacionalidad"));
                    return autor;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar autor: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Buscar autores por nombre
     * @param busqueda texto a buscar en el nombre
     * @return Lista de autores que coinciden
     */
    public List<Autor> buscarPorNombre(String busqueda) {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT id, nombre, nacionalidad FROM autores WHERE nombre LIKE ? ORDER BY nombre";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + busqueda + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Autor autor = new Autor();
                    autor.setId(rs.getInt("id"));
                    autor.setNombre(rs.getString("nombre"));
                    autor.setNacionalidad(rs.getString("nacionalidad"));
                    autores.add(autor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar autores: " + e.getMessage());
            e.printStackTrace();
        }
        return autores;
    }
    
    /**
     * Actualizar un autor existente
     * @param autor objeto Autor con los nuevos datos
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Autor autor) {
        String sql = "UPDATE autores SET nombre = ?, nacionalidad = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.setInt(3, autor.getId());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar autor: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Eliminar un autor
     * @param id identificador del autor
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM autores WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar autor: " + e.getMessage());
            // Si hay error de FK, puede ser porque hay libros asociados
            if (e.getMessage().contains("foreign key constraint")) {
                System.err.println("No se puede eliminar: existen libros de este autor");
            }
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Verificar si existe un autor con el mismo nombre
     * @param nombre nombre del autor
     * @param idExcluir ID a excluir de la búsqueda (para actualizar)
     * @return true si existe
     */
    public boolean existeNombre(String nombre, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM autores WHERE nombre = ? AND id != ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idExcluir);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Contar total de autores
     * @return cantidad de autores
     */
    public int contar() {
        String sql = "SELECT COUNT(*) FROM autores";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar autores: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}