$(function relatorios() {
	
	(function init() {
		bindBtnVisoes();
    	buscarRelatoriosUsuario();
    	extRelatorios();
    }());
	
	function extRelatorios() {
		visent.relatorios = {};
		visent.relatorios.recarregarListaCorrente = recarregarListaCorrente;
		visent.relatorios.relLoading = relLoading;
	}
	
	function recarregarListaCorrente() {
		$('.btn-visoes.active').trigger('click');
	}
	
	function resetBtnsVisoes() {
		$('.btn-visoes').removeClass('active');
	}
	
	function bindBtnVisoes() {
		$('#btn-rel-usuario').on('click', function() {
			resetBtnsVisoes();
			$(this).addClass('active');
			buscarRelatoriosUsuario();
		});
		$('#btn-rel-favoritos').on('click', function() {
			resetBtnsVisoes();
			$(this).addClass('active');
			buscarRelatoriosFavoritos();
		});
		$('#btn-rel-compartilhados').on('click', function() {
			resetBtnsVisoes();
			$(this).addClass('active');
			buscarRelatoriosCompartilhados();
		});
	}
	
	function relLoading(bool) {
		var $loadingContainer = $('#relatorios-tb').parent();
		$loadingContainer.loading(bool);
	}
	
	function buscarRelatoriosUsuario() {
		relLoading(true);
		visent.ws({
			path: 'relatorio/usuario',
			done: function result(list) {
				var cols = [{ title: msgs['relatorios.col.tipo'], data: 'tipoRelatorio.nome'},
							  { title: '', data: 'idFavorito', width: '1%', sortable: false, render: function(data, display, row, meta) {
					        	  if (data == null) {
					        		  return '<a href="#" title="Favoritos" class="btn-rel-fav"><i class="glyphicon glyphicon-star-empty"></i></a>';
					        	  } else {
					        		  return '<a href="#" title="Favoritos" class="btn-rel-fav" data-fav="'+data+'"><i class="glyphicon glyphicon-star"></i></a>';
					        	  }
					          }},
					          { title: msgs['relatorios.col.nome'], data: 'nome'},
					          { title: msgs['relatorios.col.ultimaExecucao'], data: 'dataUltimaExecucao', width: '5%', render: function(data, display, row, meta) {
					        	  return data;
					          }},
					          { title: msgs['relatorios.col.tempoUltimaExecucao'], data: 'tempoUltimaExecucao', sortable: false, width: '5%', render: function(data, display, row, meta) {
					        	  return data;
					          }},
					          { title: msgs['relatorios.col.acoes'], data: null, sortable: false, width: '70px', className: 'text-center', render: function(data, display, row, meta) {
					        	  return '<div class="btn-group btn-group-xs">'
						        	  +'<button type="button" class="btn btn-filter" title="'+msgs['relatorios.col.acoes.detalhar']+'"><i class="glyphicon glyphicon-wrench"></i></button>'
						        	  +'<button type="button" class="btn btn-play" title="'+msgs['relatorios.col.acoes.executar']+'"><i class="glyphicon glyphicon-play"></i></button>'
						        	  +'<button type="button" class="btn btn-share" title="'+msgs['relatorios.col.acoes.compartilhar']+'"><i class="glyphicon glyphicon-share"></i></button>'
						        	+'</div>';
					          }},
					          { title: '', 
					        	  data: null, width: '1%', sortable: false, render: function(data, display, row, meta) {
					        	  return '<input type="checkbox" class="rel-check" data-id="'+row.id+'"></input>';
					          }}
					          ];
				
				var rowFn = function(row, data, dataIndex) {
			    	   var $row = $(row);
			    	   $row.addClass('tipo-relatorio-'+visent.formatStr(data.tipoRelatorio.nome));
			    	   $row.find('td:first').on('click', function() {
			    		   var $td = $(this);
			    		   if ($td.find('a').data('fav') == null) {
			    			   addFavorito(data, this);
			    		   } else {
			    			   removerFavorito($(this).find('a').data('fav'), function() {
			    				   $td.find('a').data('fav', null);
			    				   $td.find('i').removeClass('glyphicon glyphicon-star').addClass('glyphicon glyphicon-star-empty');
			    			   });
			    		   } 
			    	   });
			    	   $row.find('.btn-filter').on('click', function() {
			    		   visent.configuracao.addConfiguracaoRelatorio(data.id);
			    	   });
			    	   $row.find('.btn-play').on('click', function() {
			    		   visent.execucao.exec(data);
			    	   });
			    	   $row.find('.btn-share').on('click', function() {
			    		   visent.compartilhamento.carregar(data);
			    	   });
			    };
			    
			    var otherFn = function() {
			    	  var $removerSelecionados = $('<button class="btn btn-sm btn-del-rel" title="'+msgs['relatorios.btn.del']+'"><i class="glyphicon glyphicon-trash"></i></button>');
			    	  var $selecionarTodos = $('<button class="btn btn-sm btn-check-rel" title="'+msgs['relatorios.btn.sel']+'"><i class="glyphicon glyphicon-check"></i></button>');
			    	  $selecionarTodos.on('click', function() {
			    		  $(this).blur();
						  var total = $('.rel-check').length;
						  var checkeds = $('.rel-check:checked').length;
						  var notCheckeds = total - checkeds; 
						  if (notCheckeds >= checkeds) {
							  $('.rel-check').prop('checked', true);
						  } else {
							  $('.rel-check').prop('checked', false);
						  }
			    	  });
			    	  $removerSelecionados.on('click', function() {
			    		  visent.confirm(msgs['relatorios.del'], function() {
			    			  var ids = [];
							  $('.rel-check:checked').each(function(i, chk) {
								  ids.push($(chk).data('id'));
							  });
							  if (ids.length > 0) {
								  removerRelatorios(ids);
							  }
			    		  });
			    	  });
			    	  $('.acoes').append($removerSelecionados).append($selecionarTodos);
			    };
				
				criarTabela(list, cols, rowFn, otherFn);
				visent.tips.reload();
				
			},
			always: function() {
				relLoading(false);
			}
		});
	}
	
	function buscarRelatoriosFavoritos() {
		relLoading(true);
		visent.ws({
			path: 'relatorio/favoritos',
			done: function result(list) {
				var cols = [{ title: msgs['relatorios.col.tipo'], data: 'relatorio.tipoRelatorio.nome'},
					          { title: '', data: null, width: '1%', sortable: false, render: function() {
					        	  return '<a href="#" title="Favoritos" class="btn-rel-fav"><i class="glyphicon glyphicon-star"></i></a>';
					          }},
					          { title: msgs['relatorios.col.nome'], data: 'relatorio.nome'},
					          { title: msgs['relatorios.col.criador'], data: 'relatorio.usuario.nome'},
					          { title: msgs['relatorios.col.ultimaExecucao'], data: 'relatorio.dataUltimaExecucao', width: '5%', render: function(data, display, row, meta) {
					        	  return data;
					          }},
					          { title: msgs['relatorios.col.tempoUltimaExecucao'], sortable: false, width: '5%', data: 'relatorio.tempoUltimaExecucao', render: function(data, display, row, meta) {
					        	  return data;
					          }}];
				
				var rowFn = function(row, data, dataIndex) {
		    	  	var $row = $(row);
			    	$row.addClass('tipo-relatorio-'+visent.formatStr(data.relatorio.tipoRelatorio.nome));
			    	$row.find('td:first').on('click', function() {
			    		removerFavorito(data.id, buscarRelatoriosFavoritos);
			    	});
			   	};
				
				criarTabela(list, cols, rowFn);
				visent.tips.reload();
				
			},
			always: function() {
				relLoading(false);
			}
		});
	}
	
	function buscarRelatoriosCompartilhados() {
		relLoading(true);
		visent.ws({
			path: 'relatorio/compartilhados',
			done: function result(list) {
				var cols = [{ title: msgs['relatorios.col.tipo'], data: 'relatorio.tipoRelatorio.nome'},
							  { title: '', data: 'relatorio.idFavorito', width: '1%', sortable: false, render: function(data, display, row, meta) {
					        	  if (data == null) {
					        		  return '<a href="#" title="Favoritos" class="btn-rel-fav"><i class="glyphicon glyphicon-star-empty"></i></a>';
					        	  } else {
					        		  return '<a href="#" title="Favoritos" class="btn-rel-fav" data-fav="'+data+'"><i class="glyphicon glyphicon-star"></i></a>';
					        	  }
					          }},
					          { title: msgs['relatorios.col.nome'], data: 'relatorio.nome'},
					          { title: msgs['relatorios.col.criador'], data: 'relatorio.usuario.nome'},
					          { title: msgs['relatorios.col.ultimaExecucao'], data: 'relatorio.dataUltimaExecucao', width: '5%', render: function(data, display, row, meta) {
					        	  return data;
					          }},
					          { title: msgs['relatorios.col.tempoUltimaExecucao'], data: 'relatorio.tempoUltimaExecucao', sortable: false, width: '5%', render: function(data, display, row, meta) {
					        	  return data;
					          }},
					          { title: msgs['relatorios.col.acoes'], data: null, sortable: false, width: '70px', className: 'text-center', render: function(data, display, row, meta) {
					        	  return '<div class="btn-group btn-group-xs">'
//						        	  +'<button type="button" class="btn" title="'+msgs['relatorios.col.acoes.detalhar']+'"><i class="glyphicon glyphicon-eye-open"></i></button>'
						        	  +'<button type="button" class="btn btn-play" title="'+msgs['relatorios.col.acoes.executar']+'"><i class="glyphicon glyphicon-play"></i></button>'
						        	  +'<button type="button" class="btn btn-clone" title="'+msgs['relatorios.col.acoes.clonar']+'"><i class="glyphicon glyphicon-duplicate"></i></button>'
						        	+'</div>';
					          }}];
				
				var rowFn = function(row, data, dataIndex) {
			    	   var $row = $(row);
			    	   $row.addClass('tipo-relatorio-'+visent.formatStr(data.relatorio.tipoRelatorio.nome));
			    	   $row.find('td:first').on('click', function() {
			    		   var $td = $(this);
			    		   if ($td.find('a').data('fav') == null) {
			    			   addFavorito(data.relatorio, this);
			    		   } else {
			    			   removerFavorito($(this).find('a').data('fav'), function() {
			    				   $td.find('a').data('fav', null);
			    				   $td.find('i').removeClass('glyphicon glyphicon-star').addClass('glyphicon glyphicon-star-empty');
			    			   });
			    		   } 
			    	   });
			    	   $row.find('.btn-play').on('click', function() {
			    		   visent.execucao.exec(data.relatorio);
			    	   });
			    	   $row.find('.btn-clone').on('click', function() {
			    		   visent.compartilhamento.clonar(data.relatorio);
			    	   });
			    };
				
				criarTabela(list, cols, rowFn);
				visent.tips.reload();
				
			},
			always: function() {
				relLoading(false);
			}
		});
	}
	
	function criarTabela(list, cols, rowFn, otherFn) {
		$('#relatorios-tb').html('<table id="relatorios-tb-gen" class="table table-striped table-bordered table-hover table-condensed"></table>');
		$('#relatorios-tb-gen').DataTable({
			data: list,
			columnDefs: [
			   { "visible": false, "targets": 0 }
	        ],
	        order: [[ 0, 'asc' ]],
	        drawCallback: function (settings) {
	           var api = this.api();
	           var rows = api.rows( {page:'current'} ).nodes();
	           var last=null;

	           api.column(0, {page:'current'}).data().each(function (group, i) {
	               if (last !== group) {
	            	   var $tr = $('<tr class="group"><td colspan="'+cols.length+'"><i class="small glyphicon glyphicon-minus"></i> '+group+'</td></tr>');
	            	   $tr.on('click', function() {
	            		   if ($tr.find('i').hasClass('glyphicon-plus')) {
	            			   $tr.find('i').removeClass('glyphicon-plus').addClass('glyphicon-minus');
	            			   $('.tipo-relatorio-'+visent.formatStr(group)).show();
	            		   } else {
	            			   $tr.find('i').removeClass('glyphicon-minus').addClass('glyphicon-plus');
	            			   $('.tipo-relatorio-'+visent.formatStr(group)).hide();
	            		   }
	            	   });
	                   $(rows).eq(i).before($tr);
	                   last = group;
	               }
//	               $(rows).hide();
	           });
	           
//	           $('#relatorios-tb .group:first').trigger('click');
	        },
		    columns: cols,
	        createdRow: rowFn,
	        pageLength: -1,
	        scrollY: '400px',
	        scrollCollapse: true,
	        dom: "<'row'<'col-sm-6'f><'col-sm-6 acoes'>><'row'<'col-sm-12'<'table_overflow't>r>><'row'<'col-sm-12'i>>",
	        language: visent.dtLanguage
		});
		
		if (otherFn !== undefined) {
			otherFn();
		}
	}
	
	function addFavorito(rel, td) {
		visent.ws({
			path: 'relatorio/favoritos/'+rel.id,
			method: 'POST',
			done: function(id) {
				$(td).find('a').data('fav', id);
				$(td).find('i').removeClass('glyphicon glyphicon-star-empty').addClass('glyphicon glyphicon-star');
			}
		});
	}
	
	function removerFavorito(idFavorito, fn) {
		visent.ws({
			path: 'relatorio/favoritos/'+idFavorito,
			method: 'DELETE',
			done: fn
		});
	}
	
	function removerRelatorios(ids) {
		visent.ws({
			path: 'relatorio/usuario',
			method: 'DELETE',
			data: JSON.stringify(ids),
			done: function() {
				visent.configuracao.fecharAbas(ids);
				buscarRelatoriosUsuario();
			}
		});
	}
	
});