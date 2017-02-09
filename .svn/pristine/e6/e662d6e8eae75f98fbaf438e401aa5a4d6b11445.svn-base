$(function colunas() {
	
	var SELECTOR_TREE_COLUNAS = '.tree-colunas';
	var SELECTOR_COLUNAS_SELECIONADAS_UL = '.colunas-selecionadas ul';
	var SELECTOR_COL_TO_RIGHT = '.col-to-right';
	
	(function init() {
		extColunas();
	})();
	
	function extColunas() {
		visent.colunas = {};
		visent.colunas.init = initColunas;
		visent.colunas.carregar = carregarColunas;
		visent.colunas.toObj = criarColunas;
		visent.colunas.resumo = formatarResumo;
	}
	
	function initColunas($tab, pathWS) {
		var $tree = $tab.find(SELECTOR_COLUNAS_SELECIONADAS_UL);
		if ($tree.hasClass('treeview')) {
			$tree.empty();
		}
		bindSortable($tab);
		initTreeView($tab, pathWS);
		bindAcoes($tab);
	}
	
	function carregarColunas($tab, colunasJson) {
		$('.colunas-selecionadas-resumo').loading(true);
		var $ul = $tab.find(SELECTOR_COLUNAS_SELECIONADAS_UL);
		var colunas = JSON.parse(colunasJson);
		for (var i in colunas) {
			var coluna = colunas[i];
			addLinhaColuna($ul, coluna.tipoColuna, coluna.nome, 
					coluna.tipoFiltroDrill, coluna.ignoraFiltroDrill, coluna.registroDados,
					 coluna.formulaExecucao);
		}
		formatarResumo($tab);
		$('.colunas-selecionadas-resumo').loading(false);
	}
	
	function bindSortable($tab) {
		var $ul = $tab.find(SELECTOR_COLUNAS_SELECIONADAS_UL);
		
		$ul.sortable({
			start: function(event, ui) {
				ui.item.addClass('grabbing');
			},
			stop: function(event, ui) {
				ui.item.removeClass('grabbing');
			}
		});
	}
	
	function bindAcoes($tab) {
		var $tree = $tab.find(SELECTOR_TREE_COLUNAS),
			$ul = $tab.find(SELECTOR_COLUNAS_SELECIONADAS_UL);
		
		$tab.find(SELECTOR_COL_TO_RIGHT).off('click');
		$tab.find(SELECTOR_COL_TO_RIGHT).on('click', function(e) {
			var vals = [];
			$ul.find('li').each(function(i, li){
				 vals.push($.trim(li.textContent));
			});
			var list = $tree.treeview('getSelected');
			for (var i in list) {
				var node = list[i];
				if (vals.indexOf(node.text) == -1) {
					addLinhaColuna($ul, node.tipoColuna, $.trim(node.text),
							node.tipoFiltroDrill, node.ignoraFiltroDrill, node.registroDados, node.formulaExecucao);
				}
				$tree.treeview('unselectNode', node.nodeId, { silent: true });
			}
			var $btn = $(this);
		});
		
		$tab.find('.colunas-sel-chk').off('click');
		$tab.find('.colunas-sel-chk').on('click', function(e) {
			e.preventDefault();
			var $checks = $tab.find('.colunas-selecionadas input');
			var total = $checks.length;
			var checkeds = $tab.find('.colunas-selecionadas input:checked').length;
			var notCheckeds = total - checkeds; 
			if (notCheckeds >= checkeds) {
				$checks.prop('checked', true);
			} else {
				$checks.prop('checked', false);
			}
		});
		
		$tab.find('.colunas-sel-del').off('click');
		$tab.find('.colunas-sel-del').on('click', function(e) {
			e.preventDefault();
			$tab.find('.colunas-selecionadas input:checked').closest('li').remove();
		});
	}
	
	function addLinhaColuna($ul, tipoColuna, text, tipoFiltroDrill, ignoraFiltroDrill, registroDados, formulaExecucao) {
		var $li = $('<li class="list-group-item" data-tipocoluna="'+tipoColuna+'" data-tipofiltrodrill="'+tipoFiltroDrill+'" data-ignorafiltrodrill="'+ignoraFiltroDrill+'" data-registrodados="'+registroDados+'" data-formulaexecucao="'+formulaExecucao+'">'
				+text+'<span class="pull-right"><input type="checkbox"></input></span> </li>');
		$ul.append($li);
	}
	
	function initTreeView($tab, pathWS) {
		var $tree = $tab.find(SELECTOR_TREE_COLUNAS),
			$ul = $tab.find(SELECTOR_COLUNAS_SELECIONADAS_UL);

		var tipoRel = visent.configuracao.getTipoRel($tab);
		var tipoTec = visent.configuracao.getTipoTec($tab);
		var path = 'configuracao/colunas/'+tipoRel+'/'+tipoTec;
		if (pathWS !== undefined) {
			path = pathWS;
		}
		
		visent.ws({
			path: path,
			done: function(json) {
				visent.log('colunas', json);
				$tree.treeview({
					data: montarTree(json),
					multiSelect: true,
					showTags: true,
					collapseIcon: 'glyphicon glyphicon-folder-open',
					expandIcon: 'glyphicon glyphicon-folder-close'
				});
			}
		});
		
	}
	
	function montarTree(json) {
		if (json === undefined) return [];
		var tree = [],
			recursos = json.recursos,
			metricas = json.metricas,
			colunas  = json.colunas;
		
		if (colunas === undefined) {
			addItensTree(tree, recursos, msgs['lbl.recursos'], false);
			addItensTree(tree, metricas, msgs['lbl.metricas'], true);
		} else {
			addItensTree(tree, colunas, msgs['lbl.colunas'], false);
		}
		
		return tree;
	}
	
	function addItensTree(tree, list, nome, showTags) {
		var root = {text: nome, selectable: false, nodes: [], state: {expanded: true}};
		for (var i in list) {
			var listItem = list[i];
			var treeItem = {text: listItem.nome, selectable: false, nodes: []};
			var increment = -1;
			if (listItem.nome == 'Recurso') {
				increment = 0;
			}
			for (var j in listItem.campos) {
				var campo = listItem.campos[j];
				var tipoColuna = campo.tipo || 0;
				if (increment >= 0) {
					tipoColuna = (++increment);
				}
				var tipoFiltroDrill = campo.tipoFiltroDrill || 0;
				var ignoraFiltroDrill = campo.ignoraFiltroDrill || false;
				var registroDados = campo.registrodados || '';
				var formulaExecucao = campo.formulaEXECUCAO || '';
				var node = {
						text: campo.nome, 
						tipoColuna: tipoColuna,
						tipoFiltroDrill: tipoFiltroDrill,
						ignoraFiltroDrill: ignoraFiltroDrill,
						registroDados: registroDados,
						formulaExecucao: formulaExecucao
				};
				if (showTags) {
					node.tags = ['<span title="'+campo.descricao+': '+campo.formula+'">?</span>'];
				}
				treeItem.nodes.push(node);
			}
			root.nodes.push(treeItem);
		}
		tree.push(root);
	}
	
	function criarColunas($tab) {
		var $ul = $tab.find(SELECTOR_COLUNAS_SELECIONADAS_UL);
		var vals = [];
		$ul.find('li').each(function(i, li){
			vals.push({
				tipoColuna: li.dataset.tipocoluna,
				nome: $.trim(li.textContent),
				tipoFiltroDrill: li.dataset.tipofiltrodrill,
				ignoraFiltroDrill: li.dataset.ignorafiltrodrill,
				registroDados: li.dataset.registrodados,
				formulaExecucao: li.dataset.formulaexecucao
			});
		});
		return vals;
	}
	
	function formatarResumo($tab) {
		var $ul = $tab.find(SELECTOR_COLUNAS_SELECIONADAS_UL);
		var $colunasSelecionadasResumo = $tab.find('.colunas-selecionadas-resumo ul');
		$colunasSelecionadasResumo.empty();
		$ul.find('li').each(function(i, li){
			var $liResumo = $('<li class="list-group-item">'+li.textContent+'</li>');
			$colunasSelecionadasResumo.append($liResumo);
		});
	}
	
});