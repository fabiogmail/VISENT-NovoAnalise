$(function() {
	
	var structs; 
	
	function init(tipStructs) {
		structs = tipStructs;
		
		$('.visent-tip-link').hover(function() {
			var $glyph = $(this).find('.glyphicon');
			if ($glyph.hasClass('glyphicon-ban-circle')) {
				$('#visent-tip-msg').html(msgs['tip.msg.ativar']);
			} else {
				$('#visent-tip-msg').html(msgs['tip.msg.desativar']);
			}
			$('#visent-tip').fadeIn(100);
		}, function() {
			$('#visent-tip').hide();
		});
		
		$('.visent-tip-link').click(function() {
			$(this).blur();
			var $glyph = $(this).find('.glyphicon');
			if ($glyph.hasClass('glyphicon-ban-circle')) {
				tipEvent(true);
				$(this).attr('title', msgs['tip.title.desativar']);
			} else {
				tipEvent(false);
				$(this).attr('title', msgs['tip.title.ativar']);
				$('#visent-tip').hide();
			}
			$glyph.toggleClass('glyphicon-ban-circle glyphicon-ok-sign');
		});
		
		$('.visent-tip-link').trigger('click');
		
	}
	
	function tipEvent(bool) {
		if (bool) {
			$.each(structs, function(i, tip) {
				$(tip.selector).hover(function() {
					$('#visent-tip-msg').html(tip.msg);
					$('#visent-tip').fadeIn(100);
				}, function() {
					$('#visent-tip').hide();
				});
			});
		} else {
			$.each(structs, function(i, tip) {
				$(tip.selector).unbind('mouseenter mouseleave');
			});
		}
	}
	
	function reload() {
		tipEvent(false);
		tipEvent(true);
	}
	
	visent.tips = {
		init: init,
		reload: reload
	};
	
});