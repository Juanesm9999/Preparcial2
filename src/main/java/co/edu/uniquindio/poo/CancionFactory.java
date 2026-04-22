package co.edu.uniquindio.poo;

public class CancionFactory extends ContenidoFactory {
    @Override
    public Contenido crearContenido(String id, String titulo, int duracionSeg,
                                    String artista, String album) {
        return new Cancion(id, titulo, duracionSeg, artista, album);
    }
}