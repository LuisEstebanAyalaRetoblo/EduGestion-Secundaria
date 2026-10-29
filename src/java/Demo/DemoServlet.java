package Demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Presenta el escenario público y ficticio bajo /demo/* dentro del WAR original.
 * No autentica cuentas, no establece atributos de sesión de producción y no usa DAO.
 */
@WebServlet("/demo/*")
public class DemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DatosDemo datos = new DatosDemo();
    private static final List<String> PAGINAS = Arrays.asList("inicio", "estudiantes", "docentes", "matriculas", "cursos", "calificaciones", "asistencia", "horarios", "solicitudes", "anuncios", "reportes");
    private static final List<String> TITULOS = Arrays.asList("Panel de Dirección", "Estudiantes", "Docentes", "Matrículas", "Cursos y secciones", "Calificaciones", "Asistencia", "Horario semanal", "Solicitudes académicas", "Comunicados", "Reportes institucionales");

    /** Renderiza solo rutas permitidas y parámetros validados, sin persistencia. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruta = request.getPathInfo();
        String pagina = ruta == null || "/".equals(ruta) ? "inicio" : ruta.substring(1);
        int indice = PAGINAS.indexOf(pagina);
        if (indice < 0) { response.setStatus(404); response.setContentType("text/plain;charset=UTF-8"); response.getWriter().write("Pantalla de demostración no encontrada."); return; }
        int trimestre = numero(request.getParameter("trimestre"), 1, 3, 3);
        int detalle = numero(request.getParameter("detalle"), 1, 10, 0);
        request.setAttribute("pagina", pagina);
        request.setAttribute("titulo", TITULOS.get(indice));
        request.setAttribute("paginas", PAGINAS);
        request.setAttribute("titulos", TITULOS);
        request.setAttribute("datos", datos);
        request.setAttribute("trimestre", trimestre);
        request.setAttribute("notas", datos.calificaciones(trimestre));
        request.setAttribute("detalle", detalle);
        response.setHeader("Cache-Control", "no-store");
        request.getRequestDispatcher("/WEB-INF/vistas/demo/panel.jsp").forward(request, response);
    }

    /** Rechaza escrituras: el catálogo no representa una base de datos. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Allow", "GET, HEAD");
        response.sendError(405, "La demostración no acepta escrituras.");
    }

    private int numero(String valor, int minimo, int maximo, int defecto) {
        try { int numero = Integer.parseInt(valor); return numero >= minimo && numero <= maximo ? numero : defecto; }
        catch (NumberFormatException e) { return defecto; }
    }
}
