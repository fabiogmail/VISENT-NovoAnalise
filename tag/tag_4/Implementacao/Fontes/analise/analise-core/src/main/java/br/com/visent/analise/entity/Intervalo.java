package br.com.visent.analise.entity;

import java.time.Duration;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.visent.analise.adapter.DurationAdapter;
import br.com.visent.analise.adapter.LocalTimeAdapter;
import br.com.visent.analise.enums.Granularidade;

@Entity
@Table(name="intervalo")
public class Intervalo extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_periodo")
	@XmlTransient
	private Periodo periodo;
	
	@Column(name="hora_inicial")
	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	private LocalTime horaInicial;
	
	@Column(name="duracao")
	@XmlJavaTypeAdapter(DurationAdapter.class)
	private Duration duracao;
	
	@Column(name="granularidade")
	private Granularidade granularidade;

	public LocalTime getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(LocalTime horaInicial) {
		this.horaInicial = horaInicial;
	}

	public Duration getDuracao() {
		return duracao;
	}

	public void setDuracao(Duration duracao) {
		this.duracao = duracao;
	}

	public Granularidade getGranularidade() {
		return granularidade;
	}

	public void setGranularidade(Granularidade granularidade) {
		this.granularidade = granularidade;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	@Override
	public String toString() {
		return "Intervalo [horaInicial=" + horaInicial + ", duracao=" + duracao + ", granularidade=" + granularidade
				+ "]";
	}
	
}
