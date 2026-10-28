package Servlet;

import DAO.UsuarioSistemaDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/cuenta/clave")
public class CambiarClaveServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/cambiar_clave.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession sesion=request.getSession();
        String actual=request.getParameter("actual");
        String nueva=request.getParameter("nueva");
        String confirmar=request.getParameter("confirmar");

        if(actual==null || nueva==null || confirmar==null || nueva.length()<8 || !nueva.equals(confirmar)) {
            response.sendRedirect(request.getContextPath()+"/app/cuenta/clave?resultado=invalido");
            return;
        }

        boolean correcto=new UsuarioSistemaDAO().cambiarClave(
            (Integer)sesion.getAttribute("idUsuario"),
            actual,
            nueva
        );

        response.sendRedirect(request.getContextPath()+"/app/cuenta/clave?resultado="+(correcto?"guardado":"actual"));
    }
}