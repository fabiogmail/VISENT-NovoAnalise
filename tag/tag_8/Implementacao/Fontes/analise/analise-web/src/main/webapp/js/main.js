$(function main() {
    
	var $principal = $('.fullpage');
	
    (function init() {
    	sugar();
    	fullPage();
    	initDlgs();
    	bindSections();
    	initTips();
    	initAjuda();
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
    
    function initTips() {
    	visent.tips.init([
    	                  {
    	                	  selector: '.visent-help-link',
    	                	  msg: msgs['tip.msg.help']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-agend',
    	                	  msg: msgs['tip.msg.relatorios.agenda']
    	                  },
    	                  {
    	                	  selector: '#btn-rel-usuario',
    	                	  msg: msgs['tip.msg.relatorios.meusRelatorios']
    	                  },
    	                  {
    	                	  selector: '#btn-rel-compartilhados',
    	                	  msg: msgs['tip.msg.relatorios.compartilhados']
    	                  },
    	                  {
    	                	  selector: '#btn-rel-favoritos',
    	                	  msg: msgs['tip.msg.relatorios.favoritos']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-rel-fav',
    	                	  msg: msgs['tip.msg.relatorios.favoritar']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-filter',
    	                	  msg: msgs['tip.msg.relatorios.editar']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-play',
    	                	  msg: msgs['tip.msg.relatorios.executar']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-share',
    	                	  msg: msgs['tip.msg.relatorios.compartilhar']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-clone',
    	                	  msg: msgs['tip.msg.relatorios.clonar']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-check-rel',
    	                	  msg: msgs['tip.msg.relatorios.selecionar']
    	                  },
    	                  {
    	                	  selector: '#relatorios .btn-del-rel',
    	                	  msg: msgs['tip.msg.relatorios.deletar']
    	                  },
    	                  {
    	                	  selector: '#btn-novo-rel',
    	                	  msg: msgs['tip.msg.relatorios.novo']
    	                  },
    	                  {
    	                	  selector: '.group-ag-intervalo-exec',
    	                	  msg: msgs['tip.msg.agendamento.intervaloExec']
    	                  },
    	                  {
    	                	  selector: '.conf-nome .conf-edit',
    	                	  msg: msgs['tip.msg.configuracao.nome']
    	                  },
    	                  {
    	                	  selector: '.conf-tecnologia .conf-edit',
    	                	  msg: msgs['tip.msg.configuracao.tecnologia']
    	                  },
    	                  {
    	                	  selector: '.conf-tipo-rel .conf-edit',
    	                	  msg: msgs['tip.msg.configuracao.tipoRelatorio']
    	                  },
    	                  {
    	                	  selector: '.conf-row-btn-save',
    	                	  msg: msgs['tip.msg.configuracao.btn.salvar']
    	                  },
    	                  {
    	                	  selector: '.conf-row-btn-edit',
    	                	  msg: msgs['tip.msg.configuracao.btn.editar']
    	                  },
    	                  ]);
    }
    
    function initAjuda() {
    	visent.help.init([
    	                  {
    	                	  title: msgs['help.rel.title'],
    	                	  msg: '<p>'+msgs['help.rel.msg']+'</p>'
    	                	  	+ '<p><i class="glyphicon glyphicon-user"></i> '+msgs['tip.msg.relatorios.meusRelatorios']+'</p>'
    	                	  	+ '<p><i class="glyphicon glyphicon-th"></i> '+msgs['tip.msg.relatorios.compartilhados']+'</p>'
    	                	  	+ '<p><i class="glyphicon glyphicon-star"></i> '+msgs['tip.msg.relatorios.favoritos']+'</p>'
    	                	  	+ '<p><i class="glyphicon glyphicon-wrench"></i> '+msgs['tip.msg.relatorios.editar']+'</p>'
    	                	  	+ '<p><i class="glyphicon glyphicon-play"></i> '+msgs['tip.msg.relatorios.executar']+'</p>'
    	                	  	+ '<p><i class="glyphicon glyphicon-share"></i> '+msgs['tip.msg.relatorios.compartilhar']+'</p>'
    	                	  	+ '<p><i class="glyphicon glyphicon-duplicate"></i> '+msgs['tip.msg.relatorios.clonar']+'</p>'
    	                  },
    	                  {
    	                	  title: msgs['help.configuracao.title'],
    	                	  msg: '<p>'+msgs['help.configuracao.msg.geral']+'</p>'
    	                	  	+ '<p>'+msgs['help.configuracao.msg.tecnologia']+'</p>'
    	                	  	+ '<p>'+msgs['help.configuracao.msg.salvar']+'</p>'
    	                	  	+ '<p>'+msgs['help.configuracao.msg.etapas']+'</p>'
    	                	  	+ '<p>'+msgs['help.configuracao.msg.periodo']+'</p>'
    	                	  	+ '<p>'+msgs['help.configuracao.msg.filtros']+'</p>'
    	                	  	+ '<p>'+msgs['help.configuracao.msg.colunas']+'</p>'
    	                	  	+ '<p>'+msgs['help.configuracao.msg.resumo']+'</p>'
    	                  },
    	                  {
    	                	  title: msgs['help.agenda.title'],
    	                	  msg: '<p>'+msgs['help.agenda.msg']+'</p>'
    	                  }
    	                  ]);
    }
    
});