package br.com.visent.analise.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.visent.analise.dao.CDRViewDAO;
import br.com.visent.analise.entity.CampoRegistro;
import br.com.visent.analise.entity.DataPreDefinida;
import br.com.visent.analise.entity.Filtro;
import br.com.visent.analise.entity.InterfaceFiltro;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.entity.TipoRelatorio;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.entity.ValoresFiltro;
import br.com.visent.analise.entity.ValoresTree;
import br.com.visent.analise.entity.ValoresTreeSalvos;
import br.com.visent.analise.enums.ComparadorFiltro;
import br.com.visent.analise.util.DrillParams;
import br.com.visent.analise.util.ResultadoExecucao;
import br.com.visent.bean.ParametroJson;
import br.com.visent.bean.ParametroJson.ID_PARAMETOS_JN;

@Stateless
public class ExecucaoService extends AbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecucaoService.class);

	@Inject private CDRViewDAO cdrview;
	@Inject private RelatorioService relatorioService;
	
	public String getJSONExecucao(Long idRel, Usuario usuario) {
		Relatorio relatorio = relatorioService.getRelatorio(idRel, usuario);
		List<JsonObject> jsonObjects = construirJSONs(relatorio, usuario, null);
		List<String> jsons = jsonObjects.stream()
			.map(jsonObject -> jsonObjectToString(jsonObject))
			.collect(Collectors.toList());
		return jsons.toString();
	}
	
	public String getJSONAgenda(Long idRel, Usuario usuario) {
		Relatorio relatorio = relatorioService.getRelatorio(idRel, usuario);
		List<JsonObject> jsonObjects = construirJSONs(relatorio, usuario, null);
		List<String> jsons = jsonObjects.stream()
			.map(jsonObject -> jsonObjectToString(jsonObject))
			.collect(Collectors.toList());
		return jsons.get(0).toString();
	}
	
	public List<ResultadoExecucao> executar(Long idRel, Usuario usuario) {
		return executar(idRel, usuario, null);
	}
	
	public List<ResultadoExecucao> executar(Long idRel, Usuario usuario, DrillParams drillParams) {
		List<ResultadoExecucao> resultados = new ArrayList<>();
		Relatorio relatorio = relatorioService.getRelatorio(idRel, usuario);
		
		List<JsonObject> jsonObjects = construirJSONs(relatorio, usuario, drillParams);
		List<String> jsons = jsonObjects.stream()
			.map(jsonObject -> jsonObjectToString(jsonObject))
			.collect(Collectors.toList());
		
		jsons.forEach(LOGGER::debug);
		
		LocalDateTime dataExecucaoInicio = LocalDateTime.now();
		jsons.forEach(json -> {
			List<String> linhas = cdrview.executar(json);
			if (linhas != null) {
				resultados.add(new ResultadoExecucao(relatorio, linhas, drillParams));
			}
		});
		LocalDateTime dataExecucaoFim = LocalDateTime.now();
		Duration duracao = Duration.between(dataExecucaoInicio, dataExecucaoFim);
		relatorio.setDataUltimaExecucao(dataExecucaoInicio);
		relatorio.setTempoUltimaExecucao(duracao);
		dao.merge(relatorio);
		
		return resultados;
	}
	
	private String jsonObjectToString(JsonObject jsonObject) {
		Map<String, Object> properties = new HashMap<>();
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        
        StringWriter sw = new StringWriter();
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(jsonObject);
        jsonWriter.close();
        
        return sw.toString();
	}
	
	private List<JsonObject> construirJSONs(Relatorio relatorio, Usuario usuario, DrillParams drillParams) {
		
		List<JsonObjectBuilder> jsonObjectBuilders = new ArrayList<>();
		jsonObjectBuilders.add(Json.createObjectBuilder());
		if (relatorio.getPeriodo().getDataEspecificaComparativa() != null
				|| relatorio.getPeriodo().getDataPreDefinidaComparativa() != null) {
			jsonObjectBuilders.add(Json.createObjectBuilder());
		}
		
		final TipoRelatorio tipoRelatorio = (drillParams != null ? 
				getTipoRelatorioDetalhe() : relatorio.getTipoRelatorio());
		
		jsonObjectBuilders.forEach(jsonObjectBuilder -> {
			jsonObjectBuilder
				.add("NomeRelatorio", relatorio.getNome() + (drillParams != null ? " - Drill" : "") )
				.add("TipoRelatorio", tipoRelatorio.getId())
				.add("Algoritmo", tipoRelatorio.getAlgoritmo());
		});
		
		construirJSONData(jsonObjectBuilders, relatorio, drillParams);
		construirJSONColunas(jsonObjectBuilders, relatorio, drillParams);
		construirJSONFiltros(jsonObjectBuilders, relatorio, usuario, drillParams);
		construirJSONFiltrosTree(jsonObjectBuilders, relatorio);
		
		List<JsonObject> jsonObjects = jsonObjectBuilders.stream()
			.map(jsonObjectBuilder -> Json.createObjectBuilder().add("Relatorio", jsonObjectBuilder.build()).build())
			.collect(Collectors.toList());
		
		return jsonObjects;
	}
	
	private void construirJSONData(List<JsonObjectBuilder> jsons, Relatorio relatorio, DrillParams drillParams) {
		JsonArray periodos = construirJSONPeriodos(relatorio);
		if (drillParams != null) {
			int indicePeriodo = drillParams.getIndicePeriodo();
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			List<JsonValue> subList = periodos.subList(indicePeriodo, indicePeriodo+1);
			subList.forEach(arrayBuilder::add);
			periodos = arrayBuilder.build();
		}
		JsonObjectBuilder dataBuilder = construirData(relatorio.getPeriodo().getDataEspecifica(), relatorio.getPeriodo().getDataPreDefinida());
		dataBuilder.add("Periodos", periodos);
		jsons.get(0).add("Data", dataBuilder.build());
		
		if (jsons.size() > 1) {
			JsonObjectBuilder dataComparativaBuilder = construirData(relatorio.getPeriodo().getDataEspecificaComparativa(), 
					relatorio.getPeriodo().getDataPreDefinidaComparativa());
			dataComparativaBuilder.add("Periodos", periodos);
			jsons.get(1).add("Data", dataComparativaBuilder.build());
		}
	}
	
	private JsonObjectBuilder construirData(LocalDate dataEspecifica, DataPreDefinida dataPreDefinida) {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		if (dataEspecifica != null) {
			objectBuilder
				.add("id", 0)
				.add("nome", "")
				.add("ano", dataEspecifica.getYear())
				.add("dia", dataEspecifica.getDayOfMonth())
				.add("mes", dataEspecifica.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")))
				.add("tipo", 4);
		} else {
			objectBuilder
				.add("id", 0)
				.add("nome", dataPreDefinida.getNome())
				.add("ano", 0)
				.add("dia", 0)
				.add("mes", "")
				.add("tipo", 3);
		}
		return objectBuilder;
	}
	
	private JsonArray construirJSONPeriodos(Relatorio relatorio) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		relatorio.getPeriodo().getIntervalosEspecificos().forEach(intervalo -> {
			adicionarPeriodo(arrayBuilder, intervalo.getHoraInicial(), intervalo.getDuracao());
		});
		relatorio.getPeriodo().getIntervalosPreDefinidos().forEach(intervaloPreDefinido -> {
			adicionarPeriodo(arrayBuilder, intervaloPreDefinido.getHoraInicial(), intervaloPreDefinido.getDuracao());
		});
		return arrayBuilder.build();
	}
	
	private void adicionarPeriodo(JsonArrayBuilder arrayBuilder, LocalTime horaInicial, Duration duracao) {
		long days = duracao.toDays();
		long hours = duracao.minusDays(days).toHours();
		long minutes = duracao.minusDays(days).minusHours(hours).toMinutes();
		JsonObject obj = Json.createObjectBuilder()
			.add("id", 0)
			.add("hora", horaInicial.getHour())
			.add("minuto", horaInicial.getMinute())
			.add("diaDuracao", days)
			.add("horaDuracao", hours)
			.add("minutoDuracao", minutes)
			.add("tipo", 3).build();
		arrayBuilder.add(obj);
	}
	
	private void construirJSONColunas(List<JsonObjectBuilder> jsons, Relatorio relatorio, DrillParams drillParams) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		String colunasJson = (drillParams != null ? drillParams.getColunas() : relatorio.getColunas());
		JsonReader jsonReader = Json.createReader(new StringReader(colunasJson));
		JsonArray colunasArray = jsonReader.readArray();
		jsonReader.close();
		
		colunasArray.forEach(jsonValue -> {
			JsonObject obj = (JsonObject) jsonValue;
			String tipoColuna = obj.getString("tipoColuna");
			String nome = obj.getString("nome");
			arrayBuilder.add(Json.createObjectBuilder()
					.add("id", 0)
					.add("tipoColuna", Integer.parseInt(tipoColuna))
					.add("nome", nome.trim())
					.build());
		});
		
		JsonArray colunas = arrayBuilder.build();
		jsons.forEach(json-> json.add("Colunas", colunas));
	}
	
	private void construirJSONFiltros(List<JsonObjectBuilder> jsons, Relatorio relatorio, Usuario usuario, DrillParams drillParams) {
		JsonArrayBuilder arrayBuilderFiltros = Json.createArrayBuilder();
		JsonArrayBuilder arrayBuilderParametros = Json.createArrayBuilder();
		adicionarParametrosDefault(arrayBuilderParametros, usuario);
		List<ValoresFiltro> valoresFiltros = relatorio.getValoresFiltros();
		for (ValoresFiltro val : valoresFiltros) {
			InterfaceFiltro interfaceFiltro = dao.find(InterfaceFiltro.class, val.getInterfaceFiltro());
			arrayBuilderFiltros.add(construirFiltro(val, interfaceFiltro));
			if (interfaceFiltro.getFiltro().getParametro() != ParametroJson.ID_PARAMETOS_JN.INICIO) {
				arrayBuilderParametros.add(construirParametro(val, interfaceFiltro.getFiltro().getParametro()));
			}
		}
		
		if (drillParams != null) {
			String colunaDrill = drillParams.getColunaDrill();
			String json = cdrview.getCondicoesDrill(colunaDrill, relatorio.getTipoRelatorio().getId().intValue());
			if (json != null && !json.isEmpty() && !"*".equals(json)) {
				JsonReader jsonReader = Json.createReader(new StringReader(json));
				JsonObject filtro = jsonReader.readObject();
				JsonArray jsonArrayFiltros = filtro.getJsonArray("Filtro");
				jsonArrayFiltros.forEach(arrayBuilderFiltros::add);
			}
			
			List<String> nomesFiltrosPrimarios = dao.getNomesFiltrosPrimarios();
			List<Map<String, String>> aberturas = drillParams.getAberturas();
			List<Map<String, String>> aberturasFiltros = aberturas.stream()
				.filter(abertura -> !nomesFiltrosPrimarios.contains(abertura.get("nome")))
				.collect(Collectors.toList());
			JsonReader jsonReader = Json.createReader(new StringReader(relatorio.getColunas()));
			JsonArray colunasArray = jsonReader.readArray();
			aberturasFiltros.forEach(aberturaFiltro -> {
				Optional<JsonValue> optColuna = colunasArray.stream()
					.filter(jsonValue -> ((JsonObject)jsonValue).getString("nome").equals(aberturaFiltro.get("nome")))
					.findFirst();
				if (optColuna.isPresent()) {
					JsonObject coluna = (JsonObject) optColuna.get();
					if ("false".equals(coluna.getString("ignoraFiltroDrill"))) {
						JsonArrayBuilder valoresBuilder = Json.createArrayBuilder();
						valoresBuilder.add(aberturaFiltro.get("valor"));
						arrayBuilderFiltros.add(Json.createObjectBuilder()
								.add("IdCampo", 0)
								.add("IdFiltro", 60000)
								.add("IdTipoFiltro", Integer.parseInt(coluna.getString("tipoFiltroDrill")))
								.add("NomeCampoRecDados", coluna.getString("registroDados"))
								.add("NomeExibicao", coluna.getString("nome"))
								.add("Valor", valoresBuilder.build())
								.add("IdTecnologia", 10000)
								.build());
					}
				}
			});
		}
		
		JsonArray parametro = arrayBuilderParametros.build();
		JsonArray filtro = arrayBuilderFiltros.build();
		jsons.forEach(json -> {
			json.add("Parametro", parametro);
			json.add("Filtro", filtro);
		});
	}
	
	private void adicionarParametrosDefault(JsonArrayBuilder arrayBuilderParametros, Usuario usuario) {
		arrayBuilderParametros.add(construirParametroDefault(ID_PARAMETOS_JN.ID_PARJN_USUARIO, usuario.getNome()));
		arrayBuilderParametros.add(construirParametroDefault(ID_PARAMETOS_JN.ID_PARJN_PERFIL, usuario.getPerfil().getNome(), usuario.getPerfil().getId().toString()));
		arrayBuilderParametros.add(construirParametroDefault(ID_PARAMETOS_JN.ID_PARJN_MODULO, "CDRView Analise"));
		arrayBuilderParametros.add(construirParametroDefault(ID_PARAMETOS_JN.ID_PARJN_RELATORIO_ATUALIZAVEL, "0"));
	}

	private JsonObject construirParametroDefault(ID_PARAMETOS_JN parametro, String... valores) {
		JsonArrayBuilder jsonValores = Json.createArrayBuilder();
		Arrays.stream(valores).forEach(jsonValores::add);
		return Json.createObjectBuilder()
			.add("nome", parametro.name())
			.add("IdEnum", parametro.ordinal())
			.add("Valor", Json.createArrayBuilder().build())
			.add("ValorString", jsonValores.build())
			.build();
	}

	private JsonObject construirParametro(ValoresFiltro val, ID_PARAMETOS_JN parametro) {
		return Json.createObjectBuilder()
				.add("nome", parametro.name())
				.add("IdEnum", parametro.ordinal())
				.add("Valor", Json.createArrayBuilder().build())
				.add("ValorString", Json.createArrayBuilder().add(val.getValor()).build())
				.build();
	}

	private JsonObject construirFiltro(ValoresFiltro val, InterfaceFiltro interfaceFiltro) {
		Filtro filtro = interfaceFiltro.getFiltro();
		Optional<CampoRegistro> campoRegistro = Optional.ofNullable(filtro.getCampoRegistro());
		JsonArrayBuilder valoresBuilder = Json.createArrayBuilder();
		Arrays.stream(val.getValor().split(";")).forEach(valoresBuilder::add);
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
			.add("IdCampo", (campoRegistro.isPresent() ? campoRegistro.get().getId() : 0))
			.add("IdFiltro", filtro.getId())
			.add("IdTipoFiltro", filtro.getTipoFiltro().getId())
			.add("NomeCampoRecDados", campoRegistro.isPresent() ? campoRegistro.get().getNome() : "")
			.add("NomeExibicao", interfaceFiltro.getLabel())
			.add("Valor", valoresBuilder.build())
			.add("IdTecnologia", filtro.getTecnologia().getId())
			.add("IdFiltroComparar", interfaceFiltro.getIdFiltroComparar())
			.add("CaminhoArquivoDefinicoes", "")
			.add("ComboIndex", 0);
		Optional<ComparadorFiltro> comparador = Optional.ofNullable(interfaceFiltro.getComparador());
		if (comparador.isPresent()) {
			objectBuilder.add("Comparador", comparador.get().getCodigo());
		} else {
			objectBuilder.add("Comparador", 0);
		}
		return objectBuilder.build();
	}
	
	private void construirJSONFiltrosTree(List<JsonObjectBuilder> jsons, Relatorio relatorio) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		List<ValoresTreeSalvos> valoresTreeSalvos = relatorio.getValoresTreeSalvos();
		for (ValoresTreeSalvos val : valoresTreeSalvos) {
			int nivel = 0;
			Filtro filtro = dao.find(Filtro.class, val.getFiltro());
			ValoresTree tree = dao.find(ValoresTree.class, val.getTree());
			arrayBuilder.add(construirFiltroTree(filtro, tree, nivel));
			adicionarFiltrosTreeFilhos(arrayBuilder, filtro, tree.getValoresFilhos(), nivel+1);
		}
		
		JsonArray filtroPrimario = arrayBuilder.build();
		jsons.forEach(json -> json.add("FiltroPrimario", filtroPrimario));
	}
	
	private void adicionarFiltrosTreeFilhos(JsonArrayBuilder arrayBuilder, Filtro filtro, List<ValoresTree> valoresFilhos, int nivel) {
		if (valoresFilhos.isEmpty()) return;
		for (ValoresTree tree : valoresFilhos) {
			arrayBuilder.add(construirFiltroTree(filtro, tree, nivel));
			adicionarFiltrosTreeFilhos(arrayBuilder, filtro, tree.getValoresFilhos(), nivel+1);
		}
	}
	
	private JsonObject construirFiltroTree(Filtro filtro, ValoresTree tree, int nivel) {
		Optional<CampoRegistro> campoRegistro = Optional.ofNullable(filtro.getCampoRegistro());
		return Json.createObjectBuilder()
			.add("Nome", tree.getNome())
			.add("IdCampo", (campoRegistro.isPresent() ? campoRegistro.get().getId() : 0))
			.add("IdFiltro", tree.getId())
			.add("NomeTipoFiltro", filtro.getNomeExibicao())
			.add("IdTipoFiltro", filtro.getTipoFiltro().getId())
			.add("IdPai", tree.getPai().getId())
			.add("Nivel", nivel)
			.add("NomePai", tree.getPai().getNome())
			.add("ePasta", tree.isNo() ? 1 : 0)
			.add("NomeTecnologia", "")
			.add("IdTecnologia", 0)
			.build();
	}
	
	public List<String> validarExecucao(Long idRel) {
		List<String> erros = new ArrayList<>();
		
		Relatorio relatorio = dao.find(Relatorio.class, idRel);
		
		List<Filtro> filtrosPrimarios = dao.getFiltrosPrimarios();
		List<ValoresTreeSalvos> valoresTreeSalvos = relatorio.getValoresTreeSalvos();
		List<Filtro> filtrosPrimariosSelecionados = valoresTreeSalvos.stream()
			.map(val -> {
				return dao.find(Filtro.class, val.getFiltro());
			}).collect(Collectors.toList());
		
		boolean filtrouBilhetador = filtrosPrimariosSelecionados.stream()
			.anyMatch(filtroSelecionado -> "BILHETADOR".equals(filtroSelecionado.getNome()));
		if (!filtrouBilhetador) {
			erros.add("Filtro Primário de Bilhetador é obrigatório");
		}
		
		JsonReader jsonReader = Json.createReader(new StringReader(relatorio.getColunas()));
		JsonArray colunasArray = jsonReader.readArray();
		jsonReader.close();
		for (int i=0; i < colunasArray.size(); i++) {
			JsonObject obj = colunasArray.getJsonObject(i);
			String nomeColuna = obj.getString("nome");
			
			Optional<Filtro> filtroPrimarioOpt = filtrosPrimarios.stream()
					.filter(f -> f.getNomeExibicao().equals(nomeColuna))
					.findAny();
			if (filtroPrimarioOpt.isPresent()) {
				boolean anyMatch = filtrosPrimariosSelecionados.stream()
					.anyMatch(filtroSelecionado -> filtroSelecionado.getId().equals(filtroPrimarioOpt.get().getId()));
				if (!anyMatch) {
					erros.add("Coluna "+nomeColuna+" sem Filtro Primário correspondente.");
				}
			}
		}
		
		return erros;
	}
	
}
