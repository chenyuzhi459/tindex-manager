package io.sugo.http.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


public class CrossDomainSupportFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Enumeration<String> enums = filterConfig.getInitParameterNames();

        while (enums.hasMoreElements()) {
            String param = (String) enums.nextElement();
            System.out.println(param + ":" + filterConfig.getInitParameter(param));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        filterChain.doFilter(servletRequest,response);

    }

    @Override
    public void destroy() {

    }
}
