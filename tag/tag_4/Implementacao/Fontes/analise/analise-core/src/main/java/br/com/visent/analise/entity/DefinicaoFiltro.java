package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="valor_definicoes")
public class DefinicaoFiltro extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@OneToOne
	@JoinColumn(name="id_filtro")
	@XmlTransient
	private Filtro filtro;
	
	@Column(name="nome_arquivo")
	private String nomeArquivo;
	
	@Column(name="chave_arquivo")
	private String chaveArquivo;
	
	@Column(name="valor_apender")
	private String valorApender;
	
	@Column(name="chave")
	private String chave;
	
	@Column(name="valor")
	private String valor;
	
	@Column(name="separador")
	private String separador;
	
	@Column(name="quebra_chave")
	private String quebraChave;
	
	@Column(name="posicao_quebra_chave")
	private String posicaoQuebraChave;
	
	@Column(name="ignorar")
	private String ignorar;

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getChaveArquivo() {
		return chaveArquivo;
	}

	public void setChaveArquivo(String chaveArquivo) {
		this.chaveArquivo = chaveArquivo;
	}

	public String getValorApender() {
		return valorApender;
	}

	public void setValorApender(String valorApender) {
		this.valorApender = valorApender;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getSeparador() {
		return separador;
	}

	public void setSeparador(String separador) {
		this.separador = separador;
	}

	public String getQuebraChave() {
		return quebraChave;
	}

	public void setQuebraChave(String quebraChave) {
		this.quebraChave = quebraChave;
	}

	public String getPosicaoQuebraChave() {
		return posicaoQuebraChave;
	}

	public void setPosicaoQuebraChave(String posicaoQuebraChave) {
		this.posicaoQuebraChave = posicaoQuebraChave;
	}

	public String getIgnorar() {
		return ignorar;
	}

	public void setIgnorar(String ignorar) {
		this.ignorar = ignorar;
	}

	@Override
	public String toString() {
		return "DefinicaoFiltro [nomeArquivo=" + nomeArquivo + ", chaveArquivo=" + chaveArquivo + ", valorApender="
				+ valorApender + ", chave=" + chave + ", valor=" + valor + ", separador=" + separador + ", quebraChave="
				+ quebraChave + ", posicaoQuebraChave=" + posicaoQuebraChave + ", ignorar=" + ignorar + "]";
	}

}
