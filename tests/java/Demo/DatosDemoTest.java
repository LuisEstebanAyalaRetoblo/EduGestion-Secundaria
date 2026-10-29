package Demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Pruebas sin dependencias: consistencia de muestra, ponderaciones e inmutabilidad. */
public final class DatosDemoTest {
    private DatosDemoTest() { }

    /** @param args no utilizados; lanza AssertionError si una invariante no se cumple. */
    public static void main(String[] args) {
        DatosDemo datos = new DatosDemo();
        verificar(datos.getEstudiantes().size() == 10, "Diez estudiantes");
        Set<Object> ids = new HashSet<>();
        for (Map<String, Object> estudiante : datos.getEstudiantes()) {
            verificar(ids.add(estudiante.get("id")), "Identificador único");
            int grado = (Integer) estudiante.get("grado");
            verificar(grado >= 1 && grado <= 5, "Solo secundaria");
        }
        for (int trimestre = 1; trimestre <= 3; trimestre++) {
            List<Map<String, Object>> notas = datos.calificaciones(trimestre);
            for (Map<String, Object> nota : notas) {
                if ("PROVISIONAL".equals(nota.get("estado"))) {
                    verificar(nota.get("examen") == null && nota.get("promedio") == null, "Ausencia no es cero");
                } else {
                    BigDecimal promedio = ((BigDecimal) nota.get("tarea")).multiply(new BigDecimal("0.30"))
                        .add(((BigDecimal) nota.get("practica")).multiply(new BigDecimal("0.30")))
                        .add(((BigDecimal) nota.get("examen")).multiply(new BigDecimal("0.40")))
                        .setScale(2, RoundingMode.HALF_UP);
                    verificar(promedio.equals(nota.get("promedio")), "Ponderación 30/30/40");
                    verificar(promedio.signum() >= 0 && promedio.compareTo(new BigDecimal("20")) <= 0, "Escala 0–20");
                }
            }
        }
        Set<String> profesores = new HashSet<>(), aulas = new HashSet<>(), secciones = new HashSet<>();
        for (Map<String, Object> horario : datos.getHorarios()) {
            String bloque = horario.get("dia") + ":" + horario.get("hora") + ":";
            verificar(profesores.add(bloque + horario.get("docente")), "Docente sin conflicto en muestra");
            verificar(aulas.add(bloque + horario.get("aula")), "Aula sin conflicto en muestra");
            verificar(secciones.add(bloque + horario.get("seccion")), "Sección sin conflicto en muestra");
        }
        try {
            datos.getEstudiantes().get(0).put("nombre", "alteración");
            throw new AssertionError("El catálogo debe ser inmutable");
        } catch (UnsupportedOperationException esperado) { /* La muestra no admite escrituras. */ }
        System.out.println("OK: muestra coherente, 3 trimestres, promedio ponderado, provisional sin cero, horario sin conflictos e inmutabilidad.");
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }
}
