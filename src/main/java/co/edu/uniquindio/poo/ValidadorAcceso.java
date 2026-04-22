package co.edu.uniquindio.poo;

public class ValidadorAcceso {
    public static boolean puedeReproducir(Usuario u, Contenido c) {
        if (u == null) return false;
        if (u.esPremium()) return true;
        if (c instanceof Audiolibro) return false;
        return true;
    }
}
