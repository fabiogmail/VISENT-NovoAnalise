package br.com.visent.analise.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.visent.bean.ParametroJson;

@Entity
@Table(name="filtros")
@AttributeOverride(name = "id", column = @Column(name = "id_filtro"))
public class Filtro extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome_filtro")
	private String nome;
	
	@Column(name="nome_exibicao")
	private String nomeExibicao;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_filtro")
	private TipoFiltro tipoFiltro;
	
	@ManyToOne
	@JoinColumn(name="id_campo")
	private CampoRegistro campoRegistro;
	
	@Column(name="guarda_registro_uso", columnDefinition="TINYINT(1)")
	private boolean guardaRegistroUso;
	
	@ManyToOne
	@JoinColumn(name="id_tecnologia")
	private Tecnologia tecnologia;

	@Column(name="parametro")
	@Enumerated(EnumType.ORDINAL)
	private ParametroJson.ID_PARAMETOS_JN parametro;
	
	@Column(name="valor_minimo")
	private Long valorMinimo;
	
	@Column(name="valor_maximo")
	private Long valorMaximo;
	
	@OneToOne(mappedBy="filtro")
	private DefinicaoFiltro definicaoFiltro;
	
//	@OneToMany(mappedBy="filtro")
	@Transient // populado manualmente
	private List<ValoresTree> valoresTree;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoFiltro getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(TipoFiltro tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public CampoRegistro getCampoRegistro() {
		return campoRegistro;
	}

	public void setCampoRegistro(CampoRegistro campoRegistro) {
		this.campoRegistro = campoRegistro;
	}

	public boolean isGuardaRegistroUso() {
		return guardaRegistroUso;
	}

	public void setGuardaRegistroUso(boolean guardaRegistroUso) {
		this.guardaRegistroUso = guardaRegistroUso;
	}

	public Tecnologia getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(Tecnologia tecnologia) {
		this.tecnologia = tecnologia;
	}

	public ParametroJson.ID_PARAMETOS_JN getParametro() {
		return parametro;
	}

	public void setParametro(ParametroJson.ID_PARAMETOS_JN parametro) {
		this.parametro = parametro;
	}

	public Long getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(Long valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public Long getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Long valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public List<ValoresTree> getValoresTree() {
		return valoresTree;
	}
	
	public void setValoresTree(List<ValoresTree> valoresTree) {
		this.valoresTree = valoresTree;
	}
	
	@Override
	public String toString() {
		return "Filtro [nome=" + nome + ", tipoFiltro=" + tipoFiltro + ", campoRegistro=" + campoRegistro
				+ ", guardaRegistroUso=" + guardaRegistroUso + ", tecnologia=" + tecnologia + ", parametro=" + parametro
				+ ", valorMinimo=" + valorMinimo + ", valorMaximo=" + valorMaximo + "]";
	}

	public String getNomeExibicao() {
		return nomeExibicao;
	}

	public void setNomeExibicao(String nomeExibicao) {
		this.nomeExibicao = nomeExibicao;
	}
	
	public DefinicaoFiltro getDefinicaoFiltro() {
		return definicaoFiltro;
	}

	public void setDefinicaoFiltro(DefinicaoFiltro definicaoFiltro) {
		this.definicaoFiltro = definicaoFiltro;
	}
	
}
