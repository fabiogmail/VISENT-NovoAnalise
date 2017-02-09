package br.com.visent.analise.util;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.com.visent.analise.entity.DataPreDefinida;
import br.com.visent.analise.entity.IntervaloPreDefinido;
import br.com.visent.analise.entity.Tecnologia;
import br.com.visent.analise.entity.TipoRelatorio;
import br.com.visent.analise.entity.TipoTecnologia;

@ApplicationScoped
public class SystemCache {
	
	private List<TipoRelatorio> tiposRelatorio;
	private List<TipoTecnologia> tiposTecnologia;
	private List<Tecnologia> tecnologias;
	private List<DataPreDefinida> datasPreDefinidas;
	private List<IntervaloPreDefinido> intervalosPreDefinidos;
	
	public List<TipoRelatorio> getTiposRelatorio() {
		return tiposRelatorio;
	}
	public void setTiposRelatorio(List<TipoRelatorio> tiposRelatorio) {
		this.tiposRelatorio = tiposRelatorio;
	}
	public List<TipoTecnologia> getTiposTecnologia() {
		return tiposTecnologia;
	}
	public void setTiposTecnologia(List<TipoTecnologia> tiposTecnologia) {
		this.tiposTecnologia = tiposTecnologia;
	}
	public List<DataPreDefinida> getDatasPreDefinidas() {
		return datasPreDefinidas;
	}
	public void setDatasPreDefinidas(List<DataPreDefinida> datasPreDefinidas) {
		this.datasPreDefinidas = datasPreDefinidas;
	}
	public List<IntervaloPreDefinido> getIntervalosPreDefinidos() {
		return intervalosPreDefinidos;
	}
	public void setIntervalosPreDefinidos(List<IntervaloPreDefinido> intervalosPreDefinidos) {
		this.intervalosPreDefinidos = intervalosPreDefinidos;
	}
	public List<Tecnologia> getTecnologias() {
		return tecnologias;
	}
	public void setTecnologias(List<Tecnologia> tecnologias) {
		this.tecnologias = tecnologias;
	}

}
