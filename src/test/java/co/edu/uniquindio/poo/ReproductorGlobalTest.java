package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ReproductorGlobalTest {

    private ByteArrayOutputStream salida;

    @BeforeEach
    void capturarSalida() {
        salida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salida));
    }

    // ────────────────────────────────────────────────
    // SINGLETON
    // ────────────────────────────────────────────────

    @Test
    void singleton_mismaInstancia() {
        ReproductorGlobal a = ReproductorGlobal.getInstancia();
        ReproductorGlobal b = ReproductorGlobal.getInstancia();
        assertSame(a, b, "Debe retornarse siempre la misma instancia");
    }

    @Test
    void singleton_sesionGuardaUsuario() {
        ReproductorGlobal global = ReproductorGlobal.getInstancia();
        Usuario u = new Usuario("U99", "Test", Usuario.TipoSuscripcion.PREMIUM);
        global.iniciarSesion(u);
        assertEquals("Test", global.getUsuarioActivo().getNombre());
    }

    // ────────────────────────────────────────────────
    // FACTORY METHOD
    // ────────────────────────────────────────────────

    @Test
    void factory_cancionCreaInstanciaCorrecta() {
        Contenido c = new CancionFactory()
                .crearContenido("C1", "Tema", 180, "Artista", "Album");
        assertInstanceOf(Cancion.class, c);
        assertEquals("Tema", c.getTitulo());
        assertEquals(180, c.getDuracionSeg());
    }

    @Test
    void factory_podcastCreaInstanciaCorrecta() {
        Contenido p = new PodcastFactory()
                .crearContenido("P1", "Episodio", 600, "Host", "1");
        assertInstanceOf(Podcast.class, p);
    }

    @Test
    void factory_audioCreaInstanciaCorrecta() {
        Contenido a = new AudiolibroFactory()
                .crearContenido("A1", "Libro", 3600, "Autor", "Narrador");
        assertInstanceOf(Audiolibro.class, a);
    }

    // ────────────────────────────────────────────────
    // COMPOSITE
    // ────────────────────────────────────────────────

    @Test
    void composite_duracionTotalSumaHijos() {
        Contenido c1 = new CancionFactory().crearContenido("C1", "A", 120, "X", "Y");
        Contenido c2 = new CancionFactory().crearContenido("C2", "B", 180, "X", "Y");
        Playlist pl = new Playlist("Mix");
        pl.agregar(new ElementoContenido(c1));
        pl.agregar(new ElementoContenido(c2));
        assertEquals(300, pl.duracionTotal());
    }

    @Test
    void composite_playlistAnidadaSumaDuraciones() {
        Contenido c1 = new CancionFactory().crearContenido("C1", "A", 100, "X", "Y");
        Contenido c2 = new CancionFactory().crearContenido("C2", "B", 200, "X", "Y");
        Playlist sub = new Playlist("Sub");
        sub.agregar(new ElementoContenido(c1));
        Playlist padre = new Playlist("Padre");
        padre.agregar(sub);
        padre.agregar(new ElementoContenido(c2));
        assertEquals(300, padre.duracionTotal());
    }

    // ────────────────────────────────────────────────
    // DECORATOR
    // ────────────────────────────────────────────────

    @Test
    void decorator_eqMuestraMensaje() {
        Contenido c = new CancionFactory().crearContenido("C1", "X", 60, "A", "B");
        IReproductor r = new EqEfecto(new ReproductorBase());
        r.reproducir(c);
        assertTrue(salida.toString().contains("Ecualizador aplicado"));
    }

    @Test
    void decorator_reverbMuestraMensaje() {
        Contenido c = new CancionFactory().crearContenido("C1", "X", 60, "A", "B");
        IReproductor r = new ReverbEfecto(new ReproductorBase());
        r.reproducir(c);
        assertTrue(salida.toString().contains("Reverb aplicado"));
    }

    @Test
    void decorator_efecto8DMuestraMensaje() {
        Contenido c = new CancionFactory().crearContenido("C1", "X", 60, "A", "B");
        IReproductor r = new Efecto8D(new ReproductorBase());
        r.reproducir(c);
        assertTrue(salida.toString().contains("Efecto 8D aplicado"));
    }

    @Test
    void decorator_encadenado_aplicaTodosLosEfectos() {
        Contenido c = new CancionFactory().crearContenido("C1", "X", 60, "A", "B");
        IReproductor r = new Efecto8D(new ReverbEfecto(new EqEfecto(new ReproductorBase())));
        r.reproducir(c);
        String out = salida.toString();
        assertTrue(out.contains("Ecualizador aplicado"));
        assertTrue(out.contains("Reverb aplicado"));
        assertTrue(out.contains("Efecto 8D aplicado"));
    }

    // ────────────────────────────────────────────────
    // PROXY
    // ────────────────────────────────────────────────

    @Test
    void proxy_usuarioPremiumReproduceSinAnuncio() {
        Usuario premium = new Usuario("U1", "David", Usuario.TipoSuscripcion.PREMIUM);
        Contenido c = new CancionFactory().crearContenido("C1", "X", 60, "A", "B");
        IReproductor r = new ProxyReproductor(new ReproductorBase(), premium);
        r.reproducir(c);
        assertFalse(salida.toString().contains("Anuncio"));
    }

    @Test
    void proxy_usuarioFreeVeAnuncio() {
        Usuario free = new Usuario("U2", "Juan", Usuario.TipoSuscripcion.FREE);
        Contenido c = new CancionFactory().crearContenido("C1", "X", 60, "A", "B");
        IReproductor r = new ProxyReproductor(new ReproductorBase(), free);
        r.reproducir(c);
        assertTrue(salida.toString().contains("Anuncio"));
    }

    @Test
    void proxy_usuarioFreeNoPuedeReproducirAudiolibro() {
        Usuario free = new Usuario("U2", "Juan", Usuario.TipoSuscripcion.FREE);
        Contenido a = new AudiolibroFactory().crearContenido("A1", "Libro", 3600, "Autor", "Narrador");
        IReproductor r = new ProxyReproductor(new ReproductorBase(), free);
        r.reproducir(a);
        assertTrue(salida.toString().contains("exclusivos para Premium"));
    }

    @Test
    void proxy_usuarioPremiumPuedeReproducirAudiolibro() {
        Usuario premium = new Usuario("U1", "David", Usuario.TipoSuscripcion.PREMIUM);
        Contenido a = new AudiolibroFactory().crearContenido("A1", "Libro", 3600, "Autor", "Narrador");
        IReproductor r = new ProxyReproductor(new ReproductorBase(), premium);
        r.reproducir(a);
        assertTrue(salida.toString().contains("Audiolibro"));
    }

    @Test
    void proxy_saltosFreeLimitadosA3() {
        Usuario free = new Usuario("U2", "Juan", Usuario.TipoSuscripcion.FREE);
        ProxyReproductor proxy = new ProxyReproductor(new ReproductorBase(), free);
        proxy.saltar(); // 1
        proxy.saltar(); // 2
        proxy.saltar(); // 3
        proxy.saltar(); // debe bloquear
        assertTrue(salida.toString().contains("Límite de saltos"));
    }

    @Test
    void proxy_saltoPremiumIlimitado() {
        Usuario premium = new Usuario("U1", "David", Usuario.TipoSuscripcion.PREMIUM);
        ProxyReproductor proxy = new ProxyReproductor(new ReproductorBase(), premium);
        for (int i = 0; i < 10; i++) proxy.saltar();
        assertFalse(salida.toString().contains("Límite de saltos"));
    }
}
