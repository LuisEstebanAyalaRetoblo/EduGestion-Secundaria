package DAO;

import Conexion.Conectar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReporteDAO extends Conectar {
    private List<Map<String,Object>> consultar(String sql) {
        conectar();
        List<Map<String,Object>> lista=new ArrayList<>();
        try(PreparedStatement ps=getConexion().prepareStatement(sql);ResultSet rs=ps.executeQuery()) {
            ResultSetMetaData md=rs.getMetaData();
            while(rs.next()) {
                Map<String,Object> fila=new LinkedHashMap<>();
                for(int i=1;i<=md.getColumnCount();i++) fila.put(md.getColumnLabel(i),rs.getObject(i));
                lista.add(fila);
            }
            return lista;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            desconectar();
        }
    }

    public List<Map<String,Object>> matriculasPorSeccion() {
        return consultar("SELECT CONCAT(g.nombre,' ',s.nombre,' - ',s.anio) seccion,s.capacidad,COUNT(m.id_matricula) matriculados,s.capacidad-COUNT(m.id_matricula) vacantes FROM secciones s INNER JOIN grados g ON g.id_grado=s.id_grado LEFT JOIN matriculas m ON m.id_seccion=s.id_seccion AND m.estado='ACTIVA' GROUP BY s.id_seccion,g.nombre,g.orden,s.nombre,s.anio,s.capacidad ORDER BY s.anio DESC,g.orden,s.nombre");
    }

    public List<Map<String,Object>> promedioPorCurso() {
        return consultar("SELECT c.codigo,c.nombre curso,COUNT(ca.id_calificacion) calificaciones,ROUND(AVG(ca.nota),2) promedio,ROUND(MIN(ca.nota),2) minima,ROUND(MAX(ca.nota),2) maxima FROM cursos c LEFT JOIN asignaciones a ON a.id_curso=c.id_curso LEFT JOIN evaluaciones e ON e.id_asignacion=a.id_asignacion LEFT JOIN calificaciones ca ON ca.id_evaluacion=e.id_evaluacion GROUP BY c.id_curso,c.codigo,c.nombre ORDER BY c.nombre");
    }

    public List<Map<String,Object>> resumenAsistencia() {
        return consultar("SELECT c.nombre curso,COUNT(asi.id_asistencia) registros,COALESCE(SUM(asi.estado='PRESENTE'),0) presentes,COALESCE(SUM(asi.estado='TARDANZA'),0) tardanzas,COALESCE(SUM(asi.estado='FALTA'),0) faltas,COALESCE(SUM(asi.estado='JUSTIFICADA'),0) justificadas FROM cursos c LEFT JOIN asignaciones a ON a.id_curso=c.id_curso LEFT JOIN asistencias asi ON asi.id_asignacion=a.id_asignacion GROUP BY c.id_curso,c.nombre ORDER BY c.nombre");
    }

    public List<Map<String,Object>> cargaDocente() {
        return consultar("SELECT d.codigo,CONCAT(d.apellidos,', ',d.nombres) docente,d.especialidad,COUNT(a.id_asignacion) asignaciones,COALESCE(SUM(c.horas_semanales),0) horas_semanales FROM docentes d LEFT JOIN asignaciones a ON a.id_docente=d.id_docente AND a.estado=1 LEFT JOIN cursos c ON c.id_curso=a.id_curso GROUP BY d.id_docente,d.codigo,d.apellidos,d.nombres,d.especialidad ORDER BY d.apellidos,d.nombres");
    }
}