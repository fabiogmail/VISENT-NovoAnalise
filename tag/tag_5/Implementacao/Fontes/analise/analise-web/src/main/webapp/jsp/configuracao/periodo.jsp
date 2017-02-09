<div class="row">
	<div class="col-xs-12 col-md-5">
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
				    <label><fmt:message key="lbl.data"/></label>
				    <select data-input=".input-data-especifica" class="tipo-data form-control input-sm">
				    	<option value=""><fmt:message key="lbl.especifica"/></option>
				    </select>
				</div>
			</div>	
			<div class="col-xs-6">
				<div class="form-group">
				    <label>&nbsp;</label>
				    <div class="input-data-especifica input-group input-group-sm">
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						<input type="text" class="data-especifica form-control input-sm" placeholder="<fmt:message key="lbl.data"/>" />
					</div>
				</div>
			</div>	
		</div>
	</div>
	<div class="col-xs-12 col-md-2">
		<div class="checkbox">
			<label>
		      <input class="chk-comparativo" name="comparativo" type="checkbox"> <fmt:message key="lbl.comparativo"/> 
		    </label>
		</div>
	</div>
	<div class="col-comparativo col-xs-12 col-md-5" style="display: none">
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
				    <label><fmt:message key="lbl.dataComparativa"/></label>
				    <select data-input=".input-data-especifica-comparativa" class="tipo-data tipo-data-comparativa form-control input-sm">
				    	<option value=""><fmt:message key="lbl.especifica"/></option>
				    </select>
				</div>
			</div>	
			<div class="col-xs-6">
				<div class="form-group">
				    <label>&nbsp;</label>
				    <div class="input-data-especifica-comparativa input-group input-group-sm">
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						<input type="text" class="data-especifica-comparativa form-control input-sm" placeholder="<fmt:message key="lbl.dataComparativa"/>" />
					</div>
				</div>
			</div>	
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<fieldset>
			<legend><fmt:message key="lbl.intervalosPreDefinidos"/></legend>
			<div class="intervalos-pre-definidos"></div>
		</fieldset>
		<br>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<fieldset>
			<legend><fmt:message key="lbl.intervalosEspecificos"/></legend>
			<div class="row">
				<div class="col-xs-12 col-md-8">
					<div class="row">
						<div class="col-xs-12 col-md-2">
							<div class="form-group">
								<label><fmt:message key="lbl.horaInicial"/></label>
								<div class="input-group input-group-sm bootstrap-timepicker timepicker">
									<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
									<input type="text" class="hora-inicial form-control input-sm" placeholder="HH:MM" />
								</div>
							</div>
						</div> <!-- col-md-2 -->
						<div class="col-xs-12 col-md-4">
							<div class="form-group">
								<label><fmt:message key="lbl.duracao"/></label>
								<form class="form-inline">
									<div class="form-group">
										<input type="text" class="duracao form-control input-sm" data-max="7" data-step="1"/>
										<div class="legenda-duracao"><fmt:message key="lbl.uppercase.dia"/></div>
									</div>
									<div class="form-group">
										<input type="text" class="duracao form-control input-sm" data-max="24" data-step="1"/>
										<div class="legenda-duracao"><fmt:message key="lbl.uppercase.hora"/></div>
									</div>
									<div class="form-group">
										<input type="text" class="duracao form-control input-sm" data-max="60" data-step="15"/>
										<div class="legenda-duracao"><fmt:message key="lbl.uppercase.min"/></div>
									</div>
								</form>
							</div>
						</div> <!-- col-md-4 -->
						<div class="col-xs-12 col-md-3">
							<div class="form-group">
								<label><fmt:message key="lbl.calculo"/></label> 
								<br>
								<label>(<span class="duracao-calculo"></span>)</label>
							</div>
						</div> <!-- col-md-3 -->
					</div> <!-- row -->
					<div class="row">
						<div class="col-xs-12 col-md-9">
<!-- 							<label>Granularidade</label> -->
							<button type="button" class="btn-add-intervalo btn btn-sm pull-right"><i class="glyphicon glyphicon-plus"></i> <fmt:message key="lbl.adicionarIntervalo"/></button>
<!-- 							<form class="form-inline"> -->
<!-- 								<div class="radio"> -->
<!-- 									<label> -->
<!-- 								      <input name="granularidade" type="radio" value="" checked> Nenhuma  -->
<!-- 								    </label> -->
<!-- 								</div> -->
<!-- 								<div class="radio"> -->
<!-- 									<label> -->
<!-- 								      <input name="granularidade" type="radio" value="60"> 1h  -->
<!-- 								    </label> -->
<!-- 								</div> -->
<!-- 								<div class="radio"> -->
<!-- 									<label> -->
<!-- 								      <input name="granularidade" type="radio" value="30"> 30m  -->
<!-- 								    </label> -->
<!-- 								</div> -->
<!-- 								<div class="radio"> -->
<!-- 									<label> -->
<!-- 								      <input name="granularidade" type="radio" value="15"> 15m  -->
<!-- 								    </label> -->
<!-- 								</div> -->
<!-- 							</form> -->
						</div> <!-- col-md-9 -->
					</div> <!-- row -->
				</div> <!-- col-md-8 -->
				<div class="col-xs-12 col-md-4">
					<div class="intervalos-especificos">
						<div class="intervalos-especificos-cabecalho">
							<fmt:message key="lbl.intervalosAdicionados"/>
							<a href="#" style="margin-left: 5px" class="pull-right intervalos-especificos-chk"><i class="glyphicon glyphicon-check"></i></a>
							<a href="#" class="pull-right intervalos-especificos-del"><i class="glyphicon glyphicon-trash"></i></a>
						</div>
						<ul class="list-group">
						</ul>
					</div>
				</div> <!-- col-md-4 -->
			</div> <!-- row -->
		</fieldset>
	</div> <!-- col-xs-12 -->
</div> <!-- row -->