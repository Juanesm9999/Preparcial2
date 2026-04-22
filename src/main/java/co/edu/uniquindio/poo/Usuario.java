package co.edu.uniquindio.poo;

public class Usuario {
    public enum TipoSuscripcion { FREE, PREMIUM }
    private String id;
    private String nombre;
    private TipoSuscripcion suscripcion;
    public Usuario(String id, String nombre, TipoSuscripcion suscripcion) {
        this.id = id;
        this.nombre = nombre;
        this.suscripcion = suscripcion;
    }
    public String getNombre() { return nombre; }
    public TipoSuscripcion getSuscripcion() { return suscripcion; }
    public boolean esPremium() { return suscripcion == TipoSuscripcion.PREMIUM; }
}
