package br.com.visent.analise.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.visent.analise.dao.AnaliseDAO;
import br.com.visent.analise.entity.Usuario;

@WebServlet(name="fakeServlet", urlPatterns="/fakeLogin")
public class FakeLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject private AnaliseDAO dao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("usuarios", dao.findAll(Usuario.class));
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

}
