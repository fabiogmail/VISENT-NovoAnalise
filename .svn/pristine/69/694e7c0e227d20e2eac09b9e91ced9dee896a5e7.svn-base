package br.com.visent.analise.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="interface_aba")
public class InterfaceAba extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="posicao")
	private Integer posicao;
	
	@ManyToOne
	@JoinColumn(name="id_aba_pai")
	@XmlTransient
	private InterfaceAba abaPai;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_relatorio")
	private TipoRelatorio tipoRelatorio;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_tecnologia")
	private TipoTecnologia tipoTecnologia;

	@OneToMany(mappedBy="abaPai", fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<InterfaceAba> abasFilhas;
	
	@OneToMany(mappedBy="aba", fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<InterfaceFiltro> interfaceFiltros;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public InterfaceAba getAbaPai() {
		return abaPai;
	}

	public void setAbaPai(InterfaceAba abaPai) {
		this.abaPai = abaPai;
	}

	public TipoRelatorio getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(TipoRelatorio tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public List<InterfaceAba> getAbasFilhas() {
		return abasFilhas;
	}

	public void setAbasFilhas(List<InterfaceAba> abasFilhas) {
		this.abasFilhas = abasFilhas;
	}

	public List<InterfaceFiltro> getInterfaceFiltros() {
		return interfaceFiltros;
	}
	
	public void setInterfaceFiltros(List<InterfaceFiltro> interfaceFiltros) {
		this.interfaceFiltros = interfaceFiltros;
	}
	
	@Override
	public String toString() {
		return "InterfaceAba [nome=" + nome + ", posicao=" + posicao + ", tipoRelatorio=" + tipoRelatorio + "]";
	}

}
