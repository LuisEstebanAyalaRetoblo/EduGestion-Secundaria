package Servlet;

import DAO.CrudDAO;
import Modelo.Campo;
import Modelo.Modulo;
import Modelo.Opcion;
import Util.Catalogo;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/app/admin/crud")
public class CrudServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        String clave=request.getParameter("m");
        Modulo modulo=Catalogo.obtener(clave);

        if(modulo==null) {
            response.sendRedirect(request.getContextPath()+"/app/dashboard");
            return;
        }

        CrudDAO dao=new CrudDAO();
        String accion=request.getParameter("accion");

        if("eliminar".equals(accion)) {
            int id=entero(request.getParameter("id"));
            boolean correcto=id>0 && dao.eliminar(modulo,id);
            response.sendRedirect(request.getContextPath()+"/app/admin/crud?m="+clave+"&resultado="+(correcto?"eliminado":"error"));
            return;
        }

        Map<String,List<Opcion>> opciones=new LinkedHashMap<>();

        for(Campo campo:modulo.getCampos()) {
            opciones.put(campo.getNombre(),dao.opciones(campo));
        }

        request.setAttribute("modulo",modulo);
        request.setAttribute("registros",dao.listar(modulo));
        request.setAttribute("opciones",opciones);

        if("editar".equals(accion)) {
            request.setAttribute("registro",dao.obtener(modulo,entero(request.getParameter("id"))));
        }

        request.getRequestDispatcher("/WEB-INF/vistas/admin/crud.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String clave=request.getParameter("m");
        Modulo modulo=Catalogo.obtener(clave);

        if(modulo==null) {
            response.sendRedirect(request.getContextPath()+"/app/dashboard");
            return;
        }

        int id=entero(request.getParameter("id"));
        Map<String,String> datos=new LinkedHashMap<>();

        for(Campo campo:modulo.getCampos()) {
            String valor="checkbox".equals(campo.getTipo())
                ?(request.getParameter(campo.getNombre())==null?"0":"1")
                :request.getParameter(campo.getNombre());

            if(campo.isRequerido() && (valor==null || valor.trim().isEmpty())) {
                response.sendRedirect(request.getContextPath()+"/app/admin/crud?m="+clave+"&resultado=incompleto");
                return;
            }

            if(id==0 && "password".equals(campo.getTipo()) && (valor==null || valor.trim().isEmpty())) {
                response.sendRedirect(request.getContextPath()+"/app/admin/crud?m="+clave+"&resultado=incompleto");
                return;
            }

            datos.put(campo.getNombre(),valor);
        }

        boolean correcto=new CrudDAO().guardar(modulo,id,datos);
        response.sendRedirect(request.getContextPath()+"/app/admin/crud?m="+clave+"&resultado="+(correcto?"guardado":"error"));
    }

    private int entero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch(Exception e) {
            return 0;
        }
    }
}