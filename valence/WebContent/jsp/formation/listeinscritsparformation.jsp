<%
String etat1="INSCRITS";
String  monchoix="listeinscritsparformation";
%>

<%@ include file="/jsp/templates/listeparformation.jsp" %>


		<%	
		//PreInscription preins=predao.findByID(Integer.parseInt(num));
		ListeFormations liste=listeda.findByID(Integer.parseInt(num));
		
				 PreInscription preincrip=new PreInscription();
				 preincrip.setListe(liste);
				 preincrip.setInscrit(true);
				 preincrip.setDateAbandon(null);
				 List<PreInscription> listepreins=predao.findByCriteria(preincrip);
				if(listepreins.size()>0){
		%>
		<p><%@ include file='/menus/accueil/menusimple.jsp' %></p><br>
		<div id="creation">LISTE DES
			<%=etat1 %> POUR <%=liste.getFormation() %></div>
		<br>
		
			<br>
			<hr>
			<br/>
			<div>
		<table id="tableinscritpouruneformation" class="display">
			<thead>
				<tr>
					<th>NOM</th>
					<th>PRENOM</th>
					<th>CODE POSTAL</th>
					<th>VILLE</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					for(int i=0;i<listepreins.size();i++){
								  Identite une=new IdentiteDAO().findByID(listepreins.get(i).getIdentite().getId_IDE());
				%>
				<tr>
					<td><%=une.getNom_IDE()%></td>
					<td><%=une.getPrenom_IDE()%></td>
					<td><%=une.getCp_IDE()%></td>
					<td><%=une.getVille_IDE()%></td>
					<td class="inscrire"><a
						href="jsp/formation/modifierinscritabandon.jsp?numero=<%=listepreins.get(i).getId_formation()%>" >Modifier
					</a></td>
				</tr>
				<%
					}
				%>
			</tbody>

		</table>
		<br>
		<%
			}
				else
				{
					
		%>
		<p><%@ include file='/menus/accueil/menusimple.jsp' %></p><br>
		<div id="creation">LISTE DES
			<%=etat1 %> POUR <%=liste.getFormation() %></div>
		<br>
		<h3>Personne n'est inscrit � cette formation</h3>
		<%
			}}
		%>
		<br />
	




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
		src="/valence/javascript/jquery.dataTables.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/ZeroClipboard.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/TableTools.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>




</div>


</body>
</html>