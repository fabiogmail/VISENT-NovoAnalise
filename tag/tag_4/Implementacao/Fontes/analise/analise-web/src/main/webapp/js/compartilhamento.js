$(function compartilhamento() {
	
	var $dlg = $('#dlg-compartilhamento');
	
	(function init() {
		initDialog();
		extCompartilhamento();
    }());
	
	function extCompartilhamento() {
		visent.compartilhamento = {};
		visent.compartilhamento.carregar = carregar;
		visent.compartilhamento.clonar = clonar;
	}
	
	function initDialog() {
		$dlg.dialog({
			title: '',
			autoOpen: false,
			width: 480, 
			height: 400,
			classes: {
				"ui-dialog": "dlg-compartilhamento"
			}
		});
	}
	
	function carregar(rel) {
		$dlg.dialog('open');
		$dlg.loading(true);
		visent.ws({
			path: 'relatorio/'+rel.id+'/compartilhar',
			method: 'GET',
			done: function(infos) {
				var perfisUsuarios = infos.perfisUsuarios;
				var usuarios = infos.usuarios;
				var $boxes = $('#compartilhamento-boxes');
				$boxes.empty();
				$boxes.dualListBoxes({
					valores: perfisUsuarios,
					valoresAttrs: {value: 'id', text: 'nome'}
				});
				var sel = [];
				for (var i in usuarios) {
					sel.push(usuarios[i].id);
				}
				$boxes.find('.listboxFrom').val(sel);
				$boxes.find('.dlb_enviar').trigger('click');
				$dlg.dialog('option', 'title', msgs['lbl.compartilhamento']+' - '+rel.nome);
				$dlg.dialog('option', 'buttons', [
	           	    {
	           	    	text: msgs['lbl.confirmar'],
	           	    	click: function() {
	           	    		$dlg.loading(true);
	           	    		
	           	    		var arr = [];
	    					$dlg.find('.listboxTo option').each(function(i, option) {
	    						arr.push(option.value);
	    					});
	           	    		
	           	    		visent.ws({
	           	    			path: 'relatorio/'+rel.id+'/compartilhar',
	           	    			method: 'POST',
	           	    			data: JSON.stringify(arr),
	           	    			done: function() {
	           	    				$dlg.dialog("close");
	           	    			}, 
	           	    			always: function() {
	           	    				$dlg.loading(false);
	           	    			}
           	    			});
	           	    		
	           	    	}
	           	    },
	           	    {
	           	    	text: msgs['lbl.cancelar'],
	           	    	click: function() {
	           	    		$(this).dialog("close");
	           	    	}
	           	    }
       	    	]);
				$dlg.loading(false);
			}
		});
	}
	
	function clonar(rel) {
		visent.confirm(msgs['relatorios.confirmacao.clonar'], function() {
			visent.relatorios.relLoading(true);
			visent.ws({
				path: 'relatorio/'+rel.id+'/clonar',
				method: 'POST',
				done: function() {
					$('#btn-rel-usuario').trigger('click');
				},
				always: function() {
					visent.relatorios.relLoading(false);
				}
			});
		});
	}
	
});