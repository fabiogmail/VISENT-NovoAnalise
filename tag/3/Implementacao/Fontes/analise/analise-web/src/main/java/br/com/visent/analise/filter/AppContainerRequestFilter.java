package br.com.visent.analise.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import br.com.visent.analise.util.Constants;
import br.com.visent.analise.util.Messages;

@Provider
public class AppContainerRequestFilter implements ContainerRequestFilter {

	@Context
    private HttpServletRequest servletRequest;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String path = requestContext.getUriInfo().getPath();
		if (!path.startsWith("/"+Constants.Path.LOGIN)) {
			HttpSession session = servletRequest.getSession(false);
			if (session == null || session.getAttribute(Constants.Session.USUARIO) == null) {
				requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(Messages.getString("validacao.sessao")).build());
			}
		}
	}

}
