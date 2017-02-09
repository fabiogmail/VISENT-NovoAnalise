package br.com.visent.analise.ws;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.visent.analise.entity.Agenda;
import br.com.visent.analise.entity.DataExecucao;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.enums.AgDiaSemana;
import br.com.visent.analise.enums.AgPeriodicidade;
import br.com.visent.analise.enums.AgSemanaOcorrencia;
import br.com.visent.analise.service.AgendaService;
import br.com.visent.analise.service.RelatorioService;
import br.com.visent.analise.util.Constants;

@Path(Constants.Path.AGENDA)
public class AgendaWS extends AbstractWS {

	@Inject 
	private AgendaService agendaService;
	
	@Inject 
	private RelatorioService relatorioService;
	
	@GET
	@Path(Constants.Path.AGENDA_USUARIO)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Agenda> getAgendasUsuario() {
		return agendaService.getAgendas(getUsuario(), false);
	}
	
	@GET
	@Path(Constants.Path.AGENDA_HISTORICO)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Agenda> getHistoricos() {
		return agendaService.getAgendas(getUsuario(), true);
	}
	
	@GET
	@Path(Constants.Path.AGENDA_RELATORIOS)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<Relatorio>> getRelatoriosUsuario() {
		return relatorioService.getRelatoriosSimplesPorTipo(getUsuario());
	}
	
	@GET
	@Path(Constants.Path.AGENDA_RELATORIOS_HISTORICO)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<Relatorio>> getRelatoriosHistorico() {
		return relatorioService.getRelatoriosSimplesHistoricoPorTipo(getUsuario());
	}
	
	@GET
	@Path(Constants.Path.AGENDA_INFOS)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfosAgendamento() {
		Map<String, Object> infos = new HashMap<>();
		infos.put("diaSemana", AgDiaSemana.values());
		Function<AgPeriodicidade, Map<String, Object>> periodicidadeFunction = periodicidade -> {
			Map<String, Object> map = new HashMap<>();
			map.put("valor", periodicidade);
			map.put("descricao", periodicidade.getDescricao());
			return map;
		};
		infos.put("periodicidadeAgenda", Arrays.stream(AgPeriodicidade.valuesAgenda())
				.map(periodicidadeFunction).collect(Collectors.toList()));
		infos.put("periodicidadeHistorico", Arrays.stream(AgPeriodicidade.valuesHistorico())
				.map(periodicidadeFunction).collect(Collectors.toList()));
		infos.put("semanaOcorrencia", AgSemanaOcorrencia.values());
		infos.put("anos", getAnosAgenda());
		infos.put("meses", getMesesAgenda());
		return Response.ok(infos).build();
	}
	
	@GET
	@Path(Constants.Path.AGENDA_DIAS_ANO_MES)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiasDoMes(@PathParam("ano") int ano, @PathParam("mes") int mes) {
		YearMonth yearMonth = YearMonth.of(ano, mes);
		return Response.ok(yearMonth.lengthOfMonth()).build();
	}

	private List<Map<String, Object>> getMesesAgenda() {
		return Arrays.stream(Month.values())
			.map(month -> {
				Map<String, Object> map = new HashMap<>();
				map.put("valor", month.getValue());
				map.put("descricao", month.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
				return map;
			}).collect(Collectors.toList());
	}

	private List<Integer> getAnosAgenda() {
		List<Integer> anos = new ArrayList<>();
		Year year = Year.now().minusYears(1);
		for (int i=0; i<=20; i++) {
			year = year.plusYears(1);
			anos.add(year.getValue());
		}
		return anos;
	}
	
	@POST
	@Path(Constants.Path.AGENDA_SALVAR)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarAgenda(Agenda agenda) {
		agendaService.salvar(agenda, getUsuario(), false);
		return Response.ok().build();
	}
	
	@POST
	@Path(Constants.Path.AGENDA_HISTORICO_SALVAR)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarHistorico(Agenda agenda) {
		agendaService.salvar(agenda, getUsuario(), true);
		return Response.ok().build();
	}
	
	@GET
	@Path(Constants.Path.AGENDA_CONF)
	@Produces(MediaType.APPLICATION_JSON)
	public Agenda getConfiguracaoAgenda(@PathParam("nome") String nome) {
		return agendaService.getConf(nome, getUsuario(), false);
	}
	
	@GET
	@Path(Constants.Path.AGENDA_HISTORICO_CONF)
	@Produces(MediaType.APPLICATION_JSON)
	public Agenda getConfiguracaoHistorico(@PathParam("nome") String nome) {
		return agendaService.getConf(nome, getUsuario(), true);
	}
	
	@DELETE
	@Path(Constants.Path.AGENDA_CONF)
	public Response cancelarAgendas(@PathParam("nome") String nome) {
		agendaService.cancelarAgendas(nome, getUsuario(), false);
		return Response.ok().build();
	}
	
	@DELETE
	@Path(Constants.Path.AGENDA_HISTORICO_CONF)
	public Response cancelarHistoricos(@PathParam("nome") String nome) {
		agendaService.cancelarAgendas(nome, getUsuario(), true);
		return Response.ok().build();
	}
	
	@GET
	@Path(Constants.Path.AGENDA_EXECS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DataExecucao> getExecucoes(@PathParam("nome") String nome) {
		return agendaService.getExecucoes(nome, getUsuario());
	}
	
}
