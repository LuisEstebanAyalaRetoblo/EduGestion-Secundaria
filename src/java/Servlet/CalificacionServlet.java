package Servlet;

import DAO.AcademicoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/docente/calificaciones")
public class CalificacionServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        int idEvaluacion=entero(request.getParameter("idEvaluacion"));

        if(idEvaluacion==0) {
            response.sendRedirect(request.getContextPath()+"/app/docente/evaluaciones");
            return;
        }

        HttpSession sesion=request.getSession();
        boolean director="DIRECTOR".equals(sesion.getAttribute("rol"));
        int idDocente=director?0:(Integer)sesion.getAttribute("idDocente");
        AcademicoDAO dao=new AcademicoDAO();
        Map<String,Object> evaluacion=dao.evaluacion(idEvaluacion);

        if(evaluacion==null || !director && numero(evaluacion.get("id_docente"))!=idDocente) {
            response.sendRedirect(request.getContextPath()+"/app/dashboard?acceso=denegado");
            return;
        }

        request.setAttribute("evaluacion",evaluacion);
        request.setAttribute("matriculas",dao.matriculasAsignacion(numero(evaluacion.get("id_asignacion"))));
        request.setAttribute("calificaciones",dao.calificacionesEvaluacion(idEvaluacion));
        request.getRequestDispatcher("/WEB-INF/vistas/docente/calificaciones.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int idEvaluacion=entero(request.getParameter("idEvaluacion"));
        int idMatricula=entero(request.getParameter("idMatricula"));
        HttpSession sesion=request.getSession();
        boolean director="DIRECTOR".equals(sesion.getAttribute("rol"));
        int idDocente=director?0:(Integer)sesion.getAttribute("idDocente");
        AcademicoDAO dao=new AcademicoDAO();
        Map<String,Object> evaluacion=dao.evaluacion(idEvaluacion);

        boolean permitido=evaluacion!=null
            && (director || numero(evaluacion.get("id_docente"))==idDocente)
            && dao.matriculaValida(idEvaluacion,idMatricula);

        boolean correcto=false;

        try {
            BigDecimal nota=new BigDecimal(request.getParameter("nota"));
            String observacion=request.getParameter("observacion");

            correcto=permitido
                && nota.compareTo(BigDecimal.ZERO)>=0
                && nota.compareTo(new BigDecimal("20"))<=0
                && dao.guardarCalificacion(idEvaluacion,idMatricula,nota,observacion);
        } catch(Exception e) {
            correcto=false;
        }

        response.sendRedirect(request.getContextPath()+"/app/docente/calificaciones?idEvaluacion="+idEvaluacion+"&resultado="+(correcto?"guardado":"error"));
    }

    private int entero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch(Exception e) {
            return 0;
        }
    }

    private int numero(Object valor) {
        return valor instanceof Number?((Number)valor).intValue():entero(String.valueOf(valor));
    }
}