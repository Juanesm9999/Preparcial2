package co.edu.uniquindio.poo;

public abstract class Contenido {
    protected String id;
    protected String titulo;
    protected int duracionSeg;
    public Contenido(String id, String titulo, int duracionSeg) {
        this.id = id;
        this.titulo = titulo;
        this.duracionSeg = duracionSeg;
    }
    public String getTitulo() { return titulo; }
    public int getDuracionSeg() { return duracionSeg; }
    public abstract void reproducir();
}
