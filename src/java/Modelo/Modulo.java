package Modelo;

import java.util.List;

public class Modulo {
    private final String clave;
    private final String titulo;
    private final String tabla;
    private final String pk;
    private final String sqlListado;
    private final List<Campo> campos;
    private final List<Columna> columnas;

    public Modulo(String clave, String titulo, String tabla, String pk, String sqlListado, List<Campo> campos, List<Columna> columnas) {
        this.clave = clave;
        this.titulo = titulo;
        this.tabla = tabla;
        this.pk = pk;
        this.sqlListado = sqlListado;
        this.campos = campos;
        this.columnas = columnas;
    }

    public String getClave() { return clave; }
    public String getTitulo() { return titulo; }
    public String getTabla() { return tabla; }
    public String getPk() { return pk; }
    public String getSqlListado() { return sqlListado; }
    public List<Campo> getCampos() { return campos; }
    public List<Columna> getColumnas() { return columnas; }
}