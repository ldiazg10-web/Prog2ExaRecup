package biblioteca.model;

/**
 * Clase modelo para Libro
 */
public class Libro {
    private int id;
    private String titulo;
    private int autorId;
    private String autorNombre; // Para mostrar en tabla
    private int categoriaId;
    private String categoriaNombre; // Para mostrar en tabla
    private int anio;
    private int stock;
    private boolean destacado; // NUEVO: para favoritos
    
    // Constructores
    public Libro() {}
    
    public Libro(int id, String titulo, int autorId, int categoriaId, int anio, int stock) {
        this.id = id;
        this.titulo = titulo;
        this.autorId = autorId;
        this.categoriaId = categoriaId;
        this.anio = anio;
        this.stock = stock;
        this.destacado = false; // Por defecto no destacado
    }
    
    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getTitulo() { 
        return titulo; 
    }
    
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }
    
    public int getAutorId() { 
        return autorId; 
    }
    
    public void setAutorId(int autorId) { 
        this.autorId = autorId; 
    }
    
    public String getAutorNombre() { 
        return autorNombre; 
    }
    
    public void setAutorNombre(String autorNombre) { 
        this.autorNombre = autorNombre; 
    }
    
    public int getCategoriaId() { 
        return categoriaId; 
    }
    
    public void setCategoriaId(int categoriaId) { 
        this.categoriaId = categoriaId; 
    }
    
    public String getCategoriaNombre() { 
        return categoriaNombre; 
    }
    
    public void setCategoriaNombre(String categoriaNombre) { 
        this.categoriaNombre = categoriaNombre; 
    }
    
    public int getAnio() { 
        return anio; 
    }
    
    public void setAnio(int anio) { 
        this.anio = anio; 
    }
    
    public int getStock() { 
        return stock; 
    }
    
    public void setStock(int stock) { 
        this.stock = stock; 
    }
    
    // NUEVOS: Getters y Setters para destacado
    public boolean isDestacado() { 
        return destacado; 
    }
    
    public void setDestacado(boolean destacado) { 
        this.destacado = destacado; 
    }
}