package Modelo;

public class Columna {
    private final String clave;
    private final String etiqueta;

    public Columna(String clave, String etiqueta) {
        this.clave = clave;
        this.etiqueta = etiqueta;
    }

    public String getClave() { return clave; }
    public String getEtiqueta() { return etiqueta; }
}