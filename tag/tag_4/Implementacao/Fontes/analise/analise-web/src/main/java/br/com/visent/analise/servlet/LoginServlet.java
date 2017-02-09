package br.com.visent.analise.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.service.LoginService;
import br.com.visent.analise.util.Constants;

@WebServlet(name="loginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject private LoginService loginService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		login(req, resp);
	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String nomeUsuario = req.getParameter("usuario");
		String nomePerfil = req.getParameter("perfil");
		
		Usuario usuario = loginService.login(nomeUsuario, nomePerfil);
		
		if (usuario != null) {
			req.getSession().setAttribute(Constants.Session.USUARIO, usuario);
			resp.sendRedirect(req.getRequestURL().toString().replace("/login", ""));
		} else {
			resp.sendError(Status.UNAUTHORIZED.getStatusCode());
		}
		
	}

}
