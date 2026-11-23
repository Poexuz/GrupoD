package app.Modelos;

public class Libro {
    
    private final String id;
    private final String titulo;
    private final int copiasTotales;
    private int copiasDisponibles;

    public Libro(String id, String titulo, int copias) {
        this.id = id;
        this.titulo = titulo;
        this.copiasTotales = copias;
        this.copiasDisponibles = copias;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getCopiasTotales() { return copiasTotales; }
    public int getCopiasDisponibles() { return copiasDisponibles; }

    public boolean tieneDisponibles() {
        return copiasDisponibles > 0;
    }

    public void prestarCopia() {
        copiasDisponibles--;
    }

    public void devolverCopia() {
        if (copiasDisponibles < copiasTotales) {
            copiasDisponibles++;
        }
    }
}