package br.com.visent.analise.execucao;

import java.util.List;
import java.util.concurrent.Callable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.visent.analise.entity.Usuario;
import br.com.visent.analise.service.ExecucaoService;
import br.com.visent.analise.util.DrillParams;
import br.com.visent.analise.util.ResultadoExecucao;

@Dependent
public class Execucao implements Callable<List<ResultadoExecucao>> {

	@Inject private ExecucaoService execucaoService;
	private Long idRel;
	private Usuario usuario;
	private DrillParams drillParams;
	
	public Execucao() {
		
	}
	
	public void setParams(Long idRel, Usuario usuario, DrillParams drillParams) {
		this.idRel = idRel;
		this.usuario = usuario;
		this.drillParams = drillParams;
	}
	
	@Override
	public List<ResultadoExecucao> call() throws Exception {
		return execucaoService.executar(idRel, usuario, drillParams);
	}

}
