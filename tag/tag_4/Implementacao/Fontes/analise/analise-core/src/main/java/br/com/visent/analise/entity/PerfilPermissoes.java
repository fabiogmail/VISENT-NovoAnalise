package br.com.visent.analise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="perfil_permissoes")
public class PerfilPermissoes extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="permissoes_relatorios")
	private String permissoesRelatorios;
	
	@Column(name="permissoes_tecnologias")
	private String permissoesTecnologias;
	
	@Column(name="permissoes_historicos")
	private String permissoesHistoricos;

	public String getPermissoesRelatorios() {
		return permissoesRelatorios;
	}

	public void setPermissoesRelatorios(String permissoesRelatorios) {
		this.permissoesRelatorios = permissoesRelatorios;
	}

	public String getPermissoesTecnologias() {
		return permissoesTecnologias;
	}

	public void setPermissoesTecnologias(String permissoesTecnologias) {
		this.permissoesTecnologias = permissoesTecnologias;
	}

	public String getPermissoesHistoricos() {
		return permissoesHistoricos;
	}

	public void setPermissoesHistoricos(String permissoesHistoricos) {
		this.permissoesHistoricos = permissoesHistoricos;
	}

	@Override
	public String toString() {
		return "PerfilPermissoes [permissoesRelatorios=" + permissoesRelatorios + ", permissoesTecnologias="
				+ permissoesTecnologias + ", permissoesHistoricos=" + permissoesHistoricos + "]";
	}
	
}
