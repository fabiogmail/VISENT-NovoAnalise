package br.com.visent.analise.entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.visent.analise.adapter.DurationAdapter;
import br.com.visent.analise.adapter.LocalDateTimeAdapter;
import br.com.visent.analise.adapter.LocalTimeAdapter;
import br.com.visent.analise.enums.AgPeriodicidade;
import br.com.visent.analise.enums.AgListValue;
import br.com.visent.analise.enums.AgStatus;

@Entity
@Table(name="agenda")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Agenda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_agenda")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="periodicidade")
	@Enumerated(EnumType.STRING)
	private AgPeriodicidade periodicidade;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private AgStatus status;

	@Column(name="executado_em")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime executadoEm;
	
	@Column(name="ultima_exec")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime ultimaExecucao;
	
	@Column(name="progresso")
	private Double progresso;
	
	@Column(name="infinito")
	private Integer infinito;
	
	@Column(name="inicio")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime inicio;
	
	@Column(name="termino")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime termino;
	
	@Column(name="horaInicio")
	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	private LocalTime horaInicio;
	
	@Column(name="horaTermino")
	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	private LocalTime horaTermino;
	
	@Column(name="reprocessarUltimasHoras")
	private Integer reprocessarUltimasHoras;
	
	@Column(name="latencia")
	private Integer latencia;
	
	@Column(name="periodo")
	private Integer periodo;
	
	@Column(name="dias_do_mes")
	private AgListValue<String> diasDoMes;
	
	@Column(name="semanas_de_ocorrencia")
	private AgListValue<String> semanasDeOcorrencia;
	
	@Column(name="dias_da_semana")
	private AgListValue<String> diasDaSemana;
	
	@Column(name="eh_historico", columnDefinition="TINYINT(1)")
	private boolean historico;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="exportacao", columnDefinition="TINYINT(1)")
	private Integer exportacao;
	
	@Column(name="usuario")
	private String usuario;
	
	@Column(name="privacidade", columnDefinition="TINYINT(1)")
	private boolean privacidade;
	
	@Column(name="processando", columnDefinition="TINYINT(1)")
	private boolean processando;

	@Column(name="id_relatorio_cfg")
	private Long idRelatorioCfg;
	
	@Column(name="id_tipo_relatorio")
	private Long idTipoRelatorio;
	
	@Column(name="id_perfil")
	private Long idPerfil;
	
	@Column(name="json")
	private String json;
	
	@OneToMany(mappedBy="agenda")
	@XmlTransient
	private List<DataExecucao> execucoes;
	
	@Transient
	@XmlJavaTypeAdapter(DurationAdapter.class)
	private Duration intervaloExecucao;
	
	@Transient
	private List<Relatorio> relatorios;
	
	@Transient
	private List<String> exportacoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AgPeriodicidade getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(AgPeriodicidade periodicidade) {
		this.periodicidade = periodicidade;
	}

	public AgStatus getStatus() {
		return status;
	}

	public void setStatus(AgStatus status) {
		this.status = status;
	}

	public LocalDateTime getExecutadoEm() {
		return executadoEm;
	}

	public void setExecutadoEm(LocalDateTime executadoEm) {
		this.executadoEm = executadoEm;
	}

	public LocalDateTime getUltimaExecucao() {
		return ultimaExecucao;
	}

	public void setUltimaExecucao(LocalDateTime ultimaExecucao) {
		this.ultimaExecucao = ultimaExecucao;
	}

	public Double getProgresso() {
		return progresso;
	}

	public void setProgresso(Double progresso) {
		this.progresso = progresso;
	}

	public Integer getInfinito() {
		return infinito;
	}

	public void setInfinito(Integer infinito) {
		this.infinito = infinito;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getTermino() {
		return termino;
	}

	public void setTermino(LocalDateTime termino) {
		this.termino = termino;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(LocalTime horaTermino) {
		this.horaTermino = horaTermino;
	}

	public Integer getReprocessarUltimasHoras() {
		return reprocessarUltimasHoras;
	}

	public void setReprocessarUltimasHoras(Integer reprocessarUltimasHoras) {
		this.reprocessarUltimasHoras = reprocessarUltimasHoras;
	}

	public Integer getLatencia() {
		return latencia;
	}

	public void setLatencia(Integer latencia) {
		this.latencia = latencia;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public AgListValue<String> getDiasDoMes() {
		return diasDoMes;
	}

	public void setDiasDoMes(AgListValue<String> diasDoMes) {
		this.diasDoMes = diasDoMes;
	}

	public AgListValue<String> getSemanasDeOcorrencia() {
		return semanasDeOcorrencia;
	}

	public void setSemanasDeOcorrencia(AgListValue<String> semanasDeOcorrencia) {
		this.semanasDeOcorrencia = semanasDeOcorrencia;
	}

	public AgListValue<String> getDiasDaSemana() {
		return diasDaSemana;
	}

	public void setDiasDaSemana(AgListValue<String> diasDaSemana) {
		this.diasDaSemana = diasDaSemana;
	}

	public boolean isHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getExportacao() {
		return exportacao;
	}

	public void setExportacao(Integer exportacao) {
		this.exportacao = exportacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean isPrivacidade() {
		return privacidade;
	}

	public void setPrivacidade(boolean privacidade) {
		this.privacidade = privacidade;
	}

	public boolean isProcessando() {
		return processando;
	}

	public void setProcessando(boolean processando) {
		this.processando = processando;
	}

	public Long getIdRelatorioCfg() {
		return idRelatorioCfg;
	}

	public void setIdRelatorioCfg(Long idRelatorioCfg) {
		this.idRelatorioCfg = idRelatorioCfg;
	}

	public Long getIdTipoRelatorio() {
		return idTipoRelatorio;
	}

	public void setIdTipoRelatorio(Long idTipoRelatorio) {
		this.idTipoRelatorio = idTipoRelatorio;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Duration getIntervaloExecucao() {
		return intervaloExecucao;
	}

	public void setIntervaloExecucao(Duration intervaloExecucao) {
		this.intervaloExecucao = intervaloExecucao;
	}

	public List<Relatorio> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}

	public List<DataExecucao> getExecucoes() {
		return execucoes;
	}

	public void setExecucoes(List<DataExecucao> execucoes) {
		this.execucoes = execucoes;
	}

	public List<String> getExportacoes() {
		return exportacoes;
	}

	public void setExportacoes(List<String> exportacoes) {
		this.exportacoes = exportacoes;
	}

}
