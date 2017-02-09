package br.com.visent.analise.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.visent.analise.entity.Agenda;
import br.com.visent.analise.entity.Filtro;
import br.com.visent.analise.entity.InterfaceAba;
import br.com.visent.analise.entity.Relatorio;
import br.com.visent.analise.entity.TipoRelatorio;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.entity.ValoresTree;
import br.com.visent.analise.entity.ValoresTreeSalvos;
import br.com.visent.analise.enums.AgStatus;

@Stateless
public class AnaliseDAO extends GenericDAO {

	private static final String CONDICAO_FILTRO_PRIMARIO = "id >= 10000";
	
	@SuppressWarnings("unchecked")
	public List<Filtro> getFiltrosPrimarios() {
		Query query = em.createQuery(
				"from "+Filtro.class.getSimpleName()+" where "+CONDICAO_FILTRO_PRIMARIO);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getNomesFiltrosPrimarios() {
		Query query = em.createQuery(
				"select nomeExibicao from "+Filtro.class.getSimpleName()+" where "+CONDICAO_FILTRO_PRIMARIO);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ValoresTree> getValoresTreeRaizes(Long idFiltro, Long idPerfil) {
		Query query = em.createQuery(
				"select distinct o from "+ValoresTree.class.getSimpleName()+" o left join fetch o.valoresFilhos where o.filtro = :filtro and o.perfil = :perfil and o.pai is null");
		query.setParameter("filtro", idFiltro);
		query.setParameter("perfil", idPerfil);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<InterfaceAba> getAbasRaizes(Long idTipoRel, Long idTipoTec) {
		Query query = em.createQuery(
				"from "+InterfaceAba.class.getSimpleName()+" where tipoRelatorio.id = :tipoRel and abaPai is null"
						+ " and (tipoTecnologia is null or tipoTecnologia.id = :tipoTec) ");
		query.setParameter("tipoRel", idTipoRel);
		query.setParameter("tipoTec", idTipoTec);
		return query.getResultList();
	}
	
	public void atualizarNomeValorTree(Long id, String nome) {
		Query query = em.createQuery("update "+ValoresTree.class.getSimpleName()+" set nome = :nome where id = :id");
		query.setParameter("nome", nome);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	public String getNomeValorTree(Long idValorTree) {
		Query query = em.createQuery(
				"select nome from "+ValoresTree.class.getSimpleName()+" where id = :id");
		query.setParameter("id", idValorTree);
		return (String) query.getSingleResult();
	}
	
	public Long getIdRaiz(Long idFiltro, Long idPerfil) {
		Query query = em.createQuery(
				"select id from "+ValoresTree.class.getSimpleName()+" where filtro = :filtro and perfil = :perfil and pai is null");
		query.setParameter("filtro", idFiltro);
		query.setParameter("perfil", idPerfil);
		return (Long) query.getSingleResult();
	}
	
	public Long getCountValoresTree(Long idFiltro, Long idPerfil) {
		Query query = em.createQuery(
				"select count(*) from "+ValoresTree.class.getSimpleName()+" where filtro = :filtro and perfil = :perfil and pai is not null");
		query.setParameter("filtro", idFiltro);
		query.setParameter("perfil", idPerfil);
		return (Long) query.getSingleResult();
	}
	
	public void excluirValoresTree(Long idValoresTree) {
		Query query = em.createQuery("delete from "+ValoresTreeSalvos.class.getSimpleName()+" where tree = :tree");
		query.setParameter("tree", idValoresTree);
		query.executeUpdate();
		em.remove(em.getReference(ValoresTree.class, idValoresTree));
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getNomesAgendasAtivas(Usuario usuario, boolean historico) {
		Query query = em.createQuery("select distinct a.nome from "+Agenda.class.getSimpleName()+" a where historico = :historico and a.usuario = :usuario and a.idPerfil = :idPerfil and a.status <> :status");
		query.setParameter("historico", historico);
		query.setParameter("usuario", usuario.getNome());
		query.setParameter("idPerfil", usuario.getPerfil().getId());
		query.setParameter("status", AgStatus.CANCELADO);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> getRelatoriosSimples(Usuario usuario) {
		List<Relatorio> list = new ArrayList<>();
		Query query = em.createQuery("select r.id, r.nome, r.tipoRelatorio.id, r.tipoRelatorio.nome, r.tipoRelatorio.algoritmo from "+Relatorio.class.getSimpleName()+" r where r.usuario.id = :usuarioId");
		query.setParameter("usuarioId", usuario.getId());
		List<Object[]> resultList = query.getResultList();
		for (Object[] obj : resultList) {
			int i=0;
			Relatorio rel = new Relatorio();
			TipoRelatorio tipoRel = new TipoRelatorio();
			rel.setId((Long) obj[i++]);
			rel.setNome((String) obj[i++]);
			tipoRel.setId((Long) obj[i++]);
			tipoRel.setNome((String) obj[i++]);
			tipoRel.setAlgoritmo((Long) obj[i++]);
			rel.setTipoRelatorio(tipoRel);
			list.add(rel);
		}
		return list;
	}
	
	public Long getIdTipoRelatorio(Long idRelatorio) {
		Query query = em.createQuery("select r.tipoRelatorio.id from "+Relatorio.class.getSimpleName()+" r where r.id = :idRelatorio");
		query.setParameter("idRelatorio", idRelatorio);
		return (Long) query.getSingleResult();
	}

	public void cancelarAgendas(Agenda agenda, Usuario usuario, boolean historico) {
		Query query = em.createQuery("update "+Agenda.class.getSimpleName()+" set status = :status where historico = :historico and nome = :nome and usuario = :usuario and idPerfil = :idPerfil");
		query.setParameter("historico", historico);
		query.setParameter("status", AgStatus.CANCELADO);
		query.setParameter("nome", agenda.getNome());
		query.setParameter("usuario", usuario.getNome());
		query.setParameter("idPerfil", usuario.getPerfil().getId());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Agenda> getAgendasAtivasPorNome(String nome, Usuario usuario, boolean historico) {
		Query query = em.createQuery("from "+Agenda.class.getSimpleName()+" a where historico = :historico and a.nome = :nome and a.usuario = :usuario and a.idPerfil = :idPerfil and a.status <> :status");
		query.setParameter("historico", historico);
		query.setParameter("nome", nome);
		query.setParameter("usuario", usuario.getNome());
		query.setParameter("idPerfil", usuario.getPerfil().getId());
		query.setParameter("status", AgStatus.CANCELADO);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Agenda> getAgendasAtivasPorRelatorio(Long idRelatorio, Usuario usuario) {
		Query query = em.createQuery("from "+Agenda.class.getSimpleName()+" a where idRelatorioCfg = :idRelatorio and a.usuario = :usuario and a.idPerfil = :idPerfil and a.status <> :status");
		query.setParameter("idRelatorio", idRelatorio);
		query.setParameter("usuario", usuario.getNome());
		query.setParameter("idPerfil", usuario.getPerfil().getId());
		query.setParameter("status", AgStatus.CANCELADO);
		return query.getResultList();
	}
	
	public void clear() {
		em.clear();
	}
	
}
