<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="aideProjet.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<title><%= getServletContext().getInitParameter("title")%></title>
</head>

<body> 

<%-- Element d'action : jsp:include --%>
		<jsp:include page="<%= getServletContext().getInitParameter(\"header\")%>" />

<% 
	request.getParameter("id"); 
	int etudiant_id = Integer.parseInt(request.getParameter("id"));
	Etudiant etudiant =  GestionFactory.getEtudiantById(etudiant_id);
	%>
	
	<p><b><%= etudiant.getNom().toUpperCase() %></b> <%= etudiant.getPrenom() %></p>
</body>
</html>