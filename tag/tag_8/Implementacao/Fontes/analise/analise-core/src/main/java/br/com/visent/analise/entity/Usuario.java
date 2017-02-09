package br.com.visent.analise.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="usuario_cdrview")
@AttributeOverride(name = "id", column = @Column(name = "id_usuario"))
public class Usuario extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="id_perfil")
	private Perfil perfil;
	
	@OneToMany(mappedBy="usuario")
	@XmlTransient
	private List<Relatorio> relatorios;
	
	@OneToMany(mappedBy="usuario")
	@XmlTransient
	private List<Favorito> favoritos;
	
	@OneToMany(mappedBy="usuario")
	@XmlTransient
	private List<Compartilhamento> compartilhamentos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Relatorio> getRelatorios() {
		return relatorios;
	}
	
	public void setRelatorios(List<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}
	
	public List<Favorito> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}
	
	public List<Compartilhamento> getCompartilhamentos() {
		return compartilhamentos;
	}
	
	public void setCompartilhamentos(List<Compartilhamento> compartilhamentos) {
		this.compartilhamentos = compartilhamentos;
	}
	
	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", perfil=" + perfil + "]";
	}
	
}
