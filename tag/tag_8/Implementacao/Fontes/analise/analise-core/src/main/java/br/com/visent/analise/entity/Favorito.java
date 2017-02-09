package br.com.visent.analise.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="favorito")
public class Favorito extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_relatorio")
	private Relatorio relatorio;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	@Override
	public String toString() {
		return "Favorito [usuario=" + usuario + ", relatorio=" + relatorio + "]";
	}
	
}
