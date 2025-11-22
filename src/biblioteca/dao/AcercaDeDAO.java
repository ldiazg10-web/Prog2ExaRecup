package biblioteca.dao;

import biblioteca.config.DatabaseConfig;
import biblioteca.model.AcercaDe;
import java.sql.*;

/**
 * DAO para la entidad AcercaDe
 */
public class AcercaDeDAO {
    private Connection connection;
    
    public AcercaDeDAO() {
        this.connection = DatabaseConfig.getConnection();
    }
    
    public AcercaDe obtenerInfo() {
        String sql = "SELECT id, carne, nombres, numero_carne, foto_path, proyecto, version, fecha " +
                     "FROM acerca_de LIMIT 1";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                AcercaDe info = new AcercaDe();
                info.setId(rs.getInt("id"));
                info.setCarne(rs.getString("carne"));
                info.setNombres(rs.getString("nombres"));
                info.setNumeroCarne(rs.getString("numero_carne"));
                info.setFotoPath(rs.getString("foto_path"));
                info.setProyecto(rs.getString("proyecto"));
                info.setVersion(rs.getString("version"));
                info.setFecha(rs.getString("fecha"));
                return info;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener información: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean actualizar(AcercaDe info) {
        String sql = "UPDATE acerca_de SET carne = ?, nombres = ?, numero_carne = ?, " +
                     "foto_path = ?, proyecto = ?, version = ?, fecha = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, info.getCarne());
            ps.setString(2, info.getNombres());
            ps.setString(3, info.getNumeroCarne());
            ps.setString(4, info.getFotoPath());
            ps.setString(5, info.getProyecto());
            ps.setString(6, info.getVersion());
            ps.setString(7, info.getFecha());
            ps.setInt(8, info.getId());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar información: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean actualizarFoto(int id, String fotoPath) {
        String sql = "UPDATE acerca_de SET foto_path = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fotoPath);
            ps.setInt(2, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar foto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}