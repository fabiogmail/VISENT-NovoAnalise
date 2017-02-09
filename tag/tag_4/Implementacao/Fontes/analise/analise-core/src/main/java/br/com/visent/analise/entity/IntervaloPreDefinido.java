package br.com.visent.analise.entity;

import java.time.Duration;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.visent.analise.adapter.DurationAdapter;
import br.com.visent.analise.adapter.LocalTimeAdapter;

@Entity
@Table(name="intervalo_pre_definido")
public class IntervaloPreDefinido extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="hora_inicial")
	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	private LocalTime horaInicial;
	
	@Column(name="duracao")
	@XmlJavaTypeAdapter(DurationAdapter.class)
	private Duration duracao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

	@Override
	public String toString() {
		return "IntervaloPreDefinido [nome=" + nome + ", horaInicial=" + horaInicial + ", duracao=" + duracao + "]";
	}

}
