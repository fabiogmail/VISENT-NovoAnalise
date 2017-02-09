package br.com.visent.analise.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="perfil")
@AttributeOverride(name = "id", column = @Column(name = "id_perfil"))
public class Perfil extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="nome")
	private String nome;
	
	@OneToOne
	@JoinColumn(name="id_permissoes")
	private PerfilPermissoes permissoes;

	@Column(name="exportacao_comum", columnDefinition="TINYINT(1)")
	private boolean podeExportacaoComum;
	
	@Column(name="exportacao_conf", columnDefinition="TINYINT(1)")
	private boolean podeExportacaoConfiguracao;
	
	@OneToMany(mappedBy="perfil")
	@XmlTransient
	private List<Usuario> usuarios;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public PerfilPermissoes getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(PerfilPermissoes permissoes) {
		this.permissoes = permissoes;
	}

	public boolean isPodeExportacaoComum() {
		return podeExportacaoComum;
	}

	public void setPodeExportacaoComum(boolean podeExportacaoComum) {
		this.podeExportacaoComum = podeExportacaoComum;
	}

	public boolean isPodeExportacaoConfiguracao() {
		return podeExportacaoConfiguracao;
	}

	public void setPodeExportacaoConfiguracao(boolean podeExportacaoConfiguracao) {
		this.podeExportacaoConfiguracao = podeExportacaoConfiguracao;
	}

	@Override
	public String toString() {
		return "Perfil [nome=" + nome + ", podeExportacaoComum=" + podeExportacaoComum + ", podeExportacaoConfiguracao="
				+ podeExportacaoConfiguracao + "]";
	}
	
}
