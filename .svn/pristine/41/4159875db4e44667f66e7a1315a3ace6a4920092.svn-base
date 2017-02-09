package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="parametro_sistema")
public class ParametroSistema extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="valor")
	private String valor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "ParametroSistema [nome=" + nome + ", valor=" + valor + "]";
	}

}
