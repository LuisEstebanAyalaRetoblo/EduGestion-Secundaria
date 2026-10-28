package DAO;

import Conexion.Conectar;
import Modelo.Dashboard;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO extends Conectar {
    private int contar(String tabla) {
        try (PreparedStatement ps=getConexion().prepareStatement("SELECT COUNT(*) total FROM "+tabla); ResultSet rs=ps.executeQuery()) {
            return rs.next()?rs.getInt("total"):0;
        } catch (SQLException e) {
            return 0;
        }
    }

    public Dashboard obtener() {
        conectar();
        try {
            Dashboard d=new Dashboard();
            d.setUsuarios(contar("usuarios"));
            d.setDocentes(contar("docentes"));
            d.setEstudiantes(contar("estudiantes"));
            d.setCursos(contar("cursos"));
            d.setSecciones(contar("secciones"));
            d.setMatriculas(contar("matriculas"));
            d.setAsignaciones(contar("asignaciones"));
            d.setAnuncios(contar("anuncios"));
            return d;
        } finally {
            desconectar();
        }
    }
}