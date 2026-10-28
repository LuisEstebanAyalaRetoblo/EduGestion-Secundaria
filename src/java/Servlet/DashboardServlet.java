package Servlet;

import DAO.AcademicoDAO;
import DAO.AnuncioDAO;
import DAO.DashboardDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        HttpSession sesion=request.getSession();
        String rol=String.valueOf(sesion.getAttribute("rol"));
        AcademicoDAO academicoDAO=new AcademicoDAO();
        AnuncioDAO anuncioDAO=new AnuncioDAO();

        if("DIRECTOR".equals(rol)) {
            request.setAttribute("dashboard",new DashboardDAO().obtener());
            request.setAttribute("anuncios",anuncioDAO.administrar((Integer)sesion.getAttribute("idUsuario"),true));
            request.getRequestDispatcher("/WEB-INF/vistas/dashboard_director.jsp").forward(request,response);
            return;
        }

        if("DOCENTE".equals(rol)) {
            int idDocente=(Integer)sesion.getAttribute("idDocente");
            request.setAttribute("perfil",academicoDAO.perfilDocente(idDocente));
            request.setAttribute("asignaciones",academicoDAO.asignacionesDocente(idDocente));
            request.setAttribute("horario",academicoDAO.horarioDocente(idDocente));
            request.setAttribute("anuncios",anuncioDAO.visibles(rol));
            request.getRequestDispatcher("/WEB-INF/vistas/dashboard_docente.jsp").forward(request,response);
            return;
        }

        int idEstudiante=(Integer)sesion.getAttribute("idEstudiante");
        request.setAttribute("perfil",academicoDAO.perfilEstudiante(idEstudiante));
        request.setAttribute("notas",academicoDAO.notasEstudiante(idEstudiante));
        request.setAttribute("horario",academicoDAO.horarioEstudiante(idEstudiante));
        request.setAttribute("anuncios",anuncioDAO.visibles(rol));
        request.getRequestDispatcher("/WEB-INF/vistas/dashboard_estudiante.jsp").forward(request,response);
    }
}