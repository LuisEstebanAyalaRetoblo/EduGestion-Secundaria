package Demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Catálogo ficticio compartido por todas las pantallas de la exposición.
 * No abre conexiones JDBC ni modifica datos; los identificadores son sintéticos.
 */
public final class DatosDemo {
    private static final String[] CURSOS = {"Matemática", "Comunicación", "Ciencia y Tecnología", "Ciencias Sociales", "Inglés"};
    private static final String[] DOCENTES = {"Docente Álamo", "Docente Brisa", "Docente Cedro", "Docente Duna", "Docente Estrella"};
    private final List<Map<String, Object>> estudiantes = new ArrayList<>();
    private final List<Map<String, Object>> docentes = new ArrayList<>();
    private final List<Map<String, Object>> secciones = new ArrayList<>();
    private final List<Map<String, Object>> cursos = new ArrayList<>();
    private final List<Map<String, Object>> horarios = new ArrayList<>();

    /** Construye un escenario pequeño con cinco grados y diez estudiantes ficticios. */
    public DatosDemo() {
        String[] nombres = {"Estudiante Alfa", "Estudiante Beta", "Estudiante Gamma", "Estudiante Delta", "Estudiante Épsilon", "Estudiante Zeta", "Estudiante Eta", "Estudiante Theta", "Estudiante Iota", "Estudiante Kappa"};
        for (int i = 0; i < nombres.length; i++) {
            int grado = i / 2 + 1;
            estudiantes.add(fila("id", i + 1, "codigo", String.format("DEMO-%03d", i + 1), "nombre", nombres[i],
                "grado", grado, "seccion", grado + "° A", "estado", "Activo", "apoderado", "Apoderado ficticio " + (i + 1),
                "asistencia", i == 2 ? "TARDANZA" : i == 5 ? "FALTA" : i == 8 ? "JUSTIFICADA" : "PRESENTE"));
        }
        for (int i = 0; i < CURSOS.length; i++) {
            docentes.add(fila("id", i + 1, "nombre", DOCENTES[i], "especialidad", CURSOS[i], "secciones", "1° A a 5° A", "estado", "Activo"));
            cursos.add(fila("nombre", CURSOS[i], "codigo", "CUR-0" + (i + 1), "docente", DOCENTES[i], "horas", 4));
            secciones.add(fila("grado", i + 1, "nombre", (i + 1) + "° A", "aula", "A-10" + (i + 1), "capacidad", 30, "ocupacion", 2, "tutor", DOCENTES[i]));
            // Rotación por día: cada docente y sección ocupa un único bloque diario.
            for (int dia = 0; dia < 5; dia++) {
                int curso = (i + dia) % 5;
                horarios.add(fila("dia", Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes").get(dia),
                    "hora", "08:00 – 09:00", "seccion", (i + 1) + "° A", "aula", "A-10" + (i + 1),
                    "curso", CURSOS[curso], "docente", DOCENTES[curso]));
            }
        }
    }

    private static Map<String, Object> fila(Object... valores) {
        Map<String, Object> resultado = new LinkedHashMap<>();
        for (int i = 0; i < valores.length; i += 2) resultado.put((String) valores[i], valores[i + 1]);
        return Collections.unmodifiableMap(resultado);
    }

    /** @return estudiantes sintéticos, sin documentos ni contactos personales. */
    public List<Map<String, Object>> getEstudiantes() { return Collections.unmodifiableList(estudiantes); }
    /** @return docentes ficticios y especialidades compartidos por todos los módulos. */
    public List<Map<String, Object>> getDocentes() { return Collections.unmodifiableList(docentes); }
    /** @return cinco secciones con capacidad oficial y ocupación de la muestra. */
    public List<Map<String, Object>> getSecciones() { return Collections.unmodifiableList(secciones); }
    /** @return catálogo académico de la demostración. */
    public List<Map<String, Object>> getCursos() { return Collections.unmodifiableList(cursos); }
    /** @return programación semanal ficticia, sin conflictos dentro de esta muestra. */
    public List<Map<String, Object>> getHorarios() { return Collections.unmodifiableList(horarios); }

    /**
     * Calcula exclusivamente ejemplos; nunca sustituye el futuro servicio académico.
     * @param trimestre número validado entre 1 y 3
     * @return notas y promedio ponderado; los pendientes conservan promedio nulo
     */
    public List<Map<String, Object>> calificaciones(int trimestre) {
        List<Map<String, Object>> filas = new ArrayList<>();
        for (int i = 0; i < estudiantes.size(); i++) {
            BigDecimal tarea = BigDecimal.valueOf(12 + (i + trimestre) % 7);
            BigDecimal practica = BigDecimal.valueOf(13 + (i + trimestre) % 6);
            BigDecimal examen = BigDecimal.valueOf(11 + (i + trimestre) % 8);
            boolean pendiente = trimestre == 3 && i == 1;
            BigDecimal promedio = pendiente ? null : tarea.multiply(new BigDecimal("0.30"))
                .add(practica.multiply(new BigDecimal("0.30"))).add(examen.multiply(new BigDecimal("0.40")))
                .setScale(2, RoundingMode.HALF_UP);
            filas.add(fila("nombre", estudiantes.get(i).get("nombre"), "seccion", estudiantes.get(i).get("seccion"),
                "tarea", tarea, "practica", practica, "examen", pendiente ? null : examen, "promedio", promedio,
                "estado", pendiente ? "PROVISIONAL" : trimestre == 1 ? "DEFINITIVO" : "BORRADOR"));
        }
        return Collections.unmodifiableList(filas);
    }
}
