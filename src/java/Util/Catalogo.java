package Util;

import Modelo.Campo;
import Modelo.Columna;
import Modelo.Modulo;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Catalogo {
    private static final Map<String, Modulo> MODULOS = new LinkedHashMap<>();

    private static Campo c(String nombre, String etiqueta, String tipo, boolean requerido, boolean nullable, String opcionesSql, String opcionesFijas) {
        return new Campo(nombre, etiqueta, tipo, requerido, nullable, opcionesSql, opcionesFijas);
    }

    private static Columna col(String clave, String etiqueta) {
        return new Columna(clave, etiqueta);
    }

    static {
        MODULOS.put("usuarios", new Modulo(
            "usuarios","Usuarios","usuarios","id_usuario",
            "SELECT u.id_usuario,u.usuario,r.nombre rol,IF(u.estado=1,'Activo','Inactivo') estado,u.fecha_creacion FROM usuarios u INNER JOIN roles r ON r.id_rol=u.id_rol ORDER BY u.id_usuario DESC",
            Arrays.asList(
                c("usuario","Usuario","text",true,false,null,null),
                c("clave","Contraseña","password",false,false,null,null),
                c("id_rol","Rol","select",true,false,"SELECT id_rol id,nombre texto FROM roles ORDER BY id_rol",null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("usuario","Usuario"),col("rol","Rol"),col("estado","Estado"),col("fecha_creacion","Creación"))
        ));

        MODULOS.put("docentes", new Modulo(
            "docentes","Docentes","docentes","id_docente",
            "SELECT d.id_docente,d.codigo,d.dni,CONCAT(d.apellidos,', ',d.nombres) docente,d.especialidad,d.correo,d.telefono,u.usuario,IF(d.estado=1,'Activo','Inactivo') estado FROM docentes d INNER JOIN usuarios u ON u.id_usuario=d.id_usuario ORDER BY d.apellidos,d.nombres",
            Arrays.asList(
                c("codigo","Código","text",true,false,null,null),
                c("dni","DNI","text",true,false,null,null),
                c("nombres","Nombres","text",true,false,null,null),
                c("apellidos","Apellidos","text",true,false,null,null),
                c("correo","Correo","email",true,false,null,null),
                c("telefono","Teléfono","text",true,false,null,null),
                c("especialidad","Especialidad","text",true,false,null,null),
                c("id_usuario","Cuenta de usuario","select",true,false,"SELECT u.id_usuario id,u.usuario texto FROM usuarios u INNER JOIN roles r ON r.id_rol=u.id_rol WHERE r.nombre='DOCENTE' ORDER BY u.usuario",null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("codigo","Código"),col("dni","DNI"),col("docente","Docente"),col("especialidad","Especialidad"),col("correo","Correo"),col("telefono","Teléfono"),col("usuario","Cuenta"),col("estado","Estado"))
        ));

        MODULOS.put("estudiantes", new Modulo(
            "estudiantes","Estudiantes","estudiantes","id_estudiante",
            "SELECT e.id_estudiante,e.codigo,e.dni,CONCAT(e.apellidos,', ',e.nombres) estudiante,e.fecha_nacimiento,e.sexo,e.telefono,e.correo,u.usuario,IF(e.estado=1,'Activo','Inactivo') estado FROM estudiantes e INNER JOIN usuarios u ON u.id_usuario=e.id_usuario ORDER BY e.apellidos,e.nombres",
            Arrays.asList(
                c("codigo","Código","text",true,false,null,null),
                c("dni","DNI","text",true,false,null,null),
                c("nombres","Nombres","text",true,false,null,null),
                c("apellidos","Apellidos","text",true,false,null,null),
                c("fecha_nacimiento","Fecha de nacimiento","date",true,false,null,null),
                c("sexo","Sexo","select",true,false,null,"M:Masculino|F:Femenino"),
                c("direccion","Dirección","text",true,false,null,null),
                c("telefono","Teléfono","text",false,true,null,null),
                c("correo","Correo","email",false,true,null,null),
                c("id_usuario","Cuenta de usuario","select",true,false,"SELECT u.id_usuario id,u.usuario texto FROM usuarios u INNER JOIN roles r ON r.id_rol=u.id_rol WHERE r.nombre='ESTUDIANTE' ORDER BY u.usuario",null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("codigo","Código"),col("dni","DNI"),col("estudiante","Estudiante"),col("fecha_nacimiento","Nacimiento"),col("sexo","Sexo"),col("telefono","Teléfono"),col("correo","Correo"),col("usuario","Cuenta"),col("estado","Estado"))
        ));

        MODULOS.put("apoderados", new Modulo(
            "apoderados","Apoderados","apoderados","id_apoderado",
            "SELECT id_apoderado,dni,CONCAT(apellidos,', ',nombres) apoderado,telefono,correo,direccion,IF(estado=1,'Activo','Inactivo') estado FROM apoderados ORDER BY apellidos,nombres",
            Arrays.asList(
                c("dni","DNI","text",true,false,null,null),
                c("nombres","Nombres","text",true,false,null,null),
                c("apellidos","Apellidos","text",true,false,null,null),
                c("telefono","Teléfono","text",true,false,null,null),
                c("correo","Correo","email",false,true,null,null),
                c("direccion","Dirección","text",true,false,null,null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("dni","DNI"),col("apoderado","Apoderado"),col("telefono","Teléfono"),col("correo","Correo"),col("direccion","Dirección"),col("estado","Estado"))
        ));

        MODULOS.put("vinculos", new Modulo(
            "vinculos","Vínculos familiares","estudiante_apoderado","id_estudiante_apoderado",
            "SELECT ea.id_estudiante_apoderado,CONCAT(e.codigo,' - ',e.apellidos,', ',e.nombres) estudiante,CONCAT(a.apellidos,', ',a.nombres) apoderado,ea.parentesco,IF(ea.principal=1,'Principal','Secundario') principal FROM estudiante_apoderado ea INNER JOIN estudiantes e ON e.id_estudiante=ea.id_estudiante INNER JOIN apoderados a ON a.id_apoderado=ea.id_apoderado ORDER BY e.apellidos,e.nombres",
            Arrays.asList(
                c("id_estudiante","Estudiante","select",true,false,"SELECT id_estudiante id,CONCAT(codigo,' - ',apellidos,', ',nombres) texto FROM estudiantes WHERE estado=1 ORDER BY apellidos,nombres",null),
                c("id_apoderado","Apoderado","select",true,false,"SELECT id_apoderado id,CONCAT(apellidos,', ',nombres,' - ',dni) texto FROM apoderados WHERE estado=1 ORDER BY apellidos,nombres",null),
                c("parentesco","Parentesco","text",true,false,null,null),
                c("principal","Responsable principal","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("estudiante","Estudiante"),col("apoderado","Apoderado"),col("parentesco","Parentesco"),col("principal","Responsabilidad"))
        ));

        MODULOS.put("grados", new Modulo(
            "grados","Grados","grados","id_grado",
            "SELECT id_grado,orden,nombre,nivel,IF(estado=1,'Activo','Inactivo') estado FROM grados ORDER BY orden",
            Arrays.asList(
                c("nombre","Nombre","text",true,false,null,null),
                c("nivel","Nivel","text",true,false,null,null),
                c("orden","Orden","number",true,false,null,null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("orden","Orden"),col("nombre","Grado"),col("nivel","Nivel"),col("estado","Estado"))
        ));

        MODULOS.put("periodos", new Modulo(
            "periodos","Periodos académicos","periodos","id_periodo",
            "SELECT id_periodo,nombre,anio,fecha_inicio,fecha_fin,IF(estado=1,'Activo','Inactivo') estado FROM periodos ORDER BY anio DESC,fecha_inicio DESC",
            Arrays.asList(
                c("nombre","Nombre","text",true,false,null,null),
                c("anio","Año","number",true,false,null,null),
                c("fecha_inicio","Fecha de inicio","date",true,false,null,null),
                c("fecha_fin","Fecha de fin","date",true,false,null,null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("nombre","Periodo"),col("anio","Año"),col("fecha_inicio","Inicio"),col("fecha_fin","Fin"),col("estado","Estado"))
        ));

        MODULOS.put("secciones", new Modulo(
            "secciones","Secciones","secciones","id_seccion",
            "SELECT s.id_seccion,CONCAT(g.nombre,' ',s.nombre) seccion,s.anio,s.turno,s.aula,s.capacidad,COALESCE(CONCAT(d.apellidos,', ',d.nombres),'Sin tutor') tutor,IF(s.estado=1,'Activo','Inactivo') estado FROM secciones s INNER JOIN grados g ON g.id_grado=s.id_grado LEFT JOIN docentes d ON d.id_docente=s.id_tutor ORDER BY s.anio DESC,g.orden,s.nombre",
            Arrays.asList(
                c("id_grado","Grado","select",true,false,"SELECT id_grado id,nombre texto FROM grados WHERE estado=1 ORDER BY orden",null),
                c("nombre","Sección","text",true,false,null,null),
                c("anio","Año","number",true,false,null,null),
                c("turno","Turno","select",true,false,null,"MAÑANA:Mañana|TARDE:Tarde"),
                c("aula","Aula","text",true,false,null,null),
                c("capacidad","Capacidad","number",true,false,null,null),
                c("id_tutor","Tutor","select",false,true,"SELECT id_docente id,CONCAT(apellidos,', ',nombres) texto FROM docentes WHERE estado=1 ORDER BY apellidos,nombres",null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("seccion","Sección"),col("anio","Año"),col("turno","Turno"),col("aula","Aula"),col("capacidad","Capacidad"),col("tutor","Tutor"),col("estado","Estado"))
        ));

        MODULOS.put("cursos", new Modulo(
            "cursos","Cursos","cursos","id_curso",
            "SELECT id_curso,codigo,nombre,horas_semanales,descripcion,IF(estado=1,'Activo','Inactivo') estado FROM cursos ORDER BY nombre",
            Arrays.asList(
                c("codigo","Código","text",true,false,null,null),
                c("nombre","Nombre","text",true,false,null,null),
                c("horas_semanales","Horas semanales","number",true,false,null,null),
                c("descripcion","Descripción","textarea",false,true,null,null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("codigo","Código"),col("nombre","Curso"),col("horas_semanales","Horas"),col("descripcion","Descripción"),col("estado","Estado"))
        ));

        MODULOS.put("matriculas", new Modulo(
            "matriculas","Matrículas","matriculas","id_matricula",
            "SELECT m.id_matricula,CONCAT(e.codigo,' - ',e.apellidos,', ',e.nombres) estudiante,CONCAT(g.nombre,' ',s.nombre,' - ',s.anio) seccion,p.nombre periodo,m.fecha_matricula,m.estado,m.observacion FROM matriculas m INNER JOIN estudiantes e ON e.id_estudiante=m.id_estudiante INNER JOIN secciones s ON s.id_seccion=m.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado INNER JOIN periodos p ON p.id_periodo=m.id_periodo ORDER BY m.fecha_matricula DESC",
            Arrays.asList(
                c("id_estudiante","Estudiante","select",true,false,"SELECT id_estudiante id,CONCAT(codigo,' - ',apellidos,', ',nombres) texto FROM estudiantes WHERE estado=1 ORDER BY apellidos,nombres",null),
                c("id_seccion","Sección","select",true,false,"SELECT s.id_seccion id,CONCAT(g.nombre,' ',s.nombre,' - ',s.anio) texto FROM secciones s INNER JOIN grados g ON g.id_grado=s.id_grado WHERE s.estado=1 ORDER BY s.anio DESC,g.orden,s.nombre",null),
                c("id_periodo","Periodo","select",true,false,"SELECT id_periodo id,nombre texto FROM periodos WHERE estado=1 ORDER BY anio DESC",null),
                c("fecha_matricula","Fecha de matrícula","date",true,false,null,null),
                c("estado","Estado","select",true,false,null,"ACTIVA:Activa|RETIRADA:Retirada|TRASLADADA:Trasladada|FINALIZADA:Finalizada"),
                c("observacion","Observación","textarea",false,true,null,null)
            ),
            Arrays.asList(col("estudiante","Estudiante"),col("seccion","Sección"),col("periodo","Periodo"),col("fecha_matricula","Fecha"),col("estado","Estado"),col("observacion","Observación"))
        ));

        MODULOS.put("asignaciones", new Modulo(
            "asignaciones","Asignaciones docentes","asignaciones","id_asignacion",
            "SELECT a.id_asignacion,CONCAT(d.apellidos,', ',d.nombres) docente,c.nombre curso,CONCAT(g.nombre,' ',s.nombre,' - ',s.anio) seccion,p.nombre periodo,IF(a.estado=1,'Activo','Inactivo') estado FROM asignaciones a INNER JOIN docentes d ON d.id_docente=a.id_docente INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado INNER JOIN periodos p ON p.id_periodo=a.id_periodo ORDER BY p.anio DESC,d.apellidos,c.nombre",
            Arrays.asList(
                c("id_docente","Docente","select",true,false,"SELECT id_docente id,CONCAT(apellidos,', ',nombres) texto FROM docentes WHERE estado=1 ORDER BY apellidos,nombres",null),
                c("id_curso","Curso","select",true,false,"SELECT id_curso id,nombre texto FROM cursos WHERE estado=1 ORDER BY nombre",null),
                c("id_seccion","Sección","select",true,false,"SELECT s.id_seccion id,CONCAT(g.nombre,' ',s.nombre,' - ',s.anio) texto FROM secciones s INNER JOIN grados g ON g.id_grado=s.id_grado WHERE s.estado=1 ORDER BY s.anio DESC,g.orden,s.nombre",null),
                c("id_periodo","Periodo","select",true,false,"SELECT id_periodo id,nombre texto FROM periodos WHERE estado=1 ORDER BY anio DESC",null),
                c("estado","Estado","checkbox",false,false,null,null)
            ),
            Arrays.asList(col("docente","Docente"),col("curso","Curso"),col("seccion","Sección"),col("periodo","Periodo"),col("estado","Estado"))
        ));

        MODULOS.put("horarios", new Modulo(
            "horarios","Horarios","horarios","id_horario",
            "SELECT h.id_horario,h.dia_semana,h.hora_inicio,h.hora_fin,c.nombre curso,CONCAT(g.nombre,' ',s.nombre) seccion,CONCAT(d.apellidos,', ',d.nombres) docente,h.aula FROM horarios h INNER JOIN asignaciones a ON a.id_asignacion=h.id_asignacion INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado INNER JOIN docentes d ON d.id_docente=a.id_docente ORDER BY FIELD(h.dia_semana,'LUNES','MARTES','MIERCOLES','JUEVES','VIERNES'),h.hora_inicio",
            Arrays.asList(
                c("id_asignacion","Asignación","select",true,false,"SELECT a.id_asignacion id,CONCAT(c.nombre,' - ',g.nombre,' ',s.nombre,' - ',d.apellidos,', ',d.nombres) texto FROM asignaciones a INNER JOIN cursos c ON c.id_curso=a.id_curso INNER JOIN secciones s ON s.id_seccion=a.id_seccion INNER JOIN grados g ON g.id_grado=s.id_grado INNER JOIN docentes d ON d.id_docente=a.id_docente WHERE a.estado=1 ORDER BY c.nombre",null),
                c("dia_semana","Día","select",true,false,null,"LUNES:Lunes|MARTES:Martes|MIERCOLES:Miércoles|JUEVES:Jueves|VIERNES:Viernes"),
                c("hora_inicio","Hora de inicio","time",true,false,null,null),
                c("hora_fin","Hora de fin","time",true,false,null,null),
                c("aula","Aula","text",true,false,null,null)
            ),
            Arrays.asList(col("dia_semana","Día"),col("hora_inicio","Inicio"),col("hora_fin","Fin"),col("curso","Curso"),col("seccion","Sección"),col("docente","Docente"),col("aula","Aula"))
        ));
    }

    public static Modulo obtener(String clave) {
        return MODULOS.get(clave);
    }

    public static Map<String, Modulo> todos() {
        return MODULOS;
    }
}