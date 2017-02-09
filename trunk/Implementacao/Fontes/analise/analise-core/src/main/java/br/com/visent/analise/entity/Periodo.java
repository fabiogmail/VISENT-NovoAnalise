package br.com.visent.analise.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.visent.analise.adapter.LocalDateAdapter;

@Entity
@Table(name="periodo")
public class Periodo extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="data_especifica")
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate dataEspecifica;
	
	@Column(name="data_especifica_comparativa")
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate dataEspecificaComparativa;
	
	@ManyToOne
	@JoinColumn(name="id_data_pre_definida")
	private DataPreDefinida dataPreDefinida;
	
	@ManyToOne
	@JoinColumn(name="id_data_pre_definida_comparativa")
	private DataPreDefinida dataPreDefinidaComparativa;
	
	@OneToMany(mappedBy="periodo", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Intervalo> intervalosEspecificos;
	
	@ManyToMany
	@JoinTable(name = "periodo_intervalo_pre_definido", 
		joinColumns = @JoinColumn(name = "id_periodo"), 
		inverseJoinColumns = @JoinColumn(name = "id_intervalo_pre_definido"))
	private List<IntervaloPreDefinido> intervalosPreDefinidos;

	public LocalDate getDataEspecifica() {
		return dataEspecifica;
	}

	public void setDataEspecifica(LocalDate dataEspecifica) {
		this.dataEspecifica = dataEspecifica;
	}

	public LocalDate getDataEspecificaComparativa() {
		return dataEspecificaComparativa;
	}

	public void setDataEspecificaComparativa(LocalDate dataEspecificaComparativa) {
		this.dataEspecificaComparativa = dataEspecificaComparativa;
	}

	public DataPreDefinida getDataPreDefinida() {
		return dataPreDefinida;
	}

	public void setDataPreDefinida(DataPreDefinida dataPreDefinida) {
		this.dataPreDefinida = dataPreDefinida;
	}

	public DataPreDefinida getDataPreDefinidaComparativa() {
		return dataPreDefinidaComparativa;
	}

	public void setDataPreDefinidaComparativa(DataPreDefinida dataPreDefinidaComparativa) {
		this.dataPreDefinidaComparativa = dataPreDefinidaComparativa;
	}

	public List<Intervalo> getIntervalosEspecificos() {
		return intervalosEspecificos;
	}

	public void setIntervalosEspecificos(List<Intervalo> intervalosEspecificos) {
		this.intervalosEspecificos = intervalosEspecificos;
	}

	public List<IntervaloPreDefinido> getIntervalosPreDefinidos() {
		return intervalosPreDefinidos;
	}

	public void setIntervalosPreDefinidos(List<IntervaloPreDefinido> intervalosPreDefinidos) {
		this.intervalosPreDefinidos = intervalosPreDefinidos;
	}

	@Override
	public String toString() {
		return "Periodo [dataEspecifica=" + dataEspecifica + ", dataEspecificaComparativa=" + dataEspecificaComparativa
				+ ", dataPreDefinida=" + dataPreDefinida + ", dataPreDefinidaComparativa=" + dataPreDefinidaComparativa
				+ ", intervalosEspecificos=" + intervalosEspecificos + ", intervalosPreDefinidos="
				+ intervalosPreDefinidos + "]";
	}
	
}
