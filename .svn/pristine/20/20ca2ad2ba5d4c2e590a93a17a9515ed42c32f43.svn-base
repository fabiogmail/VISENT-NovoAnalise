package br.com.visent.analise.util;

import java.util.ArrayList;
import java.util.List;

public class ResumoExecucao {

	private String titulo;
	private String usuario;
	private String dataHoraExecucao;
	private String tempoExecucao;
	private String nomeRelatorio;
	private String tipoRelatorio;
	private String periodos;
	private String data;
	private String colunas;
	private List<String> filtros = new ArrayList<>();
	private int quantidadeLinhas;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getDataHoraExecucao() {
		return dataHoraExecucao;
	}
	public void setDataHoraExecucao(String dataHoraExecucao) {
		this.dataHoraExecucao = dataHoraExecucao;
	}
	public String getTempoExecucao() {
		return tempoExecucao;
	}
	public void setTempoExecucao(String tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}
	public String getNomeRelatorio() {
		return nomeRelatorio;
	}
	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}
	public String getTipoRelatorio() {
		return tipoRelatorio;
	}
	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
	public String getPeriodos() {
		return periodos;
	}
	public void setPeriodos(String periodos) {
		this.periodos = periodos;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		if (data == null) {
			this.data = data;
		} else {
			this.data += (", "+data);
		}
	}
	public String getColunas() {
		return colunas;
	}
	public void setColunas(String colunas) {
		this.colunas = colunas;
	}
	public List<String> getFiltros() {
		return filtros;
	}
	public void setFiltros(List<String> filtros) {
		this.filtros = filtros;
	}
	public void addFiltro(String filtro) {
		this.filtros.add(filtro);
	}
	public int getQuantidadeLinhas() {
		return quantidadeLinhas;
	}
	public void setQuantidadeLinhas(int quantidadeLinhas) {
		this.quantidadeLinhas = quantidadeLinhas;
	}
	
}
