package br.com.visent.analise.filter;

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
import javax.ws.rs.core.Response.Status;

import br.com.visent.analise.util.Constants;

@WebFilter("/index.jsp")
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		
		if (session == null || session.getAttribute(Constants.Session.USUARIO) == null) {
			resp.sendError(Status.UNAUTHORIZED.getStatusCode());
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		
	}

}
