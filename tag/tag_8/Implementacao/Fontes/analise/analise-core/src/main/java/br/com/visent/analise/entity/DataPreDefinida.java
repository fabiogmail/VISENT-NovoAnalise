package br.com.visent.analise.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="datas")
@AttributeOverride(name = "id", column = @Column(name = "iddata"))
public class DataPreDefinida extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="tipo")
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
