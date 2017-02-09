package br.com.visent.analise.execucao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.visent.analise.entity.ParametroSistema;
import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.exception.ValidacaoException;
import br.com.visent.analise.util.DrillParams;
import br.com.visent.analise.util.Messages;
import br.com.visent.analise.util.ParametrosHelper;
import br.com.visent.analise.util.ResultadoExecucao;

@ApplicationScoped
public class ControladorExecucoes {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControladorExecucoes.class);
	
	@Resource
    private ManagedExecutorService managedExecutorService;
	
	@Inject
	private Instance<Execucao> execucaoInstance;
	
	@Inject
	private ParametrosHelper paramHelper;
	
	// idUsuario -> idRel -> task 
	private Map<Long, Map<Long, Future<List<ResultadoExecucao>>>> execucoes = new HashMap<>();
	
	public void addExecucao(Long idRel, DrillParams params, Usuario usuario) {
		validarMaximo(usuario.getId());
		execucoes.compute(usuario.getId(), (Long idUsuario, Map<Long, Future<List<ResultadoExecucao>>> tasks) -> {
			if (tasks == null) {
				tasks = new HashMap<>();
			}
			tasks.compute(idRel, (k,task) -> {
				if (task == null || task.isDone()) {
					Execucao execucao = execucaoInstance.get();
					execucao.setParams(idRel, usuario, params);
					return managedExecutorService.submit(execucao);
				}
				return task;
			});
			return tasks;
		});
	}

	private void validarMaximo(Long idUsuario) {
		Map<Long, Future<List<ResultadoExecucao>>> tasks = execucoes.get(idUsuario);
		if (tasks != null) {
			ParametroSistema param = paramHelper.get(ParametrosHelper.MAXIMO_EXECUCOES_USUARIO);
			long count = tasks.values().stream()
				.filter(task -> !task.isDone())
				.count();
			if (count >= Long.parseLong(param.getValor())) {
				throw new ValidacaoException(Messages.getString("validacao.maximoExecucoes", param.getValor()));
			}
		}
	}

	public List<ResultadoExecucao> getResultado(Long idRel, Usuario usuario) {
		Map<Long, Future<List<ResultadoExecucao>>> tasks = execucoes.get(usuario.getId());
		Future<List<ResultadoExecucao>> task = tasks.get(idRel);
		List<ResultadoExecucao> resultados = null;
		if (task.isDone()) {
			try {
				resultados = task.get();
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			} finally {
				tasks.remove(idRel);
			}
		}
		return resultados;
	}
	
}
