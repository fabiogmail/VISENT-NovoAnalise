package br.com.visent.analise.service;

import javax.inject.Inject;

import br.com.visent.analise.dao.AnaliseDAO;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.entity.TipoRelatorio;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.exception.PermissaoException;

public abstract class AbstractService {

	@Inject
	protected AnaliseDAO dao;
	
	protected void validarRelatorioDono(Relatorio relatorio, Usuario usuario) {
		if (!relatorio.getUsuario().getId().equals(usuario.getId())) {
			throw new PermissaoException("validacao.permissao", true);
		}
	}
	
	protected void validarRelatorioDonoCompartilhamento(Relatorio relatorio, Usuario usuario) {
		boolean ok = false;
		if (relatorio.getUsuario().getId().equals(usuario.getId())) {
			ok = true;
		}
		if (!ok) {
			ok = relatorio.getCompartilhamentos().stream()
				.anyMatch(compartilhamento -> compartilhamento.getUsuario().getId().equals(usuario.getId()));
		}
		if (!ok) {
			throw new PermissaoException("validacao.permissao", true);
		}
	}
	
	public TipoRelatorio getTipoRelatorioDetalhe() {
		TipoRelatorio filtro = new TipoRelatorio();
		filtro.setAlgoritmo(2L);
		return dao.findByFilter(filtro).get(0);
	}
	
}
