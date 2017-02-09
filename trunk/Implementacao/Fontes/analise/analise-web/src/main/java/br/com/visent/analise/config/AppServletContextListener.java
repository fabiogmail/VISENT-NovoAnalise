package br.com.visent.analise.config;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.visent.analise.dao.AnaliseDAO;
import br.com.visent.analise.dao.CDRViewDAO;
import br.com.visent.analise.entity.DataPreDefinida;
import br.com.visent.analise.entity.IntervaloPreDefinido;
import br.com.visent.analise.entity.Tecnologia;
import br.com.visent.analise.entity.TipoRelatorio;
import br.com.visent.analise.entity.TipoTecnologia;
import br.com.visent.analise.util.ParametrosHelper;
import br.com.visent.analise.util.SystemCache;

@WebListener
public class AppServletContextListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppServletContextListener.class);
	
	@Inject private CDRViewDAO cdrview;
	@Inject private AnaliseDAO dao;
	@Inject private SystemCache cache;
	@Inject private ParametrosHelper paramHelper;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("Inicializando CDRView Analise");
		LOGGER.info("Carregando parametros de sistema");
		Map<String, String> parametrosSistema = paramHelper.getTodos();
		LOGGER.info("Conectando com CDRView");
		try {
			cdrview.init(parametrosSistema.get(ParametrosHelper.CDRVIEW_SERVER), 
					Integer.parseInt(parametrosSistema.get(ParametrosHelper.CDRVIEW_PORT_CONTROLE)),
					Integer.parseInt(parametrosSistema.get(ParametrosHelper.CDRVIEW_PORT_PROCESSOS)));
		} catch (Exception e) {
			LOGGER.error("Erro ao conectar com CDRView: "+e.getMessage());
		}
		LOGGER.info("Carregando configuracoes em cache");
		carregarCache();
		LOGGER.info("OK");
	}

	private void carregarCache() {
		cache.setTiposRelatorio(dao.findAll(TipoRelatorio.class));
		cache.setTiposTecnologia(dao.findAll(TipoTecnologia.class));
		cache.setTecnologias(dao.findAll(Tecnologia.class));
		cache.setDatasPreDefinidas(dao.findAll(DataPreDefinida.class));
		cache.setIntervalosPreDefinidos(dao.findAll(IntervaloPreDefinido.class));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		cdrview.close();
	}
	
}
