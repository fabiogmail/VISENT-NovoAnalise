package br.com.visent.analise.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.visent.analise.dao.AnaliseDAO;
import br.com.visent.analise.dao.CDRViewDAO;
import br.com.visent.analise.entity.ParametroSistema;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.util.ParametrosHelper;
import br.com.visent.analise.util.ResultadoExecucao;

//@RunWith(Arquillian.class)
public class ExecucaoServiceTest {

//	@Deployment
//	public static Archive<?> createDeployment() {
//		File[] files = Maven.resolver().loadPomFromFile("pom.xml")
//				.importRuntimeDependencies().resolve().withTransitivity().asFile();
//
//		WebArchive war = ShrinkWrap.create(WebArchive.class, "test-analise.war")
//				.addPackages(true, "br.com.visent.analise")
//				.addAsResource("META-INF/persistence.xml")
//				.addAsWebInfResource("jboss-deployment-structure.xml")
//				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//				.addAsLibraries(files);
//		System.out.println(war.toString(true));
//		return war;
//	}
//	
//	@Inject private ExecucaoService execucaoService;
//	@Inject private AnaliseDAO dao;
//	@Inject private CDRViewDAO cdrview;
//	
//	@Test
//	@InSequence(1)
//	public void init() {
//		Map<String, String> parametrosSistema = carregarParametrosSistema();
//		try {
//			cdrview.init(parametrosSistema.get(ParametrosHelper.CDRVIEW_SERVER), 
//					Integer.parseInt(parametrosSistema.get(ParametrosHelper.CDRVIEW_PORT_CONTROLE)),
//					Integer.parseInt(parametrosSistema.get(ParametrosHelper.CDRVIEW_PORT_PROCESSOS)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private Map<String, String> carregarParametrosSistema() {
//		List<ParametroSistema> list = dao.findAll(ParametroSistema.class);
//		return list.stream().collect(Collectors.toMap(ParametroSistema::getNome, ParametroSistema::getValor));
//	}
//
//	@Test
//	@InSequence(2)
//	public void executar() {
//		Usuario usuario = dao.find(Usuario.class, 1L);
//		List<ResultadoExecucao> resultados = execucaoService.executar(30L, usuario);
//		System.out.println(resultados);
//		for (ResultadoExecucao resultadoExecucao : resultados) {
//			System.out.println(resultadoExecucao.getNome());
//			System.out.println(resultadoExecucao.getCabecalho());
//			System.out.println(resultadoExecucao.getPeriodoLinhas());
//			System.out.println(resultadoExecucao.getLinhasNaoMapeadas());
//		}
//	}

}
