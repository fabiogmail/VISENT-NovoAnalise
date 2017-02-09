package br.com.visent.analise.dao;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.visent.analise.entity.DataExecucao;
import br.com.visent.analise.entity.DefinicaoFiltro;
import excecao.ConexaoException;
import execucao.Cdrview;
import interfaceComunicacao.IPreparedRelatorioCDRView;

@ApplicationScoped
public class CDRViewDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CDRViewDAO.class);
	
	private Cdrview cdrviewServControle = new Cdrview();
	private Cdrview cdrviewServProcessos = new Cdrview();
	
	public void init(String server, int portControle, int portProcessos) throws Exception {
		cdrviewServControle.conecteServControle(server, portControle);
		cdrviewServProcessos.conecte(server, portProcessos);
	}
	
	public String getIndicadores(int tipoRelatorio, List<String> tecnologias) throws ConexaoException {
		return cdrviewServControle.getIndicadoresCDRView(tipoRelatorio, tecnologias);
	}
	
	public String getValoresDefinicaoFiltro(DefinicaoFiltro definicaoFiltro) {
		String json = null;
		try {
			json = cdrviewServControle.getValoresArquivo(definicaoFiltro.getNomeArquivo(), 
					definicaoFiltro.getChave(), 
					definicaoFiltro.getValor(), 
					definicaoFiltro.getSeparador());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return json;
	}
	
	public List<String> executar(String json) {
		List<String> linhas = null;
		try {
			IPreparedRelatorioCDRView ps = cdrviewServProcessos.getPrepareStatement(json);
			linhas = cdrviewServProcessos.executaRelatorio(ps);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return linhas;
	}
	
	public String getCondicoesDrill(String nomeContador, int idTipoRelatorio) {
		String json = null;
		try {
			json = cdrviewServControle.getCondicoesContadorCfgRelatorioDrill(nomeContador, idTipoRelatorio);
		} catch (ConexaoException e) {
			LOGGER.error(e.getMessage());
		}
		return json;
	}
	
	public List<String> getRelatorioAgendado(DataExecucao dataExecucao) {
		List<String> linhas = null;
		try {
			linhas = cdrviewServProcessos.getRelatorioAgendado(dataExecucao.getAgenda().getIdPerfil().intValue(), 
					dataExecucao.getAgenda().getIdTipoRelatorio().intValue(), 
					dataExecucao.getAgenda().getNome(), 
					Date.from(dataExecucao.getDataExecucao().atZone(ZoneId.systemDefault()).toInstant()), 
					dataExecucao.getNomeArquivo());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return linhas;
	}
	
	public void close() {
		cdrviewServControle.desconecte();
		cdrviewServProcessos.desconecte();
	}

	public static void main(String[] args) {
		try {
			CDRViewDAO dao = new CDRViewDAO();
			dao.init("192.168.200.71", 7767, 7868);
			
//			String indicadores = dao.getIndicadores(0, Arrays.asList("Ericsson GSM"));
//			System.out.println(indicadores);
			
//			byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\naironnunes\\Desktop\\analise files\\json-tronco.txt"));
//			List<String> linhas = dao.executar(new String(bytes));
//			System.out.println("-----------");
//			linhas.forEach(System.out::println);
			
//			String condicoesDrill = dao.getCondicoesDrill("DSC_NORMAL_REL", 0);
//			System.out.println(condicoesDrill);
			
			Cdrview cdrviewServControle = new Cdrview();
			cdrviewServControle.conecteServControle("192.168.200.71", 7767);
			String valoresArquivo = cdrviewServControle.getValoresArquivo("/cfgcli/Prefixos/Operadoras.txt", "NOME", "ID", ";");
			System.out.println(valoresArquivo);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
