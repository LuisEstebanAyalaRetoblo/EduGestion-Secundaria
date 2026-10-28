package DAO;

import Conexion.Conectar;
import Modelo.Campo;
import Modelo.Modulo;
import Modelo.Opcion;
import Util.Seguridad;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrudDAO extends Conectar {
    public List<Map<String,Object>> listar(Modulo modulo) {
        conectar();
        List<Map<String,Object>> lista = new ArrayList<>();
        try (PreparedStatement ps = getConexion().prepareStatement(modulo.getSqlListado()); ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                Map<String,Object> fila = new LinkedHashMap<>();
                for (int i=1;i<=md.getColumnCount();i++) fila.put(md.getColumnLabel(i), rs.getObject(i));
                lista.add(fila);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            desconectar();
        }
    }

    public Map<String,Object> obtener(Modulo modulo, int id) {
        conectar();
        String sql = "SELECT * FROM " + modulo.getTabla() + " WHERE " + modulo.getPk() + "=?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Map<String,Object> fila = new LinkedHashMap<>();
                ResultSetMetaData md = rs.getMetaData();
                for (int i=1;i<=md.getColumnCount();i++) fila.put(md.getColumnLabel(i), rs.getObject(i));
                if ("usuarios".equals(modulo.getTabla())) fila.put("clave","");
                return fila;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            desconectar();
        }
    }

    public List<Opcion> opciones(Campo campo) {
        List<Opcion> lista = new ArrayList<>();
        if (campo.getOpcionesFijas()!=null) {
            String[] items = campo.getOpcionesFijas().split("\\|");
            for (String item : items) {
                String[] partes = item.split(":",2);
                lista.add(new Opcion(partes[0], partes.length>1?partes[1]:partes[0]));
            }
            return lista;
        }
        if (campo.getOpcionesSql()==null) return lista;
        conectar();
        try (PreparedStatement ps = getConexion().prepareStatement(campo.getOpcionesSql()); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(new Opcion(rs.getString("id"),rs.getString("texto")));
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            desconectar();
        }
    }

    public boolean guardar(Modulo modulo, int id, Map<String,String> datos) {
        List<Campo> usados = new ArrayList<>();
        for (Campo campo : modulo.getCampos()) {
            String valor = datos.get(campo.getNombre());
            if ("password".equals(campo.getTipo()) && id>0 && (valor==null || valor.trim().isEmpty())) continue;
            usados.add(campo);
        }
        StringBuilder sql = new StringBuilder();
        if (id==0) {
            sql.append("INSERT INTO ").append(modulo.getTabla()).append("(");
            for (int i=0;i<usados.size();i++) {
                if(i>0) sql.append(",");
                sql.append(usados.get(i).getNombre());
            }
            sql.append(") VALUES(");
            for (int i=0;i<usados.size();i++) {
                if(i>0) sql.append(",");
                sql.append("?");
            }
            sql.append(")");
        } else {
            sql.append("UPDATE ").append(modulo.getTabla()).append(" SET ");
            for (int i=0;i<usados.size();i++) {
                if(i>0) sql.append(",");
                sql.append(usados.get(i).getNombre()).append("=?");
            }
            sql.append(" WHERE ").append(modulo.getPk()).append("=?");
        }
        conectar();
        try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
            int pos=1;
            for (Campo campo : usados) {
                asignar(ps,pos++,campo,datos.get(campo.getNombre()));
            }
            if(id>0) ps.setInt(pos,id);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }

    private void asignar(PreparedStatement ps, int pos, Campo campo, String valor) throws SQLException {
        if (campo.isNullable() && (valor==null || valor.trim().isEmpty())) {
            ps.setObject(pos,null);
            return;
        }
        if ("checkbox".equals(campo.getTipo())) {
            ps.setInt(pos,"1".equals(valor)?1:0);
        } else if ("number".equals(campo.getTipo()) || "select".equals(campo.getTipo()) && valor!=null && valor.matches("\\d+")) {
            ps.setInt(pos,Integer.parseInt(valor));
        } else if ("decimal".equals(campo.getTipo())) {
            ps.setBigDecimal(pos,new BigDecimal(valor));
        } else if ("date".equals(campo.getTipo())) {
            ps.setDate(pos,Date.valueOf(valor));
        } else if ("time".equals(campo.getTipo())) {
            ps.setTime(pos,Time.valueOf(valor.length()==5?valor+":00":valor));
        } else if ("password".equals(campo.getTipo())) {
            ps.setString(pos,Seguridad.sha256(valor));
        } else {
            ps.setString(pos,valor);
        }
    }

    public boolean eliminar(Modulo modulo, int id) {
        conectar();
        String sql = "DELETE FROM " + modulo.getTabla() + " WHERE " + modulo.getPk() + "=?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }
}