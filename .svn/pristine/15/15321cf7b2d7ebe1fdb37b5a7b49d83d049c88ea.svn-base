$(function() {
	
	var TIPO_VAL = 'val',
		TIPO_CHK = 'chk',
		TIPO_DUAL = 'dual',
		TIPO_TREEVAL = 'treeval';
	
	(function init() {
		extFiltros();
	})();
	
	function extFiltros() {
		visent.filtros = {};
		visent.filtros.init = initFiltros;
		visent.filtros.initDialog = initDialogFiltros;
		visent.filtros.carregar = carregarFiltros;
		visent.filtros.toObj = criarFiltros;
		visent.filtros.resumo = formatarFiltros;
	}
	
	function initFiltros($tab) {
		var tipoRel = visent.configuracao.getTipoRel($tab);
		var tipoTec = visent.configuracao.getTipoTec($tab);
		var $section = $tab.find('.section-filtros');
		$section.loading(true);
		visent.ws({
			path: 'configuracao/filtros/'+tipoRel+'/'+tipoTec,
			done: function(abas) {
				console.log('filtros', abas);
				
				var $tabTitulos = $tab.find('.tab-filtros-titulos');
				var $tabConteudos = $tab.find('.tab-filtros-conteudos');
				
				$tabTitulos.empty();
				$tabConteudos.empty();
				
				var valores = {};
				
				for (var i in abas) {
					var aba = abas[i];
					var id = aba.id+'-'+new Date().getTime();
					var clazz = '';
					if (i == 0) {
						clazz = 'active';
					}
					var conteudo = '';
					if (aba.interfaceFiltros != null && aba.interfaceFiltros.length > 0) {
						conteudo += '<form class="form-horizontal">';
						conteudo += adicionarFiltros(aba.interfaceFiltros, valores);
						conteudo += '</form>';
					}
					if (aba.abasFilhas != null && aba.abasFilhas.length > 0) {
						conteudo += '<div class="col-xs-3"><ul class="nav nav-tabs tabs-left">';
						for (var j in aba.abasFilhas) {
							var abaFilha = aba.abasFilhas[j];
							var subid = abaFilha.id+'-'+new Date().getTime();
							abaFilha.tabid = subid;
							var subtabClazz = '';
							if (j == 0) {
								subtabClazz = 'active';
							}
							conteudo += '<li class="'+subtabClazz+'"><a href="#'+subid+'" data-toggle="tab">'+abaFilha.nome+'</a></li>';
						}
						conteudo += '</ul></div>';
						conteudo += '<div class="col-xs-9"><div class="tab-content">';
						for (var j in aba.abasFilhas) {
							var abaFilha = aba.abasFilhas[j];
							var subtabClazz = '';
							if (j == 0) {
								subtabClazz = 'active';
							}
							conteudo += '<div class="tab-pane tab-pane-vertical '+subtabClazz+'" id="'+abaFilha.tabid+'">';
							conteudo += '<form class="form-horizontal">';
							conteudo += adicionarFiltros(abaFilha.interfaceFiltros, valores);
							conteudo += '</form>';
							conteudo += '</div>';
						}
						conteudo += '</div></div>';
					}
					
					$tabTitulos.append('<li class="'+clazz+'"><a id="tab-'+id+'" href="#'+id+'" data-toggle="tab" class="'+(aba.id == null ? 'filtros-primarios':'')+'">'+aba.nome+'</a></li>');
					$tabConteudos.append('<div class="tab-pane '+clazz+'" id="'+id+'">'+conteudo+'</div>');
					
				}
				
				adicionarComponentesEventos($tab, valores);
				
			},
			always: function() {
				$section.loading(false);
			}
		});
	}
	
	function initDialogFiltros($tab) {
		var id = $tab.attr('id');
		$tab.append('<div id="dlg-filtros-tree-'+id+'"></div>');
		$tab.find('#dlg-filtros-tree-'+id).dialog({
			height: 400,
			width: 450,
			autoOpen: false,
			title: '',
			classes: {
				"ui-dialog": "dlg-filtros-tree"
			},
			buttons: [
			          {
			        	  text: msgs['lbl.confirmar'],
			        	  click: function() {
			        		  var list = $(this).find('.filtros-tree').treeview('getSelected');
			        		  var selected = '';
			        		  var idselected = '';
			        		  if (list.length > 0) {
			        			  selected = list[0].text;
			        			  idselected = list[0].idval;
			        		  }
			        		  var treeid = $(this).find('.filtros-tree').data('treeid');
			        		  var $target = $tab.find('[data-idfiltro="'+treeid+'"]');
			        		  $target.html(selected);
			        		  $target.data('treeval', idselected);
			        		  $tab.find('[data-treeid="'+treeid+'"]').data('treeval', idselected);
			        		  $(this).dialog("close");
			        	  }
			          },
			          {
			        	  text: msgs['lbl.limpar'],
			        	  click: function() {
			        		  var treeid = $(this).find('.filtros-tree').data('treeid');
			        		  var $target = $tab.find('[data-idfiltro="'+treeid+'"]');
			        		  $target.html('');
			        		  $target.data('treeval', '');
			        		  $tab.find('[data-treeid="'+treeid+'"]').data('treeval', '');
			        		  $(this).dialog("close");
			        	  }
			          },
			          {
			        	  text: msgs['lbl.cancelar'],
			        	  click: function() {
			        		  $(this).dialog("close");
			        	  }
			          }
			          ]
		});
	}
	
	function adicionarFiltros(interfaceFiltros, valores) {
		var conteudo = '';
		for (var i in interfaceFiltros) {
			var interfaceFiltro = interfaceFiltros[i];
			conteudo += 
				'<div class="form-group">'
				+ '<label class="col-sm-3 control-label">'+interfaceFiltro.label+'</label>'
				+ '<div class="col-sm-9">'
				+ criarComponente(interfaceFiltro, valores)
				+ '</div>'
				+ '</div>';
		}
		return conteudo;
	}
	
	function criarComponente(interfaceFiltro, valores) {
		switch (interfaceFiltro.componente) {
			case 'CAMPO_TEXTFIELD':
				return CAMPO_TEXTFIELD(interfaceFiltro);
			case 'CAMPO_CHECKBOX':
				return CAMPO_CHECKBOX(interfaceFiltro);
			case 'CAMPO_NUMERICO':
				return CAMPO_NUMERICO(interfaceFiltro);
			case 'CAMPO_LISTDOUBLE':
				return CAMPO_LISTDOUBLE_ID(interfaceFiltro, valores);
			case 'CAMPO_LISTDOUBLE_ID':
				return CAMPO_LISTDOUBLE_ID(interfaceFiltro, valores);
			case 'CAMPO_LISTDOUBLE_STRING':
				return CAMPO_LISTDOUBLE_ID(interfaceFiltro, valores);
			case 'CAMPO_TEXTLIST':
				return CAMPO_LISTDOUBLE_ID(interfaceFiltro, valores);
			case 'CAMPO_COMBOBOX':
				return CAMPO_COMBOBOX(interfaceFiltro);
			case 'TREE':
				return TREE(interfaceFiltro);
			default:
				console.log('sem mapeamento: ', interfaceFiltro.componente, interfaceFiltro);
				return interfaceFiltro.componente;
		}
	}
	
	/**
	 * Para recuperacao dos valores dos filtros, sao utilizados alguns tipos:
	 * val => textfield, numerico: apenas recupera o valor informado no campo
	 * chk => checkbox: verifica se esta selecionado ou nao
	 * dual => duallistbox: recupera os valores do select a direita 
	 */
	
	function CAMPO_TEXTFIELD(interfaceFiltro) {
		return '<input type="text" class="form-control input-sm" data-tipo="'+TIPO_VAL+'" data-idfiltro="'+interfaceFiltro.filtro.id+'" data-idinterfacefiltro="'+interfaceFiltro.id+'"/>';
	}
	
	function CAMPO_CHECKBOX(interfaceFiltro) {
		return '<input type="checkbox" data-tipo="'+TIPO_CHK+'" data-idfiltro="'+interfaceFiltro.filtro.id+'"/>';
	}
	
	function CAMPO_NUMERICO(interfaceFiltro) {
		return '<input type="text" class="campo-numerico form-control input-sm" data-tipo="'+TIPO_VAL+'" data-idfiltro="'+interfaceFiltro.filtro.id+'" data-idinterfacefiltro="'+interfaceFiltro.id+'" '
			+'data-min="'+interfaceFiltro.filtro.valorMinimo+'" '
			+'data-max="'+interfaceFiltro.filtro.valorMaximo+'" data-step="1"/>';
	}
	
	function CAMPO_LISTDOUBLE_ID(interfaceFiltro, valores) {
		var val = '';
		try {
			val = interfaceFiltro.filtro.campoRegistro.valores[0].valor;
		} catch (e) {};
		valores[interfaceFiltro.filtro.id] = val;
		return '<div class="campo-listdouble-id" data-tipo="'+TIPO_DUAL+'" data-idfiltro="'+interfaceFiltro.filtro.id+'" data-idinterfacefiltro="'+interfaceFiltro.id+'"></div>';
	}
	
	function CAMPO_COMBOBOX(interfaceFiltro) {
		var campo = interfaceFiltro.filtro.campoRegistro;
		if (campo == null) return '<span data-idfiltroerro="'+interfaceFiltro.filtro.id+'" data-idinterfacefiltroerro="'+interfaceFiltro.id+'">CAMPO_REGISTRO_NULO</span>';
		if (campo.valores == null || campo.valores.length == 0) return '<span data-idfiltroerro="'+interfaceFiltro.filtro.id+'" data-idinterfacefiltroerro="'+interfaceFiltro.id+'">CAMPO_VALORES_VAZIO</span>';
		var val = campo.valores[0].valor.split(';');
		var options = '<option></option>';
		for (var v in val) {
			var vParts = val[v].split('=');
			options += '<option value="'+vParts[1]+'">'+vParts[0]+'</option>';
		}
		return '<select class="form-control input-sm" data-tipo="'+TIPO_VAL+'" data-idfiltro="'+interfaceFiltro.filtro.id+'" data-idinterfacefiltro="'+interfaceFiltro.id+'">'+options+'</select>';
	}
	
	function TREE(interfaceFiltro) {
		return '<button data-treeid="'+interfaceFiltro.filtro.id+'" data-treelabel="'+interfaceFiltro.label+'" class="btn btn-xs"><i class="glyphicon glyphicon-option-vertical"></i></button> ' 
			+ '<span data-tipo="'+TIPO_TREEVAL+'" data-idfiltro="'+interfaceFiltro.filtro.id+'" data-treeval="" class="fp-selec"></span>';
	}
	
	function carregarFiltros($tab, valoresFiltros, valoresTreeSalvos) {
		$('.resumo-filtros-sel').loading(true);
		if ($tab.find('[data-idfiltro]').length == 0) {
			setTimeout(function() {
				visent.filtros.carregar($tab, valoresFiltros, valoresTreeSalvos);
			}, 500);
			return;
		}
		for (var i in valoresFiltros) {
			var valorFiltro = valoresFiltros[i];
			var $filtro = $tab.find('[data-idfiltro="'+valorFiltro.filtro+'"]');
			var tipo = $filtro.data('tipo');
			var valor = valorFiltro.valor;
			switch (tipo) {
				case TIPO_VAL:
					$filtro.val(valor);
					break;
				case TIPO_CHK:
					$filtro.prop('checked', true);
					break;
				case TIPO_DUAL:
					var sel = [];
					var arr = valor.split(';');
					for (var j in arr) {
						sel.push(arr[j]);
					}
					$filtro.find('.listboxFrom').val(sel);
					$filtro.find('.dlb_enviar').trigger('click');
					break;
				default:
					return;
			}
		}
		for (var i in valoresTreeSalvos) {
			var valorFiltro = valoresTreeSalvos[i];
			var $filtro = $tab.find('[data-idfiltro="'+valorFiltro.filtro+'"]');
			$filtro.data('treeval', valorFiltro.tree);
			$filtro.html(valorFiltro.nome);
			$tab.find('[data-treeid="'+valorFiltro.filtro+'"]').data('treeval', valorFiltro.tree);
		}
		formatarFiltros($tab);
		$('.resumo-filtros-sel').loading(false);
	}
	
	function criarFiltros($tab) {
		var valoresFiltros = [];
		var valoresTreeSalvos = [];
		$tab.find('[data-idfiltro]').each(function(i, input) {
			var $filtro = $(this);
			var idfiltro = $filtro.data('idfiltro');
			var tipo = $filtro.data('tipo');
			var valor = '';
			var treeid = '';
			switch (tipo) {
				case TIPO_VAL:
					valor = $filtro.val();
					break;
				case TIPO_CHK:
					valor = ($filtro.is(':checked') ? '1' : '');
					break;
				case TIPO_DUAL:
					var arr = [];
					$filtro.find('.listboxTo option').each(function(i, option) {
						arr.push(option.value);
					});
					valor = arr.join(';');
					break;
				case TIPO_TREEVAL:
					treeid = $filtro.data('treeval');
					break;
				default:
					return;
			}
			if (valor != '') {
				var valorFiltro = {
//					filtro: {id: idfiltro},
					filtro: idfiltro,
					interfaceFiltro: $filtro.data('idinterfacefiltro'),
					valor: valor
				};
				valoresFiltros.push(valorFiltro);
			}
			if (treeid != '') {
				var valorTreeSalvo = {
					filtro: idfiltro,
					tree: treeid
				};
				valoresTreeSalvos.push(valorTreeSalvo);
			}
		});
		return {
			valoresFiltros: valoresFiltros,
			valoresTreeSalvos: valoresTreeSalvos
		};
	}
	
	function adicionarComponentesEventos($tab, valores) {
		$tab.find('.campo-numerico').TouchSpin({
		      verticalbuttons: true,
		      verticalupclass: 'glyphicon glyphicon-plus',
		      verticaldownclass: 'glyphicon glyphicon-minus',
		      buttondown_class: 'btn',
		      buttonup_class: 'btn',
		      forcestepdivisibility: 'none'
	    });
		$tab.find('.campo-listdouble-id').each(function(i, campo) {
			var $campo = $(campo);
			var idfiltro = $campo.data('idfiltro');
			var val = valores[idfiltro].split(';');
			var list = []; 
			for (var v in val) {
				var vParts = val[v].split('=');
				list.push({
					id: vParts[1],
					label: vParts[0]
				});
			}
			$campo.dualListBoxes({
				valores: list,
				valoresAttrs: {value: 'id', text: 'label'}
			});
		});
		$tab.find('[data-treeid]').on('click', function(e) {
			e.preventDefault();
			var id = $(this).data('treeid');
			var selected = $(this).data('treeval');
			var $dlg = $('#dlg-filtros-tree-'+$tab.attr('id'));
			$dlg.empty();
			$dlg.dialog('option', 'title', $(this).data('treelabel'));
			$dlg.dialog('open');
			$dlg.loading(true);
			visent.ws({
				path: 'configuracao/filtros/tree/'+id,
				done: function(json) {
					console.log('tree', json);
					$dlg.loading(false);
					$dlg.empty();
					var $actionBar = $(getActionBarHTML());
					var $tree = $('<div class="filtros-tree" data-treeid="'+id+'"></div>');
					$dlg.append($actionBar);
					$dlg.append($tree);
					criarTree($tree, json, $actionBar, selected);
					bindActionBar($dlg, $tree, $actionBar, id, selected);
				}
			});
		});
	}
	
	function criarTree($tree, json, $actionBar, selected) {
		$tree.treeview({
			data: [montarTree(json[0], [], false, selected)],
			showTags: true,
			collapseIcon: 'glyphicon glyphicon-folder-open',
			expandIcon: 'glyphicon glyphicon-folder-close',
			onNodeSelected: function(event, data) {
				var $input = $actionBar.find('.tree-nome');
				$input.val(data.text);
				$input.data('id', data.idval);
				$actionBar.find('.filtros-tree-action-bar-edit').show();
			},
			onNodeUnselected: function(event, data) {
				var $input = $actionBar.find('.tree-nome');
				$input.val('');
				$input.data('id', '');
				$actionBar.find('.filtros-tree-action-bar-edit').hide();
			}
		});
		$actionBar.find('.filtros-tree-action-bar-edit').hide();
	}
	
	function getActionBarHTML() {
		return '<div><form class="form-inline filtros-tree-action-bar">'
			+'<div class="form-group"><input type="text" class="form-control input-sm tree-nome" style="width: 150px"></div>'
			+'<span class="filtros-tree-action-bar-novo">'
				+'<button title="Adicionar" class="btn btn-xs filtros-tree-action-bar-btn-add"><i class="glyphicon glyphicon-plus"></i></button>'
				+'<div class="btn-group btn-group-xs">'
					+'<button class="btn active" data-tipo="item"><i class="glyphicon glyphicon-th-list"></i></button>'
					+'<button class="btn" data-tipo="pasta"><i class="glyphicon glyphicon-folder-close"></i></button>'
				+'</div>'
			+'</span>'
			+'<span class="filtros-tree-action-bar-edit">'
				+'<button title="Salvar" class="btn btn-xs filtros-tree-action-bar-btn-save"><i class="glyphicon glyphicon-ok"></i></button>'
				+'<button title="Excluir" class="btn btn-xs filtros-tree-action-bar-btn-del"><i class="glyphicon glyphicon-trash"></i></button>'
			+'</span>'
			+'<button title="Download" data-tipo="down" class="btn btn-xs filtros-tree-action-bar-ext pull-right"><i class="glyphicon glyphicon-download"></i></button>'
			+'<button title="Upload" data-tipo="up" class="btn btn-xs filtros-tree-action-bar-ext pull-right"><i class="glyphicon glyphicon-upload"></i></button>'
			+'</form>'
			+'<form class="form-inline filtros-tree-upload" method="post" action="'+contexto+'/ws/filtros-tree/{id}/upload" enctype="multipart/form-data">'
				+'<div class="radio">'
					+'<label>'
						+'<input type="radio" name="tipo" value="bkup" checked> Backup'
					+'</label>'
					+'<label style="margin-left: 10px;">'
						+'<input type="radio" name="tipo" value="linhas"> Linhas'
					+'</label>'
				+'</div>'
				+'<div class="form-group">'
				    +'<div class="fileUpload btn btn-sm">'
				    	+'<i class="glyphicon glyphicon-file"></i>'
					    +'<span>Upload</span>'
					    +'<input type="file" name="arquivo" class="upload" />'
				    +'</div>'
			    +'</div>'
			+'</form></div>';
	}
	
	function bindActionBar($dlg, $tree, $actionBar, filtroId, selected) {
		
		var reRender = function() {
			$dlg.loading(true);
			visent.ws({
				path: 'configuracao/filtros/tree/'+filtroId,
				done: function(json) {
					criarTree($tree, json, $actionBar, selected);
					$dlg.loading(false);
				}
			});
		};
		
		$actionBar.find('.btn-group button').on('click', function(e) {
			e.preventDefault();
			$(this).parent().find('button').removeClass('active');
			$(this).addClass('active');
		});
		$actionBar.find('.filtros-tree-action-bar-btn-add').on('click', function(e) {
			e.preventDefault();
			var nome = $actionBar.find('.tree-nome').val();
			var tipo = $actionBar.find('.filtros-tree-action-bar-novo .btn-group .active').data('tipo');
			var nodes = $tree.treeview('getSelected');
			var parentNode = null;
			if (nodes.length > 0) {
				var node = nodes[0];
				if (node.nodes != null) {
					parentNode = node;
				} else {
					parentNode = $tree.treeview('getNode', node.parentId);
				}
			} else {
				parentNode = $tree.treeview('getNode', 0);
			}
			var valoresTree = {
				nome: nome,
				idPai: parentNode.idval
			};
			valoresTree.no = (tipo == 'pasta') ? true : false;
			$dlg.loading(true);
			visent.ws({
				path: 'filtros-tree',
				method: 'POST',
				data: JSON.stringify(valoresTree),
				always: function() {
					$dlg.loading(false);
					reRender();
				}
			});
		});
		$actionBar.find('.filtros-tree-action-bar-btn-save').on('click', function(e) {
			e.preventDefault();
			var $input = $actionBar.find('.tree-nome');
			var id = $input.data('id');
			var nome = $input.val();
			$dlg.loading(true);
			visent.ws({
				path: 'filtros-tree',
				method: 'PUT',
				data: JSON.stringify({id: id, nome: nome}),
				always: function() {
					$dlg.loading(false);
					reRender();
				}
			});
		});
		$actionBar.find('.filtros-tree-action-bar-btn-del').on('click', function(e) {
			e.preventDefault();
			var id = $actionBar.find('.tree-nome').data('id');
			var node = $tree.treeview('getSelected')[0];
			var delFn = function() {
				$dlg.loading(true);
				visent.ws({
					path: 'filtros-tree/'+id,
					method: 'DELETE',
					always: function() {
						$dlg.loading(false);
						reRender();
					}
				});
			};
			if (node.nodes != null && node.nodes.length > 0) {
				visent.confirm(msgs['configuracao.confirmacao.filtros.pasta.del'], delFn);
			} else {
				delFn();
			}
		});
		$actionBar.find('.filtros-tree-action-bar-ext').on('click', function(e) {
			e.preventDefault();
			var tipo = $(this).data('tipo');
			if (tipo == 'down') {
				window.location = ('ws/filtros-tree/'+filtroId);
			} else {
				var $upload = $actionBar.find('.filtros-tree-upload');
				if ($upload.is(':visible')) {
					$upload.slideUp();
				} else {
					$upload.slideDown();
				}
			}
		});
		$actionBar.find('.filtros-tree-upload [name="arquivo"]').on('change', function() {
			var $form = $(this).closest('form');
			var formData = new FormData($form[0]);
			$dlg.loading(true);
			$.ajax({
		        url: $form.attr("action").replace('{id}', filtroId),
		        type: $form.attr("method"),
		        data: formData,
		        contentType: false,
		        processData: false
		    }).always(function(ret) {
		    	$actionBar.find('.filtros-tree-upload')[0].reset();
				$dlg.loading(false);
				reRender();
				if (ret.status == 400) { // bad_request
					visent.alert(ret.responseText);
				}
			});
		});
	}
	
	function montarTree(obj, nodes, selectable, selected) {
		var item = {text: obj.nome, idval:obj.id, nodes: nodes, selectable: selectable};
		if (obj.id == selected) {
			item.state = {selected:true};
		}
		if (nodes != null) {
			item.tags = [obj.valoresFilhos.length];
		}
		for (var i in obj.valoresFilhos) {
			var subnodes = null;
			if (obj.valoresFilhos[i].no) {
				subnodes = [];
			}
			item.nodes.push(montarTree(obj.valoresFilhos[i], subnodes, true, selected));
		}
		return item;
	}
	
	function formatarFiltros($tab) {
		var valores = criarFiltros($tab);
		var valoresFiltros = valores.valoresFiltros;
		var valoresTreeSalvos = valores.valoresTreeSalvos;
		var exibicao = {};
		for (var i in valoresFiltros) {
			var valorFiltro = valoresFiltros[i];
			var $filtro = $tab.find('[data-idfiltro="'+valorFiltro.filtro+'"]');
			var $tabPane = $filtro.closest('.tab-pane');
			if ($tabPane.hasClass('tab-pane-vertical')) {
				$tabPane = $tabPane.parents('.tab-pane:first');
			}
			var nomeAba = $tab.find('#tab-'+$tabPane.attr('id')).text();
			if (exibicao[nomeAba] === undefined) exibicao[nomeAba] = [];
			
			var tipo = $filtro.data('tipo');
			var valor = valorFiltro.valor;
			var label = $filtro.closest('.form-group').find('label').text();
			switch (tipo) {
				case TIPO_VAL:
					exibicao[nomeAba].push($.trim(label)+': '+$filtro.val());
					break;
				case TIPO_CHK:
					exibicao[nomeAba].push(label);
					break;
				case TIPO_DUAL:
					var sel = [];
					var arr = valor.split(';');
					for (var j in arr) {
						sel.push(arr[j]);
					}
					var exibits = [];
					$filtro.find('.listboxTo option').each(function(i, option) {
						if (sel.indexOf(option.value) != -1) {
							exibits.push(option.text);
						}
					});
					exibicao[nomeAba].push(label+': '+exibits.join(', '));
					break;
				default:
					return;
			}
		}
		for (var i in valoresTreeSalvos) {
			var valorFiltro = valoresTreeSalvos[i];
			var $filtro = $tab.find('[data-idfiltro="'+valorFiltro.filtro+'"]');
			var nomeAba = $tab.find('#tab-'+$filtro.closest('.tab-pane').attr('id')).text();
			if (exibicao[nomeAba] === undefined) exibicao[nomeAba] = [];
			var label = $filtro.closest('.form-group').find('label').text();
			exibicao[nomeAba].push(label+': '+$filtro.text());
		}
		
		var $div = $tab.find('.resumo-filtros-sel');
		$div.empty();
		$tab.find('.tab-filtros-titulos a').each(function(i, a) {
			var titulo = $(a).text();
			if (exibicao[titulo] != null) {
				var fieldset = '<fieldset><legend>'+titulo+'</legend>';
				for (var j in exibicao[titulo]) {
					fieldset += exibicao[titulo][j] + '<br>';
				}
				fieldset += '</fieldset>';
				$div.append(fieldset);
			}
		});
		
	}
	
});