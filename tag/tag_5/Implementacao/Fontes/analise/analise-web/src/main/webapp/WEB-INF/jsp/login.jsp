<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<link rel="stylesheet" href="css/style.css">
	<!-- endinject -->
	<script>
		var contexto = '${pageContext.request.contextPath}';
		var msgs = [];
	</script>
</head>
<body>
	<div class="list-group">
		<c:forEach items="${usuarios}" var="usuario">
			<a href="#" class="list-group-item">${usuario.nome}/${usuario.perfil.nome}</a>
		</c:forEach>
	</div>
	<!-- inject:js -->
	<script src="libs/jquery/dist/jquery.js"></script>
	<script src="libs/bootstrap/dist/js/bootstrap.js"></script>
	<script src="libs/jquery-ui/jquery-ui.min.js"></script>
	<script src="js/util.js"></script>
	<!-- endinject -->
	<script>
		var msgs = [];
		$(function() {
			$('.list-group-item').on('click', function(e) {
				e.preventDefault();
				var path = $(this).text();
				visent.ws({
					path: 'login/'+path,
					method: 'GET',
					done: function() {
						window.location = contexto;
					},
					fail: function() {
						console.log(arguments);
					},
					always: function() {
					}
				});
			});
		});
	</script>
</body>
</html>