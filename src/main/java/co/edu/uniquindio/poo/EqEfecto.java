package co.edu.uniquindio.poo;

public class EqEfecto extends EfectoDecorator {
    public EqEfecto(IReproductor envuelto) { super(envuelto); }
    @Override
    public void reproducir(Contenido c) {
        super.reproducir(c);
        System.out.println("Ecualizador aplicado");
    }
}
