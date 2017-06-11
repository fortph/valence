<%@page
	import="dao.imp.identite.*, beans.identite.*,java.util.*,java.sql.*,
dao.*,dao.exception.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="/valence/css/rechercherPersonne.css">
<link rel="stylesheet" href="/valence/css/menu.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>recherche</title>
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript" src="/valence/dwr/interface/Employeur.js"></script>


<!-- script qui permet de cacher les balises select et creer -->
<SCRIPT type="text/javascript">

	function griser(sour, un) {
		//var sources =$("#nomarechercher").val();
		var sources =$(sour).val();
		var propose=$("#nom>option").length;
		
		if(sources.length >=3){
			
			document.getElementById("afficher").style.visibility="visible";
			un.style.visibility="visible";
			
			
		if(propose >1){
			un.style.visibility="visible";
			un.style.display = "block";
		}
	
		
		else {
			un.style.visibility="hidden";
			//document.getElementById("afficher").style.visibility="visible";
		}
		}
		else{
			document.getElementById("nom").style.visibility="hidden";
			document.getElementById("afficher").style.visibility="hidden";
			
		}
			
	}
	
	function grise(){
		document.getElementById("nom").style.visibility="hidden";
		document.getElementById("afficher").style.visibility="hidden";
	}
</script>

</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<h3><%=rechercherpersonne %></h3>
		<h4><%=libelle1 %></h4>

		<%
			char c;

			for (c = 'A'; c <= 'Z'; c++) {
		%>
		<input type="button" name="debut" class="boutonvert" value="<%=c%>"
			onclick="self.location.href='/valence/controleur?action=<%=recherchernom %>&numero=<%=c%>'" />
		<%
			}
		%>
		<h4><%=libelle2 %></h4>

		
			<form method="post" autocomplete="off" action="/valence/jsp/<%=majsp %>" >
			<input type="text" autocomplete="off" name="nomarechercher" id="nomarechercher"/>
			
				<input type="submit"  value="Créer Fiche" class="boutonvert"/>
				<br>
				</form>
			
	</div>
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
							.write('<script src="/valence/javascript/jquery-ui-1.10.3.custom.min.js"><\/script>')
		</script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/<%=monscript %>" ></script>