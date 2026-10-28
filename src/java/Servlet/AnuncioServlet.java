package Servlet;

import DAO.AnuncioDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/app/anuncios")
public class AnuncioServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        HttpSession sesion=request.getSession();
        String rol=String.valueOf(sesion.getAttribute("rol"));
        int idUsuario=(Integer)sesion.getAttribute("idUsuario");
        boolean director="DIRECTOR".equals(rol);
        boolean editor=director || "DOCENTE".equals(rol);
        AnuncioDAO dao=new AnuncioDAO();
        String accion=request.getParameter("accion");
        int id=entero(request.getParameter("id"));

        if("eliminar".equals(accion) && editor && id>0) {
            Map<String,Object> actual=dao.obtener(id);
            boolean permitido=actual!=null && (director || numero(actual.get("id_usuario"))==idUsuario);
            boolean correcto=permitido && dao.eliminar(id);
            response.sendRedirect(request.getContextPath()+"/app/anuncios?resultado="+(correcto?"eliminado":"denegado"));
            return;
        }

        if(editor) {
            request.setAttribute("anuncios",dao.administrar(idUsuario,director));

            if("editar".equals(accion) && id>0) {
                Map<String,Object> actual=dao.obtener(id);

                if(actual!=null && (director || numero(actual.get("id_usuario"))==idUsuario)) {
                    request.setAttribute("anuncio",actual);
                }
            }
        } else {
            request.setAttribute("anuncios",dao.visibles(rol));
        }

        request.setAttribute("editor",editor);
        request.getRequestDispatcher("/WEB-INF/vistas/anuncios.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession sesion=request.getSession();
        String rol=String.valueOf(sesion.getAttribute("rol"));
        boolean director="DIRECTOR".equals(rol);
        boolean editor=director || "DOCENTE".equals(rol);
        int idUsuario=(Integer)sesion.getAttribute("idUsuario");
        int id=entero(request.getParameter("id"));
        AnuncioDAO dao=new AnuncioDAO();
        boolean permitido=editor;

        if(id>0) {
            Map<String,Object> actual=dao.obtener(id);
            permitido=permitido && actual!=null && (director || numero(actual.get("id_usuario"))==idUsuario);
        }

        boolean correcto=false;

        try {
            String titulo=request.getParameter("titulo");
            String contenido=request.getParameter("contenido");
            Date publicacion=Date.valueOf(request.getParameter("fechaPublicacion"));
            String expira=request.getParameter("fechaExpiracion");
            Date expiracion=expira==null || expira.trim().isEmpty()?null:Date.valueOf(expira);
            String destino=request.getParameter("destino");
            boolean estado=request.getParameter("estado")!=null;

            correcto=permitido
                && titulo!=null
                && !titulo.trim().isEmpty()
                && contenido!=null
                && !contenido.trim().isEmpty()
                && dao.guardar(id,titulo.trim(),contenido.trim(),publicacion,expiracion,destino,idUsuario,estado);
        } catch(Exception e) {
            correcto=false;
        }

        response.sendRedirect(request.getContextPath()+"/app/anuncios?resultado="+(correcto?"guardado":"error"));
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