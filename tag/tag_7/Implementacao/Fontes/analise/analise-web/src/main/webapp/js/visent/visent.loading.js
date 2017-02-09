$(function() {
	$.fn.loading = function(action) {
		if (action) {
			if (!this.hasClass('visent-loading-container')) {
				this.addClass('visent-loading-container');
				this.prepend('<div class="visent-loading"><div class="visent-loading-msg"><img src="'+contexto+'/images/ajaxLoading.gif" /> Aguarde...</div></div>');
			}
		} else {
			this.removeClass('visent-loading-container');
			this.find('.visent-loading:first').remove();
		}
	};
	$.fn.blockdiv = function(action) {
		if (action) {
			if (!this.hasClass('visent-loading-container')) {
				this.addClass('visent-loading-container');
				this.prepend('<div class="visent-blockdiv"></div>');
			}
		} else {
			this.removeClass('visent-loading-container');
			this.find('.visent-blockdiv:first').remove();
		}
	};
})