$(function agendamento() {

	var $agendamento = $('#agendamento');
	var $abaAgendas = $('#agendamento-agendas');
	var $abaHistoricos = $('#agendamento-historicos');
	var SELECTOR_PERIODICIDADE_UMA_VEZ = '.ag-periodicidade-UMA_VEZ'; 
	var SELECTOR_PERIODICIDADE_SEMANA = '.ag-periodicidade-SEMANA';
	var SELECTOR_PERIODICIDADE_MES = '.ag-periodicidade-MES';
	var agendas = null;
	var historicos = null;
	var infos = null;
	var relatorios = null;
	var relatoriosHistorico = null;
	
	(function init() {
		extAgendamento();
		initDialog();
		bindBtnAddAg();
		bindChangeExportacao();
		bindSpinners();
		bindPeriodo();
	})();
	
	function extAgendamento() {
		visent.agendamento = {};
		visent.agendamento.init = initAgendamento;
	}
	
	function initDialog() {
		$('#dlg-ag-execs').dialog({
			title: '',
			autoOpen: false,
			width: 550, 
			height: 500,
			classes: {
				"ui-dialog": "dlg-geral"
			}
		});
	}
	
	function getCurrentTab() {
		return $('#agendamento .tab-pane:visible');
	}
	
	function bindBtnAddAg() {
		$('.btn-add-ag').on('click', function() {
			var $tab = getCurrentTab();
			if ($tab.attr('id') == 'agendamento-agendas') {
				atualizarRelatorios(); 
			} else {
				atualizarRelatoriosHistorico();
			}
			$tab.find('.ag-data').val(Date.create().format('{dd}/{MM}/{yyyy}'));
			$tab.find('.ag-hora').val(Date.create().format('{HH}:{mm}'));
			$tab.find('.ag-conf-nome').html('');
			$tab.find('.ag-nome').val('').prop('disabled', false);
			$tab.find('.ag-intervalo-exec').val('00:00');
			$tab.find('.ag-exportacao input, .checkbox input, .radio input').prop('checked', false);
			$tab.find('select').each(function(i, select){
				if (!select.multiple) {
					select.selectedIndex=0;
					$(select).trigger('change');
				}
			});
			$tab.find('.latencia').val('0');
			$tab.find('.periodos').html('0');
			$(this).hide();
		});
	}
	
	function bindChangeExportacao() {
		$('[name="ag-exportacao-comum"]').on('click', function() {
			var $tab = getCurrentTab();
			$tab.find('[name="ag-exportacao-conf"]').prop('checked', false);
		});
		$('[name="ag-exportacao-conf"]').on('click', function() {
			var $tab = getCurrentTab();
			$tab.find('[name="ag-exportacao-comum"]').prop('checked', false);
		});
	}
	
	function initAgendamento(idRel) {
		$abaHistoricos.find('.ag-intervalo-exec-group').hide();
		bindDatepicker();
		bindBtnSalvar();
		
		$abaAgendas.loading(true);
		visent.ws({
			path: 'agenda/relatorios',
			done: function(relatoriosRet) {
				relatorios = relatoriosRet;
				atualizarRelatorios(idRel);
				if (agendas == null) {
					atualizarAgendas();
				}
			},
			always: function() {
				$abaAgendas.loading(false);
			}
		});
		
		$abaHistoricos.loading(true);
		visent.ws({
			path: 'agenda/relatorios-historico',
			done: function(relatoriosRet) {
				relatoriosHistorico = relatoriosRet;
				atualizarRelatoriosHistorico(idRel);
				if (historicos == null) {
					atualizarHistoricos();
				}
			},
			always: function() {
				$abaHistoricos.loading(false);
			}
		});
		
		if (infos == null) {
			visent.ws({
				path:'agenda/infos',
				done: function(infosRet) {
					infos = infosRet;
					visent.html.select($abaAgendas.find('.ag-periodicidade'), infos.periodicidadeAgenda, 'valor', 'descricao');
					visent.html.select($abaHistoricos.find('.ag-periodicidade'), infos.periodicidadeHistorico, 'valor', 'descricao');
					visent.html.select($('.ag-ano'), infos.anos);
					visent.html.select($('.ag-mes'), infos.meses, 'valor', 'descricao');
					$('.ag-ano').prepend('<option value="-1">'+msgs['agenda.todos.anos']+'</option>');
					$('.ag-mes').prepend('<option value="-1">'+msgs['agenda.todos.meses']+'</option>');
					addOcorrencias();
					addDiasSemana();
					bindPeriodicidade();
					bindDiasDoMes();
				}
			});
		} 
		
	}
	
	function bindDatepicker() {
		var $datepicker = $('.ag-data');
		$datepicker.val(Date.create().format('{dd}/{MM}/{yyyy}'));
		$datepicker.each(function() {
			var alldays = $(this).data('alldays') != null ? true : false;
			var conf = {
					format: "dd/mm/yyyy",
					todayHighlight: true,
					language: "pt-BR",
					autoclose: true,
					calendarWeeks: true
				};
			if (!alldays) {
				conf.startDate = new Date();
			}
			$(this).mask('00/00/0000');
			$(this).datepicker(conf);
		});
		$('.ag-hora, .ag-intervalo-exec').timepicker({
			showMeridian: false
		});
		$('.ag-intervalo-exec').val('00:00');
	}
	
	function bindPeriodicidade() {
		var $sel = $('.ag-periodicidade');
		$sel.on('change', function() {
			var $tab = $(this).closest('.tab-pane');
			var val = $(this).val();
			$tab.find('.ag-periodicidade-conf').hide();
			$tab.find('.ag-periodicidade-'+val).show();
		});
		$sel.trigger('change');
	}
	
	function bindDiasDoMes() {
		$('.ag-periodicidade-MES select').on('change', function() {
			var $conf = $(this).closest('.ag-periodicidade-conf');
			var mes = $conf.find('.ag-mes').val();
			var ano = $conf.find('.ag-ano').val();
			visent.ws({
				path: 'agenda/dias/'+ano+'/'+mes,
				done: function(qtdDias) {
					var $tab = $conf.closest('.tab-pane');
					var dias = [];
					for (var i=1; i<=qtdDias; i++) {
						dias.push(i);
					}
					var $boxes = $tab.find('.ag-dias');
					$boxes.empty();
					$boxes.dualListBoxes({
						valores: dias,
						info: true,
						titulo: 'Dias'
					});
				}
			});
		});
		$('.ag-periodicidade-MES .ag-ano').trigger('change');
		
		var dias = [];
		for (var i=1; i<=31; i++) {
			dias.push(i);
		}
		var $boxes = $abaHistoricos.find('.ag-dias');
		$boxes.empty();
		$boxes.dualListBoxes({
			valores: dias,
			info: true,
			titulo: 'Dias'
		});
		
	}
	
	function addOcorrencias() {
		var $container = $('.ag-ocorrencias');
		$container.empty();
		for (var i in infos.semanaOcorrencia) {
			var semana = infos.semanaOcorrencia[i];
			$container.append('<div class="checkbox">'
					+ '<label>'
					+ '<input name="ag-ocorrencia" type="checkbox" value="'+semana+'"> '+semana
					+ '</label>'
				+ '</div>');
		}
	}
	
	function addDiasSemana() {
		var $container = $('.ag-dias-semana');
		$container.empty();
		for (var i in infos.diaSemana) {
			var dia = infos.diaSemana[i];
			$container.append('<div class="checkbox">'
					+ '<label>'
					+ '<input name="ag-dia-semana" type="checkbox" value="'+dia+'"> '+dia
					+ '</label>'
				+ '</div>');
		}
	}
	
	function atualizarAgendas() {
		
		$abaAgendas.loading(true);
		visent.ws({
			path:'agenda/usuario',
			done: function(agendasRet) {
				agendas = agendasRet;
				$('#lista-agendas').html('<table style="width: 100%;" id="tb-agendas" class="table table-striped table-bordered table-hover table-condensed"></table>');
				$('#tb-agendas').DataTable({
					data: agendas,
				    columns: [
				              {title: 'Nome', data: 'nome'},
				              { title: msgs['relatorios.col.acoes'], data: null, sortable: false, width: '70px', className: 'text-center', render: function(data, display, row, meta) {
					        	  return '<div class="btn-group btn-group-xs">'
						        	  +'<button type="button" class="btn btn-edit" title="'+msgs['agenda.col.edit']+'"><i class="glyphicon glyphicon-wrench"></i></button>'
						        	  +'<button type="button" class="btn btn-del" title="'+msgs['agenda.col.del']+'"><i class="glyphicon glyphicon-trash"></i></button>'
						        	  +'<button type="button" class="btn btn-execs" title="'+msgs['agenda.col.exec']+'"><i class="glyphicon glyphicon-eye-open"></i></button>'
						        	+'</div>';
					          }}
				              ],
			        language: visent.dtLanguage,
			        dom: "<'row'<'col-sm-12'l>>" +
			        	 "<'row'<'col-sm-12'tr>>" +
			        	 "<'row'<'col-sm-5'i><'col-sm-7'p>>",
	        	    createdRow: function(row, data, dataIndex) {
	        	    	var $row = $(row);
	        	    	$row.find('.btn-edit').on('click', function() {
	        	    		$abaAgendas.loading(true);
	        	    		visent.ws({
        	    				path: 'agenda/'+data.nome,
        	    				done: function(agendaConf) {
        	    					editarConf(agendaConf, getCurrentTab());
        	    				},
        	    				always: function() {
        	    					$abaAgendas.loading(false);
        	    				}
	        	    		});
	        	    	});
	        	    	$row.find('.btn-del').on('click', function() {
	        	    		visent.confirm(msgs['agenda.confirmacao.del'], function() {
	        	    			$abaAgendas.loading(true);
	        	    			visent.ws({
	        	    				path: 'agenda/'+data.nome,
	        	    				method: 'DELETE',
	        	    				done: function() {
	        	    					atualizarAgendas();
	        	    				},
	        	    				always: function() {
	        	    					$abaAgendas.loading(false);
	        	    				}
	        	    			});
	        	    		});
	        	    	});
	        	    	$row.find('.btn-execs').on('click', function() {
	        	    		$('#dlg-ag-execs').dialog('option', 'title', data.nome);
	        	    		$('#dlg-ag-execs').dialog('open');
	        	    		$('#dlg-ag-execs').loading(true);
	        	    		buscarExecucoes(data.nome);
	        	    	});
	        	    }
				});
			},
			always: function() {
				$abaAgendas.loading(false);
			}
		});
		
	}
	
	function buscarExecucoes(nome) {
		visent.ws({
			path: 'agenda/'+nome+'/execs',
			done: function(execs) {
				visent.log(execs);
				$('#execs-wrapper').html('<table id="tb-ag-execs" class="table table-striped table-bordered table-hover table-condensed"></table>');
				$('#tb-ag-execs').DataTable({
					data: execs,
				    columns: [
				              {title: msgs['agenda.col.dataExecucao'], data: 'dataExecucao'},
				              {title: msgs['agenda.col.nomeArquivo'], data: 'nomeArquivo', className: 'execAgenda'},
				              ],
			        language: visent.dtLanguage,
			        createdRow: function(row, data, dataIndex) {
			        	$(row).find('.execAgenda').on('click', function() {
			        		visent.execucao.execAgenda(data);
			        	});
			        }
				});
			}, 
			always: function() {
				$('#dlg-ag-execs').loading(false);
			}
		});
	}
	
	function atualizarHistoricos() {
		
		$abaHistoricos.loading(true);
		visent.ws({
			path:'agenda/historico',
			done: function(historicosRet) {
				historicos = historicosRet;
				$('#lista-historicos').html('<table style="width: 100%;" id="tb-historicos" class="table table-striped table-bordered table-hover table-condensed"></table>');
				$('#tb-historicos').DataTable({
					data: historicos,
				    columns: [
				              {title: 'Nome', data: 'nome'},
				              { title: msgs['relatorios.col.acoes'], data: null, sortable: false, width: '70px', className: 'text-center col-acoes', render: function(data, display, row, meta) {
					        	  return '<div class="btn-group btn-group-xs">'
						        	  +'<button type="button" class="btn btn-edit" title="'+msgs['agenda.col.edit']+'"><i class="glyphicon glyphicon-wrench"></i></button>'
						        	  +'<button type="button" class="btn btn-del" title="'+msgs['agenda.col.del']+'"><i class="glyphicon glyphicon-trash"></i></button>'
						        	+'</div>';
					          }}
				              ],
			        language: visent.dtLanguage,
			        dom: "<'row'<'col-sm-12'l>>" +
			        	 "<'row'<'col-sm-12'tr>>" +
			        	 "<'row'<'col-sm-5'i><'col-sm-7'p>>",
	        	    createdRow: function(row, data, dataIndex) {
	        	    	var $row = $(row);
	        	    	$row.find('.btn-edit').on('click', function() {
	        	    		$abaHistoricos.loading(true);
	        	    		visent.ws({
        	    				path: 'agenda/historico/'+data.nome,
        	    				done: function(agendaConf) {
        	    					editarConf(agendaConf, getCurrentTab());
        	    				},
        	    				always: function() {
        	    					$abaHistoricos.loading(false);
        	    				}
	        	    		});
	        	    	});
	        	    	$row.find('.btn-del').on('click', function() {
	        	    		visent.confirm(msgs['agenda.historico.confirmacao.del'], function() {
	        	    			$abaHistoricos.loading(true);
	        	    			visent.ws({
	        	    				path: 'agenda/historico/'+data.nome,
	        	    				method: 'DELETE',
	        	    				done: function() {
	        	    					atualizarHistoricos();
	        	    				},
	        	    				always: function() {
	        	    					$abaHistoricos.loading(false);
	        	    				}
	        	    			});
	        	    		});
	        	    	});
	        	    }
				});
			},
			always: function() {
				$abaHistoricos.loading(false);
			}
		});
		
	}
	
	function editarConf(agendaConf, $tab) {
		visent.log(agendaConf);
		var ehHistorico = agendaConf.historico;
		$tab.find('.btn-add-ag').show();
		$tab.find('.ag-nome').val(agendaConf.nome).prop('disabled', true);
		$tab.find('.ag-conf-nome').html(' - '+agendaConf.nome);
		$tab.find('.ag-periodicidade').val(agendaConf.periodicidade).trigger('change');
		
		if (agendaConf.intervaloExecucao != null) {
			$tab.find('.ag-intervalo-exec').val(agendaConf.intervaloExecucao.substr(0,5));
		} else {
			$tab.find('.ag-intervalo-exec').val('00:00');
		}
		
		var dataHoraInicioArr = agendaConf.inicio.split(' ');
		var dataInicio = dataHoraInicioArr[0];
		var horaInicio = dataHoraInicioArr[1];
		if (!ehHistorico) {
			$tab.find('.ag-hora').val(horaInicio.substr(0,5));
		}
		
		var rels = [];
		for (var i in agendaConf.relatorios) {
			rels.push(agendaConf.relatorios[i].id);
		}
		$tab.find('.ag-conf-relatorios .listboxFrom').val(rels);
		$tab.find('.ag-conf-relatorios .dlb_enviar').trigger('click');
		
		if ($tab.find(SELECTOR_PERIODICIDADE_UMA_VEZ).is(':visible')) {
			$tab.find('.ag-data').val(dataInicio);
		} else {
			if (!ehHistorico) {
				$tab.find('.ag-ano:visible').val(dataInicio.substring(6));
				$tab.find('.ag-mes:visible option')[(dataInicio.substr(3,2)-1)].selected = true;
			} else {
				// datas, latencia, periodo
				$tab.find('.ag-data-i').val(dataInicio);
				$tab.find('.ag-data-t').val(agendaConf.termino.split(' ')[0]);
				var horaI = agendaConf.horaInicio != null ? agendaConf.horaInicio.substr(0,5) : horaInicio.substr(0,5);
				var horaT = agendaConf.horaTermino != null ? agendaConf.horaTermino.substr(0,5) : agendaConf.termino.split(' ')[1].substr(0,5);
				$tab.find('.ag-hora-i, .ag-hora-i-consolidacao').val(horaI);
				$tab.find('.ag-hora-t, .ag-hora-t-consolidacao').val(horaT);
				var latencia = agendaConf.latencia;
				var dias = Number.parseInt(latencia / 86400);
				latencia = (latencia-(dias*86400));
				var horas = Number.parseInt(latencia / 3600);
				latencia = (latencia-(horas*3600));
				var minutos = Number.parseInt(latencia / 60);
				var inputs = $tab.find('.latencia');
				inputs[0].value = dias;
				inputs[1].value = horas;
				inputs[2].value = minutos;
				$tab.find('.periodos-armazenados').html(agendaConf.periodo);
			}
			if ($tab.find(SELECTOR_PERIODICIDADE_SEMANA).is(':visible')) {
				$tab.find('.ag-ocorrencias input').each(function(i, input) {
					if (agendaConf.semanasDeOcorrencia.values.indexOf(input.value) != -1) {
						input.checked = true;
					}
				});
				$tab.find('.ag-dias-semana input').each(function(i, input) {
					if (agendaConf.diasDaSemana.values.indexOf(input.value) != -1) {
						input.checked = true;
					}
				});
			} 
			if ($tab.find(SELECTOR_PERIODICIDADE_MES).is(':visible')) {
				var sel = [];
				for (var i in agendaConf.diasDoMes.values) {
					sel.push(agendaConf.diasDoMes.values[i]);
				}
				$tab.find('.ag-dias .listboxFrom').val(sel);
				$tab.find('.ag-dias .dlb_enviar').trigger('click');
			}
		}
		
		if (agendaConf.exportacoes != null && agendaConf.exportacoes.length > 0) {
			$tab.find('.ag-exportacao input').each(function(i, input) {
				if (agendaConf.exportacoes.indexOf(input.value) != -1) {
					input.checked = true;
				} else {
					input.checked = false;
				}
			});
		} else {
			$tab.find('.ag-exportacao input').each(function(i, input) {
				input.checked = false;
			});
		}
		
	}
	
	function atualizarRelatorios(idRel) {
		var $boxes = $abaAgendas.find('.ag-conf-relatorios');
		$boxes.empty();
		$boxes.dualListBoxes({
			valores: relatorios,
			valoresAttrs: {value: 'id', text: 'nome'}
		});
		if (idRel != null) {
			var sel = [idRel];
			$boxes.find('.listboxFrom').val(sel);
			$boxes.find('.dlb_enviar').trigger('click');
		}
	}
	
	function atualizarRelatoriosHistorico(idRel) {
		var $boxes = $abaHistoricos.find('.ag-conf-relatorios');
		$boxes.empty();
		$boxes.dualListBoxes({
			valores: relatoriosHistorico,
			valoresAttrs: {value: 'id', text: 'nome'}
		});
		if (idRel != null) {
			var sel = [idRel];
			$boxes.find('.listboxFrom').val(sel);
			$boxes.find('.dlb_enviar').trigger('click');
		}
	}
	
	function bindBtnSalvar() {
		var $btn = $('.btn-ag-salvar');
		$btn.off('click');
		$btn.on('click', function() {
			var $tab = getCurrentTab();
			var ehHistorico = ($tab.data('historico') == '1');
			var agenda = {};
			agenda.historico = ehHistorico;
			agenda.nome = $tab.find('.ag-nome').val();
			if (agenda.nome == '') {
				visent.alert(msgs['validacao.nome']);
				return;
			}
			if ($tab.find('.ag-nome').attr('disabled') != 'disabled') {
				var filterFn = function(o) {
					if (o.nome == agenda.nome) {
						return true;
					}
					return false;
				};
				if (!ehHistorico) {
					var filtered = agendas.filter(filterFn);
				} else {
					var filtered = historicos.filter(filterFn);
				}
				if (filtered.length > 0) {
					visent.alert(msgs['validacao.agenda.nomeRepetido']);
					return;
				}
			}
			agenda.periodicidade = $tab.find('.ag-periodicidade').val();
			if ($tab.find(SELECTOR_PERIODICIDADE_UMA_VEZ).is(':visible')) {
				agenda.inicio = $tab.find('.ag-data').val()+' '+$tab.find('.ag-hora').val()+':00';
			} else {
				if (!ehHistorico) {
					var ano = $tab.find('.ag-ano:visible').val();
					var mes = $tab.find('.ag-mes:visible').val();
					var tmp = new Date();
					if (ano == -1 && mes == -1) {
						agenda.infinito = 4;
						ano = tmp.getFullYear();
						mes = (tmp.getMonth()+1);
					} else if (ano == -1) {
						agenda.infinito = 3;
						ano = tmp.getFullYear();
					} else if (mes == -1) {
						agenda.infinito = 2;
						mes = (tmp.getMonth()+1);
					}
					agenda.inicio = '01/'+visent.format2digits(mes)+'/'+ano+' '+$tab.find('.ag-hora').val()+':00';
				} else {
					setarDatasHistorico(agenda);
					agenda.latencia = getLatencia();
					agenda.periodo = Number.parseInt($tab.find('.periodos-armazenados').html());
				}
				if ($tab.find(SELECTOR_PERIODICIDADE_SEMANA).is(':visible')) {
					var ocorrencias = [];
					$tab.find('.ag-ocorrencias input:checked').each(function(i, input) {
						ocorrencias.push(input.value);
					});
					agenda.semanasDeOcorrencia = {values: ocorrencias};
					
					var dias = [];
					$tab.find('.ag-dias-semana input:checked').each(function(i, input) {
						dias.push(input.value);
					});
					agenda.diasDaSemana = {values: dias};
				}
				if ($tab.find(SELECTOR_PERIODICIDADE_MES).is(':visible')) {
					var arr = [];
					$tab.find('.ag-dias .listboxTo option').each(function(i, option) {
						arr.push(option.value);
					});
					agenda.diasDoMes = {values: arr};
				}
			}
			var rels = [];
			$tab.find('.ag-conf-relatorios .listboxTo option').each(function(i, option) {
				rels.push({id:option.value});
			});
			agenda.relatorios = rels;
			if (!ehHistorico) {
				agenda.intervaloExecucao = $tab.find('.ag-intervalo-exec').val()+':00';
			} else {
				agenda.intervaloExecucao = '00:00:00';
			}
			
			agenda.exportacoes = [];
			$tab.find('.ag-exportacao input:checked').each(function(i, input) {
				agenda.exportacoes.push(input.value);
			});
			
			var url = (ehHistorico ? 'agenda/historico/salvar' : 'agenda/salvar');
			
			$tab.loading(true);
			visent.ws({
				path: url,
				method: 'POST',
				data: JSON.stringify(agenda),
				done: function() {
					if (ehHistorico) {
						atualizarHistoricos();
					} else {
						atualizarAgendas();
					}
					triggerBtnAdd($tab);
				},
				fail: function(e) {
					visent.alert(e.responseText);
				},
				always: function() {
					$tab.loading(false);
				}
			});
		});
	}
	
	function triggerBtnAdd($tab) {
		if ($tab == null) {
			$tab = getCurrentTab();
		}
		$tab.find('.btn-add-ag').trigger('click');
	}
	
	function setarDatasHistorico(agenda) {
		if ($abaHistoricos.find('.ag-hora-i').is(':visible')) {
			var inicioHora = $abaHistoricos.find('.ag-hora-i').val();
			var horaInicio = null;
			var terminoHora = $abaHistoricos.find('.ag-hora-t').val();
			var horaTermino = null;
		} else {
			var inicioHora = '00:00';
			var horaInicio = $abaHistoricos.find('.ag-hora-i-consolidacao').val();
			var terminoHora = '00:00';
			var horaTermino = $abaHistoricos.find('.ag-hora-t-consolidacao').val();
		}
		agenda.inicio = $abaHistoricos.find('.ag-data-i').val()+' '+inicioHora+':00';
		agenda.termino = $abaHistoricos.find('.ag-data-t').val()+' '+terminoHora+':00';
		agenda.horaInicio = horaInicio;
		agenda.horaTermino = horaTermino;
	}
	
	function getLatencia() {
		var vals = [];
		$abaHistoricos.find('.latencia').each(function(i, input) {
			vals.push(Number.parseInt(input.value) || 0);
		});
		return (vals[0]*60*60*24)+(vals[1]*60*60)+(vals[2]*60);
	}
	
	function bindSpinners() {
		$abaHistoricos.find('.latencia').TouchSpin({
		      verticalbuttons: true,
		      verticalupclass: 'glyphicon glyphicon-plus',
		      verticaldownclass: 'glyphicon glyphicon-minus',
		      buttondown_class: 'btn',
		      buttonup_class: 'btn',
		      initval: 0,
		      forcestepdivisibility: 'none'
	    });
	}
	
	function bindPeriodo() {
		var fnPeriodo = function() {
			var periodos = 0;
			try {
				var agenda = {};
				setarDatasHistorico(agenda);
				var inicioData = agenda.inicio.split(' ')[0].split('/');
				var inicioHora = agenda.inicio.split(' ')[1].split(':');
				var terminoData = agenda.termino.split(' ')[0].split('/');
				var terminoHora = agenda.termino.split(' ')[1].split(':');
				var dateInicio = Date.create().set({
					date: inicioData[0],
					month: (Number.parseInt(inicioData[1])-1),
					year: inicioData[2],
					hour: inicioHora[0],
					minute: inicioHora[1],
					second: inicioHora[2]
				}, true);
				var dateTermino = Date.create().set({
					date: terminoData[0],
					month: (Number.parseInt(terminoData[1])-1),
					year: terminoData[2],
					hour: terminoHora[0],
					minute: terminoHora[1],
					second: terminoHora[2]
				}, true);
				var periodicidade = $abaHistoricos.find('.ag-periodicidade').val();
				while (dateInicio.isBefore(dateTermino)) {
					if (periodicidade == 'MINUTO_15') {
						dateInicio.addMinutes(15);
					} else if (periodicidade == 'MINUTO_30') {
						dateInicio.addMinutes(30);
					} else if (periodicidade == 'HORA') {
						dateInicio.addHours(1);
					} else if (periodicidade == 'DIA') {
						dateInicio.addDays(1);
					} else if (periodicidade == 'SEMANA') {
						dateInicio.addWeeks(1);
					} else if (periodicidade == 'MES') {
						dateInicio.addMonths(1);
					}
					periodos++;
				}
				if (dateInicio.isAfter(dateTermino)) {
					periodos--;
				}
			} catch (e) {
				visent.log('Erro ao calcular periodos', e);
			}
			$abaHistoricos.find('.periodos-armazenados').html(periodos);
		};
		$abaHistoricos.find('.ag-data').on('change', fnPeriodo);
		$abaHistoricos.find('.ag-hora').on('blur', fnPeriodo);
		$abaHistoricos.find('.ag-periodicidade').on('change', fnPeriodo);
	}
	
});