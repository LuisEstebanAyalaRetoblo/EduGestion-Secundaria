package Servlet;

import DAO.AcademicoDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/horario")
public class HorarioServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        HttpSession sesion=request.getSession();
        String rol=String.valueOf(sesion.getAttribute("rol"));
        AcademicoDAO dao=new AcademicoDAO();

        if("DIRECTOR".equals(rol)) {
            request.setAttribute("horario",dao.horarioTodos());
        }

        if("DOCENTE".equals(rol)) {
            request.setAttribute("horario",dao.horarioDocente((Integer)sesion.getAttribute("idDocente")));
        }

        if("ESTUDIANTE".equals(rol)) {
            request.setAttribute("horario",dao.horarioEstudiante((Integer)sesion.getAttribute("idEstudiante")));
        }

        request.getRequestDispatcher("/WEB-INF/vistas/horario.jsp").forward(request,response);
    }
}