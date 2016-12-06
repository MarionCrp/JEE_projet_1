<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="aideProjet.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body> 

<% 
	request.getParameter("id"); 
	int etudiant_id = Integer.parseInt(request.getParameter("id"));
	Etudiant etudiant =  GestionFactory.getEtudiantById(etudiant_id);
	
	%>
	
	<p><b><%= etudiant.getNom().toUpperCase() %></b> <%= etudiant.getPrenom() %></p>
</body>
</html>