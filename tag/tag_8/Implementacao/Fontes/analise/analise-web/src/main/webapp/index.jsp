<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ResourceBundle"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages" var="messagesBundle"></fmt:setBundle>
<c:set var="msgs" value="${messagesBundle.resourceBundle}"></c:set>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${msgs['pagina.titulo']}</title>
	<link type="image/x-icon" rel="shortcut icon" href="favicon.png"/>
	
	<!-- inject:css -->
    <link rel="stylesheet" href="libs/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="libs/jquery-ui/themes/base/jquery-ui.min.css">
    <link rel="stylesheet" href="libs/bootstrap-vertical-tabs/bootstrap.vertical-tabs.css">
    <link rel="stylesheet" href="libs/bootstrap-touchspin/src/jquery.bootstrap-touchspin.css">
    <link rel="stylesheet" href="libs/bootstrap-treeview/dist/bootstrap-treeview.min.css">
    <link rel="stylesheet" href="libs/datatables.net-bs/css/dataTables.bootstrap.css">
    <link rel="stylesheet" href="libs/fullpage.js/dist/jquery.fullpage.css">
    <link rel="stylesheet" href="libs/jquery.steps/demo/css/jquery.steps.css">
    <link rel="stylesheet" href="libs/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css">
    <link rel="stylesheet" href="css/visent/visent.dualListBoxes.css">
    <link rel="stylesheet" href="css/visent/visent.help.css">
    <link rel="stylesheet" href="css/visent/visent.loading.css">
    <link rel="stylesheet" href="css/visent/visent.tabs.css">
    <link rel="stylesheet" href="css/style.css">
    <!-- endinject -->
    
    <script>
		var contexto = '${pageContext.request.contextPath}';
		var msgs = {
			<%
				ResourceBundle bundle = ResourceBundle.getBundle("messages");
				Enumeration<String> keys = bundle.getKeys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					out.print("'"+key+"': '"+bundle.getString(key)+"'");
					if (keys.hasMoreElements()) {
						out.println(",");
					}
				}
			%>
		};
		
    </script>
</head>

<body>

	<div class="navbar navbar-default navbar-fixed-top">
		<div id="selo"><img src="images/selo_visent.png"/></div>
		<img class="pull-left" id="logo" src="images/logo_cdrview.png"/>
		<div id="info-usuario" class="pull-left">
			<p><fmt:message key="lbl.usuario"/>: ${cdrview_usuario.nome}</p>
			<p><fmt:message key="lbl.perfil"/>: ${cdrview_usuario.perfil.nome}</p>
		</div>
      	<a href="#" class="pull-right visent-help-link" title="<fmt:message key="tip.msg.help"/>">
      		<span class="glyphicon glyphicon-question-sign visent-help-icon"></span>
      	</a>
      	<a href="#" class="pull-right visent-tip-link" title='<fmt:message key="tip.title.ativar"/>'>
      		<span class="glyphicon glyphicon-ban-circle visent-help-icon"></span>
      	</a>
    </div>

	<div class="col-md-12">
		
		<div id="principal" class="fullpage">
			<div id="section-rel" class="section">
			    <div id="relatorios" class="slide">
			    	<%@include file="jsp/relatorios/relatorios.jsp" %>
			    </div>
			    <div id="configuracao" class="slide">
			    	<%@include file="jsp/configuracao/configuracao.jsp" %>
			    </div>
			</div>
			<div id="section-agend" class="section">
				<%@include file="jsp/agendamento/agendamento.jsp" %>
			</div>
		</div>
		
	</div>
	
	<div id="dlg-confirm" title="<fmt:message key="lbl.confirmacao"/>" style="display: none">
		<div class="col-xs-2"><i class="confirm-warning glyphicon glyphicon-warning-sign"></i></div>
		<div class="col-xs-10"><span id="dlg-confirm-msg"></span></div>
	</div>
	
	<div id="dlg-alert" title="<fmt:message key="lbl.sistema"/>" style="display: none">
		<div class="col-xs-2"><i class="confirm-warning glyphicon glyphicon-warning-sign"></i></div>
		<div class="col-xs-10"><span id="dlg-alert-msg"></span></div>
	</div>
	
	<div id="execucoes">
	</div>
	
	<div id="compartilhamento" style="display: none">
		<%@include file="jsp/compartilhamento/compartilhamento.jsp" %>
	</div>
	
	<div id="dlg-colunas-drill" title="<fmt:message key="lbl.colunas"/>" style="display: none">
	</div>
	
	<div id="visent-tip">
		<div id="visent-tip-header">
			<a class="glyphicon glyphicon-question-sign"></a>
		</div> 
		<div id="visent-tip-msg"></div>
	</div>
	
	<!-- inject:js -->
    <script src="libs/jquery/dist/jquery.js"></script>
    <script src="libs/bootstrap/dist/js/bootstrap.js"></script>
    <script src="libs/jquery-ui/jquery-ui.min.js"></script>
    <script src="libs/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
    <script src="libs/bootstrap-touchspin/src/jquery.bootstrap-touchspin.js"></script>
    <script src="libs/bootstrap-treeview/dist/bootstrap-treeview.min.js"></script>
    <script src="libs/datatables.net/js/jquery.dataTables.js"></script>
    <script src="libs/datatables.net-bs/js/dataTables.bootstrap.js"></script>
    <script src="libs/fullpage.js/dist/jquery.fullpage.js"></script>
    <script src="libs/jquery-mask-plugin/dist/jquery.mask.js"></script>
    <script src="libs/jquery.steps/build/jquery.steps.js"></script>
    <script src="libs/sugar/dist/sugar.js"></script>
    <script src="libs/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
    <script src="js/libs/jquery.dialogextend.min.js"></script>
    <script src="js/visent/visent.dualListBoxes.js"></script>
    <script src="js/visent/visent.help.modal.js"></script>
    <script src="js/visent/visent.help.tips.js"></script>
    <script src="js/visent/visent.loading.js"></script>
    <script src="js/colunas.js"></script>
    <script src="js/agendamento.js"></script>
    <script src="js/compartilhamento.js"></script>
    <script src="js/configuracao.js"></script>
    <script src="js/execucao.js"></script>
    <script src="js/filtros.js"></script>
    <script src="js/main.js"></script>
    <script src="js/periodo.js"></script>
    <script src="js/relatorios.js"></script>
    <script src="js/util.js"></script>
    <!-- endinject -->
</body>
</html>										