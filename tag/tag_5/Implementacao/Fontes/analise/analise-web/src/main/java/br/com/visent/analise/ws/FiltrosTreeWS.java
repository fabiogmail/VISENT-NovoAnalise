package br.com.visent.analise.ws;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.visent.analise.entity.Filtro;
import br.com.visent.analise.entity.ParametroSistema;
import br.com.visent.analise.entity.ValoresTree;
import br.com.visent.analise.exception.ValidacaoException;
import br.com.visent.analise.service.ConfiguracaoService;
import br.com.visent.analise.util.Constants;
import br.com.visent.analise.util.Messages;
import br.com.visent.analise.util.ParametrosHelper;

@Path(Constants.Path.FILTROSTREE)
public class FiltrosTreeWS extends AbstractWS {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FiltrosTreeWS.class);
	
	@Inject private ParametrosHelper paramHelper;
	@Inject	private ConfiguracaoService configuracaoService;
	
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addValoresTree(ValoresTree valoresTree) {
		ValoresTree pai = new ValoresTree();
		pai.setId(valoresTree.getIdPai());
		valoresTree.setPai(pai);
		dao.persist(valoresTree);
	}
	
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void salvarValoresTree(ValoresTree valoresTree) {
		dao.atualizarNomeValorTree(valoresTree.getId(), valoresTree.getNome());
	}
	
	@DELETE
	@Path(Constants.Path.FILTROSTREE_ID)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void excluirValoresTree(@PathParam("id") Long id) {
		dao.excluirValoresTree(id);
	}
	
	@GET
	@Path(Constants.Path.FILTROSTREE_ID)
	@Produces(MediaType.TEXT_PLAIN)
	public Response exportar(@PathParam("id") Long id) {
		Filtro filtro = dao.find(Filtro.class, id);
		ValoresTree raiz = configuracaoService.getValoresTree(id, getUsuario().getPerfil().getId()).get(0);
		
		File file = null;
		InputStream inputStream = null;
		try {
			file = File.createTempFile(filtro.getNome()+"-", ".tmp");
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			List<ValoresTree> lista = raiz.getValoresFilhos();
			for (ValoresTree valoresTree : lista) {
				arrayBuilder.add(addValorExportacao(valoresTree));
			}
			String json = arrayBuilder.build().toString();
			writer.write(json);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
		try {
			java.nio.file.Path path = Paths.get(file.getAbsolutePath());
			inputStream = Files.newInputStream(path);
			Files.delete(path);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
		String fileName = filtro.getNome()+"-"+getUsuario().getPerfil().getNome()+".txt";
		return Response.ok(inputStream).header("Content-Disposition","attachment; filename=\""+fileName+"\"").build();
	}
	
	private JsonObject addValorExportacao(ValoresTree valoresTree) {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("nome", valoresTree.getNome());
		if (valoresTree.getValoresFilhos() != null && !valoresTree.getValoresFilhos().isEmpty()) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			List<ValoresTree> lista = valoresTree.getValoresFilhos();
			for (ValoresTree valoresTreeFilho : lista) {
				arrayBuilder.add(addValorExportacao(valoresTreeFilho));
			}
			objectBuilder.add("lista", arrayBuilder.build());
		}
		return objectBuilder.build();
	}

	@POST
	@Path(Constants.Path.FILTROSTREE_ID_UPLOAD)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importar(@PathParam("id") Long idFiltro, MultipartFormDataInput input) {
		Map<String, List<InputPart>> form = input.getFormDataMap();
		
		String tipo = null;
		try {
			tipo = form.get("tipo").get(0).getBodyAsString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().build();
		}
		
		List<InputPart> parts = form.get("arquivo");
		InputStream inputStream = null;
		String fileName = null;
		for (InputPart part : parts) {
			try {
				MultivaluedMap<String, String> headers = part.getHeaders();
				fileName = getFileName(headers);
				inputStream = part.getBody(InputStream.class, null);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				return Response.serverError().build();
			}
		}
		
		ValoresTree raiz = getRaiz(idFiltro);
		Long countBD = dao.getCountValoresTree(idFiltro, getUsuario().getPerfil().getId());
		Long maximo = getParametroMaximoImportacao();
		int disponivel = maximo.intValue() - countBD.intValue();
		
		try {
			if ("bkup".equals(tipo)) {
				importarBkup(raiz, inputStream, disponivel);
			} else {
				importarLinhas(raiz, inputStream, fileName, disponivel);
			}
		} catch (ValidacaoException e) {
			String msg = Messages.getString("validacao.limiteImportacao.part1", maximo)+" "+e.getMessage();
			return Response.status(Status.BAD_REQUEST).entity(msg).build();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().build();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		
		return Response.ok().build();
	}
	
	private Long getParametroMaximoImportacao() {
		ParametroSistema param = paramHelper.get(ParametrosHelper.MAXIMO_IMPORTACAO_VALORES_TREE);
		return Long.valueOf(param.getValor());
	}

	private void importarBkup(ValoresTree raiz, InputStream inputStream, int disponivel) throws IOException {
		String conteudo = IOUtils.toString(inputStream, Charset.defaultCharset());
		JsonReader jsonReader = Json.createReader(new StringReader(conteudo));
		JsonArray array = jsonReader.readArray();
		jsonReader.close();

		List<ValoresTree> listValores = new ArrayList<>();
		array.forEach(jsonValue -> {
			listValores.add(addValorImportacao(raiz, (JsonObject)jsonValue));
		});
		int count = 0;
		listValores.forEach(val -> {
			countVals(val, count);
		});
		if (count > disponivel) {
			throw new ValidacaoException(Messages.getString("validacao.limiteImportacao.part2", count, disponivel));
		}
		if (!listValores.isEmpty()) {
			listValores.forEach(v -> dao.persist(v));
		}
	}
	
	private int countVals(ValoresTree val, int count) {
		if (val.getValoresFilhos() != null) {
			List<ValoresTree> filhos = val.getValoresFilhos();
			for (ValoresTree valFilho : filhos) {
				countVals(valFilho, count);
			}
		}
		return ++count;
	}
	
	private ValoresTree addValorImportacao(ValoresTree valoresTreePai, JsonObject jsonObject) {
		ValoresTree valoresTree = new ValoresTree();
		String nome = jsonObject.getString("nome");
		JsonArray lista = jsonObject.getJsonArray("lista");
		
		valoresTree.setPai(valoresTreePai);
		valoresTree.setNome(nome);
		if (lista != null) {
			lista.forEach(jsonValue -> {
				valoresTree.setNo(true);
				valoresTree.setValoresFilhos(new ArrayList<>());
				valoresTree.getValoresFilhos().add(addValorImportacao(valoresTree, (JsonObject)jsonValue));
			});
		}
		
		return valoresTree;
	}
	
	private void importarLinhas(ValoresTree raiz, InputStream inputStream, String fileName, int disponivel) throws IOException {
		LineIterator it = IOUtils.lineIterator(inputStream, Charset.defaultCharset());
		List<ValoresTree> listValores = new ArrayList<>();
		while (it.hasNext()) {
			String line = it.nextLine();
			ValoresTree valoresTree = new ValoresTree();
			valoresTree.setNome(line);
			listValores.add(valoresTree);
		}
		int count = listValores.size();
		if (count > disponivel) {
			throw new ValidacaoException(Messages.getString("validacao.limiteImportacao.part2", count, disponivel));
		}
		if (!listValores.isEmpty()) {
			ValoresTree pasta = new ValoresTree();
			pasta.setPai(raiz);
			pasta.setNo(true);
			pasta.setNome(fileName);
			listValores.forEach(v -> v.setPai(pasta));
			pasta.setValoresFilhos(listValores);
			dao.persist(pasta);
			listValores.forEach(v -> dao.persist(v));
		}
	}
	
	private ValoresTree getRaiz(Long idFiltro) {
		Long idRaiz = dao.getIdRaiz(idFiltro, getUsuario().getPerfil().getId());
		ValoresTree raiz = new ValoresTree();
		raiz.setId(idRaiz);
		return raiz;
	}
	
	private String getFileName(MultivaluedMap<String, String> headers) {
		String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "").replace(".txt", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
	
}
