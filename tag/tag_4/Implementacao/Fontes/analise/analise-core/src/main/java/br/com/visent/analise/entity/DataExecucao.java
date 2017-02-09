package br.com.visent.analise.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.visent.analise.adapter.LocalDateTimeAdapter;

@Entity
@Table(name="datas_execucoes")
public class DataExecucao extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="id_agenda")
	private Agenda agenda;
	
	@Column(name="data")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime data;
	
	@Column(name="executou", columnDefinition="TINYINT(1)")
	private boolean executou;
	
	@Column(name="data_execucao")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime dataExecucao;
	
	@Column(name="nome_arquivo")
	private String nomeArquivo;
	
	@Column(name="retentativa")
	private Long retentativa;

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public boolean isExecutou() {
		return executou;
	}

	public void setExecutou(boolean executou) {
		this.executou = executou;
	}

	public LocalDateTime getDataExecucao() {
		return dataExecucao;
	}

	public void setDataExecucao(LocalDateTime dataExecucao) {
		this.dataExecucao = dataExecucao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Long getRetentativa() {
		return retentativa;
	}

	public void setRetentativa(Long retentativa) {
		this.retentativa = retentativa;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
}
