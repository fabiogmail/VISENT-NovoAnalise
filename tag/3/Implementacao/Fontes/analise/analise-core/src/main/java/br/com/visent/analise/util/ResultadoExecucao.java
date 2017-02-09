package br.com.visent.analise.util;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.visent.analise.entity.Relatorio;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultadoExecucao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private List<String> cabecalho = new ArrayList<>();
	private Map<String, List<List<String>>> periodoLinhas = new HashMap<>();
	private List<String> linhasNaoMapeadas = new ArrayList<>();
	private int[] indices;
	private List<String> colunas = new ArrayList<>();
	private List<String> aberturas = new ArrayList<>();
	
	public ResultadoExecucao(){}
	
	public ResultadoExecucao(Relatorio relatorio, List<String> linhas, DrillParams drillParams) {
		this.nome = relatorio.getNome();
		String colunasJson = (drillParams != null ? drillParams.getColunas() : relatorio.getColunas());
		JsonReader jsonReader = Json.createReader(new StringReader(colunasJson));
		JsonArray colunas = jsonReader.readArray();
		jsonReader.close();
		indices = new int[colunas.size()];
		
		String periodo = "--";
		for (String linhaRel : linhas) {
			String prefix = linhaRel.substring(0, 2);
			String linha = linhaRel.substring(2);
			if ("5;".equals(prefix)) {
				linhaCabecalho(linha);
			} else if ("9;".equals(prefix)) {
				linhaColunas(linha, colunas);
			} else if ("2;".equals(prefix)) {
				periodo = linha;
				linhaPeriodo(linha);
			} else if ("0;".equals(prefix)) {
				linhaDados(linha, periodo);
			} else {
				linhaNaoMapeada(linhaRel);
			}
		}
	}
	
	private void linhaNaoMapeada(String linha) {
		if (linha.startsWith("Abertura=")) {
			String[] aberturaArr = linha.split("=");
			if (aberturaArr.length>1) {
				aberturas.addAll(Arrays.asList(linha.split("=")[1].split(";")));
			}
		} 
		linhasNaoMapeadas.add(linha);
	}

	private void linhaDados(String linha, String periodo) {
		List<String> valores = new ArrayList<>();
		String[] linhaArr = linha.split(";");
		periodoLinhas.compute(periodo, (String periodoKey, List<List<String>> linhas) -> {
			if (linhas == null) {
				linhas = new ArrayList<>();
			}
			for(int indice : indices) {
				String valor = linhaArr[indice];
				valores.add(valor);
			}
			linhas.add(valores);
			return linhas;
		});
	}

	private void linhaPeriodo(String linha) {
		periodoLinhas.putIfAbsent(linha, new ArrayList<>());
	}

	private void linhaColunas(String linha, JsonArray colunas) {
		List<JsonObject> objects = colunas.getValuesAs(JsonObject.class);
		List<String> linhaList = Arrays.asList(linha.split(";"));
		for (int i=0; i < objects.size(); i++) {
			JsonObject jsonObject = objects.get(i);
			String formulaExecucao = jsonObject.getString("formulaExecucao");
			String nome = jsonObject.getString("nome");
			String find = !formulaExecucao.isEmpty() ? formulaExecucao : nome.trim();
			indices[i] = linhaList.indexOf(find);
			this.colunas.add(nome);
		}
	}

	private void linhaCabecalho(String linha) {
		cabecalho.add(linha);
	}

	public String getNome() {
		return this.nome;
	}

	public List<String> getCabecalho() {
		return cabecalho;
	}

	public Map<String, List<List<String>>> getPeriodoLinhas() {
		return periodoLinhas;
	}

	public List<String> getLinhasNaoMapeadas() {
		return linhasNaoMapeadas;
	}

	public List<String> getColunas() {
		return colunas;
	}
	
	public List<String> getAberturas() {
		return aberturas;
	}
	
}
