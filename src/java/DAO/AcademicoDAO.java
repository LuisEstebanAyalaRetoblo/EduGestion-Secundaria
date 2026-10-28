package DAO;

import Conexion.Conectar;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AcademicoDAO extends Conectar {
    private List<Map<String,Object>> consultar(String sql, Object... valores) {
        conectar();
        List<Map<String,Object>> lista=new ArrayList<>();
        try (PreparedStatement ps=getConexion().prepareStatement(sql)) {
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

    private Map<String,Object> uno(String sql, Object... valores) {
        List<Map<String,Object>> lista=consultar(sql,valores);
        return lista.isEmpty()?null:lista.get(0);
    }

    public List<Map<String,Object>> asignacionesDocente(int idDocente) {
        String sql="SELECT a.id_asignacion,c.nombre curso,CONCAT(g.nombre,' ',s.nombre,' - ',s.anio) seccion,p.nombre periodo FROM asignaciones a INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado INNER JOIN periodos p ON p.id_periodo=a.id_periodo WHERE a.id_docente=? AND a.estado=1 ORDER BY c.nombre";
        return consultar(sql,idDocente);
    }

    public List<Map<String,Object>> asignacionesTodas() {
        String sql="SELECT a.id_asignacion,c.nombre curso,CONCAT(g.nombre,' ',s.nombre,' - ',s.anio) seccion,p.nombre periodo,CONCAT(d.apellidos,', ',d.nombres) docente FROM asignaciones a INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado INNER JOIN periodos p ON p.id_periodo=a.id_periodo INNER JOIN docentes d ON d.id_docente=a.id_docente WHERE a.estado=1 ORDER BY c.nombre";
        return consultar(sql);
    }

    public boolean asignacionPermitida(int idAsignacion, int idDocente, boolean director) {
        if(director) return true;
        return uno("SELECT id_asignacion FROM asignaciones WHERE id_asignacion=? AND id_docente=? AND estado=1",idAsignacion,idDocente)!=null;
    }

    public List<Map<String,Object>> evaluaciones(int idDocente, boolean director) {
        String base="SELECT e.id_evaluacion,e.id_asignacion,e.titulo,e.tipo,e.fecha,e.ponderacion,IF(e.estado=1,'Activo','Inactivo') estado,c.nombre curso,CONCAT(g.nombre,' ',s.nombre) seccion FROM evaluaciones e INNER JOIN asignaciones a ON a.id_asignacion=e.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado ";
        return director?consultar(base+"ORDER BY e.fecha DESC"):consultar(base+"WHERE a.id_docente=? ORDER BY e.fecha DESC",idDocente);
    }

    public Map<String,Object> evaluacion(int idEvaluacion) {
        return uno("SELECT e.*,a.id_docente,a.id_seccion,a.id_periodo,c.nombre curso,CONCAT(g.nombre,' ',s.nombre) seccion FROM evaluaciones e INNER JOIN asignaciones a ON a.id_asignacion=e.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado WHERE e.id_evaluacion=?",idEvaluacion);
    }

    public boolean guardarEvaluacion(int idEvaluacion,int idAsignacion,String titulo,String tipo,Date fecha,BigDecimal ponderacion,boolean estado) {
        conectar();
        String sql=idEvaluacion==0?"INSERT INTO evaluaciones(id_asignacion,titulo,tipo,fecha,ponderacion,estado) VALUES(?,?,?,?,?,?)":"UPDATE evaluaciones SET id_asignacion=?,titulo=?,tipo=?,fecha=?,ponderacion=?,estado=? WHERE id_evaluacion=?";
        try(PreparedStatement ps=getConexion().prepareStatement(sql)) {
            ps.setInt(1,idAsignacion);
            ps.setString(2,titulo);
            ps.setString(3,tipo);
            ps.setDate(4,fecha);
            ps.setBigDecimal(5,ponderacion);
            ps.setBoolean(6,estado);
            if(idEvaluacion>0) ps.setInt(7,idEvaluacion);
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean eliminarEvaluacion(int idEvaluacion) {
        conectar();
        try(PreparedStatement ps=getConexion().prepareStatement("DELETE FROM evaluaciones WHERE id_evaluacion=?")) {
            ps.setInt(1,idEvaluacion);
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }

    public List<Map<String,Object>> matriculasAsignacion(int idAsignacion) {
        String sql="SELECT m.id_matricula,e.codigo,CONCAT(e.apellidos,', ',e.nombres) estudiante FROM asignaciones a INNER JOIN matriculas m ON m.id_seccion=a.id_seccion AND m.id_periodo=a.id_periodo INNER JOIN estudiantes e ON e.id_estudiante=m.id_estudiante WHERE a.id_asignacion=? AND m.estado='ACTIVA' ORDER BY e.apellidos,e.nombres";
        return consultar(sql,idAsignacion);
    }

    public List<Map<String,Object>> calificacionesEvaluacion(int idEvaluacion) {
        String sql="SELECT c.id_calificacion,c.id_matricula,e.codigo,CONCAT(e.apellidos,', ',e.nombres) estudiante,c.nota,c.observacion,c.fecha_registro FROM calificaciones c INNER JOIN matriculas m ON m.id_matricula=c.id_matricula INNER JOIN estudiantes e ON e.id_estudiante=m.id_estudiante WHERE c.id_evaluacion=? ORDER BY e.apellidos,e.nombres";
        return consultar(sql,idEvaluacion);
    }

    public boolean matriculaValida(int idEvaluacion,int idMatricula) {
        String sql="SELECT m.id_matricula FROM evaluaciones e INNER JOIN asignaciones a ON a.id_asignacion=e.id_asignacion INNER JOIN matriculas m ON m.id_seccion=a.id_seccion AND m.id_periodo=a.id_periodo WHERE e.id_evaluacion=? AND m.id_matricula=? AND m.estado='ACTIVA'";
        return uno(sql,idEvaluacion,idMatricula)!=null;
    }

    public boolean guardarCalificacion(int idEvaluacion,int idMatricula,BigDecimal nota,String observacion) {
        conectar();
        String sql="INSERT INTO calificaciones(id_evaluacion,id_matricula,nota,observacion) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE nota=VALUES(nota),observacion=VALUES(observacion),fecha_registro=CURRENT_TIMESTAMP";
        try(PreparedStatement ps=getConexion().prepareStatement(sql)) {
            ps.setInt(1,idEvaluacion);
            ps.setInt(2,idMatricula);
            ps.setBigDecimal(3,nota);
            ps.setString(4,observacion);
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }

    public List<Map<String,Object>> asistencias(int idAsignacion,Date fecha) {
        String sql="SELECT a.id_asistencia,a.id_matricula,e.codigo,CONCAT(e.apellidos,', ',e.nombres) estudiante,a.estado,a.observacion FROM asistencias a INNER JOIN matriculas m ON m.id_matricula=a.id_matricula INNER JOIN estudiantes e ON e.id_estudiante=m.id_estudiante WHERE a.id_asignacion=? AND a.fecha=? ORDER BY e.apellidos,e.nombres";
        return consultar(sql,idAsignacion,fecha);
    }

    public List<Map<String,Object>> registroAsistencia(int idAsignacion,Date fecha) {
        String sql="SELECT m.id_matricula,e.codigo,CONCAT(e.apellidos,', ',e.nombres) estudiante,COALESCE(asi.estado,'PRESENTE') estado,COALESCE(asi.observacion,'') observacion FROM asignaciones a INNER JOIN matriculas m ON m.id_seccion=a.id_seccion AND m.id_periodo=a.id_periodo INNER JOIN estudiantes e ON e.id_estudiante=m.id_estudiante LEFT JOIN asistencias asi ON asi.id_asignacion=a.id_asignacion AND asi.id_matricula=m.id_matricula AND asi.fecha=? WHERE a.id_asignacion=? AND m.estado='ACTIVA' ORDER BY e.apellidos,e.nombres";
        return consultar(sql,fecha,idAsignacion);
    }

    public boolean guardarAsistencia(int idAsignacion,int idMatricula,Date fecha,String estado,String observacion) {
        conectar();
        String sql="INSERT INTO asistencias(id_matricula,id_asignacion,fecha,estado,observacion) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE estado=VALUES(estado),observacion=VALUES(observacion)";
        try(PreparedStatement ps=getConexion().prepareStatement(sql)) {
            ps.setInt(1,idMatricula);
            ps.setInt(2,idAsignacion);
            ps.setDate(3,fecha);
            ps.setString(4,estado);
            ps.setString(5,observacion);
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean matriculaValidaAsignacion(int idAsignacion,int idMatricula) {
        String sql="SELECT m.id_matricula FROM asignaciones a INNER JOIN matriculas m ON m.id_seccion=a.id_seccion AND m.id_periodo=a.id_periodo WHERE a.id_asignacion=? AND m.id_matricula=? AND m.estado='ACTIVA'";
        return uno(sql,idAsignacion,idMatricula)!=null;
    }

    public Map<String,Object> perfilDocente(int idDocente) {
        return uno("SELECT codigo,CONCAT(nombres,' ',apellidos) nombre,dni,especialidad,correo,telefono FROM docentes WHERE id_docente=?",idDocente);
    }

    public Map<String,Object> perfilEstudiante(int idEstudiante) {
        return uno("SELECT codigo,CONCAT(nombres,' ',apellidos) nombre,dni,fecha_nacimiento,sexo,direccion,telefono,correo FROM estudiantes WHERE id_estudiante=?",idEstudiante);
    }

    public List<Map<String,Object>> notasEstudiante(int idEstudiante) {
        String sql="SELECT c.nombre curso,e.titulo evaluacion,e.tipo,e.fecha,ca.nota,ca.observacion FROM calificaciones ca INNER JOIN evaluaciones e ON e.id_evaluacion=ca.id_evaluacion INNER JOIN asignaciones a ON a.id_asignacion=e.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN matriculas m ON m.id_matricula=ca.id_matricula WHERE m.id_estudiante=? ORDER BY e.fecha DESC,c.nombre";
        return consultar(sql,idEstudiante);
    }

    public List<Map<String,Object>> asistenciasEstudiante(int idEstudiante) {
        String sql="SELECT asi.fecha,c.nombre curso,asi.estado,asi.observacion FROM asistencias asi INNER JOIN asignaciones a ON a.id_asignacion=asi.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN matriculas m ON m.id_matricula=asi.id_matricula WHERE m.id_estudiante=? ORDER BY asi.fecha DESC,c.nombre";
        return consultar(sql,idEstudiante);
    }

    public List<Map<String,Object>> horarioDocente(int idDocente) {
        String sql="SELECT h.dia_semana,h.hora_inicio,h.hora_fin,c.nombre curso,CONCAT(g.nombre,' ',s.nombre) seccion,h.aula FROM horarios h INNER JOIN asignaciones a ON a.id_asignacion=h.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado WHERE a.id_docente=? ORDER BY FIELD(h.dia_semana,'LUNES','MARTES','MIERCOLES','JUEVES','VIERNES'),h.hora_inicio";
        return consultar(sql,idDocente);
    }

    public List<Map<String,Object>> horarioEstudiante(int idEstudiante) {
        String sql="SELECT h.dia_semana,h.hora_inicio,h.hora_fin,c.nombre curso,CONCAT(g.nombre,' ',s.nombre) seccion,h.aula FROM matriculas m INNER JOIN asignaciones a ON a.id_seccion=m.id_seccion AND a.id_periodo=m.id_periodo INNER JOIN horarios h ON h.id_asignacion=a.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado WHERE m.id_estudiante=? AND m.estado='ACTIVA' ORDER BY FIELD(h.dia_semana,'LUNES','MARTES','MIERCOLES','JUEVES','VIERNES'),h.hora_inicio";
        return consultar(sql,idEstudiante);
    }

    public List<Map<String,Object>> horarioTodos() {
        String sql="SELECT h.dia_semana,h.hora_inicio,h.hora_fin,c.nombre curso,CONCAT(g.nombre,' ',s.nombre) seccion,CONCAT(d.apellidos,', ',d.nombres) docente,h.aula FROM horarios h INNER JOIN asignaciones a ON a.id_asignacion=h.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado INNER JOIN docentes d ON d.id_docente=a.id_docente ORDER BY FIELD(h.dia_semana,'LUNES','MARTES','MIERCOLES','JUEVES','VIERNES'),h.hora_inicio";
        return consultar(sql);
    }
}