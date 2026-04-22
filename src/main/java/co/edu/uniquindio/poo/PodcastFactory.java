package co.edu.uniquindio.poo;

public class PodcastFactory extends ContenidoFactory {
    @Override
    public Contenido crearContenido(String id, String titulo, int duracionSeg,
                                    String presentador, String numEp) {
        return new Podcast(id, titulo, duracionSeg, presentador, numEp);
    }
}
