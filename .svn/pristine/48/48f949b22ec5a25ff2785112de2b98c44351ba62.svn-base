package br.com.visent.analise.entity;

import javax.persistence.Table;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table(name="campos_registro")
@AttributeOverride(name = "id", column = @Column(name = "id_campo"))
public class CampoRegistro extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_campo")
	private TipoCampoRegistro tipoCampoRegistro;
	
	@OneToMany(mappedBy="campo")
	private List<ValoresCampo> valores;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoCampoRegistro getTipoCampoRegistro() {
		return tipoCampoRegistro;
	}

	public void setTipoCampoRegistro(TipoCampoRegistro tipoCampoRegistro) {
		this.tipoCampoRegistro = tipoCampoRegistro;
	}

	public List<ValoresCampo> getValores() {
		return valores;
	}

	public void setValores(List<ValoresCampo> valores) {
		this.valores = valores;
	}
	
	@Override
	public String toString() {
		return "CampoRegistro [nome=" + nome + ", tipoCampoRegistro=" + tipoCampoRegistro + "]";
	}
	
}
