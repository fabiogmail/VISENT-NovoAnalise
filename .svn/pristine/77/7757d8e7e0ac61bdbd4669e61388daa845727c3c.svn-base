<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form class="ag-exportacao form-inline">
	<h5>Exportação</h5>
	<c:if test="${cdrview_usuario.perfil.podeExportacaoComum}">
		<div class="checkbox">
			<label>
		    	<input name="ag-exportacao-comum" type="checkbox" value="HTML"> HTML
		    </label>
		</div>
		<div class="checkbox">
			<label>
		    	<input name="ag-exportacao-comum" type="checkbox" value="CSV"> CSV
		    </label>
		</div>
		<div class="checkbox">
			<label>
		    	<input name="ag-exportacao-comum" type="checkbox" value="TXT"> TXT
		    </label>
		</div>
	</c:if>
	<c:if test="${cdrview_usuario.perfil.podeExportacaoConfiguracao}">
		<br>
		<div class="radio">
			<label>
		    	<input name="ag-exportacao-conf" type="radio" value="FTP"> FTP
		    </label>
		</div>
		<div class="radio">
			<label>
		    	<input name="ag-exportacao-conf" type="radio" value="BD"> BD
		    </label>
		</div>
		<div class="radio">
			<label>
		    	<input name="ag-exportacao-conf" type="radio" value="TERMINAIS"> Terminais
		    </label>
		</div>
	</c:if>
</form>