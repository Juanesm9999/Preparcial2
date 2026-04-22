package co.edu.uniquindio.poo;

public class Audiolibro extends Contenido {
    private String autor;
    private String narrador;
    public Audiolibro(String id, String titulo, int duracionSeg, String autor, String
            narrador) {
        super(id, titulo, duracionSeg);
        this.autor = autor;
        this.narrador = narrador;
    }
    @Override
    public void reproducir() {
        System.out.println("Audiolibro: " + titulo + " de " + autor);
    }
}
