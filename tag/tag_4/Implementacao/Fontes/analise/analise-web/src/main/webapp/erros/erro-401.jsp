<!doctype html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${msgs['pagina.titulo']}</title>
	<link type="image/x-icon" rel="shortcut icon" href="favicon.png"/>
	
	<style>
		body {
			background-color: #EAEEF0;
		}
		.erro-container {
			position: relative;
		    background-color: #fff;
		    width: 230px;
		    padding: 70px 30px 50px 30px;
		    border: 1px solid #1D3445;
		    border-radius: 4px;
		    text-align: center;
		    margin: 0 auto;
		    margin-top: 100px;
		}
		.img-selo, .img-logo {
			position: absolute;
		}
		.img-selo {
			top: 5px;
    		left: 5px;
		}
		.img-logo {
			top: 7px;
		    left: 50px;
		    width: 170px;
		}
		.erro-codigo {
			font-weight: bold;
    		font-size: 13px;
		}
		.erro-mensagem {
			font-size: 16px;
		    font-weight: bold;
		    text-transform: uppercase;
		}
	</style>
	
</head>

<body>

	<div class="erro-container">
		<img class="img-selo" src="images/selo_visent.png"/>
		<img class="img-logo" src="images/logo_cdrview.png"/>
		<div class="erro-codigo">Erro 401</div>
		<div class="erro-mensagem">Acesso não autorizado</div>
	</div>
	
</body>
</html>										