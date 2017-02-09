$(function execucao() {
	
	var $execucoes = $('#execucoes'),
		$dlgColunasDrill = $('#dlg-colunas-drill');
	var mapExecucoes = {};
	
	(function init() {
		extExecucao();
		initDialog();
    }());
	
	function extExecucao() {
		visent.execucao = {};
		visent.execucao.exec = executar;
		visent.execucao.buscarResultado = buscarResultado;
		visent.execucao.execAgenda = executarAgenda;
	}
	
	function initDialog() {
		$dlgColunasDrill.dialog({
			resizable: false,
			height: 470,
			width: 800,
			modal: true,
			autoOpen: false,
			classes: {
				"ui-dialog": "dlg-geral"
			}
	    });
	}
	
	function executar(rel, params) {
		
		var idExec = 'exec-'+rel.id;
		if (params != null) {
			idExec += '-drill';
		}
		if (mapExecucoes[idExec] !== undefined) {
			mapExecucoes[idExec].dialogExtend("maximize");
			return;
		}
		$execucoes.append('<div id="'+idExec+'"><div class="dlg-execucao"></div></div>');
		var $exec = $('#'+idExec);
		var $dlgExec = $exec.find('.dlg-execucao');
		var nome = rel.nome;
		if (params != null) {
			nome += ' - Drill Down';
		}
		$dlgExec.dialog({
			classes: {
				"ui-dialog": "dlg-geral execucao-relatorio execucao-relatorio-"+idExec
			},
			title: nome,
			width: 600, 
			height: 450,
			autoOpen: false,
			close: function(ev, ui) {
				$(this).dialog('destroy');
				$exec.remove();
				delete mapExecucoes[idExec];
			}
		}).dialogExtend({
			"closable" : true,
			"maximizable" : true,
			"minimizable" : true,
			"minimizeLocation" : "right"
		});
		mapExecucoes[idExec] = $dlgExec;
		$dlgExec.load(contexto+'/jsp/execucao/execucao.jsp', function() {
			$dlgExec.dialog('open');
			$dlgExec.dialogExtend("minimize");
			$dlgExec.loading(true);
			$('.execucao-relatorio-'+idExec).find('.ui-dialog-titlebar').addClass('executando');
			iniciarExecucao(idExec, rel, params);
		});
	}
	
	function iniciarExecucao(idExec, rel, params) {
		var data = null;
		if (params != null) {
			data = JSON.stringify(params);
		}
		visent.ws({
			path: 'execucao/'+rel.id,
			method: 'POST',
			data: data,
			done: function() {
				buscarResultado(idExec, rel, params);
			},
			fail: function(ret) {
				var $dlg = mapExecucoes[idExec];
				$dlg.dialog('close');
				if (ret.responseJSON != null && ret.responseJSON instanceof Array) {
					visent.alert(ret.responseJSON.join('<br>'));
				} else {
					visent.alert(ret.responseText);
				}
			}
		});
	}
	
	function buscarResultado(idExec, rel, params) {
		visent.ws({
			path: 'execucao/'+rel.id+'/resultado',
			done: function(resultados) {
				if (resultados === undefined) {
					setTimeout(function() {
						visent.execucao.buscarResultado(idExec, rel, params);
					}, 1000);
					return;
				}
				criarTabela(idExec, rel, params, resultados);
			},
			fail: function(ret) {
				var $dlg = mapExecucoes[idExec];
				$dlg.dialog('close');
				visent.alert(ret.responseText);
			}
		});
	}
	
	function criarTabela(idExec, rel, params, resultados) {
		visent.log(rel);
		visent.log(resultados);
		
		var $dlg = mapExecucoes[idExec];
		$('.execucao-relatorio-'+idExec).find('.ui-dialog-titlebar').removeClass('executando').addClass('pronto');
		$dlg.loading(false);
		var tableCols = criarColunasTabela(rel, params, resultados, $dlg);
		
		for (var i=0; i < resultados.length; i++) {
			var idCabecalho = idExec+'-cab-'+i;
			var cabecalhoHtml = '';
			for (var j=0; j<resultados[i].cabecalho.length; j++) {
				cabecalhoHtml += '<p>'+resultados[i].cabecalho[j]+'</p>';
			}
			$dlg.find('.execs-relatorios-titulos')
				.append('<li><a id="tab-'+idCabecalho+'" href="#'+idCabecalho+'" data-toggle="tab">'+msgs['lbl.cabecalho']+'</a></li>');
			$dlg.find('.execs-relatorios-tabelas')
				.append('<div class="tab-pane" id="'+idCabecalho+'">'+cabecalhoHtml+'</div>');
		}
		
		for (var i=0; i < resultados.length; i++) {
			var id = visent.formatStr(resultados[i].nome);
			var clazz = '';
			var j=0;
			for (var periodo in resultados[i].periodoLinhas) {
				if (i == 0 && j == 0) {
					clazz = 'active';
				}
				id += ('-'+periodo)+i+'_'+j;
				id = visent.formatStr(id);
				$dlg.find('.execs-relatorios-titulos')
					.append('<li class="'+clazz+'"><a id="tab-'+id+'" href="#'+id+'" data-toggle="tab">'+periodo+'</a></li>');
				$dlg.find('.execs-relatorios-tabelas')
					.append('<div class="tab-pane '+clazz+'" id="'+id+'"><div class="table-responsive"><table class="table table-striped table-bordered table-hover table-condensed"></table></div></div>');
				j++;
				$dlg.find('#'+id+' .table').DataTable({
					data: resultados[i].periodoLinhas[periodo],
					columns: tableCols,
					language: visent.dtLanguage
				});
			}
		}
		
		$dlg.find('.btn-export-exec').on('click', function(e) {
			e.preventDefault();
			visent.ws({
				path: 'execucao/'+rel.id+'/exportacao',
				method: 'POST',
				data: JSON.stringify(resultados),
				done: function() {
					window.location = ('ws/execucao/'+rel.id+'/exportacao');
				}
			});
		});
		
		if (rel.tipoRelatorio.drill && params == null) {
			var $btnDrill = $dlg.find('.btn-drill-down');
			$btnDrill.show();
			$btnDrill.on('click', function(e) {
				e.preventDefault();
				$btnDrill.blur();
				if (!$btnDrill.hasClass('active')) {
					$btnDrill.addClass('active');
					$dlg.find('.table').addClass('drill');
				} else {
					$btnDrill.removeClass('active');
					$dlg.find('.table').removeClass('drill');
				}
			});
		}
	}
	
	function criarColunasTabela(rel, params, resultados, $dlg) {
		var colunas = (params != null ? JSON.parse(params.colunas) : JSON.parse(rel.colunas));
		var tableCols = [];
		for (var i in colunas) {
			var col = {title: colunas[i].nome};
			if (resultados.length > 0 && resultados[0].aberturas.indexOf(colunas[i].nome) == -1) {
				col.className = 'drill';
				var aberturas = resultados[0].aberturas;
				col.createdCell = function (td, cellData, rowData, row, col) {
					$(td).on('click', function() {
						if ($(this).closest('.table').hasClass('drill')) {
							$dlgColunasDrill.empty();
							$dlgColunasDrill.load('jsp/configuracao/colunas.jsp', function() {
								visent.colunas.init($dlgColunasDrill, 'execucao/drill/colunas/'+rel.tipoTecnologia.id);
								$dlgColunasDrill.dialog('option', 'buttons', [
		                       	    {
		                       	    	text: msgs['lbl.confirmar'],
		                       	    	click: function() {
		                       	    		prepararDrill($dlg, $dlgColunasDrill, rel, colunas[col].formulaExecucao, aberturas, rowData);
		                   	    	    	$(this).dialog("close");
		                       	    	}
		                       	    },
		                       	    {
		                       	    	text: msgs['lbl.cancelar'],
		                       	    	click: function() {
		                       	    		$(this).dialog("close");
		                       	    	}
		                       	    }
	                   	    	]);
								$dlgColunasDrill.dialog('open');
							});
						}
					});
				};
			}
			tableCols.push(col);
		}
		return tableCols;
	}
	
	function prepararDrill($dlgRel, $dlgColunasDrill, rel, formulaExecucao, nomesAberturas, rowData) {
		var aberturas = [];
		for (var i=0; i<nomesAberturas.length; i++) {
			aberturas.push({
				nome: nomesAberturas[i],
				valor: rowData[i]
			});
		}
		var colunas = visent.colunas.toObj($dlgColunasDrill);
		var indicePeriodo = $dlgRel.find('.tab-pane.active').index();
		var params = {
			colunas: JSON.stringify(colunas),
			indicePeriodo: indicePeriodo,
			colunaDrill: formulaExecucao,
			aberturas: aberturas
		};
		executar(rel, params);
	}
	
	function executarAgenda(exec) {
		var idExec = 'agenda-'+exec.id;
		if (mapExecucoes[idExec] !== undefined) {
			mapExecucoes[idExec].dialogExtend("maximize");
			return;
		}
		$execucoes.append('<div id="'+idExec+'"><div class="dlg-execucao"></div></div>');
		var $exec = $('#'+idExec);
		var $dlgExec = $exec.find('.dlg-execucao');
		$dlgExec.dialog({
			classes: {
				"ui-dialog": "dlg-geral execucao-relatorio execucao-relatorio-"+idExec
			},
			title: exec.nomeArquivo,
			width: 600, 
			height: 450,
			autoOpen: false,
			close: function(ev, ui) {
				$(this).dialog('destroy');
				$exec.remove();
				delete mapExecucoes[idExec];
			}
		}).dialogExtend({
			"closable" : true,
			"maximizable" : true,
			"minimizable" : true,
			"minimizeLocation" : "right"
		});
		mapExecucoes[idExec] = $dlgExec;
		$dlgExec.load(contexto+'/jsp/execucao/execucao.jsp', function() {
			$dlgExec.dialog('open');
			$dlgExec.dialogExtend("minimize");
			$dlgExec.loading(true);
			$('.execucao-relatorio-'+idExec).find('.ui-dialog-titlebar').addClass('executando');
			
			visent.ws({
				path: 'execucao/agenda/'+exec.id,
				done: function(map) {
					var relatorio = map['relatorio'];
					var resultados = map['resultados'];
					criarTabela(idExec, relatorio, null, resultados);
				}
			});
			
		});
	}
	
});