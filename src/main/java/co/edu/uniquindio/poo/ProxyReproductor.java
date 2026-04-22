package co.edu.uniquindio.poo;

public class ProxyReproductor implements IReproductor {
    private IReproductor reproductorReal;
    private Usuario usuario;
    private int saltosRealizados = 0;
    private static final int MAX_SALTOS_FREE = 3;
    public ProxyReproductor(IReproductor reproductorReal, Usuario usuario) {
        this.reproductorReal = reproductorReal;
        this.usuario = usuario;
    }
    @Override
    public void reproducir(Contenido c) {
        if (usuario == null) {
            System.out.println(" No hay sesión activa.");
            return;
        }
// Usuario premium: acceso total, sin restricciones
        if (usuario.esPremium()) {
            reproductorReal.reproducir(c);
            return;
        }
// Usuario free: insertar publicidad antes
        System.out.println(" [Anuncio] Suscríbete a Premium para evitar anuncios");
// Restricción: audiolibros solo para premium
        if (c instanceof Audiolibro) {
            System.out.println(" Los audiolibros son exclusivos para Premium.");
            return;
        }
        reproductorReal.reproducir(c);
    }
    public boolean puedeSaltar() {
        if (usuario.esPremium()) return true;
        return saltosRealizados <  MAX_SALTOS_FREE;
    }
    public void saltar() {
        if (puedeSaltar()) {
        saltosRealizados++;
        System.out.println("Salto realizado (" + saltosRealizados + ")");
    } else {
        System.out.println(" Límite de saltos alcanzado. Actualiza a Premium.");
    }
    }
}
