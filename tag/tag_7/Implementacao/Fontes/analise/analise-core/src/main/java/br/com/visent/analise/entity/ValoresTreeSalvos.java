package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="valores_tree_salvos")
public class ValoresTreeSalvos extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_relatorio")
	@XmlTransient
	private Relatorio relatorio;
	
//	@ManyToOne
//	@JoinColumn(name="id_filtro")
	@Column(name="id_filtro")
	private Long filtro;
	
//	@ManyToOne
//	@JoinColumn(name="id_valor_tree")
	@Column(name="id_valor_tree")
	private Long tree;
	
	@Transient
	private String nome; 

	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	public Long getFiltro() {
		return filtro;
	}

	public void setFiltro(Long filtro) {
		this.filtro = filtro;
	}

	public Long getTree() {
		return tree;
	}

	public void setTree(Long tree) {
		this.tree = tree;
	}

	@Override
	public String toString() {
		return "ValoresTreeSalvos [relatorio=" + relatorio + ", filtro=" + filtro + ", tree=" + tree + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
