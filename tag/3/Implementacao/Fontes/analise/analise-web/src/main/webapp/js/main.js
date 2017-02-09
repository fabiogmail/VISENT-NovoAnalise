$(function main() {
    
	var $principal = $('.fullpage');
	
    (function init() {
    	sugar();
    	fullPage();
    	initDlgs();
    	bindSections();
    }());
    
    function sugar() {
    	Sugar.extend();
    }
    
    function fullPage() {
    	$principal.fullpage({
//    		scrollBar: true,
    		fitToSection: false
        });
    	$principal.fullpage.setAllowScrolling(false);
    	$('.section').show();
    }
    
    function initDlgs() {
    	$('#dlg-confirm, #dlg-alert').dialog({
			resizable: false,
			width: 400,
			modal: true,
			autoOpen: false,
			classes: {
				"ui-dialog": "dlg-confirm"
			}
	    });
    }
    
    function bindSections() {
    	$('#topo-section-agend .btn-rel').on('click', function(e) {
    		e.preventDefault();
    		$.fn.fullpage.moveSectionUp();
    	});
    	$('#topo-section-rel .btn-agend').on('click', function(e) {
    		e.preventDefault();
    		$.fn.fullpage.moveSectionDown();
    		visent.agendamento.init();
    	});
    }
    
});