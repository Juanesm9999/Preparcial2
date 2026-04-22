package co.edu.uniquindio.poo;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements ComponentePlaylist {
    private String nombre;
    private List<ComponentePlaylist> hijos = new ArrayList<>();
    public Playlist(String nombre) {
        this.nombre = nombre;
    }
    public void agregar(ComponentePlaylist c) { hijos.add(c); }
    public void quitar(ComponentePlaylist c) { hijos.remove(c); }
    @Override
    public int duracionTotal() {
        int total = 0;
        for (ComponentePlaylist c : hijos) {
            total += c.duracionTotal(); // llamada RECURSIVA
        }
        return total;
    }
    @Override
    public void mostrar(String indent) {
        System.out.println(indent + " Playlist:" + nombre);
        for (ComponentePlaylist c : hijos) {
            c.mostrar(indent + "  ");
        }
    }


    public static String formatearDuracion(int segundos) {
        int horas = segundos / 3600;
        int min = (segundos % 3600) / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d:%02d", horas, min, seg);
    }
}
