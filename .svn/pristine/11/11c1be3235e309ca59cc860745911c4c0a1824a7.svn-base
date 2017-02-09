var visent = {
	
	log: function(params) {
		if (Date.create) {
			console.log(Date.create().format('{dd}/{MM}/{yyyy} {hh}:{mm}:{ss}'), arguments);
		} else {
			console.log(arguments);
		}
	},
		
	ws: function(opts) {
		var defaults = {
				path: 'ping',
				method: 'GET',
				data: {},
				dataType: 'json',
				contentType: 'application/json',
				headers: {'Content-Type': 'application/json'},
				done: function() {
					visent.log('Done');
				},
				fail: function() {
					visent.log('Fail');
				},
				always: function() {
					
				}
		};
		
		var configs = $.extend({}, defaults, opts);
		visent.log('ajax', (configs.method+' '+configs.path));
		
		$.ajax({
			url: contexto+'/ws/'+configs.path,
			method: configs.method,
			headers: configs.headers,
			data: configs.data
		}).done(configs.done)
		.fail(configs.fail)
		.always(configs.always);
	},
	
	confirm: function(message, confirmFn) {
		$('#dlg-confirm-msg').html(message);
		$('#dlg-confirm').dialog('option', 'buttons', [
    	    {
    	    	text: msgs['lbl.confirmar'],
    	    	click: function() {
	    	    	$(this).dialog("close");
	    	    	confirmFn();
    	    	}
    	    },
    	    {
    	    	text: msgs['lbl.cancelar'],
    	    	click: function() {
    	    		$(this).dialog("close");
    	    	}
    	    }
	    	]);
		$('#dlg-confirm').dialog('open');
	},
	
	alert: function(message) {
		$('#dlg-alert-msg').html(message);
		$('#dlg-alert').dialog('option', 'buttons', [
    	    {
    	    	text: msgs['lbl.ok'],
    	    	click: function() {
	    	    	$(this).dialog("close");
    	    	}
    	    }
    	    ]);
		$('#dlg-alert').dialog('open');
	},
	
	formatStr: function(str) {
		if (str == null) return '';
		return str.replace(/ /g, '-').replace(/;/g, '-').replace(/,/g, '-').replace(/\//g, '_').replace(/:/g, '_');
	},
	
	formatLDT: function(ldt) {
		if (ldt == null) return '';
		return visent.format2digits(ldt.dayOfMonth)+'/'+visent.format2digits(ldt.monthValue)+'/'+ldt.year
			+' '+visent.format2digits(ldt.hour)+':'+visent.format2digits(ldt.minute)+':'+visent.format2digits(ldt.second);
	},
	
	format2digits: function(number) {
		return ("0" + number).slice(-2);
	},
	
	dtLanguage: {
  	  "sEmptyTable": msgs['datatables.emptyTable'],
	  "sInfo": msgs['datatables.info'],
	  "sInfoEmpty": msgs['datatables.infoEmpty'],
	  "sInfoFiltered": msgs['datatables.infoFiltered'],
	  "sInfoPostFix": "",
	  "sInfoThousands": ".",
	  "sLengthMenu": msgs['datatables.lengthMenu'],
	  "sLoadingRecords": msgs['datatables.loading'],
	  "sProcessing": msgs['datatables.processing'],
	  "sZeroRecords": msgs['datatables.zeroRecords'],
	  "sSearch": msgs['datatables.search'],
	  "oPaginate": {
		  "sNext": msgs['datatables.paginate.next'],
		  "sPrevious": msgs['datatables.paginate.previous'],
		  "sFirst": msgs['datatables.paginate.first'],
		  "sLast": msgs['datatables.paginate.last']
	  },
	  "oAria": {
		  "sSortAscending": msgs['datatables.aria.sortAsc'],
		  "sSortDescending": msgs['datatables.aria.sortDesc']
	  }
   },
   
   html: {
		  select: function($select, valores, valueField, textField) {
			  for (var i in valores) {
				  var val = valores[i];
				  if (valueField != null && textField != null) {
					  $select.append('<option value="'+val[valueField]+'">'+val[textField]+'</option>');
				  } else {
					  $select.append('<option value="'+val+'">'+val+'</option>');
				  }
			  }
		  }
   },
   
   dateAdd: function(date, interval, units) {
	   var ret = new Date(date);
	   switch (interval.toLowerCase()) {
	     case 'year'   :  ret.setFullYear(ret.getFullYear() + units);  break;
	     case 'quarter':  ret.setMonth(ret.getMonth() + 3*units);  break;
	     case 'month'  :  ret.setMonth(ret.getMonth() + units);  break;
	     case 'week'   :  ret.setDate(ret.getDate() + 7*units);  break;
	     case 'day'    :  ret.setDate(ret.getDate() + units);  break;
	     case 'hour'   :  ret.setTime(ret.getTime() + units*3600000);  break;
	     case 'minute' :  ret.setTime(ret.getTime() + units*60000);  break;
	     case 'second' :  ret.setTime(ret.getTime() + units*1000);  break;
	     default       :  ret = undefined;  break;
	   }
	   return ret;
   }

};
