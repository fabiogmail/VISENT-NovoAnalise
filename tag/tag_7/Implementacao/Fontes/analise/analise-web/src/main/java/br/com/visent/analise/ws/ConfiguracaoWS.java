package br.com.visent.analise.ws;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.visent.analise.entity.InterfaceAba;
import br.com.visent.analise.entity.PerfilPermissoes;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.entity.TipoRelatorio;
import br.com.visent.analise.entity.TipoTecnologia;
import br.com.visent.analise.entity.ValoresTree;
import br.com.visent.analise.service.ConfiguracaoService;
import br.com.visent.analise.service.RelatorioService;
import br.com.visent.analise.util.Constants;
import br.com.visent.analise.util.SystemCache;
import excecao.ConexaoException;

@Path(Constants.Path.CONFIGURACAO)
public class ConfiguracaoWS extends AbstractWS {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracaoWS.class);

	@Inject private SystemCache cache;
	@Inject	private RelatorioService relatorioService;
	@Inject	private ConfiguracaoService configuracaoService;
	
	@GET
	@Path(Constants.Path.CONFIGURACAO_CACHE)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<?>> getConfiguracoesCache() {
		Map<String, List<?>> configuracoesCache = new HashMap<>();
		
		PerfilPermissoes permissoes = getUsuario().getPerfil().getPermissoes();
		if (permissoes != null) {
			List<String> permissoesRelatorios = Arrays.asList(permissoes.getPermissoesRelatorios().split(";"));
			List<String> permissoesTecnologias = Arrays.asList(permissoes.getPermissoesTecnologias().split(";"));
			
			List<TipoRelatorio> tiposRelatorio = cache.getTiposRelatorio().stream()
				.filter(tipoRelatorio -> permissoesRelatorios.contains(tipoRelatorio.getId().toString()))
				.collect(Collectors.toList());
			
			List<TipoTecnologia> tiposTecnologia = cache.getTecnologias().stream()
				.filter(tecnologia -> permissoesTecnologias.contains(tecnologia.getId().toString()))
				.map(tecnologia -> tecnologia.getTipoTecnologia())
				.distinct()
				.collect(Collectors.toList());
			
			configuracoesCache.put("tiposRelatorio", tiposRelatorio);
			configuracoesCache.put("tiposTecnologia", tiposTecnologia);
		} else {
			configuracoesCache.put("tiposRelatorio", cache.getTiposRelatorio());
			configuracoesCache.put("tiposTecnologia", cache.getTiposTecnologia());
		}
		configuracoesCache.put("datasPreDefinidas", cache.getDatasPreDefinidas());
		configuracoesCache.put("intervalosPreDefinidos", cache.getIntervalosPreDefinidos());
		return configuracoesCache;
	}
	
	@GET
	@Path(Constants.Path.CONFIGURACAO_COLUNAS)
	@Produces(MediaType.APPLICATION_JSON)
	public String getColunas(@PathParam("idTipoRel") Long idTipoRel, 
			@PathParam("idTipoTec") Long idTipoTec) {
		try {
			return configuracaoService.getColunas(idTipoRel, idTipoTec, getUsuario().getPerfil());
		} catch (ConexaoException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@GET
	@Path(Constants.Path.CONFIGURACAO_FILTROS) 
	@Produces(MediaType.APPLICATION_JSON)
	public List<InterfaceAba> getFiltros(@PathParam("idTipoRel") Long idTipoRel,
			@PathParam("idTipoTec") Long idTipoTec) {
		return configuracaoService.getFiltros(idTipoRel, idTipoTec);
	}
	
	@GET
	@Path(Constants.Path.CONFIGURACAO_FILTROS_TREE_ID) 
	@Produces(MediaType.APPLICATION_JSON)
	public List<ValoresTree> getValoresTree(@PathParam("idFiltro") Long idFiltro) {
		return configuracaoService.getValoresTree(idFiltro, getUsuario().getPerfil().getId());
	}
	
	@POST
	@Path(Constants.Path.CONFIGURACAO_SALVAR)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvarRelatorio(Relatorio relatorio) {
		relatorio.setUsuario(getUsuario());
		relatorioService.salvarConfiguracao(relatorio, getUsuario());
		return Response.ok(relatorio.getId()).build();
	}
	
}
