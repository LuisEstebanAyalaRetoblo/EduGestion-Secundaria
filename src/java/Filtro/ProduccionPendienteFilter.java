package Filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Bloquea los flujos reales mientras no exista la integración académica segura.
 * La demo no constituye autenticación y jamás habilita las rutas /app o /login.
 * Retirar este bloqueo requiere una entrega posterior con pruebas de seguridad.
 */
@WebFilter(urlPatterns = {"/app/*", "/login"})
public class ProduccionPendienteFilter implements Filter {
    @Override public void init(FilterConfig config) { }
    /** Impide conexiones JDBC y operaciones reales, incluso con cookies anteriores. */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse http = (HttpServletResponse) response;
        http.setStatus(503);
        http.setHeader("Cache-Control", "no-store");
        http.setContentType("text/plain;charset=UTF-8");
        http.getWriter().write("Integración institucional pendiente. Utilice la demostración pública en /WebEducativa/demo/inicio. No se ha ejecutado ninguna operación real.");
    }
    @Override public void destroy() { }
}
