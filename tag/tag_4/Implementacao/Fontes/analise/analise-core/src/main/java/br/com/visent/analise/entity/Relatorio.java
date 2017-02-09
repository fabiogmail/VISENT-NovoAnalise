package br.com.visent.analise.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.visent.analise.adapter.DurationAdapter;
import br.com.visent.analise.adapter.LocalDateTimeAdapter;

@Entity
@Table(name="relatorio")
public class Relatorio extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_relatorio")
	private TipoRelatorio tipoRelatorio;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_tecnologia")
	private TipoTecnologia tipoTecnologia;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@Column(name="data_ultima_execucao")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime dataUltimaExecucao;
	
	@Column(name="tempo_ultima_execucao")
	@XmlJavaTypeAdapter(DurationAdapter.class)
	private Duration tempoUltimaExecucao;
	
	@OneToMany(mappedBy="relatorio", orphanRemoval=true)
	@XmlTransient
	private List<Favorito> favoritos;
	
	@OneToMany(mappedBy="relatorio", cascade=CascadeType.ALL, orphanRemoval=true)
	@XmlTransient
	private List<Compartilhamento> compartilhamentos;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_periodo")
	private Periodo periodo;
	
	@OneToMany(mappedBy="relatorio", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ValoresFiltro> valoresFiltros;
	
	@OneToMany(mappedBy="relatorio", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ValoresTreeSalvos> valoresTreeSalvos;
	
	@Column(name="colunas", columnDefinition="TEXT")
	private String colunas;
	
	@Transient
	private Long idFavorito;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoRelatorio getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(TipoRelatorio tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataUltimaExecucao() {
		return dataUltimaExecucao;
	}

	public void setDataUltimaExecucao(LocalDateTime dataUltimaExecucao) {
		this.dataUltimaExecucao = dataUltimaExecucao;
	}

	public Duration getTempoUltimaExecucao() {
		return tempoUltimaExecucao;
	}

	public void setTempoUltimaExecucao(Duration tempoUltimaExecucao) {
		this.tempoUltimaExecucao = tempoUltimaExecucao;
	}
	
	public Long getIdFavorito() {
		return idFavorito;
	}

	public void setIdFavorito(Long idFavorito) {
		this.idFavorito = idFavorito;
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
	
	public TipoTecnologia getTipoTecnologia() {
		return tipoTecnologia;
	}
	
	public void setTipoTecnologia(TipoTecnologia tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public String getColunas() {
		return colunas;
	}

	public void setColunas(String colunas) {
		this.colunas = colunas;
	}

	public List<ValoresFiltro> getValoresFiltros() {
		return valoresFiltros;
	}

	public void setValoresFiltros(List<ValoresFiltro> valoresFiltros) {
		this.valoresFiltros = valoresFiltros;
	}

	public List<ValoresTreeSalvos> getValoresTreeSalvos() {
		return valoresTreeSalvos;
	}

	public void setValoresTreeSalvos(List<ValoresTreeSalvos> valoresTreeSalvos) {
		this.valoresTreeSalvos = valoresTreeSalvos;
	}
	
	@Override
	public String toString() {
		return "Relatorio [nome=" + nome + ", tipoRelatorio=" + tipoRelatorio + ", tipoTecnologia=" + tipoTecnologia
				+ ", usuario=" + usuario + ", dataUltimaExecucao=" + dataUltimaExecucao + ", tempoUltimaExecucao="
				+ tempoUltimaExecucao + ", idFavorito=" + idFavorito + "]";
	}

}
