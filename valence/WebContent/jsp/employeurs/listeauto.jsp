<%@ page import="java.util.*,org.json.*,dao.imp.employeur.*,beans.employeurs.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String req = request.getParameter("term");
	req=req.replace("'", "''");
	EmployeurDAO empdao = new EmployeurDAO();
	List<Employeur> liste = new ArrayList<Employeur>();
	liste = empdao.afficherEmployeursParNom(req);

	JSONArray tab = new JSONArray();
	for (int i = 0; i < liste.size(); i++) {
		final JSONObject obj = new JSONObject();
		String p = liste.get(i).getRs_employeur();
		obj.put("name", p);
		obj.put("id",liste.get(i).getId_employeur());
		tab.put(obj);

	}

	out.print(tab);
%>