package Filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/app/*")
public class AccesoFilter implements Filter {
    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        HttpSession sesion=req.getSession(false);
        if(sesion==null || sesion.getAttribute("idUsuario")==null) {
            res.sendRedirect(req.getContextPath()+"/index.jsp?sesion=expirada");
            return;
        }
        String rol=String.valueOf(sesion.getAttribute("rol"));
        String ruta=req.getRequestURI().substring(req.getContextPath().length());
        boolean permitido=true;
        if(ruta.startsWith("/app/admin/")) permitido="DIRECTOR".equals(rol);
        if(ruta.startsWith("/app/docente/")) permitido="DIRECTOR".equals(rol) || "DOCENTE".equals(rol);
        if(ruta.startsWith("/app/estudiante/")) permitido="DIRECTOR".equals(rol) || "ESTUDIANTE".equals(rol);
        if(!permitido) {
            res.sendRedirect(req.getContextPath()+"/app/dashboard?acceso=denegado");
            return;
        }
        chain.doFilter(request,response);
    }

    public void destroy() {}
}