package br.com.visent.analise.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.com.visent.analise.dao.CDRViewDAO;
import br.com.visent.analise.entity.CampoRegistro;
import br.com.visent.analise.entity.Filtro;
import br.com.visent.analise.entity.InterfaceAba;
import br.com.visent.analise.entity.InterfaceFiltro;
import br.com.visent.analise.entity.Perfil;
import br.com.visent.analise.entity.PerfilPermissoes;
import br.com.visent.analise.entity.TipoTecnologia;
import br.com.visent.analise.entity.ValoresCampo;
import br.com.visent.analise.entity.ValoresTree;
import br.com.visent.analise.enums.ComponenteFiltro;
import excecao.ConexaoException;

@Stateless
public class ConfiguracaoService extends AbstractService {

	@Inject private CDRViewDAO cdrview;
	
	public String getColunas(Long idTipoRel, Long idTipoTec, Perfil perfil) throws ConexaoException {
		List<String> tecnologias = null;
		TipoTecnologia tipoTecnologia = dao.find(TipoTecnologia.class, idTipoTec);
		
		PerfilPermissoes permissoes = perfil.getPermissoes();
		if (permissoes != null) {
			List<String> permissoesTecnologias = Arrays.asList(permissoes.getPermissoesTecnologias().split(";"));
			tecnologias = tipoTecnologia.getTecnologias().stream()
				.filter(tecnologia -> permissoesTecnologias.contains(tecnologia.getId().toString()))
				.map(t->t.getNome()).collect(Collectors.toList());
		} else {
			tecnologias = tipoTecnologia.getTecnologias().stream()
					.map(t->t.getNome()).collect(Collectors.toList());
		}
		
		return cdrview.getIndicadores(idTipoRel.intValue(), tecnologias);
	}

	public List<InterfaceAba> getFiltros(Long idTipoRel, Long idTipoTec) {
		
		InterfaceAba abaFiltrosPrimarios = getAbaFiltrosPrimarios();
		
		List<InterfaceAba> abas = dao.getAbasRaizes(idTipoRel, idTipoTec);
		
		Stream<InterfaceFiltro> filtrosFilhos = abas.stream()
			.flatMap(aba->aba.getInterfaceFiltros().stream());
		
		Stream<InterfaceFiltro> filtrosAbasFilhas = abas.stream()
			.flatMap(aba->aba.getAbasFilhas().stream())
			.flatMap(aba->aba.getInterfaceFiltros().stream());
		
		organizarFiltros(filtrosFilhos);
		organizarFiltros(filtrosAbasFilhas);
		
		abas.add(0, abaFiltrosPrimarios);
		
		dao.clear();
		
		return abas;
	}
	
	private InterfaceAba getAbaFiltrosPrimarios() {
		List<Filtro> filtros = dao.getFiltrosPrimarios();
		InterfaceAba aba = new InterfaceAba();
		aba.setNome("Filtros Primários");
		aba.setInterfaceFiltros(new ArrayList<>());
		for (Filtro filtro : filtros) {
			InterfaceFiltro interfaceFiltro = new InterfaceFiltro();
			interfaceFiltro.setFiltro(filtro);
			interfaceFiltro.setLabel(filtro.getNomeExibicao());
			interfaceFiltro.setComponente(ComponenteFiltro.TREE);
			aba.getInterfaceFiltros().add(interfaceFiltro);
		}
		return aba;
	}

	private void organizarFiltros(Stream<InterfaceFiltro> stream) {
		Consumer<Filtro> adicionarDefinicaoFiltro = adicionarDefinicaoFiltro();
		Consumer<Filtro> filtrarValoresPelaTecnologia = filtrarValoresPelaTecnologia();
		stream
		.map(interfaceFiltros -> interfaceFiltros.getFiltro())
		.peek(adicionarDefinicaoFiltro)
//		.peek(filtro -> filtro.getValoresTree().stream().count())
		.filter(filtro -> filtro.getCampoRegistro() != null)
		.forEach(filtrarValoresPelaTecnologia);
	}
	
	private Consumer<Filtro> adicionarDefinicaoFiltro() {
		return filtro -> {
			if (filtro.getDefinicaoFiltro() != null) {
				if (filtro.getNome().toLowerCase().contains("paises")) {
					System.out.println();
				}
				String json = cdrview.getValoresDefinicaoFiltro(filtro.getDefinicaoFiltro());
				if (json == null || json.isEmpty() || json.startsWith("null")) return;
				JsonReader jsonReader = Json.createReader(new StringReader(json));
				JsonObject jsonObject = jsonReader.readObject();
				jsonReader.close();
				JsonArray jsonArray = jsonObject.getJsonArray("Filtro");
				if (filtro.getCampoRegistro() == null) {
					filtro.setCampoRegistro(new CampoRegistro());
				}
				if (filtro.getCampoRegistro().getValores() == null) {
					filtro.getCampoRegistro().setValores(new ArrayList<>());
				}
				ValoresCampo valoresCampo = new ValoresCampo();
				StringBuilder sb = new StringBuilder(); 
				jsonArray.forEach(jsonValue -> {
					JsonObject obj = (JsonObject) jsonValue;
					String nome = obj.getString("nome");
					String valor = obj.getString("valor");
					sb.append(nome+"="+valor+";");
				});
				valoresCampo.setValor(sb.toString());
				filtro.getCampoRegistro().getValores().add(valoresCampo);
			}
		};
	}
	
	private Consumer<Filtro> filtrarValoresPelaTecnologia() {
		return filtro -> {
			if (filtro.getTecnologia() != null && filtro.getTecnologia().getId().intValue() != 10000) {
				Long idTecnologiaFiltro = filtro.getTecnologia().getId();
				List<ValoresCampo> valores = filtro.getCampoRegistro().getValores();
				List<ValoresCampo> valoresFiltrados = valores.stream().filter((ValoresCampo v)->{
					return v.getTecnologia() == null || v.getTecnologia().getId().equals(idTecnologiaFiltro);
				}).collect(Collectors.toList());
				filtro.getCampoRegistro().setValores(valoresFiltrados);
			} else {
				filtro.getCampoRegistro().getValores().stream().count();
			}
		};
	}

	public List<ValoresTree> getValoresTree(Long idFiltro, Long idPerfil) {
		List<ValoresTree> valoresTree = dao.getValoresTreeRaizes(idFiltro, idPerfil);
		valoresTree.forEach(this::fetchAll);
		return valoresTree;
	}
	
	public ValoresTree getValoresTreeByID(Long id) {
		ValoresTree valoresTree = dao.find(ValoresTree.class, id);
		fetchAll(valoresTree);
		return valoresTree;
	}
	
	private void fetchAll(ValoresTree tree) {
	    for(ValoresTree child : tree.getValoresFilhos()) {
	        fetchAll(child);
	    }
	}
	
}
