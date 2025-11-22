package biblioteca.dao;

import biblioteca.config.DatabaseConfig;
import biblioteca.model.Usuario;
import java.sql.*;

/**
 * DAO para la entidad Usuario
 * Gestiona autenticación y operaciones de usuario
 */
public class UsuarioDAO {
    
    private Connection connection;
    
    public UsuarioDAO() {
        this.connection = DatabaseConfig.getConnection();
    }
    
    /**
     * Autenticar usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return objeto Usuario si las credenciales son correctas, null en caso contrario
     */
    public Usuario autenticar(String username, String password) {
        String sql = "SELECT id, username, password_hash, rol, estado FROM usuarios " +
                     "WHERE username = ? AND password_hash = ? AND estado = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // En producción, usar hash (BCrypt, SHA-256, etc.)
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPasswordHash(rs.getString("password_hash"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getBoolean("estado"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Buscar usuario por username
     * @param username nombre de usuario
     * @return objeto Usuario o null
     */
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT id, username, password_hash, rol, estado FROM usuarios WHERE username = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPasswordHash(rs.getString("password_hash"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getBoolean("estado"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Verificar si existe un usuario
     * @param username nombre de usuario
     * @return true si existe
     */
    public boolean existeUsuario(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}