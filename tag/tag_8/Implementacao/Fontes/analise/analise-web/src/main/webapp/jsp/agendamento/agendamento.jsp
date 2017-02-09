<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="topo-section-agend" class="btn-rel-agend">
	<div class="btn-group btn-group-sm">
	  <button type="button" class="btn btn-rel"><i class="glyphicon glyphicon-list-alt"></i> <fmt:message key="relatorios.btn.relatorios"/></button>
	  <button type="button" class="btn active"><i class="glyphicon glyphicon-calendar"></i> <fmt:message key="relatorios.btn.agendamentos"/></button>
	</div>
</div>

<div id="agendamento">

	<ul id="tab-agendamento-titulos" class="nav nav-tabs">
    	<li class="active"><a id="tab-agendamento-agendas" href="#agendamento-agendas" data-toggle="tab">Agendas</a></li>
    	<li><a id="tab-agendamento-historicos" href="#agendamento-historicos" data-toggle="tab">Séries Históricas</a></li>
  	</ul>
  	
  	<div id="tab-agendamento-conteudos" class="tab-content">
	    <div class="tab-pane active" id="agendamento-agendas" data-historico="0">
	    
	    	<div class="row">
		    	<div class="col-xs-12 col-sm-4 col-md-3">
		
					<div class="panel panel-default">
						<div class="panel-heading">
							Agendas
						</div>
						<div class="panel-body">
							<div id="lista-agendas"></div>
						</div>
					</div>
				
				</div>
				
				<div class="col-xs-12 col-sm-8 col-md-9" style="padding-left: 0;">
				
					<div class="panel panel-default">
						<div class="panel-heading">
							<button class="btn btn-sm btn-add-ag" style="display: none"><i class="glyphicon glyphicon-plus"></i></button>&nbsp;
							Configuração <span class="ag-conf-nome"></span>
						</div>
						<div class="panel-body">
						
							<div class="row">
								<div class="col-xs-12 col-md-5 ag-col-conf">
									<div class="row">
										<div class="col-xs-6">
											<div class="form-group">
											    <label>Nome</label>
											    <input type="text" class="form-control input-sm ag-nome" placeholder="Nome">
										  	</div>
										</div>
										<div class="col-xs-6">
											<div class="form-group">
											    <label>Periodicidade</label>
											    <select class="form-control input-sm ag-periodicidade">
											    </select>
										  	</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-xs-6 ag-periodicidade-conf ag-periodicidade-UMA_VEZ">
											<label>Data</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
												<input type="text" class="ag-data form-control input-sm" placeholder="<fmt:message key="lbl.data"/>" />
											</div>
										</div>
										<div class="col-xs-6">
											<label>Hora</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
												<input type="text" class="ag-hora form-control input-sm" placeholder="HH:MM" />
											</div>
										</div>
									</div>
									
									<div class="ag-periodicidade-conf ag-periodicidade-SEMANA">
										<br>
										<div class="row">
											<div class="col-xs-6">
												<label>Ano</label>
												<select class="form-control input-sm ag-ano">
											    </select>
											</div>
											<div class="col-xs-6">
												<label>Mês</label>
												<select class="form-control input-sm ag-mes">
											    </select>
											</div>
										</div>
										<br>
										<div>
											<label>Ocorrência</label>
											<form class="ag-ocorrencias form-inline">
											</form>
										</div>
										<br>
										<div>
											<label>Dia da Semana</label>
											<form class="ag-dias-semana form-inline">
											</form>
										</div>
									</div>
									
									<div class="ag-periodicidade-conf ag-periodicidade-MES">
										<br>
										<div class="row">
											<div class="col-xs-6">
												<label>Ano</label>
												<select class="form-control input-sm ag-ano">
											    </select>
											</div>
											<div class="col-xs-6">
												<label>Mês</label>
												<select class="form-control input-sm ag-mes">
											    </select>
											</div>
										</div>
										<br>
										<div class="row">
											<div class="col-xs-12">
												<div class="ag-dias"></div>
											</div>
										</div>
									</div>
									
									<%@include file="agendamento-exportacao.jsp" %>
								</div>
								
								<div class="col-xs-12 col-md-7">
									<%@include file="agendamento-relatorios.jsp" %>
								</div>
								
							</div>
							
						</div>
					</div>
				
				</div>
	    	</div>
			
	    </div>
	    <div class="tab-pane" id="agendamento-historicos" data-historico="1">
	    	<div class="row">
		    	
		    	<div class="col-xs-12 col-md-4">
		
					<div class="panel panel-default">
						<div class="panel-heading">
							Séries Históricas
						</div>
						<div class="panel-body">
							<div id="lista-historicos"></div>
						</div>
					</div>
				
				</div>
				
				<div class="col-xs-12 col-md-8">
				
					<div class="panel panel-default">
						<div class="panel-heading">
							<button class="btn btn-sm btn-add-ag" style="display: none"><i class="glyphicon glyphicon-plus"></i></button>&nbsp;
							Configuração <span class="ag-conf-nome"></span>
						</div>
						<div class="panel-body">
					
							<div class="row">
								<div class="col-xs-12 col-md-6 ag-col-conf">
									<div class="row">
										<div class="col-xs-6">
											<div class="form-group">
											    <label>Nome</label>
											    <input type="text" class="form-control input-sm ag-nome" placeholder="Nome">
										  	</div>
										</div>
										<div class="col-xs-6">
											<div class="form-group">
											    <label>Periodicidade</label>
											    <select class="form-control input-sm ag-periodicidade">
											    </select>
										  	</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-6">
											<label>Data Início</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
												<input type="text" class="ag-data ag-data-i form-control input-sm" placeholder="<fmt:message key="lbl.data"/>" data-alldays="1"/>
											</div>
										</div>
										<div class="col-xs-6 ag-periodicidade-conf ag-periodicidade-MINUTO_15 ag-periodicidade-MINUTO_30 ag-periodicidade-HORA">
											<label>Hora</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
												<input type="text" class="ag-hora ag-hora-i form-control input-sm" placeholder="HH:MM" />
											</div>
										</div>
										<div class="col-xs-6 ag-hora-consolidacao ag-periodicidade-conf ag-periodicidade-DIA ag-periodicidade-SEMANA ag-periodicidade-MES">
											<label>Hora Início de Consolidação</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
												<input type="text" class="ag-hora ag-hora-i-consolidacao form-control input-sm" placeholder="HH:MM" />
											</div>
										</div>
									</div>
									<br>
									<div class="row">
										<div class="col-xs-6">
											<label>Data Término</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
												<input type="text" class="ag-data ag-data-t form-control input-sm" placeholder="<fmt:message key="lbl.data"/>" />
											</div>
										</div>
										<div class="col-xs-6 ag-periodicidade-conf ag-periodicidade-MINUTO_15 ag-periodicidade-MINUTO_30 ag-periodicidade-HORA">
											<label>Hora</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
												<input type="text" class="ag-hora ag-hora-t form-control input-sm" placeholder="HH:MM" />
											</div>
										</div>
										<div class="col-xs-6 ag-hora-consolidacao ag-periodicidade-conf ag-periodicidade-DIA ag-periodicidade-SEMANA ag-periodicidade-MES">
											<label>Hora Término de Consolidação</label>
											<div class="input-group input-group-sm">
												<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
												<input type="text" class="ag-hora ag-hora-t-consolidacao form-control input-sm" placeholder="HH:MM" />
											</div>
										</div>
									</div>
									<br>
									<form class="form-horizontal">
										<div class="form-group" style="margin-bottom: 5px;">
										    <label class="col-sm-6 control-label">Qtd. Períodos Armazenados</label>
										    <div class="col-sm-6">
										    	<p class="form-control-static periodos-armazenados">0</p>
										    </div>
										</div>
									</form>
									<div class="row">
										<div class="col-xs-12">
											<label>Latência Associada</label>
											<form class="form-inline">
												<div class="form-group">
													<input type="text" class="latencia form-control input-sm" data-max="7" data-step="1"/>
													<div class="legenda-duracao"><fmt:message key="lbl.uppercase.dia"/></div>
												</div>
												<div class="form-group">
													<input type="text" class="latencia form-control input-sm" data-max="24" data-step="1"/>
													<div class="legenda-duracao"><fmt:message key="lbl.uppercase.hora"/></div>
												</div>
												<div class="form-group">
													<input type="text" class="latencia form-control input-sm" data-max="60" data-step="15"/>
													<div class="legenda-duracao"><fmt:message key="lbl.uppercase.min"/></div>
												</div>
											</form>
										</div>
									</div>
									<div class="ag-periodicidade-conf ag-periodicidade-SEMANA">
										<br>
										<div>
											<label>Ocorrência</label>
											<form class="ag-ocorrencias form-inline">
											</form>
										</div>
										<br>
										<div>
											<label>Dia da Semana</label>
											<form class="ag-dias-semana form-inline">
											</form>
										</div>
									</div>
									<div class="ag-periodicidade-conf ag-periodicidade-MES">
										<br>
										<div class="row">
											<div class="col-xs-12">
												<div class="ag-dias"></div>
											</div>
										</div>
									</div>
									<%@include file="agendamento-exportacao.jsp" %>
								</div>
								<div class="col-xs-12 col-md-6">
									<%@include file="agendamento-relatorios.jsp" %>
								</div>
							</div>
					
							<div class="row">
								<div class="col-xs-12">
									<button class="btn btn-sm pull-right btn-ag-salvar">Salvar</button>
								</div>
							</div>
						
						</div>
					</div>
				
				</div>
				
			</div>
	    </div>
  	</div>

</div>
<div id="dlg-ag-execs">
	<div class="row">
		<div id="execs-wrapper" class="col-xs-12"></div>
	</div>
</div>