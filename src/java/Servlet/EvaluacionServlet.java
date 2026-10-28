package Servlet;

import DAO.AcademicoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/docente/evaluaciones")
public class EvaluacionServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        HttpSession sesion=request.getSession();
        boolean director="DIRECTOR".equals(sesion.getAttribute("rol"));
        int idDocente=director?0:(Integer)sesion.getAttribute("idDocente");
        AcademicoDAO dao=new AcademicoDAO();
        String accion=request.getParameter("accion");
        int id=entero(request.getParameter("id"));

        if("eliminar".equals(accion) && id>0) {
            Map<String,Object> evaluacion=dao.evaluacion(id);
            boolean permitido=evaluacion!=null && (director || numero(evaluacion.get("id_docente"))==idDocente);
            boolean correcto=permitido && dao.eliminarEvaluacion(id);
            response.sendRedirect(request.getContextPath()+"/app/docente/evaluaciones?resultado="+(correcto?"eliminado":"denegado"));
            return;
        }

        request.setAttribute("asignaciones",director?dao.asignacionesTodas():dao.asignacionesDocente(idDocente));
        request.setAttribute("evaluaciones",dao.evaluaciones(idDocente,director));

        if("editar".equals(accion) && id>0) {
            Map<String,Object> evaluacion=dao.evaluacion(id);

            if(evaluacion!=null && (director || numero(evaluacion.get("id_docente"))==idDocente)) {
                request.setAttribute("evaluacion",evaluacion);
            }
        }

        request.getRequestDispatcher("/WEB-INF/vistas/docente/evaluaciones.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession sesion=request.getSession();
        boolean director="DIRECTOR".equals(sesion.getAttribute("rol"));
        int idDocente=director?0:(Integer)sesion.getAttribute("idDocente");
        int id=entero(request.getParameter("id"));
        int idAsignacion=entero(request.getParameter("idAsignacion"));
        AcademicoDAO dao=new AcademicoDAO();
        boolean permitido=dao.asignacionPermitida(idAsignacion,idDocente,director);

        if(id>0) {
            Map<String,Object> actual=dao.evaluacion(id);
            permitido=permitido && actual!=null && (director || numero(actual.get("id_docente"))==idDocente);
        }

        boolean correcto=false;

        try {
            String titulo=request.getParameter("titulo");
            String tipo=request.getParameter("tipo");
            Date fecha=Date.valueOf(request.getParameter("fecha"));
            BigDecimal ponderacion=new BigDecimal(request.getParameter("ponderacion"));
            boolean estado=request.getParameter("estado")!=null;

            correcto=permitido
                && titulo!=null
                && !titulo.trim().isEmpty()
                && ponderacion.compareTo(BigDecimal.ZERO)>0
                && ponderacion.compareTo(new BigDecimal("100"))<=0
                && dao.guardarEvaluacion(id,idAsignacion,titulo.trim(),tipo,fecha,ponderacion,estado);
        } catch(Exception e) {
            correcto=false;
        }

        response.sendRedirect(request.getContextPath()+"/app/docente/evaluaciones?resultado="+(correcto?"guardado":"error"));
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