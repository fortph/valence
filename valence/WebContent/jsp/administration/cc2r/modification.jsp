<%@page import="beans.parametres.accueil.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
Cc2rDAO cdao=new Cc2rDAO();
List<Cc2r> listevillescc2r=cdao.findAll();
CodesPostauxDAO cpdao=new CodesPostauxDAO();
List<CodesPostaux> listevillesdept=cpdao.afficherVillesDepartementVoisins();


%>


<link rel="stylesheet" href="/valence/css/admin/cc2r.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CC2R</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


</head>

<div id="body">
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div  id="creation">CC2R</div>
		<br>
		<br>
		<p>Basculer des éléments d'une liste à l'autre</p>
		<br>
<form name="formulaire" method="get"  action="/valence/controleur">
			<input type="hidden" name="action" value="modifiercc2r" />
			
	<table id="tableau" border="0">
		<tr>
		<td valign="top" >
			<center>
			<fieldset>
				<legend class="legend"> Les <%=listevillescc2r.size()%> villes de la Communauté</legend><br />
				<select name="list1" id="list1" class="ListBox" multiple="multiple"    size="<%=listevillescc2r.size() %>" >
					<%
					for(int i=0;i<listevillescc2r.size();i++){
						%>
						<option value="<%=listevillescc2r.get(i).getId_cc2r() %>"><%=listevillescc2r.get(i).getLibelle() %></option>
					<%
					}
					%>
					
				</select>
			</fieldset>
			</center>
		</td>
		<td>
			<center>
				<input type="submit"   value=" <- Ajouter" name="ajouter"/>
				<br />
				<input type="submit"  value="Enlever ->" name="supprimer" />
				<br/>
				<input type="reset"  value="Effacer" />
			</center>
		</td>
		<td  valign="top" >
			<center>
				<fieldset><legend class="legend">VILLES</legend><br />
				<select name="list2" id="list2" class="ListBox"  multiple="multiple"   size="<%=listevillescc2r.size() %>"  >
				<%
				for(int i=0;i<listevillesdept.size();i++){
						%>
						<option value="<%=listevillesdept.get(i).getId()%>"><%=listevillesdept.get(i).getVille() %></option>
					<%
					}
					%>
					</select>
				</fieldset>
			</center>
		</td>
		</tr>
	</table>
</form>
		
	
		
</div>
</body>
</html>