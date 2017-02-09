package br.com.visent.analise.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="valores_filtro")
@AttributeOverride(name = "id", column = @Column(name = "id_valor_filtro"))
public class ValoresFiltro extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_relatorio")
	@XmlTransient
	private Relatorio relatorio;
	
//	@ManyToOne
//	@JoinColumn(name="id_filtro")
	@Column(name="id_filtro")
	private Long filtro;
	
	@Column(name="id_interface_filtro")
	private Long interfaceFiltro;
	
	@Column(name="valor_filtro", columnDefinition="TEXT")
	private String valor;

	public Long getFiltro() {
		return filtro;
	}

	public void setFiltro(Long filtro) {
		this.filtro = filtro;
	}

	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "ValoresFiltro [filtro=" + filtro + ", valor=" + valor + "]";
	}

	public Long getInterfaceFiltro() {
		return interfaceFiltro;
	}

	public void setInterfaceFiltro(Long interfaceFiltro) {
		this.interfaceFiltro = interfaceFiltro;
	}

}
