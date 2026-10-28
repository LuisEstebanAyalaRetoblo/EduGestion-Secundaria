package Servlet;

import DAO.PerfilDAO;
import DAO.UsuarioSistemaDAO;
import Modelo.UsuarioSistema;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        String usuario=request.getParameter("usuario");
        String clave=request.getParameter("clave");

        if(usuario==null || clave==null || usuario.trim().isEmpty() || clave.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()+"/index.jsp?error=campos");
            return;
        }

        UsuarioSistema cuenta=new UsuarioSistemaDAO().autenticar(usuario.trim(),clave);

        if(cuenta==null) {
            response.sendRedirect(request.getContextPath()+"/index.jsp?error=credenciales");
            return;
        }

        HttpSession anterior=request.getSession(false);

        if(anterior!=null) {
            anterior.invalidate();
        }

        HttpSession sesion=request.getSession(true);
        sesion.setMaxInactiveInterval(1800);
        sesion.setAttribute("idUsuario",cuenta.getIdUsuario());
        sesion.setAttribute("usuario",cuenta.getUsuario());
        sesion.setAttribute("rol",cuenta.getRol());

        PerfilDAO perfilDAO=new PerfilDAO();

        if("DOCENTE".equals(cuenta.getRol())) {
            int idDocente=perfilDAO.obtenerDocente(cuenta.getIdUsuario());

            if(idDocente==0) {
                sesion.invalidate();
                response.sendRedirect(request.getContextPath()+"/index.jsp?error=perfil");
                return;
            }

            sesion.setAttribute("idDocente",idDocente);
        }

        if("ESTUDIANTE".equals(cuenta.getRol())) {
            int idEstudiante=perfilDAO.obtenerEstudiante(cuenta.getIdUsuario());

            if(idEstudiante==0) {
                sesion.invalidate();
                response.sendRedirect(request.getContextPath()+"/index.jsp?error=perfil");
                return;
            }

            sesion.setAttribute("idEstudiante",idEstudiante);
        }

        response.sendRedirect(request.getContextPath()+"/app/dashboard");
    }
}