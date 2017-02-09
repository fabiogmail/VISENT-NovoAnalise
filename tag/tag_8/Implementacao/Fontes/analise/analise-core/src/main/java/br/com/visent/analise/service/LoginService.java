package br.com.visent.analise.service;

import java.util.List;

import javax.ejb.Stateless;

import br.com.visent.analise.entity.Perfil;
import br.com.visent.analise.entity.Usuario;

@Stateless
public class LoginService extends AbstractService {
	
	public Usuario login(String nomeUsuario, String nomePerfil) {
		Perfil filtroPerfil = new Perfil();
		filtroPerfil.setNome(nomePerfil);
		Usuario filtroUsuario = new Usuario();
		filtroUsuario.setNome(nomeUsuario);
		filtroUsuario.setPerfil(filtroPerfil);
		List<Usuario> usuarios = dao.findByFilter(filtroUsuario);
		if (!usuarios.isEmpty()) {
			return usuarios.get(0); 
		}
		return null;
	}

}
