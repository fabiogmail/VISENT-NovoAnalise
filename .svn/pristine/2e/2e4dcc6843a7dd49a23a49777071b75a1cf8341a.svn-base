package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import br.com.visent.analise.enums.ComparadorFiltro;
import br.com.visent.analise.enums.ComponenteFiltro;

@Entity
@Table(name="interface_filtro")
public class InterfaceFiltro extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_aba")
	@XmlTransient
	private InterfaceAba aba;
	
	@ManyToOne
	@JoinColumn(name="id_filtro")
	private Filtro filtro;
	
	@Column(name="label")
	private String label;
	
	@Column(name="posicao")
	private Integer posicao;
	
	@Column(name="componente")
	@Enumerated(EnumType.STRING)
	private ComponenteFiltro componente;
	
	@Column(name="visibilidade")
	private String visibilidade;
	
	@Column(name="obrigatorio", columnDefinition="TINYINT(1)")
	private boolean obrigatorio;
	
	@Column(name="comparador")
	@Enumerated(EnumType.STRING)
	private ComparadorFiltro comparador;
	
	// esta coluna esta como Long ao inves de Filtro para nao fazer join desnecessariamente
	@Column(name="id_filtro_comparar")
	private Long idFiltroComparar;

	public InterfaceAba getAba() {
		return aba;
	}

	public void setAba(InterfaceAba aba) {
		this.aba = aba;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public ComponenteFiltro getComponente() {
		return componente;
	}

	public void setComponente(ComponenteFiltro componente) {
		this.componente = componente;
	}

	public String getVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}

	public boolean isObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public ComparadorFiltro getComparador() {
		return comparador;
	}

	public void setComparador(ComparadorFiltro comparador) {
		this.comparador = comparador;
	}

	public Long getIdFiltroComparar() {
		return idFiltroComparar;
	}

	public void setIdFiltroComparar(Long idFiltroComparar) {
		this.idFiltroComparar = idFiltroComparar;
	}

	@Override
	public String toString() {
		return "InterfaceFiltro [aba=" + aba + ", filtro=" + filtro + ", label=" + label + ", posicao=" + posicao
				+ ", componente=" + componente + ", visibilidade=" + visibilidade + ", obrigatorio=" + obrigatorio
				+ ", comparador=" + comparador + ", idFiltroComparar=" + idFiltroComparar + "]";
	}
	
}
