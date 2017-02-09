package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tecnologia")
public class Tecnologia extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_tecnologia")
	private TipoTecnologia tipoTecnologia;
	
	@Column(name="nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoTecnologia getTipoTecnologia() {
		return tipoTecnologia;
	}

	public void setTipoTecnologia(TipoTecnologia tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}

	@Override
	public String toString() {
		return "Tecnologia [tipoTecnologia=" + tipoTecnologia + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tecnologia other = (Tecnologia) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
