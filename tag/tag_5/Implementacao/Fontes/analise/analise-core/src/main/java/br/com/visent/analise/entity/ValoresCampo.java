package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="valores_campo")
public class ValoresCampo extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_campo")
	@XmlTransient
	private CampoRegistro campo;
	
	@ManyToOne
	@JoinColumn(name="id_tecnologia")
	private Tecnologia tecnologia;
	
	@Column(name="valor", columnDefinition="TEXT")
	private String valor;

	public CampoRegistro getCampo() {
		return campo;
	}

	public void setCampo(CampoRegistro campo) {
		this.campo = campo;
	}

	public Tecnologia getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(Tecnologia tecnologia) {
		this.tecnologia = tecnologia;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "ValoresCampo [campo=" + campo + ", tecnologia=" + tecnologia + ", valor=" + valor + "]";
	}

}
