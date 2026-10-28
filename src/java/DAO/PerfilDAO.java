package DAO;

import Conexion.Conectar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfilDAO extends Conectar {
    private int obtener(String sql, int idUsuario) {
        conectar();
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1,idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()?rs.getInt(1):0;
            }
        } catch (SQLException e) {
            return 0;
        } finally {
            desconectar();
        }
    }

    public int obtenerDocente(int idUsuario) {
        return obtener("SELECT id_docente FROM docentes WHERE id_usuario=? AND estado=1",idUsuario);
    }

    public int obtenerEstudiante(int idUsuario) {
        return obtener("SELECT id_estudiante FROM estudiantes WHERE id_usuario=? AND estado=1",idUsuario);
    }
}