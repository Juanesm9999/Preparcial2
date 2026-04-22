package co.edu.uniquindio.poo;

public class ReproductorBase implements IReproductor {
    @Override
    public void reproducir(Contenido c) {
        System.out.println("Reproduciendo: " + c.getTitulo());
        c.reproducir();
    }
}
