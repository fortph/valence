<%@page
	import="dao.imp.identite.*,divers.*,dao.imp.formation.*,java.text.SimpleDateFormat, beans.identite.*,beans.formation.*,java.sql.*, java.util.*"%>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<script>
	window.jQuery
			|| document
					.write('<script src="/valence/javascript/jquery-2.0.3.min.js"><\/script>')
</script>

<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<script>
	window.jQuery
			|| document
					.write('<script src="/valence/javascript/jquery-ui-1.10.3.custom.js"><\/script>')
</script>

<%
String pers=request.getParameter("numero");
%>



<script type="text/javascript">
	$(function() {
		$("#menu").menu({
			position : {
				my : "left top",
				at : "left top+25"
			}
		});

	});
</script>

<style>
.ui-menu {
	width: 15%;
	font-size: 90%;
	font-weight: bold;
	min-height: 25px;
}

ul#menu {
	width: 100%;
	background: #888;
}

ul#menu>li {
	margin: auto;
	/*largeur divise par le nombre de menus principaux soit 100/4= 25 %  */
	width: 14%;
	padding: 0;
	background: #888;
	float: left;
}

ul#menu>li>ul {
	margin: auto;
	width: 15%;
	padding: 0;
	background: #888;
	float: left;
	list-style-type: none;
}

/*.niv3{
	width: 100%;
	margin: 20%;
	background: #888;
	float: left;
	list-style-type: none;
}*/
ul#menu>li>ul>li>ul { /*position:relative;*/
	position: relative;
	width: 100%;
	padding-left: 25%;
	background: #a5a0a0;
}
</style>

</head>

<body>

	<ul id="menu">

		<li><a href="/valence/jsp/accueil/rechercherPersonne.jsp">Accueil
		</a></li>

		<li><a href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=pers%>">Identite </a>

			</li>
	<li><a href="/valence/jsp/formation/creernouvelleformation.jsp?numero=<%=pers%>">Nouvelle formation</a>

			</li>

		
		
	</ul>


</body>

</html>