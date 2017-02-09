(function($) {

	var selecaoDualListBox = function($to) {
		var $from = $(this);
		if ($from.find('optgroup').length > 0) {
			$from.find('option:selected').each(function(i, opt) {
				var group = $(opt).data('group');
				var $toGroup = $to.find('optgroup[label="'+group+'"]');
				if ($toGroup.length == 0) {
					$to.append('<optgroup label="'+group+'"></optgroup>');
					$toGroup = $to.find('optgroup[label="'+group+'"]');
				}
				$(opt).appendTo($toGroup);
				var $fromGroup = $from.find('optgroup[label="'+group+'"]');
				if ($fromGroup.find('option').length == 0) {
					$fromGroup.remove();
				}
			});
		} else {
			$from.find('option:selected').appendTo($to);
		}
		$to.find('option:selected').prop('selected', false);
		rebindOptsFiltros($(this), $to);
	}
	
    $.fn.dualListBoxes = function(opts) {

    	opts = $.extend({
    		html: true,
            filtro: false,
            titulo: '',
            valores: [],
            valoresAttrs: null, // ex: {value: 'id', text: 'nome'}
			info: false,
			maxVal: 0
        }, opts);
    	
    	if (opts.html) {
    		criarHtml(this, opts);
    	}
		bindDuploClique(this);
		bindSetas(this);
		if (opts.filtro) {
			bindFiltros(this);
		}
    }
    
    function criarHtml(selector, opts) {
    	
    	var options = '';
    	
    	if (!(opts.valores instanceof Array)) {
    		for (var group in opts.valores) {
    			options += '<optgroup label="'+group+'">';
    			options += criarOptions(opts.valores[group], opts, group);
    			options += '</optgroup>';
    		}
    	} else {
    		options += criarOptions(opts.valores, opts);
    	}
    	
		var infoMaxVal = '';
		if (opts.maxVal > 0) {
			infoMaxVal = ' (máx. '+opts.maxVal+')';
		}
		
    	var html = ''
		+ '<div class="dlb_container">'
		+ (opts.info ? ('<label> '+opts.titulo+infoMaxVal+' </label><br>') : '')
		+ '<div class="dlb_col">'
		+ (opts.filtro ? 
			'<input type="text" class="dlb_filtro" placeholder="Filtrar"/> <br>' :
			''
			)
		+ '<select class="dlb_select listboxFrom form-control" multiple="multiple">'
		+ options
		+ '</select>'
		+ '</div>'
		+ '<div class="dlb_col dlb_col_central" >'
		+ '		<i class="dlb_enviar glyphicon glyphicon-arrow-right" title="Selecionar"/>'
		+ '		<br>'
		+ '		<i class="dlb_retirar glyphicon glyphicon-arrow-left" title="Desfazer seleção"/>'
		+ '</div>'
		+ '<div class="dlb_col">'
		+ (opts.filtro ? 
			'<input type="text" class="dlb_filtro" placeholder="Filtrar"/> <br>' :
			''
			)
		+ '		<select class="dlb_select listboxTo form-control" multiple="multiple"></select>'
		+ '</div>'
		+ '</div>';
		
    	$(selector).html(html);
    		
    }
    
    function criarOptions(valores, opts, group) {
    	if (group === undefined) group = '';
    	var options = '';
    	for (var i in valores) {
    		var valor = valores[i];
    		if (opts.valoresAttrs != null) {
    			options += '<option title="'+valor[opts.valoresAttrs.text]+'" data-order="'+i+'" data-group="'+group+'" value="'+valor[opts.valoresAttrs.value]+'">'+valor[opts.valoresAttrs.text]+'</option>';
    		} else {
    			options += '<option title="'+valor+'" data-order="'+i+'" data-group="'+group+'" value="'+valor+'">'+valor+'</option>';
    		}
    	}
    	return options;
    }
    
    function bindDuploClique(selector) {
    	selector.find('.dlb_select').dblclick(function() {
			if ($(this).hasClass('listboxFrom')) {
				selecaoDualListBox.call(this, $(this).closest('.dlb_container').find('.listboxTo'));
			} else {
				selecaoDualListBox.call(this, $(this).closest('.dlb_container').find('.listboxFrom'));
			}
		});
    }
    
    function bindSetas(selector) {
    	selector.find('.dlb_enviar').click(function() {
			selecaoDualListBox.call(
					$(this).closest('.dlb_container').find('.listboxFrom')[0], 
					$(this).closest('.dlb_container').find('.listboxTo'));
		});
		
    	selector.find('.dlb_retirar').click(function() {
			selecaoDualListBox.call(
					$(this).closest('.dlb_container').find('.listboxTo')[0], 
					$(this).closest('.dlb_container').find('.listboxFrom'));
		});
    }
    
    function bindFiltros(selector) {
    	
    	$('.dlb_filtro').each(function() {
    		
    		var selectDualListBox = $(this).closest('.dlb_col').find('.dlb_select');
    		var opts = selectDualListBox.find('option');
    		
    		$(this).data('opts', opts);
    		
        	$(this).keyup(function() {
        		selectDualListBox.empty();
        		var val = $(this).val().toLowerCase();
        		$(this).data('opts').each(function() {
    				if ($.trim(val).length == 0 
    						|| $(this).text().toLowerCase().indexOf(val) != -1) {
    					selectDualListBox.append($(this));
    				}
    			});
        	});
        	
    	});
    	
    }
    
    function rebindOptsFiltros(from, to) {
    	from.closest('.dlb_col').find('.dlb_filtro').data('opts', from.find('option'));
    	to.closest('.dlb_col').find('.dlb_filtro').data('opts', to.find('option'));
    }
 
}(jQuery));