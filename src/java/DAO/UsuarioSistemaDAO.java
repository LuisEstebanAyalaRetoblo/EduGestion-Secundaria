package DAO;

import Conexion.Conectar;
import Modelo.UsuarioSistema;
import Util.Seguridad;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioSistemaDAO extends Conectar {
    public UsuarioSistema autenticar(String usuario, String clave) {
        conectar();
        String sql = "SELECT u.id_usuario,u.usuario,r.nombre rol FROM usuarios u INNER JOIN roles r ON r.id_rol=u.id_rol WHERE u.usuario=? AND u.clave=? AND u.estado=1";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, Seguridad.sha256(clave));
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                UsuarioSistema u = new UsuarioSistema();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            desconectar();
        }
    }

    public boolean cambiarClave(int idUsuario,String claveActual,String claveNueva) {
        conectar();
        String sql="UPDATE usuarios SET clave=? WHERE id_usuario=? AND clave=? AND estado=1";
        try(PreparedStatement ps=getConexion().prepareStatement(sql)) {
            ps.setString(1,Seguridad.sha256(claveNueva));
            ps.setInt(2,idUsuario);
            ps.setString(3,Seguridad.sha256(claveActual));
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }
}