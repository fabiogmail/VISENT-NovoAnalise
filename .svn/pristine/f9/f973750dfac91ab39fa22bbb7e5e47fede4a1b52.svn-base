$(function() {
	
	var structs;
	
	function init(helpStructs) {
		structs = helpStructs;
		for (var key in structs) {
			structs[key].id = key;
		}
		var close = function() {
			$('.visent-help-block').remove();
		};
		$(document).bind('keydown', 'esc', close);
		$('.visent-help-link').click(function() {
			if ($('.visent-help').length) {
				close();
			} else {
				$('html').append('<div class="visent-help visent-help-block"></div>');
				$('html').append('<div id="visent-help-container" data-index="0" class="visent-help-block col-xs-6 col-xs-offset-3 col-md-offset-4 col-md-4">'
						+'<div id="visent-help-close" class="pull-right"><a href="javascript:void(0)"><i class="glyphicon glyphicon-remove"></i></a></div>'
						+'<div class="clearfix"></div>'
						+'<div class="panel panel-default"><div class="panel-heading text-center"></div><div class="panel-body"></div></div>'
						+'<nav><ul class="pager" style="margin-bottom: 10px"><li><a id="ajuda-anterior" href="javascript:void(0)">'+msgs['help.anterior']+'</a></li><li><a id="ajuda-proximo" href="javascript:void(0)">'+msgs['help.proximo']+'</a></li></ul></nav>'
						+'<nav><ul class="pager" style="margin: 0"><li><a id="visent-help-home" href="javascript:void(0)"><i class="glyphicon glyphicon-home"></a></li></ul></nav>'
						+'</div>');
				$('#visent-help-close').click(close);
				$('#visent-help-home').click(function() {
					$('.visent-help-highlight').remove();
					selectHelpStruct(0);
					$(this).blur();
				})
			}
			$('#ajuda-anterior').click(function() {
				$('.visent-help-highlight').remove();
				var index = parseInt($('#visent-help-container').data('index'));
				if (index == 0) {
					index = helpStructs.length - 1;
				} else {
					index--;
				}
				selectHelpStruct(index);
				$(this).blur();
			});
			$('#ajuda-proximo').click(function() {
				$('.visent-help-highlight').remove();
				var index = parseInt($('#visent-help-container').data('index'));
				if (index == helpStructs.length - 1) {
					index = 0;
				} else {
					index++;
				}
				selectHelpStruct(index);
				$(this).blur();
			});
			var index = $('#visent-help-container').data('index');
			selectHelpStruct(index);
		});
	}
	
	function selectHelpStruct(index) {
		var helpStruct = structs[index];
		exec(helpStruct);
		$('#visent-help-container').data('index', index);
	}
	
	function exec(helpStruct) {
		if (helpStruct.selector != null) {
			if (!(helpStruct.selector instanceof Array)) {
				helpStruct.selector = [helpStruct.selector];
			}
			for (var i in helpStruct.selector) {
				var selector = helpStruct.selector[i];
				var $destaque = $('<div class="visent-help-highlight visent-help-block"></div>');
				var $element = $(selector);
				if ($element.length == 0) continue;
				$destaque.height($element.outerHeight());
				$destaque.width($element.outerWidth());
				var top = $element.offset().top;
				var left = $element.offset().left;
				$destaque.offset({
					top: top, 
					left: left
				});
				$('html').append($destaque);
			}
		}
		$('#visent-help-container .panel-heading').html(helpStruct.title);
		$('#visent-help-container .panel-body').html(helpStruct.msg);
		$('#visent-help-container .panel-body [data-ajuda]').click(function() {
			var helpStructId = $(this).data('ajuda');
			for (var i in structs) {
				if (helpStructId == structs[i].id) {
					$('#visent-help-container').data('index', i);
					exec(structs[i]);
					break;
				}
			}
		});
	}
	
	visent.help = {
		init: init
	};
	
});