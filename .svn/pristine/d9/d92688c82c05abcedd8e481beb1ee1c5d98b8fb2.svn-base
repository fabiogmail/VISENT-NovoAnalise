package br.com.visent.analise.ws;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import br.com.visent.analise.dao.AnaliseDAO;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.props.SystemProperties;
import br.com.visent.analise.util.Constants;

public class AbstractWS {

	@Inject @SystemProperties private ResourceBundle systemProperties;
	
	@Context 
	protected ServletContext servletContext;
	
	@Context
	protected HttpServletRequest request;
	
	@Inject
	protected AnaliseDAO dao;
	
	protected HttpSession getSession() {
		return request.getSession();
	}
	
	protected Object getSessionAttribute(String attribute) {
		return request.getSession().getAttribute(attribute);
	}
	
	protected Usuario getUsuario() {
		return (Usuario) getSessionAttribute(Constants.Session.USUARIO);
	}

}
