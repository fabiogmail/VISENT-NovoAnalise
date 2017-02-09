$(function configuracao() {
	
	var SELECTOR_CONF_TECNOLOGIA_EDIT = '.conf-tecnologia .conf-edit';
	var SELECTOR_CONF_TECNOLOGIA_VALOR = '.conf-tecnologia .conf-valor';
	var SELECTOR_CONF_NOME_EDIT = '.conf-nome .conf-edit';
	var SELECTOR_CONF_NOME_VALOR = '.conf-nome .conf-valor';
	var SELECTOR_CONF_TIPO_REL_EDIT = '.conf-tipo-rel .conf-edit';
	var SELECTOR_CONF_TIPO_REL_VALOR = '.conf-tipo-rel .conf-valor';
	var SELECTOR_CONF_ROW = '.conf-row';
	var SELECTOR_CONF_ROW_BTN_SAVE = '.conf-row-btn-save';
	var SELECTOR_CONF_ROW_BTN_EDIT = '.conf-row-btn-edit';
	var SELECTOR_BTN_FECHAR_ABA = '.btn-fechar-aba';
	
	var $principal = $('#principal'),
		$configuracao = $('#configuracao'),
		$configuracaoContainer = $('#configuracao-container'),
		$tabTitulos = $('#tab-titulos'),
		$tabConteudos = $('#tab-conteudos');
	
	(function init() {
		bindBtnNovoRel();
		extConfiguracao();
    }());
	
	function extConfiguracao() {
		visent.configuracao = {};
		visent.configuracao.getTipoTec = getTipoTec;
		visent.configuracao.getTipoRel = getTipoRel;
		visent.configuracao.fecharAbas = fecharAbas;
		visent.configuracao.addConfiguracaoRelatorio = addConfiguracaoRelatorio;
		visent.configuracao.validarExecucao = validarExecucao;
	}

	function bindBtnNovoRel() {
    	$('#btn-novo-rel').on('click', function(e) {
    		addConfiguracaoRelatorio();
    	});
    }
	
	function addConfiguracaoRelatorio(idRel) {
		$configuracao.show();
//		$.fn.fullpage.reBuild();
		$.fn.fullpage.moveSlideRight();
		$('.fp-controlArrow').show();
		
		if (idRel !== undefined && verificaAbaAberta(idRel)) return;
		
		var id = new Date().getTime();
		$tabTitulos.find('li').removeClass('active');
		$tabTitulos.append('<li class="active"><a id="tab-'+id+'" href="#'+id+'" data-toggle="tab"><span class="aba-nome-rel">'+msgs['lbl.novoRelatorio']+'</span> <i class="glyphicon glyphicon-remove btn-fechar-aba"></i></a></li>');
		$tabConteudos.find('.tab-pane-container').removeClass('active');
		$tabConteudos.append('<div class="tab-pane tab-pane-container active" id="'+id+'" data-idrel="'+(idRel!==undefined?idRel:'')+'"></div>');
		var $tab = $('#'+id);
		$configuracaoContainer.loading(true);
		$tab.load('jsp/configuracao/template-configuracao.jsp', function() {
			visent.tips.reload();
			visent.filtros.initDialog($tab);
			var startIndex = idRel!==undefined ? 3 : 0;
			$tab.find('.steps').steps({
			    headerTag: 'h3',
			    bodyTag: 'section',
			    titleTemplate: '#title#',
			    transitionEffect: 'slideLeft',
			    labels: {
			    	finish: msgs['steps.finish'],
			    	next: msgs['steps.next'],
			    	previous: msgs['steps.previous']
			    },
			    autoFocus: true,
			    enableAllSteps: true,
			    startIndex: startIndex,
			    onStepChanging: function(event, currentIndex, newIndex) {
			    	if (newIndex == 3) {
			    		validarExecucao($tab);
			    		formatarResumo($tab);
			    	}
			    	return true;
			    },
			    onFinished: function(event, currentIndex) {
			    	salvarConfiguracao($tab, function(idRelSalvo) {
			    		$configuracaoContainer.loading(true);
						visent.ws({
							path: 'relatorio/'+idRelSalvo,
							done: function(rel) {
								visent.execucao.exec(rel)
							},
							always: function() {
								$configuracaoContainer.loading(false);
							}
						});
			    	});
			    }
			});
			$configuracaoContainer.loading(false);
			
			$tab.loading(true);
			visent.ws({
				path: 'configuracao/cache',
				done: function(cache) {
					visent.html.select($tab.find('.tipo-data'), cache.datasPreDefinidas, 'id', 'nome');
					visent.html.select($tab.find('.conf-tipo-rel select'), cache.tiposRelatorio, 'id', 'nome');
					visent.html.select($tab.find('.conf-tecnologia select'), cache.tiposTecnologia, 'id', 'nome');
					$tab.find('.intervalos-pre-definidos').dualListBoxes({
						valores: cache.intervalosPreDefinidos,
						valoresAttrs: {value: 'id', text: 'nome'}
					});
					initTab($tab);
					if (idRel !== undefined) {
						$configuracaoContainer.loading(true);
						visent.ws({
							path: 'relatorio/'+idRel,
							done: function(rel) {
								carregarTab($tab, rel);
							},
							always: function() {
								$configuracaoContainer.loading(false);
							}
						});
						
					}
				},
				always: function() {
					$tab.loading(false);
				}
			});
			
		});
		bindFecharAba();
	}
	
	function validarExecucao($tab) {
		var relatorio = getRelatorioConfigurado($tab);
		$('.section-resumo .alert').hide();
		$('.section-resumo .alert-validacao').show();
		visent.ws({
			path: 'execucao/validar',
			method: 'POST',
			data: JSON.stringify(relatorio),
			done: function(erros) {
				visent.log(erros);
				$('.section-resumo .alert-validacao').hide();
				if ($.isEmptyObject(erros)) {
					$('.section-resumo .alert-ok').show();
				} else {
					$('.section-resumo .alert-erros').empty();
					criarErros($tab, erros, 'periodo', '.resumo-periodo .alert-erros');
					criarErros($tab, erros, 'filtros', '.resumo-filtros-sel .alert-erros');
					criarErros($tab, erros, 'colunas', '.colunas-selecionadas-resumo .alert-erros');
				}
			}
		});
	}
	
	function criarErros($tab, erros, key, selector) {
		if (erros.hasOwnProperty(key)) {
			var $div = $tab.find(selector);
			var msgs = erros[key];
			for (var i=0; i<msgs.length; i++) {
				$div.append('<p>'+msgs[i]+'</p>');
			}
			$div.show();
		}
	}
	
	function formatarResumo($tab) {
		visent.periodo.resumo($tab);
		visent.colunas.resumo($tab);
		visent.filtros.resumo($tab);
	}
	
	function bindFecharAba() {
		$(SELECTOR_BTN_FECHAR_ABA).off('click');
		$(SELECTOR_BTN_FECHAR_ABA).on('click', function() {
			var $a = $(this).parent();
			var $li = $a.parent();
			var id = $a.attr('id').split('-')[1];
			$('#'+id).remove();
			$li.remove();
			var $lis = $tabTitulos.find('li');
			if ($tabTitulos.find('.active').length == 0 && $lis.length > 0) {
				$tabTitulos.find(':first').addClass('active');
				$tabConteudos.find(':first').addClass('active');
			} else if ($tabTitulos.find('li').length == 0) {
				if ($('.slide.active').attr('id')=='configuracao') {
					$.fn.fullpage.moveSlideLeft();
				}
				$configuracao.hide();
//	    		$.fn.fullpage.moveSectionUp();
				$('.fp-controlArrow').hide();
			}
			
			var $dlg = $('#dlg-filtros-tree-'+id);
			$dlg.dialog('destroy');
			$dlg.remove();
		});
	}
	
	function fecharAbas(ids) {
		$('.tab-pane').each(function(i, tabpane){
			var $tabpane = $(tabpane);
			var idRel = $tabpane.data('idrel');
			for (var i in ids) {
				if (ids[i] == idRel) {
					var idTab = $tabpane.attr('id');
					$('#tab-'+idTab).find(SELECTOR_BTN_FECHAR_ABA).trigger('click');
				}
			}
		});
	}
	
	function verificaAbaAberta(id) {
		var ret = false;
		$('.tab-pane').each(function(i, tabpane){
			var $tabpane = $(tabpane);
			var idRel = $tabpane.data('idrel');
			if (id == idRel) {
				var idTab = $tabpane.attr('id');
				$tabTitulos.find('li').removeClass('active');
				$('#tab-'+idTab).parent().addClass('active');
				$tabConteudos.find('.tab-pane').removeClass('active');
				$tabpane.addClass('active');
				ret = true;
				return false;
			}
		});
		return ret;
	}
	
	function initTab($tab) {
		bindEdicao($tab);
		visent.periodo.init($tab);
		$tab.find('.conf-row-btn-save').on('click', function() {
			salvarConfiguracao($tab);
		});
	}
	
	function carregarTab($tab, rel) {
		visent.log(rel);
		var $tecnologiaEdit = $tab.find(SELECTOR_CONF_TECNOLOGIA_EDIT),
			$tecnologiaValor = $tab.find(SELECTOR_CONF_TECNOLOGIA_VALOR),
			$nomeEdit = $tab.find(SELECTOR_CONF_NOME_EDIT),
			$nomeValor = $tab.find(SELECTOR_CONF_NOME_VALOR),
			$tipoRelEdit = $tab.find(SELECTOR_CONF_TIPO_REL_EDIT),
			$tipoRelValor = $tab.find(SELECTOR_CONF_TIPO_REL_VALOR),
			$icon = $(this).find('.glyphicon'),
			$row = $tab.find(SELECTOR_CONF_ROW),
			$btnSave = $tab.find(SELECTOR_CONF_ROW_BTN_SAVE),
			$btnEdit = $tab.find(SELECTOR_CONF_ROW_BTN_EDIT);
		
		$nomeEdit.find('input').val(rel.nome);
		$tipoRelEdit.find('select').val(rel.tipoRelatorio.id);
		$tecnologiaEdit.find('select').val(rel.tipoTecnologia.id);
		
		visent.periodo.carregar($tab, rel.periodo);
		visent.colunas.carregar($tab, rel.colunas);
		visent.filtros.carregar($tab, rel.valoresFiltros, rel.valoresTreeSalvos);
		
		$btnEdit.trigger('click');
		
	}
	
	function salvarConfiguracao($tab, fn) {
		var relatorio = getRelatorioConfigurado($tab);
		if (relatorio.nome == '') {
			visent.alert(msgs['validacao.nome']);
			return;
		}
		visent.log(relatorio);
		$configuracaoContainer.loading(true);
		visent.ws({
			path: 'configuracao/salvar',
			method: 'POST',
			data: JSON.stringify(relatorio),
			done: function(id) {
				$tab.data('idrel', id);
				visent.relatorios.recarregarListaCorrente();
				if (fn !== undefined) {
					fn(id);
				}
			},
			always: function() {
				$configuracaoContainer.loading(false);
			}
		});
	}
	
	function getRelatorioConfigurado($tab) {
		var id = $tab.data('idrel');
		if (id == '') id = null;
		var periodo = visent.periodo.toObj($tab);
		var colunas = visent.colunas.toObj($tab);
		var filtrosObj = visent.filtros.toObj($tab);
		var relatorio = {
			id: id,
			nome: getNome($tab),
			tipoRelatorio: {id: getTipoRel($tab)},
			tipoTecnologia: {id: getTipoTec($tab)},
			periodo: periodo,
			colunas: JSON.stringify(colunas),
			valoresFiltros: filtrosObj.valoresFiltros,
			valoresTreeSalvos: filtrosObj.valoresTreeSalvos
		};
		return relatorio;
	}
	
	function bindEdicao($tab) {
		
		$tab.find('.steps').blockdiv(true);
		$tab.find(SELECTOR_CONF_ROW_BTN_EDIT).on('click', function() {
			var $tecnologiaEdit = $tab.find(SELECTOR_CONF_TECNOLOGIA_EDIT),
				$tecnologiaValor = $tab.find(SELECTOR_CONF_TECNOLOGIA_VALOR),
				$nomeEdit = $tab.find(SELECTOR_CONF_NOME_EDIT),
				$nomeValor = $tab.find(SELECTOR_CONF_NOME_VALOR),
				$tipoRelEdit = $tab.find(SELECTOR_CONF_TIPO_REL_EDIT),
				$tipoRelValor = $tab.find(SELECTOR_CONF_TIPO_REL_VALOR),
				$icon = $(this).find('.glyphicon'),
				$row = $tab.find(SELECTOR_CONF_ROW),
				$btnSave = $tab.find(SELECTOR_CONF_ROW_BTN_SAVE),
				$btn = $(this);
			
			if ($icon.hasClass('glyphicon-edit')) { // trocar para edicao
				$btnSave.hide();
				$tab.find('.conf-row .conf-valor').hide();
				$tab.find('.conf-row .conf-edit').show();
				$btn.attr('title', 'Confirmar');
				$icon.removeClass('glyphicon-edit').addClass('glyphicon-check');
			} else { // trocar para visualizacao
				
				var atualizarDados = function() {
					$btnSave.show();
					$tab.find('.conf-row .conf-edit').hide();
					$tab.find('.conf-row .conf-valor').show();
					$btn.attr('title', 'Editar');
					$icon.removeClass('glyphicon-check').addClass('glyphicon-edit');
					$row.removeClass('edit');
					$tab.find('.steps').blockdiv(false);
					
					$tecnologiaValor.html($tecnologiaEdit.find('select option:selected').text());
					$tipoRelValor.html($tipoRelEdit.find('select option:selected').text());
					var nome = $nomeEdit.find('input').val();
					$nomeValor.html(nome);
					$('#tab-'+$tab.attr('id')+' .aba-nome-rel').html(nome);
					
					if (isMudancaCritica($tab)) {
						visent.filtros.init($tab);
						visent.colunas.init($tab);
						$tab.data('tipotec', getTipoTec($tab));
						$tab.data('tiporel', getTipoRel($tab));
					}
				}
				
				if ($tab.data('tipotec') != null && $tab.data('tiporel') != null && isMudancaCritica($tab)) {
					visent.confirm(msgs['configuracao.confirmacao.mudanca'], function() {
						atualizarDados();
					});
				} else {
					atualizarDados();
				}
				
			}
			
		});
	}
	
	function isMudancaCritica($tab) {
		return $tab.data('tipotec') != getTipoTec($tab)
			|| $tab.data('tiporel') != getTipoRel($tab)
	}
	
	function getNome($tab) {
		var $nomeEdit = $tab.find(SELECTOR_CONF_NOME_EDIT);
		return $nomeEdit.find('input').val();
	}
	
	function getTipoTec($tab) {
		var $tecnologiaEdit = $tab.find(SELECTOR_CONF_TECNOLOGIA_EDIT);
		return $tecnologiaEdit.find('select option:selected').val();
	}
	
	function getTipoRel($tab) {
		var $tipoRelEdit = $tab.find(SELECTOR_CONF_TIPO_REL_EDIT)
		return $tipoRelEdit.find('select option:selected').val();
	}
	
});