$(function() {
	
	var structs = {
		'menu': {
			selector: '.visent-sidebar-tool',
			msg: 'Clique para abrir o menu lateral'
		}
	};
	
	function init(tipStructs) {
		
		$('.visent-tip-link').hover(function() {
			var $glyph = $(this).find('.glyphicon');
			if ($glyph.hasClass('glyphicon-ban-circle')) {
				$('#visent-tip-msg').html('Ativar dicas de ajuda para obter informações rápidas sobre elementos da tela.');
			} else {
				$('#visent-tip-msg').html('Desativar dicas de ajuda.');
			}
			$('#visent-tip').fadeIn(100);
		}, function() {
			$('#visent-tip').hide();
		});
		
		$('.visent-tip-link').click(function() {
			$(this).blur();
			var $glyph = $(this).find('.glyphicon');
			if ($glyph.hasClass('glyphicon-ban-circle')) {
				$.each(tipStructs, function(i, tip) {
					$(tip.selector).hover(function() {
						$('#visent-tip-msg').html(tip.msg);
						$('#visent-tip').fadeIn(100);
					}, function() {
						$('#visent-tip').hide();
					});
				});
				$(this).attr('title', 'Desativar dicas de ajuda');
//				if (vips_storage.habilitado) {
//					vips_storage.set(vips_storage.keys.DICAS, 'val', true);
//				}
			} else {
				$.each(tipStructs, function(i, tip) {
					$(tip.selector).unbind('mouseenter mouseleave');
				});
				$(this).attr('title', 'Ativar dicas de ajuda');
				$('#visent-tip').hide();
//				if (vips_storage.habilitado) {
//					vips_storage.set(vips_storage.keys.DICAS, 'val', false);
//				}
			}
			$glyph.toggleClass('glyphicon-ban-circle glyphicon-ok-sign');
		});
		
//		if (vips_storage.existe) {
//			var ativado = vips_storage.get(vips_storage.keys.DICAS).val;
//			if (ativado) {
//				$('.lnk-dica').trigger('click');
//			}
//		}
	}
	
	init([structs.menu]);
	
});