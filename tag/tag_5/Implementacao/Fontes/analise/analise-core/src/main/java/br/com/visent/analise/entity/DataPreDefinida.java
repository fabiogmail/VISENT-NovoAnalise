package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="data_pre_definida")
public class DataPreDefinida extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column
	private String nome;
	
	@Column
	private Long codigo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "DataPreDefinida [nome=" + nome + ", codigo=" + codigo + "]";
	}

}
