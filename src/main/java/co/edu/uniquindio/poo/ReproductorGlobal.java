package co.edu.uniquindio.poo;

public class ReproductorGlobal {
    // 1. Instancia única estática
    private static ReproductorGlobal instancia;
    private Usuario usuarioActivo;
    private Contenido contenidoActual;
    // 2. Constructor privado
    private ReproductorGlobal() { }
    // 3. Punto de acceso global
    public static ReproductorGlobal getInstancia() {
        if (instancia == null) {
            instancia = new ReproductorGlobal();
        }
        return instancia;
    }
    public void iniciarSesion(Usuario u) {
        this.usuarioActivo = u;
        System.out.println("Sesión iniciada: "+ u.getNombre() +
                "(" + u.getSuscripcion() + ")");
    }
    public Usuario getUsuarioActivo() { return usuarioActivo; }
    public void reproducir(Contenido c) {
        this.contenidoActual = c;
        c.reproducir();
    }
}
