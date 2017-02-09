package br.com.visent.analise.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tipo_relatorio")
@AttributeOverride(name = "id", column = @Column(name = "id_tipo_relatorio"))
public class TipoRelatorio extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="algoritmo")
	private Long algoritmo;
	
	@Column(name="drill", columnDefinition="TINYINT(1)")
	private boolean drill;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(Long algoritmo) {
		this.algoritmo = algoritmo;
	}

	@Override
	public String toString() {
		return "TipoRelatorio [nome=" + nome + ", algoritmo=" + algoritmo + "]";
	}

	public boolean isDrill() {
		return drill;
	}

	public void setDrill(boolean drill) {
		this.drill = drill;
	}
	
}
