<html>
<head>
<meta charset="utf-8" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>REST Demo</title>
</head>
<body>

	<script>
		function addNumbers() {
		    var number1 = $("#number1").val();
		    var number2 = $("#number2").val();
		    $.post(
		    	"/vali-it-webapp/rest/addnumbers", 
		    	{
		    		"number1": number1,
		    		"number2": number2
		    	}, 
		    	function(result){
		        	$("#resultDisplay").html("Tulemus on " + result.result + "<br>Serveri sõnum: " + result.comment);
		        }
		    );
		}
	</script>

	<h1>Liida numbrid!</h1>
	<br>
	<input type="text" id="number1" /> + 
	<input type="text" id="number2" /> 
	<input type="button" value="Liida" onClick="addNumbers();" /> 
	<br>
	<div id="resultDisplay"></div>
</body>
</html>