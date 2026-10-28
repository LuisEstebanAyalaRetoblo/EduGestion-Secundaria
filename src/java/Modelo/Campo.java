package Modelo;

public class Campo {
    private final String nombre;
    private final String etiqueta;
    private final String tipo;
    private final boolean requerido;
    private final boolean nullable;
    private final String opcionesSql;
    private final String opcionesFijas;

    public Campo(String nombre, String etiqueta, String tipo, boolean requerido, boolean nullable, String opcionesSql, String opcionesFijas) {
        this.nombre = nombre;
        this.etiqueta = etiqueta;
        this.tipo = tipo;
        this.requerido = requerido;
        this.nullable = nullable;
        this.opcionesSql = opcionesSql;
        this.opcionesFijas = opcionesFijas;
    }

    public String getNombre() { return nombre; }
    public String getEtiqueta() { return etiqueta; }
    public String getTipo() { return tipo; }
    public boolean isRequerido() { return requerido; }
    public boolean isNullable() { return nullable; }
    public String getOpcionesSql() { return opcionesSql; }
    public String getOpcionesFijas() { return opcionesFijas; }
}