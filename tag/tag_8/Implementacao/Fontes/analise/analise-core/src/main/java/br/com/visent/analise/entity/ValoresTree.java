package br.com.visent.analise.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="valores_tree")
public class ValoresTree extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_pai")
	@XmlTransient
	private ValoresTree pai;
	
	@Column(name="nome")
	private String nome;
	
//	@ManyToOne
//	@JoinColumn(name="id_filtro")
	@Column(name="id_filtro")
	private Long filtro;
	
	@Column(name="no", columnDefinition="TINYINT(1)")
	private boolean no;
	
//	@ManyToOne
//	@JoinColumn(name="id_perfil")
	@Column(name="id_perfil")
	private Long perfil;
	
	@OneToMany(mappedBy="pai", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ValoresTree> valoresFilhos;
	
	@Transient
	private Long idPai;

	public ValoresTree getPai() {
		return pai;
	}

	public void setPai(ValoresTree pai) {
		this.pai = pai;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getFiltro() {
		return filtro;
	}

	public void setFiltro(Long filtro) {
		this.filtro = filtro;
	}

	public boolean isNo() {
		return no;
	}

	public void setNo(boolean no) {
		this.no = no;
	}

	public Long getPerfil() {
		return perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "ValoresTree [pai=" + pai + ", nome=" + nome + ", filtro=" + filtro + ", no=" + no + ", perfil=" + perfil
				+ "]";
	}

	public Long getIdPai() {
		return idPai;
	}

	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}

	public List<ValoresTree> getValoresFilhos() {
		return valoresFilhos;
	}

	public void setValoresFilhos(List<ValoresTree> valoresFilhos) {
		this.valoresFilhos = valoresFilhos;
	}

}
