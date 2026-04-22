package co.edu.uniquindio.poo;

public class Podcast extends Contenido {
    private String presentador;
    private String numeroEpisodio;
    public Podcast(String id, String titulo, int duracionSeg, String presentador, String ep)
    {
        super(id, titulo, duracionSeg);
        this.presentador = presentador;
        this.numeroEpisodio = ep;
    }
    @Override
    public void reproducir() {
        System.out.println("Podcast: " + titulo + " ep."  + numeroEpisodio);
    }
}
