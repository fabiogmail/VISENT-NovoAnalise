package br.com.visent.analise.ws;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.visent.analise.annotation.Compress;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.entity.TipoRelatorio;
import br.com.visent.analise.execucao.ControladorExecucoes;
import br.com.visent.analise.service.AgendaService;
import br.com.visent.analise.service.ConfiguracaoService;
import br.com.visent.analise.service.ExecucaoService;
import br.com.visent.analise.util.Constants;
import br.com.visent.analise.util.DrillParams;
import br.com.visent.analise.util.Messages;
import br.com.visent.analise.util.ResultadoExecucao;
import br.com.visent.analise.util.ResumoExecucao;
import excecao.ConexaoException;

@Path(Constants.Path.EXECUCAO)
public class ExecucaoWS extends AbstractWS {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecucaoWS.class);
	
	private static final String SESSION_ATTR_EXPORT = "analise-exec-export";
	
	@Inject private ControladorExecucoes controlador;
	@Inject	private ConfiguracaoService configuracaoService;
	@Inject	private ExecucaoService execucaoService;
	@Inject private AgendaService agendaService;
	
	@GET
	@Path(Constants.Path.EXECUCAO_ID_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON(@PathParam("id") Long idRel) {
		return execucaoService.getJSONExecucao(idRel, getUsuario());
	}
	
	@POST
	@Path(Constants.Path.EXECUCAO_ID)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response executarRelatorio(@PathParam("id") Long idRel, DrillParams params) {
		Map<String, List<String>> errosPorEtapa = execucaoService.validarExecucao(idRel);
		List<String> erros = errosPorEtapa.values().stream().flatMap(e->e.stream()).collect(Collectors.toList());
		if (!erros.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity(erros).build();
		}
		controlador.addExecucao(idRel, params, getUsuario());
		return Response.ok().build();
	}
	
	@POST
	@Path(Constants.Path.EXECUCAO_VALIDAR)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validarExecucao(Relatorio relatorio) {
		Map<String, List<String>> erros = execucaoService.validarExecucao(relatorio);
		return Response.status(Status.OK).entity(erros).build();
	}
	
	@GET
	@Path(Constants.Path.EXECUCAO_ID_RESULTADO)
	@Produces(MediaType.APPLICATION_JSON)
	@Compress
	public Response getResultado(@PathParam("id") Long idRel) {
		List<ResultadoExecucao> resultados = controlador.getResultado(idRel, getUsuario());
		if (resultados != null) {
			return Response.ok(resultados).build();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path(Constants.Path.EXECUCAO_DRILL_COLUNAS)
	@Produces(MediaType.APPLICATION_JSON)
	public String getColunasDrill(@PathParam("idTipoTec") Long idTipoTec) {
		TipoRelatorio tipoRelatorioDetalhe = configuracaoService.getTipoRelatorioDetalhe();
		try {
			return configuracaoService.getColunas(tipoRelatorioDetalhe.getId(), idTipoTec, getUsuario().getPerfil());
		} catch (ConexaoException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@POST
	@Path(Constants.Path.EXECUCAO_EXPORTACAO)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarExportacao(List<ResultadoExecucao> resultados) {
		getSession().setAttribute(SESSION_ATTR_EXPORT, resultados);
		return Response.ok().build();
	}
	
	@GET
	@Path(Constants.Path.EXECUCAO_EXPORTACAO)
	@Produces("application/zip")
	@SuppressWarnings("unchecked")
	public Response exportar() {
		
		if (getSessionAttribute(SESSION_ATTR_EXPORT) == null) {
			return Response.serverError().build();
		}
		List<ResultadoExecucao> resultados = (List<ResultadoExecucao>) getSessionAttribute(SESSION_ATTR_EXPORT);
		getSession().removeAttribute(SESSION_ATTR_EXPORT);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			for (ResultadoExecucao resultado : resultados) {
				ResumoExecucao resumo = resultado.getResumoExecucao();
				Map<String, List<List<String>>> periodoLinhas = resultado.getPeriodoLinhas();
				Set<Entry<String, List<List<String>>>> entrySet = periodoLinhas.entrySet();
				for (Entry<String, List<List<String>>> entry : entrySet) {
					String periodo = entry.getKey();
					List<List<String>> linhas = entry.getValue();
					String fileName = periodo.replace("/", "_").replace(":", "_");
					
					File file = File.createTempFile(fileName, ".txt");
					BufferedWriter writer = Files.newBufferedWriter(file.toPath());
					Consumer<String> stringWriter = str -> {
						try {
							writer.write(str); 
							writer.newLine();
						} catch (IOException e1) {
						}
					}; 
					Consumer<List<String>> listWriter = list -> {
						try {
							writer.write(String.join(";", list)); 
							writer.newLine();
						} catch (IOException e1) {
						}
					}; 

					stringWriter.accept(resumo.getTitulo());
					stringWriter.accept(Messages.getString("resumo.usuario")+": "+resumo.getUsuario());
					stringWriter.accept(Messages.getString("resumo.relatorio")+": "+resumo.getNomeRelatorio());
					stringWriter.accept(Messages.getString("resumo.tipo")+": "+resumo.getTipoRelatorio());
					stringWriter.accept(Messages.getString("resumo.dataHoraExecucao")+": "+resumo.getDataHoraExecucao());
					stringWriter.accept(Messages.getString("resumo.tempoExecucao")+": "+resumo.getTempoExecucao());
					stringWriter.accept(Messages.getString("resumo.quantidadeLinhas")+": "+resumo.getQuantidadeLinhas());
					stringWriter.accept(Messages.getString("resumo.data")+": "+resumo.getData());
					stringWriter.accept(Messages.getString("resumo.periodos")+": "+resumo.getPeriodos());
					stringWriter.accept(Messages.getString("resumo.colunas")+": "+resumo.getColunas());
					stringWriter.accept(Messages.getString("resumo.filtros")+": "+String.join(",", resumo.getFiltros()));
					stringWriter.accept(periodo);
					stringWriter.accept(String.join(";", resultado.getColunas()));
					linhas.forEach(listWriter);
					
					writer.flush();
					ziparArquivo(zos, file, fileName);
					writer.close();
					file.delete();
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				zos.flush();
				baos.flush();
				zos.close();
				baos.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		} 
		
		String fileName = resultados.get(0).getNome()+".zip";
		return Response.ok(baos.toByteArray()).header("Content-Disposition","attachment; filename=\""+fileName+"\"").build();
	}
	
	private void ziparArquivo(ZipOutputStream zos, File file, String nome) throws Exception {
		byte[] bytes = new byte[2048];
		FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        zos.putNextEntry(new ZipEntry(nome+".txt"));
        int bytesRead;
        while ((bytesRead = bis.read(bytes)) != -1) {
            zos.write(bytes, 0, bytesRead);
        }
        zos.closeEntry();
        bis.close();
        fis.close();
	}
	
	@GET
	@Path(Constants.Path.EXECUCAO_AGENDA)
	@Produces(MediaType.APPLICATION_JSON)
	@Compress
	public Response getAgenda(@PathParam("id") Long idDataExecucao) {
		Map<String, Object> map = agendaService.getResultadoExecucao(idDataExecucao, getUsuario());
		if (map != null) {
			return Response.ok(map).build();
		}
		return Response.noContent().build();
	} 
	
}
