package co.udea.iw.cors;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

public class ResponseCorsFilter implements Filter {
	 public ResponseCorsFilter() {
	    }

	    public void destroy() {
	    }

	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {

	        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
	        chain.doFilter(request, response);
	    }

	    public void init(FilterConfig fConfig) throws ServletException {
	    }
}
