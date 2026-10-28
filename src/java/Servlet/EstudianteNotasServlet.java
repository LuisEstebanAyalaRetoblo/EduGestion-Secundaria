package Servlet;

import DAO.AcademicoDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/estudiante/notas")
public class EstudianteNotasServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        HttpSession sesion=request.getSession();

        if(!"ESTUDIANTE".equals(sesion.getAttribute("rol"))) {
            response.sendRedirect(request.getContextPath()+"/app/dashboard");
            return;
        }

        int idEstudiante=(Integer)sesion.getAttribute("idEstudiante");
        request.setAttribute("notas",new AcademicoDAO().notasEstudiante(idEstudiante));
        request.getRequestDispatcher("/WEB-INF/vistas/estudiante/notas.jsp").forward(request,response);
    }
}