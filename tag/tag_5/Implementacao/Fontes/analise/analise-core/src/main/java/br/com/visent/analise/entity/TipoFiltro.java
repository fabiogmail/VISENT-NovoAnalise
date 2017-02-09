package br.com.visent.analise.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table(name="tipo_filtro")
public class TipoFiltro extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "TipoFiltro [nome=" + nome + "]";
	}

}
