package Servlet;

import DAO.AcademicoDAO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/docente/asistencias")
public class AsistenciaServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        HttpSession sesion=request.getSession();
        boolean director="DIRECTOR".equals(sesion.getAttribute("rol"));
        int idDocente=director?0:(Integer)sesion.getAttribute("idDocente");
        AcademicoDAO dao=new AcademicoDAO();
        int idAsignacion=entero(request.getParameter("idAsignacion"));
        Date fecha=fecha(request.getParameter("fecha"));

        request.setAttribute("asignaciones",director?dao.asignacionesTodas():dao.asignacionesDocente(idDocente));
        request.setAttribute("idAsignacion",idAsignacion);
        request.setAttribute("fecha",fecha.toString());

        if(idAsignacion>0 && dao.asignacionPermitida(idAsignacion,idDocente,director)) {
            request.setAttribute("estudiantes",dao.registroAsistencia(idAsignacion,fecha));
        }

        request.getRequestDispatcher("/WEB-INF/vistas/docente/asistencias.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession sesion=request.getSession();
        boolean director="DIRECTOR".equals(sesion.getAttribute("rol"));
        int idDocente=director?0:(Integer)sesion.getAttribute("idDocente");
        int idAsignacion=entero(request.getParameter("idAsignacion"));
        int idMatricula=entero(request.getParameter("idMatricula"));
        Date fecha=fecha(request.getParameter("fecha"));
        String estado=request.getParameter("estado");
        String observacion=request.getParameter("observacion");
        AcademicoDAO dao=new AcademicoDAO();

        boolean permitido=dao.asignacionPermitida(idAsignacion,idDocente,director)
            && dao.matriculaValidaAsignacion(idAsignacion,idMatricula);

        boolean correcto=permitido
            && ("PRESENTE".equals(estado)
            || "TARDANZA".equals(estado)
            || "FALTA".equals(estado)
            || "JUSTIFICADA".equals(estado))
            && dao.guardarAsistencia(idAsignacion,idMatricula,fecha,estado,observacion);

        response.sendRedirect(request.getContextPath()+"/app/docente/asistencias?idAsignacion="+idAsignacion+"&fecha="+fecha+"&resultado="+(correcto?"guardado":"error"));
    }

    private int entero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch(Exception e) {
            return 0;
        }
    }

    private Date fecha(String valor) {
        try {
            return Date.valueOf(valor);
        } catch(Exception e) {
            return Date.valueOf(LocalDate.now());
        }
    }
}