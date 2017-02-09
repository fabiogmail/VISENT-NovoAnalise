package br.com.visent.analise.ws;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.visent.analise.entity.Compartilhamento;
import br.com.visent.analise.entity.Favorito;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.service.RelatorioService;
import br.com.visent.analise.util.Constants;

@Path(Constants.Path.RELATORIO)
public class RelatorioWS extends AbstractWS {

	@Inject 
	private RelatorioService relatorioService;
	
	@GET
	@Path(Constants.Path.RELATORIO_USUARIO)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Relatorio> getRelatoriosUsuario() {
		Consumer<Relatorio> limparRelatorio = limparRelatorio();
		return relatorioService.getRelatorios(getUsuario()).stream().peek(limparRelatorio).collect(Collectors.toList());
	}
	
	@GET
	@Path(Constants.Path.RELATORIO_COMPARTILHADOS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Compartilhamento> getRelatoriosCompartilhados() {
		Consumer<Relatorio> limparRelatorio = limparRelatorio();
		List<Compartilhamento> compartilhados = relatorioService.getRelatoriosCompartilhados(getUsuario());
		compartilhados.forEach(compartilhado -> limparRelatorio.accept(compartilhado.getRelatorio()));
		return compartilhados;
	}
	
	@GET
	@Path(Constants.Path.RELATORIO_FAVORITOS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Favorito> getRelatoriosFavoritos() {
		Consumer<Relatorio> limparRelatorio = limparRelatorio();
		List<Favorito> favoritos = relatorioService.getRelatoriosFavoritos(getUsuario());
		favoritos.forEach(favorito -> limparRelatorio.accept(favorito.getRelatorio()));
		return favoritos;
	}
	
	private Consumer<Relatorio> limparRelatorio() {
		return relatorio->{
			relatorio.setPeriodo(null);
//			relatorio.setColunas(null);
			relatorio.setValoresFiltros(null);
			relatorio.setValoresTreeSalvos(null);
		};
	}
	
	@POST
	@Path(Constants.Path.RELATORIO_FAVORITOS_ID)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFavorito(@PathParam("id") Long idRelatorio) {
		Favorito favorito = relatorioService.addFavorito(idRelatorio, getUsuario());
		return Response.ok(favorito.getId()).build();
	}
	
	@DELETE
	@Path(Constants.Path.RELATORIO_FAVORITOS_ID)
	public Response removerFavorito(@PathParam("id") Long idFavorito) {
		relatorioService.removerFavorito(idFavorito, getUsuario());
		return Response.ok().build();
	}
	
	@DELETE
	@Path(Constants.Path.RELATORIO_USUARIO)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removerRelatoriosUsuario(List<Long> idsRelatorios) {
		relatorioService.removerRelatorios(idsRelatorios, getUsuario());
		return Response.ok().build();
	}
	
	@GET
	@Path(Constants.Path.RELATORIO_ID)
	@Produces(MediaType.APPLICATION_JSON)
	public Relatorio getRelatorio(@PathParam("id") Long id) {
		return relatorioService.getRelatorio(id, getUsuario());
	}
	
	@POST
	@Path(Constants.Path.RELATORIO_COMPARTILHAR)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response compartilhar(@PathParam("idRel") Long idRelatorio, List<Long> idsUsuarios) {
		relatorioService.compartilharRelatorio(idRelatorio, idsUsuarios, getUsuario());
		return Response.ok().build();
	}
	
	@POST
	@Path(Constants.Path.RELATORIO_CLONAR)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response clonar(@PathParam("idRel") Long idRelatorio) {
		relatorioService.clonarRelatorio(idRelatorio, getUsuario());
		return Response.ok().build();
	}
	
	@GET
	@Path(Constants.Path.RELATORIO_COMPARTILHAR)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getInfosCompartilhamento(@PathParam("idRel") Long idRelatorio) {
		return relatorioService.getInfosCompartilhamento(idRelatorio, getUsuario());
	}
	
}
