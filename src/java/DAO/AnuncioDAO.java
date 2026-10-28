package DAO;

import Conexion.Conectar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AnuncioDAO extends Conectar {
    private List<Map<String,Object>> consultar(String sql,Object... valores) {
        conectar();
        List<Map<String,Object>> lista=new ArrayList<>();
        try(PreparedStatement ps=getConexion().prepareStatement(sql)) {
            for(int i=0;i<valores.length;i++) ps.setObject(i+1,valores[i]);
            try(ResultSet rs=ps.executeQuery()) {
                ResultSetMetaData md=rs.getMetaData();
                while(rs.next()) {
                    Map<String,Object> fila=new LinkedHashMap<>();
                    for(int i=1;i<=md.getColumnCount();i++) fila.put(md.getColumnLabel(i),rs.getObject(i));
                    lista.add(fila);
                }
            }
            return lista;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            desconectar();
        }
    }

    public List<Map<String,Object>> visibles(String rol) {
        String destino="DOCENTE".equals(rol)?"DOCENTES":"ESTUDIANTES";
        String sql="SELECT a.id_anuncio,a.titulo,a.contenido,a.fecha_publicacion,a.fecha_expiracion,a.destino,a.id_usuario,u.usuario autor FROM anuncios a INNER JOIN usuarios u ON u.id_usuario=a.id_usuario WHERE a.estado=1 AND a.fecha_publicacion<=CURDATE() AND (a.fecha_expiracion IS NULL OR a.fecha_expiracion>=CURDATE()) AND (a.destino='TODOS' OR a.destino=?) ORDER BY a.fecha_publicacion DESC";
        return consultar(sql,destino);
    }

    public List<Map<String,Object>> administrar(int idUsuario,boolean director) {
        String base="SELECT a.id_anuncio,a.titulo,a.contenido,a.fecha_publicacion,a.fecha_expiracion,a.destino,a.id_usuario,a.estado,u.usuario autor FROM anuncios a INNER JOIN usuarios u ON u.id_usuario=a.id_usuario ";
        return director?consultar(base+"ORDER BY a.fecha_publicacion DESC"):consultar(base+"WHERE a.id_usuario=? ORDER BY a.fecha_publicacion DESC",idUsuario);
    }

    public Map<String,Object> obtener(int id) {
        List<Map<String,Object>> l=consultar("SELECT * FROM anuncios WHERE id_anuncio=?",id);
        return l.isEmpty()?null:l.get(0);
    }

    public boolean guardar(int id,String titulo,String contenido,Date publicacion,Date expiracion,String destino,int idUsuario,boolean estado) {
        conectar();
        String sql=id==0?"INSERT INTO anuncios(titulo,contenido,fecha_publicacion,fecha_expiracion,destino,id_usuario,estado) VALUES(?,?,?,?,?,?,?)":"UPDATE anuncios SET titulo=?,contenido=?,fecha_publicacion=?,fecha_expiracion=?,destino=?,estado=? WHERE id_anuncio=?";
        try(PreparedStatement ps=getConexion().prepareStatement(sql)) {
            ps.setString(1,titulo);
            ps.setString(2,contenido);
            ps.setDate(3,publicacion);
            ps.setDate(4,expiracion);
            ps.setString(5,destino);
            if(id==0) {
                ps.setInt(6,idUsuario);
                ps.setBoolean(7,estado);
            } else {
                ps.setBoolean(6,estado);
                ps.setInt(7,id);
            }
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean eliminar(int id) {
        conectar();
        try(PreparedStatement ps=getConexion().prepareStatement("DELETE FROM anuncios WHERE id_anuncio=?")) {
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }
}