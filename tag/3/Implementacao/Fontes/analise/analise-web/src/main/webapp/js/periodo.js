$(function periodos() {
	
	var SELECTOR_CHK_COMPARATIVO = '.chk-comparativo';
	var SELECTOR_DATAS_ESPECIFICAS = '.data-especifica, .data-especifica-comparativa';
	var SELECTOR_TIPO_DATA = '.tipo-data';
	var SELECTOR_TIPO_DATA_COMPARATIVA = '.tipo-data-comparativa';
	var SELECTOR_HORA_INICIAL = '.hora-inicial';
	var SELECTOR_DURACAO = '.duracao';
	var SELECTOR_DURACAO_CALCULO = '.duracao-calculo';
	var SELECTOR_BTN_ADD_INTERVALO = '.btn-add-intervalo';
	var SELECTOR_INTERVALOS_ESPECIFICOS_UL = '.intervalos-especificos ul';
	var SELECTOR_COL_COMPARATIVO = '.col-comparativo';
	
	(function init() {
		extPeriodo();
    }());
	
	function extPeriodo() {
		visent.periodo = {};
		visent.periodo.init = initPeriodo;
		visent.periodo.carregar = carregarPeriodo;
		visent.periodo.toObj = criarPeriodo;
		visent.periodo.resumo = formatarResumo;
	}
	
	function initPeriodo($tab) {
		bindChkDataComparativa($tab);
		bindDatepicker($tab);
		bindTipoData($tab);
		bindTimepicker($tab);
		bindSpinners($tab);
		bindCalcularDuracao($tab);
		bindBtnAddIntervalo($tab);
		bindIntervalosEspecificos($tab);
	}
	
	function carregarPeriodo($tab, periodo) {
		$('.resumo-periodo').loading(true);
		var $datasEspecificas = $tab.find(SELECTOR_DATAS_ESPECIFICAS);
		if (periodo.dataEspecifica != null) {
			$datasEspecificas.eq(0).val(periodo.dataEspecifica);
		}
		if (periodo.dataPreDefinida != null) {
			$tab.find(SELECTOR_TIPO_DATA).eq(0).val(periodo.dataPreDefinida.id);
		}
		if (periodo.dataEspecificaComparativa != null || periodo.dataPreDefinidaComparativa != null) {
			$tab.find(SELECTOR_COL_COMPARATIVO).show()
			$tab.find(SELECTOR_CHK_COMPARATIVO).prop('checked', true);
		}
		if (periodo.dataEspecificaComparativa != null) {
			$datasEspecificas.eq(1).val(periodo.dataEspecificaComparativa);
		}
		if (periodo.dataPreDefinidaComparativa != null) {
			$tab.find(SELECTOR_TIPO_DATA_COMPARATIVA).val(periodo.dataPreDefinidaComparativa.id);
		}
		$tab.find(SELECTOR_TIPO_DATA).trigger('change');
		if (periodo.intervalosPreDefinidos != null) {
			var sel = [];
			for (var i in periodo.intervalosPreDefinidos) {
				var intervalo = periodo.intervalosPreDefinidos[i];
				sel.push(intervalo.id);
			}
			$tab.find('.intervalos-pre-definidos .listboxFrom').val(sel);
			$tab.find('.intervalos-pre-definidos .dlb_enviar').trigger('click')
		}
		if (periodo.intervalosEspecificos != null) {
			var $horaInicial = $tab.find(SELECTOR_HORA_INICIAL),
				$duracao = $tab.find(SELECTOR_DURACAO);
			
			for (var i in periodo.intervalosEspecificos) {
				var intervalo = periodo.intervalosEspecificos[i];
				$horaInicial.val(intervalo.horaInicial);
				var hora = intervalo.duracao.split(':')[0];
				var minuto = intervalo.duracao.split(':')[1];
				var dia = Number.parseInt(hora / 24);
				hora = (hora-(24*dia));
				$duracao.eq(0).val(dia); // dia
				$duracao.eq(1).val(hora); // hora
				$duracao.eq(2).val(minuto); // minuto
				criarLinhaIntervalo($tab);
			}
			
			calcularDuracao($tab);
		}
		formatarResumo($tab);
		$('.resumo-periodo').loading(false);
	}
	
	function bindChkDataComparativa($tab) {
		var $colComparativo = $tab.find(SELECTOR_COL_COMPARATIVO);
		$tab.find(SELECTOR_CHK_COMPARATIVO).on('change', function() {
			if ($(this).is(':checked')) {
				$colComparativo.show();
			} else {
				$colComparativo.hide();
			}
		});
	}
	
	function bindDatepicker($tab) {
		var $datasEspecificas = $tab.find(SELECTOR_DATAS_ESPECIFICAS);
		$datasEspecificas.mask('00/00/0000');
		$datasEspecificas.val(Date.create().format('{dd}/{MM}/{yyyy}'));
		$datasEspecificas.datepicker({
			format: "dd/mm/yyyy",
			todayHighlight: true,
			language: "pt-BR",
			autoclose: true,
			calendarWeeks: true,
			endDate: new Date()
		});
	}
	
	function bindTipoData($tab) {
		var $tipoData = $tab.find(SELECTOR_TIPO_DATA);
		$tipoData.on('change', function() {
			var val = $(this).val();
			if (val == '') {
				$tab.find($(this).data('input')).show();
			} else {
				$tab.find($(this).data('input')).hide();
			}
		});
	}
	
	function bindTimepicker($tab) {
		var $horaInicial = $tab.find(SELECTOR_HORA_INICIAL);
		$horaInicial.timepicker({
			showMeridian: false
		});
	}
	
	function bindSpinners($tab) {
		var $duracao = $tab.find(SELECTOR_DURACAO);
		$duracao.TouchSpin({
		      verticalbuttons: true,
		      verticalupclass: 'glyphicon glyphicon-plus',
		      verticaldownclass: 'glyphicon glyphicon-minus',
		      buttondown_class: 'btn',
		      buttonup_class: 'btn',
		      initval: 0,
		      forcestepdivisibility: 'none'
	    });
	}
	
	function bindCalcularDuracao($tab) {
		$tab.find('.tipo-data, .data-especifica, .hora-inicial, .duracao').on('change', function() {
			calcularDuracao($tab);
		});
		calcularDuracao($tab);
	}
	
	function calcularDuracao($tab) {
		var $datasEspecificas = $tab.find(SELECTOR_DATAS_ESPECIFICAS),
			$tipoData = $tab.find(SELECTOR_TIPO_DATA),
			$calculo = $tab.find(SELECTOR_DURACAO_CALCULO);
	
		var vals = getHoraMinutoDuracao($tab);
		var date = Date.create();
		if ($tipoData.val() == '') {
			var arr = $datasEspecificas.eq(0).val().split('/');
			date = Date.create(arr[1]+'/'+arr[0]+'/'+arr[2]);
		} 
		date.set({hours: vals.hora, minutes: vals.minuto})
			.addDays(vals.diaDuracao)
			.addHours(vals.horaDuracao)
			.addMinutes(vals.minutoDuracao);
		var data = null;
		var horario = date.format('{HH}:{mm}');
		if ($tipoData.val() == '') {
			data = date.format('{dd}/{MM}/{yyyy}');
		} else {
			var range = Date.range(Date.create().set({hours:0, minutes:0, seconds:0}),
					date.set({hours:0, minutes:1, seconds:0}));
			data = 'D+'+range.days();
		}
		$calculo.html(data+' '+horario);
	}
	
	function getHoraMinutoDuracao($tab) {
		var $horaInicial = $tab.find(SELECTOR_HORA_INICIAL),
			$duracao = $tab.find(SELECTOR_DURACAO);
			
		var horaMinuto = $horaInicial.val().split(':');
		var vals = [Number.parseInt(horaMinuto[0]), Number.parseInt(horaMinuto[1])];
		$duracao.each(function(i, input) {
			vals.push(Number.parseInt(input.value));
		});
		return {
			hora: vals[0],
			minuto: vals[1],
			diaDuracao: vals[2],
			horaDuracao: vals[3],
			minutoDuracao: vals[4]
		};
	}
	
	function bindBtnAddIntervalo($tab) {
		var $btnAddIntervalo = $tab.find(SELECTOR_BTN_ADD_INTERVALO);
		
		$btnAddIntervalo.on('click', function() {
//			var granularidade = $('[name="granularidade"]:checked').val();
			criarLinhaIntervalo($tab);
		});
	}
	
	function criarLinhaIntervalo($tab) {
		var $ul = $tab.find(SELECTOR_INTERVALOS_ESPECIFICOS_UL);
		var vals = getHoraMinutoDuracao($tab);
		var conteudo = ''; 
		var hora = visent.format2digits(vals.hora);
		var minuto = visent.format2digits(vals.minuto);
		
		conteudo += criarLinhaIntervaloConteudo(hora, minuto, vals)+' <span class="pull-right"><input type="checkbox"></input></span>';
		var $li = $('<li class="list-group-item" data-hora="'+hora+'" data-minuto="'+minuto
				+'" data-diaduracao="'+vals.diaDuracao+'" data-horaduracao="'+vals.horaDuracao+'" data-minutoduracao="'+vals.minutoDuracao+'">'
			+conteudo+'</li>');
		$ul.append($li);
	}
	
	function criarLinhaIntervaloConteudo(hora, minuto, valsDuracao) {
		return 'Hora Inicial: '+hora+':'+minuto
			+' | Duracao: '+valsDuracao.diaDuracao+'d '+valsDuracao.horaDuracao+'h '+valsDuracao.minutoDuracao+'m';
	}
	
	function bindIntervalosEspecificos($tab) {
		$tab.find('.intervalos-especificos-chk').on('click', function(e) {
			e.preventDefault();
			var $checks = $tab.find('.intervalos-especificos input');
			var total = $checks.length;
			var checkeds = $tab.find('.intervalos-especificos input:checked').length;
			var notCheckeds = total - checkeds; 
			if (notCheckeds >= checkeds) {
				$checks.prop('checked', true);
			} else {
				$checks.prop('checked', false);
			}
		});
		
		$tab.find('.intervalos-especificos-del').on('click', function(e) {
			e.preventDefault();
			$tab.find('.intervalos-especificos input:checked').closest('li').remove();
		});
	}
	
	function criarPeriodo($tab) {
		var periodo = {},
			$datasEspecificas = $tab.find(SELECTOR_DATAS_ESPECIFICAS),
			$tipoData = $tab.find(SELECTOR_TIPO_DATA),
			$chkComparativo = $tab.find(SELECTOR_CHK_COMPARATIVO),
			$ul = $tab.find(SELECTOR_INTERVALOS_ESPECIFICOS_UL);
		
		// datas
		if ($tipoData.eq(0).val() == '') {
			periodo.dataEspecifica = $datasEspecificas.eq(0).val();
		} else {
			periodo.dataPreDefinida = {
				id: Number.parseInt($tipoData.eq(0).val())
			}
		}
		if ($chkComparativo.is(':checked')) {
			if ($tipoData.eq(1).val() == '') {
				periodo.dataEspecificaComparativa = $datasEspecificas.eq(1).val();
			} else {
				periodo.dataPreDefinidaComparativa = {
					id: Number.parseInt($tipoData.eq(1).val())
				}
			}
		}
		
		// intervalos pre-definidos
		periodo.intervalosPreDefinidos = [];
		$tab.find('.intervalos-pre-definidos .listboxTo option').each(function(i, option) {
			periodo.intervalosPreDefinidos.push({
				id: Number.parseInt(option.value)
			});
		});
		
		// intervalos especificos
		periodo.intervalosEspecificos = [];
		$ul.find('li').each(function(i, li) {
			var $li = $(li);
			var hora = $li.data('hora');
			var minuto = $li.data('minuto');
			var diaDuracao = $li.data('diaduracao');
			var horaDuracao = $li.data('horaduracao');
			var minutoDuracao = $li.data('minutoduracao');
			periodo.intervalosEspecificos.push({
				horaInicial: hora+':'+minuto,
				duracao: (horaDuracao+(diaDuracao*24))+':'+minutoDuracao+':00'
			});
		});
		
		return periodo;
	}
	
	function formatarResumo($tab) {
		var $datasEspecificas = $tab.find(SELECTOR_DATAS_ESPECIFICAS),
			$tipoData = $tab.find(SELECTOR_TIPO_DATA),
			$chkComparativo = $tab.find(SELECTOR_CHK_COMPARATIVO),
			$ul = $tab.find(SELECTOR_INTERVALOS_ESPECIFICOS_UL);

		var data = null;
		var dataComparativa = '--';
		// datas
		if ($tipoData.eq(0).val() == '') {
			data = $datasEspecificas.eq(0).val();
		} else {
			data = $tipoData.eq(0).find('option:selected').text();
		}
		if ($chkComparativo.is(':checked')) {
			if ($tipoData.eq(1).val() == '') {
				dataComparativa = $datasEspecificas.eq(1).val();
			} else {
				dataComparativa = $tipoData.eq(1).find('option:selected').text();
			}
		}
		// intervalos pre-definidos
		var intervalosPreDefinidos = [];
		$tab.find('.intervalos-pre-definidos .listboxTo option').each(function(i, option) {
			intervalosPreDefinidos.push(option.text);
		});
		// intervalos especificos
		var $intervalosEspecificosResumo = $tab.find('.intervalos-especificos-resumo ul');
		$intervalosEspecificosResumo.empty();
		$ul.find('li').each(function(i, li) {
			var $li = $(li);
			var hora = $li.data('hora');
			var minuto = $li.data('minuto');
			var diaDuracao = $li.data('diaduracao');
			var horaDuracao = $li.data('horaduracao');
			var minutoDuracao = $li.data('minutoduracao');
			var conteudo = criarLinhaIntervaloConteudo(hora, minuto, {diaDuracao: diaDuracao, horaDuracao: horaDuracao, minutoDuracao: minutoDuracao});
			var $liResumo = $('<li class="list-group-item">'+conteudo+'</li>');
			$intervalosEspecificosResumo.append($liResumo);
		});
		
		$tab.find('.resumo-data-sel').html(data);
		$tab.find('.resumo-data-comparativa-sel').html(dataComparativa);
		$tab.find('.resumo-intervalos-pre').html(intervalosPreDefinidos.join(', '));
	}
	
});
