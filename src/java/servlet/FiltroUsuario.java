package servlet;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FiltroUsuario implements Filter  {
    Login login = new Login();
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter function.
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request2 = (HttpServletRequest) request;

        String nomeLogado = (String) request2.getSession().getAttribute("nome");
        String verif = (String) request2.getSession().getAttribute("verif");
        String existenciaStatus = (String) request2.getSession().getAttribute("existencia_status");

        if (verif == null && (existenciaStatus != null)){
            if (nomeLogado != null){
                filterChain.doFilter(request, response);
            }
            else{
                request2.getSession().invalidate();
                ((HttpServletResponse)response).sendRedirect("index.jsp");
            }
        }
        else{
            request2.getSession().invalidate();
            ((HttpServletResponse)response).sendRedirect("index.jsp");
        }
    }

    public void destroy() {
        // Filter function.
    }
}