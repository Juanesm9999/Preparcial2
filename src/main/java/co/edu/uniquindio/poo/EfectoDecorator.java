package co.edu.uniquindio.poo;

public abstract class EfectoDecorator implements IReproductor {
    protected IReproductor envuelto; // composición: referencia al objeto decorado
    public EfectoDecorator(IReproductor envuelto) {
        this.envuelto = envuelto;
    }
    @Override
    public void reproducir(Contenido c) {
        envuelto.reproducir(c); // delegación
    }
}
