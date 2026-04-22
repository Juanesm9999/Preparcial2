package co.edu.uniquindio.poo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

// ----- SINGLETON -----

        ReproductorGlobal global = ReproductorGlobal.getInstancia();
        Usuario david = new Usuario("U001", "David", Usuario.TipoSuscripcion.PREMIUM);
        Usuario juan = new Usuario("U002", ";Juan", Usuario.TipoSuscripcion.FREE);
        global.iniciarSesion(david);

// ----- FACTORY METHOD -------

        ContenidoFactory cancionF = new CancionFactory();
        ContenidoFactory podcastF = new PodcastFactory();
        ContenidoFactory audiolibroF = new AudiolibroFactory();
        Contenido c1 = cancionF.crearContenido("C01", "Talismán", 312, "Rata Blanca","Rock");
        Contenido c2 = cancionF.crearContenido("C02", "Baile Inolvidable", 367, "Bad Bunny", "Salsa");
        Contenido c3 = podcastF.crearContenido("P01", "Modo Taoísmo", 1320, "El Monasterio", "El Monasterio");
        Contenido c4 = audiolibroF.crearContenido("A01", "Hábitos atómicos", 32880,"James Clear", "James Clear");

// ----- COMPOSITE -----

        Playlist favoritas = new Playlist("Favoritas");
        favoritas.agregar(new ElementoContenido(c1));
        favoritas.agregar(new ElementoContenido(c2));
        Playlist aprender = new Playlist("Aprendizaje");
        aprender.agregar(new ElementoContenido(c3));
        aprender.agregar(new ElementoContenido(c4));
        Playlist miSemana = new Playlist("Top 50 México");
        miSemana.agregar(favoritas); // playlist anidada
        miSemana.agregar(aprender); // playlist anidada
        miSemana.mostrar("");
        System.out.println("Duración total: " + miSemana.duracionTotal() + " segundos");
        // ===== DECORATOR + PROXY =====
        IReproductor paraDavid = construirReproductor(david, true, true, false);
        IReproductor paraJuan = construirReproductor(juan, true, false, true);
        System.out.println("\n--- David (PREMIUM) reproduce un audiolibro ---");
        paraDavid.reproducir(c4);
        System.out.println("\n--- Juan (FREE) intenta un audiolibro (bloqueado) ---");
        paraJuan.reproducir(c4);
        System.out.println("\n--- Juan (FREE) escucha una canción (con anuncio) ---");
        paraJuan.reproducir(c1);
    }
    static IReproductor construirReproductor(Usuario u, boolean eq, boolean reverb,
                                             boolean d8) {
        IReproductor r = new ReproductorBase();
        if (eq) r = new EqEfecto(r);
        if (reverb) r = new ReverbEfecto(r);
        if (d8) r = new Efecto8D(r);
        return new ProxyReproductor(r, u);
    }
}