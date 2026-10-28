package Servlet;

import DAO.ReporteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/app/admin/reportes")
public class ReporteServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        ReporteDAO dao=new ReporteDAO();
        request.setAttribute("matriculas",dao.matriculasPorSeccion());
        request.setAttribute("promedios",dao.promedioPorCurso());
        request.setAttribute("asistencias",dao.resumenAsistencia());
        request.setAttribute("docentes",dao.cargaDocente());
        request.getRequestDispatcher("/WEB-INF/vistas/admin/reportes.jsp").forward(request,response);
    }
}