package Modelo;

public class Opcion {
    private final String id;
    private final String texto;

    public Opcion(String id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public String getId() { return id; }
    public String getTexto() { return texto; }
}