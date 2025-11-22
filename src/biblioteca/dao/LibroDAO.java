package biblioteca.dao;

import biblioteca.config.DatabaseConfig;
import biblioteca.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Libro
 */
public class LibroDAO {
    private Connection connection;
    
    public LibroDAO() {
        this.connection = DatabaseConfig.getConnection();
    }
    
    public boolean crear(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor_id, categoria_id, anio, stock, destacado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getAutorId());
            ps.setInt(3, libro.getCategoriaId());
            ps.setInt(4, libro.getAnio());
            ps.setInt(5, libro.getStock());
            ps.setBoolean(6, libro.isDestacado());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        libro.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear libro: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Libro> listarTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.id, l.titulo, l.anio, l.stock, l.destacado, " +
                     "l.autor_id, a.nombre AS autor_nombre, " +
                     "l.categoria_id, c.nombre AS categoria_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "INNER JOIN categorias c ON l.categoria_id = c.id " +
                     "ORDER BY l.titulo";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAnio(rs.getInt("anio"));
                libro.setStock(rs.getInt("stock"));
                libro.setDestacado(rs.getBoolean("destacado"));
                libro.setAutorId(rs.getInt("autor_id"));
                libro.setAutorNombre(rs.getString("autor_nombre"));
                libro.setCategoriaId(rs.getInt("categoria_id"));
                libro.setCategoriaNombre(rs.getString("categoria_nombre"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
            e.printStackTrace();
        }
        return libros;
    }
    
    public Libro buscarPorId(int id) {
        String sql = "SELECT l.id, l.titulo, l.anio, l.stock, " +
                     "l.autor_id, a.nombre AS autor_nombre, " +
                     "l.categoria_id, c.nombre AS categoria_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "INNER JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setAnio(rs.getInt("anio"));
                    libro.setStock(rs.getInt("stock"));
                    libro.setAutorId(rs.getInt("autor_id"));
                    libro.setAutorNombre(rs.getString("autor_nombre"));
                    libro.setCategoriaId(rs.getInt("categoria_id"));
                    libro.setCategoriaNombre(rs.getString("categoria_nombre"));
                    return libro;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libro: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Libro> buscarPorTitulo(String busqueda) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.id, l.titulo, l.anio, l.stock, " +
                     "l.autor_id, a.nombre AS autor_nombre, " +
                     "l.categoria_id, c.nombre AS categoria_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "INNER JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.titulo LIKE ? OR a.nombre LIKE ? " +
                     "ORDER BY l.titulo";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String patron = "%" + busqueda + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setAnio(rs.getInt("anio"));
                    libro.setStock(rs.getInt("stock"));
                    libro.setAutorId(rs.getInt("autor_id"));
                    libro.setAutorNombre(rs.getString("autor_nombre"));
                    libro.setCategoriaId(rs.getInt("categoria_id"));
                    libro.setCategoriaNombre(rs.getString("categoria_nombre"));
                    libros.add(libro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libros: " + e.getMessage());
            e.printStackTrace();
        }
        return libros;
    }
    
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor_id = ?, categoria_id = ?, anio = ?, stock = ?, destacado = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getAutorId());
            ps.setInt(3, libro.getCategoriaId());
            ps.setInt(4, libro.getAnio());
            ps.setInt(5, libro.getStock());
            ps.setBoolean(6, libro.isDestacado());
            ps.setInt(7, libro.getId());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Libro> listarPorAutor(int autorId) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.id, l.titulo, l.anio, l.stock, " +
                     "l.autor_id, a.nombre AS autor_nombre, " +
                     "l.categoria_id, c.nombre AS categoria_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "INNER JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.autor_id = ? " +
                     "ORDER BY l.titulo";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, autorId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setAnio(rs.getInt("anio"));
                    libro.setStock(rs.getInt("stock"));
                    libro.setAutorId(rs.getInt("autor_id"));
                    libro.setAutorNombre(rs.getString("autor_nombre"));
                    libro.setCategoriaId(rs.getInt("categoria_id"));
                    libro.setCategoriaNombre(rs.getString("categoria_nombre"));
                    libros.add(libro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros por autor: " + e.getMessage());
            e.printStackTrace();
        }
        return libros;
    }
    
    public int contar() {
        String sql = "SELECT COUNT(*) FROM libros";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar libros: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    // ===== MÉTODOS PARA FAVORITOS =====
    
    /**
     * Marcar o desmarcar un libro como destacado
     */
    public boolean marcarDestacado(int id, boolean destacado) {
        String sql = "UPDATE libros SET destacado = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, destacado);
            ps.setInt(2, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar libro como destacado: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Listar solo libros destacados/favoritos
     */
    public List<Libro> listarDestacados() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.id, l.titulo, l.anio, l.stock, l.destacado, " +
                     "l.autor_id, a.nombre AS autor_nombre, " +
                     "l.categoria_id, c.nombre AS categoria_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "INNER JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.destacado = TRUE " +
                     "ORDER BY l.titulo";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAnio(rs.getInt("anio"));
                libro.setStock(rs.getInt("stock"));
                libro.setDestacado(rs.getBoolean("destacado"));
                libro.setAutorId(rs.getInt("autor_id"));
                libro.setAutorNombre(rs.getString("autor_nombre"));
                libro.setCategoriaId(rs.getInt("categoria_id"));
                libro.setCategoriaNombre(rs.getString("categoria_nombre"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros destacados: " + e.getMessage());
            e.printStackTrace();
        }
        return libros;
    }
    
    /**
     * Contar libros destacados
     */
    public int contarDestacados() {
        String sql = "SELECT COUNT(*) FROM libros WHERE destacado = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar libros destacados: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}