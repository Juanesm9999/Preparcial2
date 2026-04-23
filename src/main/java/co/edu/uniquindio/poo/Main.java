package co.edu.uniquindio.poo;

public class Main {

    static final String LINEA  = "=".repeat(50);
    static final String GUION  = "-".repeat(50);

    public static void main(String[] args) {

        // ── SINGLETON ────────────────────────────────────
        imprimir("SINGLETON - Reproductor Global");

        ReproductorGlobal global = ReproductorGlobal.getInstancia();
        Usuario david = new Usuario("U001", "David", Usuario.TipoSuscripcion.PREMIUM);
        Usuario juan  = new Usuario("U002", "Juan",  Usuario.TipoSuscripcion.FREE);
        global.iniciarSesion(david);

        // ── FACTORY METHOD ────────────────────────────────
        imprimir("FACTORY METHOD - Creacion de contenido");

        ContenidoFactory cancionF    = new CancionFactory();
        ContenidoFactory podcastF    = new PodcastFactory();
        ContenidoFactory audiolibroF = new AudiolibroFactory();

        Contenido c1 = cancionF.crearContenido(   "C01", "Talismán",        312,   "Rata Blanca",  "Rock");
        Contenido c2 = cancionF.crearContenido(   "C02", "Baile Inolvidable",367,   "Bad Bunny",    "Salsa");
        Contenido c3 = podcastF.crearContenido(   "P01", "Modo Taoísmo",    1320,  "El Monasterio","El Monasterio");
        Contenido c4 = audiolibroF.crearContenido("A01", "Hábitos Atómicos", 32880, "James Clear",  "James Clear");

        System.out.println("Contenidos creados:");
        System.out.println("  [Cancion]    " + c1.getTitulo() + " (" + c1.getDuracionSeg() + "s)");
        System.out.println("  [Cancion]    " + c2.getTitulo() + " (" + c2.getDuracionSeg() + "s)");
        System.out.println("  [Podcast]    " + c3.getTitulo() + " (" + c3.getDuracionSeg() + "s)");
        System.out.println("  [Audiolibro] " + c4.getTitulo() + " (" + c4.getDuracionSeg() + "s)");

        // ── COMPOSITE ────────────────────────────────────
        imprimir("COMPOSITE - Playlists anidadas");

        Playlist favoritas = new Playlist("Favoritas");
        favoritas.agregar(new ElementoContenido(c1));
        favoritas.agregar(new ElementoContenido(c2));

        Playlist aprender = new Playlist("Aprendizaje");
        aprender.agregar(new ElementoContenido(c3));
        aprender.agregar(new ElementoContenido(c4));

        Playlist miSemana = new Playlist("Top 50 Mexico");
        miSemana.agregar(favoritas);
        miSemana.agregar(aprender);

        miSemana.mostrar("");
        System.out.println(GUION);
        System.out.println("Duracion total: " + Playlist.formatearDuracion(miSemana.duracionTotal()));

        // ── DECORATOR + PROXY ─────────────────────────────
        imprimir("DECORATOR + PROXY - Control de acceso y efectos");

        IReproductor paraDavid = construirReproductor(david, true,  true,  false);
        IReproductor paraJuan  = construirReproductor(juan,  true,  false, true);

        seccion("David (PREMIUM) reproduce un audiolibro");
        paraDavid.reproducir(c4);

        seccion("Juan (FREE) intenta reproducir un audiolibro");
        paraJuan.reproducir(c4);

        seccion("Juan (FREE) escucha una cancion");
        paraJuan.reproducir(c1);

        System.out.println(LINEA);
    }

    static IReproductor construirReproductor(Usuario u, boolean eq,
                                             boolean reverb, boolean d8) {
        IReproductor r = new ReproductorBase();
        if (eq)    r = new EqEfecto(r);
        if (reverb) r = new ReverbEfecto(r);
        if (d8)    r = new Efecto8D(r);
        return new ProxyReproductor(r, u);
    }

    static void imprimir(String titulo) {
        System.out.println();
        System.out.println(LINEA);
        System.out.println("  " + titulo);
        System.out.println(LINEA);
    }

    static void seccion(String texto) {
        System.out.println();
        System.out.println(GUION);
        System.out.println("  " + texto);
        System.out.println(GUION);
    }
}