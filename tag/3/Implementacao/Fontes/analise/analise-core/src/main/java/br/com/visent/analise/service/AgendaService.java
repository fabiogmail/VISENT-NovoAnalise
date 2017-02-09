package br.com.visent.analise.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.visent.analise.dao.CDRViewDAO;
import br.com.visent.analise.entity.Agenda;
import br.com.visent.analise.entity.DataExecucao;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.enums.AgDiaSemana;
import br.com.visent.analise.enums.AgExportacao;
import br.com.visent.analise.enums.AgPeriodicidade;
import br.com.visent.analise.enums.AgSemanaOcorrencia;
import br.com.visent.analise.enums.AgStatus;
import br.com.visent.analise.exception.ValidacaoException;
import br.com.visent.analise.util.ResultadoExecucao;

@Stateless
public class AgendaService extends AbstractService {
	
	@Inject
	private ExecucaoService execucaoService; 
	
	@Inject 
	private CDRViewDAO cdrview;

	public List<Agenda> getAgendas(Usuario usuario, boolean historico) {
		List<String> nomesAgendas = dao.getNomesAgendasAtivas(usuario, historico);
		return nomesAgendas.stream()
				.map(nome -> {
					Agenda agenda = new Agenda();
					agenda.setNome(nome);
					return agenda;
				}).collect(Collectors.toList());
	}
	
	public Agenda getConf(String nome, Usuario usuario, boolean historico) {
		List<Agenda> agendas = dao.getAgendasAtivasPorNome(nome, usuario, historico);
		Agenda agendaConf = agendas.get(0);
		List<Relatorio> relatorios = agendas.stream()
			.map(agenda -> {
				Relatorio relatorio = new Relatorio();
				relatorio.setId(agenda.getIdRelatorioCfg());
				return relatorio;
			}).collect(Collectors.toList());
		agendaConf.setRelatorios(relatorios);
		if (agendas.size() > 1) {
			Agenda agenda1 = agendas.get(0);
			Agenda agenda2 = agendas.get(1);
			Duration intervaloExecucao = Duration.between(agenda1.getInicio(), agenda2.getInicio());
			agendaConf.setIntervaloExecucao(intervaloExecucao);
		}
		agendaConf.setExportacoes(criarExportacoes(agendaConf));
		return agendaConf;
	}

	private List<String> criarExportacoes(Agenda agendaConf) {
		if (agendaConf.getExportacao() == null) return null;
		AgExportacao agExportacao = AgExportacao.getByOrdinal(agendaConf.getExportacao());
		if (agExportacao != null) {
			return Arrays.asList(agExportacao.getValores().split(","));
		}
		return null;
	}

	public void salvar(Agenda agenda, Usuario usuario, boolean historico) {
		cancelarAgendas(agenda, usuario, historico);
		validarEConfigurarConf(agenda);
		List<Agenda> agendas = criarNovasAgendas(agenda, usuario);
		agendas.forEach(dao::persist);
	}

	public void cancelarAgendas(String nome, Usuario usuario, boolean historico) {
		Agenda agenda = new Agenda();
		agenda.setNome(nome);
		cancelarAgendas(agenda, usuario, historico);
	}
	
	private void cancelarAgendas(Agenda agenda, Usuario usuario, boolean historico) {
		dao.cancelarAgendas(agenda, usuario, historico);
	}
	
	private void validarEConfigurarConf(Agenda agendaConf) {
		if (agendaConf.getRelatorios().isEmpty()) {
			throw new ValidacaoException("Selecione pelo menos um relatório");
		}
		LocalDateTime data = agendaConf.getInicio();
		if (data == null) {
			throw new ValidacaoException("Data inválida");
		}
		AgPeriodicidade periodicidade = agendaConf.getPeriodicidade();
		switch (periodicidade) {
			case SEMANA:
				if (agendaConf.getSemanasDeOcorrencia().isEmpty()) {
					throw new ValidacaoException("Selecione pelo menos uma ocorrência");
				}
				if (agendaConf.getDiasDaSemana().isEmpty()) {
					throw new ValidacaoException("Selecione pelo menos um dia da semana");
				}
				String semana = agendaConf.getSemanasDeOcorrencia().getValues().get(0);
				String diaSemana = agendaConf.getDiasDaSemana().getValues().get(0);
				int ordinal = AgSemanaOcorrencia.valueOf(semana).getOrdem();
				DayOfWeek dayOfWeek = AgDiaSemana.valueOf(diaSemana).getDayOfWeek();
				agendaConf.setInicio(data.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek)));
				break;
			case MES:
				if (agendaConf.getDiasDoMes().isEmpty()) {
					throw new ValidacaoException("Selecione pelo menos um dia");
				}
				String dia = agendaConf.getDiasDoMes().getValues().get(0);
				agendaConf.setInicio(data.withDayOfMonth(Integer.parseInt(dia)));
				break;
			default:
		}
		verificarExportacao(agendaConf);
		agendaConf.setStatus(AgStatus.ESPERA);
		if (agendaConf.getPeriodo() == null) {
			agendaConf.setPeriodo(0);
		}
	}

	private void verificarExportacao(Agenda agendaConf) {
		List<String> exportacoes = agendaConf.getExportacoes();
		if (exportacoes.isEmpty()) {
			agendaConf.setExportacao(AgExportacao.VAZIO.ordinal());
		} else if (exportacoes.contains("HTML") && exportacoes.contains("CSV") && exportacoes.contains("TXT")) {
			agendaConf.setExportacao(AgExportacao.HTML_CSV_TXT.ordinal());
		} else if (exportacoes.contains("HTML") && exportacoes.contains("CSV")) {
			agendaConf.setExportacao(AgExportacao.HTML_CSV.ordinal());
		} else if (exportacoes.contains("CSV") && exportacoes.contains("TXT")) {
			agendaConf.setExportacao(AgExportacao.CSV_TXT.ordinal());
		} else if (exportacoes.contains("HTML")) {
			agendaConf.setExportacao(AgExportacao.HTML.ordinal());
		} else if (exportacoes.contains("CSV")) {
			agendaConf.setExportacao(AgExportacao.CSV.ordinal());
		} else if (exportacoes.contains("TXT")) {
			agendaConf.setExportacao(AgExportacao.TXT.ordinal());
		} else if (exportacoes.contains("FTP")) {
			agendaConf.setExportacao(AgExportacao.FTP.ordinal());
		} else if (exportacoes.contains("BD")) {
			agendaConf.setExportacao(AgExportacao.BD.ordinal());
		} else if (exportacoes.contains("TERMINAIS")) {
			agendaConf.setExportacao(AgExportacao.TERMINAIS.ordinal());
		}
	}

	private List<Agenda> criarNovasAgendas(Agenda agendaConf, Usuario usuario) {
		List<Agenda> agendas = new ArrayList<>();
		Duration intervaloExecucao = agendaConf.getIntervaloExecucao();
		LocalDateTime inicio = agendaConf.getInicio();
		long seconds = intervaloExecucao.getSeconds();
		for (int i=0; i<agendaConf.getRelatorios().size(); i++) {
			Relatorio relatorio = agendaConf.getRelatorios().get(i);
			Agenda agenda = new Agenda();
			agenda.setNome(agendaConf.getNome());
			agenda.setStatus(agendaConf.getStatus());
			agenda.setPeriodicidade(agendaConf.getPeriodicidade());
			agenda.setInicio(inicio.plusSeconds((i*seconds)));
			agenda.setTermino(agendaConf.getTermino());
			agenda.setHoraInicio(agendaConf.getHoraInicio());
			agenda.setHoraTermino(agendaConf.getHoraTermino());
			agenda.setDiasDaSemana(agendaConf.getDiasDaSemana());
			agenda.setDiasDoMes(agendaConf.getDiasDoMes());
			agenda.setSemanasDeOcorrencia(agendaConf.getSemanasDeOcorrencia());
			agenda.setIdRelatorioCfg(relatorio.getId());
			agenda.setIdTipoRelatorio(dao.getIdTipoRelatorio(relatorio.getId()));
			agenda.setUsuario(usuario.getNome());
			agenda.setIdPerfil(usuario.getPerfil().getId());
			agenda.setExportacao(agendaConf.getExportacao());
			agenda.setJson(execucaoService.getJSONAgenda(relatorio.getId(), usuario));
			agenda.setPeriodo(agendaConf.getPeriodo());
			agenda.setHistorico(agendaConf.isHistorico());
			agenda.setLatencia(agendaConf.getLatencia());
			agenda.setInfinito(agendaConf.getInfinito());
			agendas.add(agenda);
		}
		
		return agendas;
	}
	
	public List<DataExecucao> getExecucoes(String nome, Usuario usuario) {
		List<Agenda> agendas = dao.getAgendasAtivasPorNome(nome, usuario, false);
		List<DataExecucao> execucoes = agendas.stream()
			.flatMap(agenda -> agenda.getExecucoes().stream())
			.filter(execucao -> execucao.isExecutou())
			.collect(Collectors.toList());
		return execucoes;
	}

	public Map<String, Object> getResultadoExecucao(Long idDataExecucao, Usuario usuario) {
		Map<String, Object> map = new HashMap<>();
		DataExecucao dataExecucao = dao.find(DataExecucao.class, idDataExecucao);
		Relatorio relatorio = dao.find(Relatorio.class, dataExecucao.getAgenda().getIdRelatorioCfg());
		List<String> linhas = cdrview.getRelatorioAgendado(dataExecucao);
		ResultadoExecucao resultadoExecucao = new ResultadoExecucao(relatorio, linhas, null);
		map.put("relatorio", relatorio);
		map.put("resultados", Arrays.asList(resultadoExecucao));
		return map;
	}
	
}
