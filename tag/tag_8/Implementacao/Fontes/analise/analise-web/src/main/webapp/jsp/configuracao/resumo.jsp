<div class="alert alert-info alert-validacao">
	<img src="images/loading.gif"/> Validando configuração...
</div>

<div class="alert alert-success alert-ok">
	Relatório configurado!
</div>

<div class="col-xs-12 col-md-4">
	<h4 class="text-center"><fmt:message key="lbl.periodo"/></h4>
	<div class="resumo-periodo">
		<div class="alert alert-danger alert-erros"></div>
		<label><fmt:message key="lbl.data"/>: </label>
		<span class="resumo-data-sel"></span>
		<br>
		<label><fmt:message key="lbl.comparativo"/>: </label>
		<span class="resumo-data-comparativa-sel"></span>
		<fieldset>
			<legend><fmt:message key="lbl.intervalosPreDefinidos"/></legend>
			<div class="resumo-intervalos-pre"></div>
		</fieldset>
		<fieldset>
			<legend><fmt:message key="lbl.intervalosEspecificos"/></legend>
			<div class="intervalos-especificos-resumo">
				<ul class="list-group" style="height: 180px">
				</ul>
			</div>
		</fieldset>
	</div>
</div>

<div class="col-xs-12 col-md-4">
	<h4 class="text-center"><fmt:message key="lbl.filtros"/></h4>
	<div class="resumo-filtros-sel">
		<div class="alert alert-danger alert-erros"></div>
		<div class="resumo-filtros-sel-content"></div>
	</div>
</div>

<div class="col-xs-12 col-md-4">
	<h4 class="text-center"><fmt:message key="lbl.colunas"/></h4>
	<div class="panel panel-default">
		<div class="colunas-selecionadas-resumo panel-body">
			<div class="alert alert-danger alert-erros"></div>
			<ul class="list-group">
			</ul>
		</div>
	</div>
</div>

