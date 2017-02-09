package br.com.visent.analise.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DrillParams implements Serializable {

	private static final long serialVersionUID = 1L;

	private String colunas;
	private int indicePeriodo;
	private String colunaDrill;
	private List<Map<String, String>> aberturas; // keys: nome, valor
	
	public String getColunas() {
		return colunas;
	}
	public void setColunas(String colunas) {
		this.colunas = colunas;
	}
	public int getIndicePeriodo() {
		return indicePeriodo;
	}
	public void setIndicePeriodo(int indicePeriodo) {
		this.indicePeriodo = indicePeriodo;
	}
	public String getColunaDrill() {
		return colunaDrill;
	}
	public void setColunaDrill(String colunaDrill) {
		this.colunaDrill = colunaDrill;
	}
	public List<Map<String, String>> getAberturas() {
		return aberturas;
	}
	public void setAberturas(List<Map<String, String>> aberturas) {
		this.aberturas = aberturas;
	}
	
}
