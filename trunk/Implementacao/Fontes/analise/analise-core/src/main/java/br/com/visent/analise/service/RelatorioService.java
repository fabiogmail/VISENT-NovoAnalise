package br.com.visent.analise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.visent.analise.entity.Agenda;
import br.com.visent.analise.entity.Compartilhamento;
import br.com.visent.analise.entity.Favorito;
import br.com.visent.analise.entity.Intervalo;
import br.com.visent.analise.entity.IntervaloPreDefinido;
import br.com.visent.analise.entity.Periodo;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.entity.ValoresFiltro;
import br.com.visent.analise.entity.ValoresTreeSalvos;

@Stateless
public class RelatorioService extends AbstractService {
	
	@Inject private ExecucaoService execucaoService;

	public Map<String, List<Relatorio>> getRelatoriosSimplesPorTipo(Usuario usuario) {
		List<Relatorio> relatorios = dao.getRelatoriosSimples(usuario);
		return relatorios.stream()
				.collect(Collectors.groupingBy(r -> r.getTipoRelatorio().getNome(), Collectors.toList()));
	}
	
	public Map<String, List<Relatorio>> getRelatoriosSimplesHistoricoPorTipo(Usuario usuario) {
		List<Relatorio> relatorios = dao.getRelatoriosSimples(usuario);
		return relatorios.stream()
				.filter(r -> r.getTipoRelatorio().getAlgoritmo().intValue() != 2)
				.collect(Collectors.groupingBy(r -> r.getTipoRelatorio().getNome(), Collectors.toList()));
	}
	
	public List<Relatorio> getRelatorios(Usuario usuario) {
		Usuario usuarioBD = dao.find(Usuario.class, usuario.getId());
		List<Relatorio> relatorios = usuarioBD.getRelatorios().stream().collect(Collectors.toList());
		List<Favorito> favoritos = usuarioBD.getFavoritos().stream().collect(Collectors.toList());
		relatorios.forEach(relatorio -> {
			Optional<Favorito> optional = favoritos.stream().filter(favorito -> favorito.getRelatorio().getId().equals(relatorio.getId())).findFirst();
			if (optional.isPresent()) {
				relatorio.setIdFavorito(optional.get().getId());
			}
		});
		return relatorios;
	}
	
	public List<Compartilhamento> getRelatoriosCompartilhados(Usuario usuario) {
		Usuario usuarioBD = dao.find(Usuario.class, usuario.getId());
		List<Compartilhamento> compartilhamentos = usuarioBD.getCompartilhamentos().stream().collect(Collectors.toList());
		List<Favorito> favoritos = usuarioBD.getFavoritos().stream().collect(Collectors.toList());
		compartilhamentos.forEach(compartilhamento -> {
			Optional<Favorito> optional = favoritos.stream().filter(favorito -> favorito.getRelatorio().getId().equals(compartilhamento.getRelatorio().getId())).findFirst();
			if (optional.isPresent()) {
				compartilhamento.getRelatorio().setIdFavorito(optional.get().getId());
			}
		});
		return compartilhamentos;
	}
	
	public List<Favorito> getRelatoriosFavoritos(Usuario usuario) {
		return dao.find(Usuario.class, usuario.getId()).getFavoritos().stream().collect(Collectors.toList());
	}

	public Favorito addFavorito(Long idRelatorio, Usuario usuario) {
		Relatorio relatorio = dao.find(Relatorio.class, idRelatorio);
		validarRelatorioDonoCompartilhamento(relatorio, usuario);
		Favorito favorito = new Favorito();
		favorito.setRelatorio(relatorio);
		favorito.setUsuario(usuario);
		dao.persist(favorito);
		return favorito;
	}

	public void removerFavorito(Long idFavorito, Usuario usuario) {
		Favorito favorito = dao.find(Favorito.class, idFavorito);
		validarRelatorioDonoCompartilhamento(favorito.getRelatorio(), usuario);
		dao.remove(favorito);
	}

	public void removerRelatorios(List<Long> idsRelatorios, Usuario usuario) {
		idsRelatorios.stream()
			.map(id -> dao.find(Relatorio.class, id))
			.forEach(relatorio -> {
				validarRelatorioDono(relatorio, usuario);
				dao.remove(relatorio);
			});
	}
	
	public void salvarConfiguracao(Relatorio relatorio, Usuario usuario) {
		List<Intervalo> intervalosEspecificos = relatorio.getPeriodo().getIntervalosEspecificos();
		if (intervalosEspecificos != null) {
			intervalosEspecificos.forEach(i->i.setPeriodo(relatorio.getPeriodo()));
		}
		relatorio.getValoresFiltros().forEach(val -> val.setRelatorio(relatorio));
		relatorio.getValoresTreeSalvos().forEach(val -> val.setRelatorio(relatorio));
		if (relatorio.getId() == null) {
			dao.persist(relatorio);
		} else {
			validarRelatorioDono(relatorio, usuario);
			dao.merge(doMerge(relatorio));
			atualizarAgendas(relatorio);
		}
	}

	private void atualizarAgendas(Relatorio relatorio) {
		List<Agenda> agendas = dao.getAgendasAtivasPorRelatorio(relatorio.getId(), relatorio.getUsuario());
		if (!agendas.isEmpty()) {
			String json = execucaoService.getJSONAgenda(relatorio.getId(), relatorio.getUsuario());
			agendas.forEach(agenda -> {
				agenda.setJson(json);
				dao.merge(agenda);
			});
		}
	}

	private Relatorio doMerge(Relatorio relatorio) {
		Relatorio relatorioPersistent = dao.find(Relatorio.class, relatorio.getId());
		
		relatorioPersistent.setNome(relatorio.getNome());
		relatorioPersistent.setTipoRelatorio(relatorio.getTipoRelatorio());
		relatorioPersistent.setTipoTecnologia(relatorio.getTipoTecnologia());
		relatorioPersistent.setColunas(relatorio.getColunas());
		relatorioPersistent.getPeriodo().setDataEspecifica(relatorio.getPeriodo().getDataEspecifica());
		relatorioPersistent.getPeriodo().setDataEspecificaComparativa(relatorio.getPeriodo().getDataEspecificaComparativa());
		relatorioPersistent.getPeriodo().setDataPreDefinida(relatorio.getPeriodo().getDataPreDefinida());
		relatorioPersistent.getPeriodo().setDataPreDefinidaComparativa(relatorio.getPeriodo().getDataPreDefinidaComparativa());
		
		relatorioPersistent.getPeriodo().getIntervalosEspecificos().clear();
		relatorioPersistent.getPeriodo().getIntervalosEspecificos().addAll(relatorio.getPeriodo().getIntervalosEspecificos());
		List<Intervalo> intervalosEspecificos = relatorioPersistent.getPeriodo().getIntervalosEspecificos();
		if (intervalosEspecificos != null) {
			intervalosEspecificos.forEach(i->i.setPeriodo(relatorioPersistent.getPeriodo()));
		}
		
		relatorioPersistent.getPeriodo().getIntervalosPreDefinidos().clear();
		relatorioPersistent.getPeriodo().getIntervalosPreDefinidos().addAll(relatorio.getPeriodo().getIntervalosPreDefinidos());
		
		relatorioPersistent.getValoresFiltros().clear();
		relatorioPersistent.getValoresFiltros().addAll(relatorio.getValoresFiltros());
		
		relatorioPersistent.getValoresTreeSalvos().clear();
		relatorioPersistent.getValoresTreeSalvos().addAll(relatorio.getValoresTreeSalvos());
		
		return relatorioPersistent;
	}

	public Relatorio getRelatorio(Long id, Usuario usuario) {
		Relatorio relatorio = dao.find(Relatorio.class, id);
		validarRelatorioDonoCompartilhamento(relatorio, usuario);
		// inicializar as listas
		relatorio.getPeriodo().getIntervalosEspecificos().stream().count();
		relatorio.getPeriodo().getIntervalosPreDefinidos().stream().count();
		relatorio.getValoresFiltros().stream().count();
		relatorio.getValoresTreeSalvos().stream()
			.forEach(valorTreeSalvo -> {
				Optional<String> optional = Optional.ofNullable(dao.getNomeValorTree(valorTreeSalvo.getTree()));
				if (optional.isPresent()) {
					valorTreeSalvo.setNome(optional.get());
				}
			});
		return relatorio;
	}
	
	public void compartilharRelatorio(Long idRelatorio, List<Long> idsUsuarios, Usuario usuario) {
		Relatorio relatorio = dao.find(Relatorio.class, idRelatorio);
		validarRelatorioDono(relatorio, usuario);
		relatorio.getCompartilhamentos().clear();
		idsUsuarios.forEach(idUsuario -> {
			Compartilhamento compartilhamento = new Compartilhamento();
			Usuario usuarioComp = new Usuario();
			usuarioComp.setId(idUsuario);
			compartilhamento.setRelatorio(relatorio);
			compartilhamento.setUsuario(usuarioComp);
			relatorio.getCompartilhamentos().add(compartilhamento);
		});
		dao.merge(relatorio);
	}
	
	public void clonarRelatorio(Long idRelatorio, Usuario usuario) {
		Relatorio relatorio = dao.find(Relatorio.class, idRelatorio);
		validarRelatorioDonoCompartilhamento(relatorio, usuario);
		Relatorio clone = new Relatorio();
		
		clone.setUsuario(usuario);
		
		clone.setNome(relatorio.getNome());
		clone.setTipoRelatorio(relatorio.getTipoRelatorio());
		clone.setTipoTecnologia(relatorio.getTipoTecnologia());
		clone.setColunas(relatorio.getColunas());
		clone.setDataUltimaExecucao(relatorio.getDataUltimaExecucao());
		clone.setTempoUltimaExecucao(relatorio.getTempoUltimaExecucao());
		
		clone.setPeriodo(new Periodo());
		clone.getPeriodo().setDataEspecifica(relatorio.getPeriodo().getDataEspecifica());
		clone.getPeriodo().setDataEspecificaComparativa(relatorio.getPeriodo().getDataEspecificaComparativa());
		clone.getPeriodo().setDataPreDefinida(relatorio.getPeriodo().getDataPreDefinida());
		clone.getPeriodo().setDataPreDefinidaComparativa(relatorio.getPeriodo().getDataPreDefinidaComparativa());
		
		clone.getPeriodo().setIntervalosEspecificos(new ArrayList<>());
		relatorio.getPeriodo().getIntervalosEspecificos().forEach(intervalo -> {
			Intervalo intervaloClone = new Intervalo();
			intervaloClone.setDuracao(intervalo.getDuracao());
			intervaloClone.setGranularidade(intervalo.getGranularidade());
			intervaloClone.setHoraInicial(intervalo.getHoraInicial());
			intervaloClone.setPeriodo(clone.getPeriodo());
			clone.getPeriodo().getIntervalosEspecificos().add(intervaloClone);
		});
		
		clone.getPeriodo().setIntervalosPreDefinidos(new ArrayList<>());
		relatorio.getPeriodo().getIntervalosPreDefinidos().forEach(intervaloPreDefinido -> {
			IntervaloPreDefinido reference = dao.find(IntervaloPreDefinido.class, intervaloPreDefinido.getId());
			clone.getPeriodo().getIntervalosPreDefinidos().add(reference);
		});
		
		clone.setValoresFiltros(new ArrayList<>());
		relatorio.getValoresFiltros().forEach(valoresFiltro -> {
			ValoresFiltro valoresClone = new ValoresFiltro();
			valoresClone.setFiltro(valoresFiltro.getFiltro());
			valoresClone.setInterfaceFiltro(valoresFiltro.getInterfaceFiltro());
			valoresClone.setValor(valoresFiltro.getValor());
			valoresClone.setRelatorio(clone);
			clone.getValoresFiltros().add(valoresClone);
		});
		
		clone.setValoresTreeSalvos(new ArrayList<>());
		relatorio.getValoresTreeSalvos().forEach(valoresTreeSalvos -> {
			ValoresTreeSalvos valoresClone = new ValoresTreeSalvos();
			valoresClone.setFiltro(valoresTreeSalvos.getFiltro());
			valoresClone.setTree(valoresTreeSalvos.getTree());
			valoresClone.setRelatorio(clone);
			clone.getValoresTreeSalvos().add(valoresClone);
		});
		
		dao.persist(clone);
	}
	
	public Map<String, Object> getInfosCompartilhamento(Long idRelatorio, Usuario usuario) {
		Relatorio relatorio = dao.find(Relatorio.class, idRelatorio);
		validarRelatorioDono(relatorio, usuario);
		Map<String, Object> infos = new HashMap<>();
		infos.put("perfisUsuarios", getPerfisUsuarios(usuario));
		infos.put("usuarios", getUsuariosCompartilhamento(idRelatorio));
		return infos;
	}
	
	private Map<String, List<Usuario>> getPerfisUsuarios(Usuario usuario) {
		List<Usuario> usuarios = dao.findAll(Usuario.class);
		return usuarios.stream()
			.filter(u -> !u.getId().equals(usuario.getId()))
			.collect(Collectors.groupingBy(u -> u.getPerfil().getNome(), Collectors.toList()));
	}
	
	private List<Usuario> getUsuariosCompartilhamento(Long idRelatorio) {
		Compartilhamento filtro = new Compartilhamento();
		Relatorio relatorioFiltro = new Relatorio();
		relatorioFiltro.setId(idRelatorio);
		filtro.setRelatorio(relatorioFiltro);
		return dao.findByFilter(filtro).stream().map(c->c.getUsuario()).collect(Collectors.toList());
	}
	
}
