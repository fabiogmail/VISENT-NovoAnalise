package br.com.visent.analise.util;

public class Constants {

	public class Path {
		
		public static final String RELATORIO = "relatorio";
		public static final String RELATORIO_ID = "{id}";
		public static final String RELATORIO_USUARIO = "usuario";
		public static final String RELATORIO_COMPARTILHADOS = "compartilhados";
		public static final String RELATORIO_FAVORITOS = "favoritos";
		public static final String RELATORIO_FAVORITOS_ID = "favoritos/{id}";
		public static final String RELATORIO_COMPARTILHAR = "{idRel}/compartilhar";
		public static final String RELATORIO_CLONAR = "{idRel}/clonar";
		
		public static final String CONFIGURACAO = "configuracao";
		public static final String CONFIGURACAO_CACHE = "cache";
		public static final String CONFIGURACAO_SALVAR = "salvar";
		public static final String CONFIGURACAO_COLUNAS = "colunas/{idTipoRel}/{idTipoTec}";
		public static final String CONFIGURACAO_FILTROS = "filtros/{idTipoRel}/{idTipoTec}";
		public static final String CONFIGURACAO_FILTROS_TREE_ID = "filtros/tree/{idFiltro}";
		
		public static final String FILTROSTREE = "filtros-tree";
		public static final String FILTROSTREE_ID = "{id}";
		public static final String FILTROSTREE_ID_UPLOAD = "{id}/upload";
		
		public static final String EXECUCAO = "execucao";
		public static final String EXECUCAO_ID = "{id}";
		public static final String EXECUCAO_ID_RESULTADO = "{id}/resultado";
		public static final String EXECUCAO_EXPORTACAO = "{id}/exportacao";
		public static final String EXECUCAO_DRILL_COLUNAS = "drill/colunas/{idTipoTec}";
		public static final String EXECUCAO_ID_JSON = "{id}/json";
		public static final String EXECUCAO_AGENDA = "agenda/{id}";
		
		public static final String LOGIN = "login";
		public static final String LOGIN_SESSION = "{usuario}/{perfil}";
		public static final String LOGIN_LOGOUT = "logout";
		
		public static final String AGENDA = "agenda";
		public static final String AGENDA_USUARIO = "usuario";
		public static final String AGENDA_INFOS = "infos";
		public static final String AGENDA_RELATORIOS = "relatorios";
		public static final String AGENDA_RELATORIOS_HISTORICO = "relatorios-historico";
		public static final String AGENDA_DIAS_ANO_MES = "dias/{ano}/{mes}";
		public static final String AGENDA_SALVAR = "salvar";
		public static final String AGENDA_HISTORICO_SALVAR = "historico/salvar";
		public static final String AGENDA_CONF = "{nome}";
		public static final String AGENDA_HISTORICO_CONF = "historico/{nome}";
		public static final String AGENDA_EXECS = "{nome}/execs";
		public static final String AGENDA_HISTORICO = "historico";
		
	}
	
	public class Session {
		
		public static final String USUARIO = "cdrview_usuario";
		
	}
	
}
