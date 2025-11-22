package biblioteca.model;

public class Usuario {
    private int id;
    private String username;
    private String passwordHash;
    private String rol;
    private boolean estado;
    
    // Constructores
    public Usuario() {}
    
    public Usuario(int id, String username, String passwordHash, String rol, boolean estado) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.estado = estado;
    }
    
    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getPasswordHash() { 
        return passwordHash; 
    }
    
    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    }
    
    public String getRol() { 
        return rol; 
    }
    
    public void setRol(String rol) { 
        this.rol = rol; 
    }
    
    public boolean isEstado() { 
        return estado; 
    }
    
    public void setEstado(boolean estado) { 
        this.estado = estado; 
    }
}