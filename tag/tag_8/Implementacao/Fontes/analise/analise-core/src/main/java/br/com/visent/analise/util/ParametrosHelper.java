package br.com.visent.analise.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.visent.analise.dao.AnaliseDAO;
import br.com.visent.analise.entity.ParametroSistema;

@Stateless
public class ParametrosHelper {

	@Inject private AnaliseDAO dao;
	
	public static final String CDRVIEW_SERVER = "CDRVIEW_SERVER"; 
	public static final String CDRVIEW_PORT_CONTROLE = "CDRVIEW_PORT_CONTROLE"; 
	public static final String CDRVIEW_PORT_PROCESSOS = "CDRVIEW_PORT_PROCESSOS"; 
	public static final String MAXIMO_IMPORTACAO_VALORES_TREE = "MAXIMO_IMPORTACAO_VALORES_TREE";
	public static final String MAXIMO_EXECUCOES_USUARIO = "MAXIMO_EXECUCOES_USUARIO";
	
	public ParametroSistema get(String parametro) {
		ParametroSistema parametroFilter = new ParametroSistema();
		parametroFilter.setNome(parametro);
		List<ParametroSistema> params = dao.findByFilter(parametroFilter);
		if (!params.isEmpty()) {
			return params.get(0); 
		}
		return null;
	}
	
	public Map<String, String> getTodos() {
		List<ParametroSistema> list = dao.findAll(ParametroSistema.class);
		return list.stream().collect(Collectors.toMap(ParametroSistema::getNome, ParametroSistema::getValor));
	}
	
}
