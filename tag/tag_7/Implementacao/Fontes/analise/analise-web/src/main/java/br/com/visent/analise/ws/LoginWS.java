package br.com.visent.analise.ws;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.service.LoginService;
import br.com.visent.analise.util.Constants;

@Path(Constants.Path.LOGIN)
public class LoginWS extends AbstractWS {

	@Inject private LoginService loginService;
	
	@GET
	@Path(Constants.Path.LOGIN_SESSION)
	public Response criarSessao(@PathParam("usuario") String nomeUsuario, @PathParam("perfil") String nomePerfil) {
		
		Usuario usuario = loginService.login(nomeUsuario, nomePerfil);
		if (usuario != null) {
			getSession().setAttribute(Constants.Session.USUARIO, usuario);
			return Response.ok().build();
		} else {
			return Response.status(Status.UNAUTHORIZED).entity("Usuario/Perfil nao encontrado").build();
		}
		
	}
	
	@GET
	@Path(Constants.Path.LOGIN_LOGOUT)
	public Response invalidar() {
		getSession().invalidate();
		return Response.ok().build();
	}
	
}
