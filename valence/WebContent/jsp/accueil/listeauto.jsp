<%@ page import="java.util.*,org.json.*,dao.imp.identite.*,beans.identite.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String req = request.getParameter("term");
	req=req.replace("'", "''");
	IdentiteDAO empdao = new IdentiteDAO();
	List<Identite> liste = new ArrayList<Identite>();
	liste = empdao.findByName(req);

	JSONArray tab = new JSONArray();
	for (int i = 0; i < liste.size(); i++) {
		final JSONObject obj = new JSONObject();
		String p = liste.get(i).getNom_IDE()+" "+liste.get(i).getPrenom_IDE();
		obj.put("name", p);
		obj.put("id",liste.get(i).getId_IDE());
		tab.put(obj);

	}

	out.print(tab);
%>