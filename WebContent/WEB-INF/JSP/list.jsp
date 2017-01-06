<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="aideProjet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="etudiants" type="java.util.Collection<aideProjet.Etudiant>" scope="request"/>

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


<ul class="nav nav-tabs"s>
  <li role="presentation" class="active"><a href="#">Etudiant</a></li>
  <li role="presentation"><a href="matiere">Matière</a></li>
</ul>
<form action="post">
	<table class="table table-bordered table-striped">
		<tr>
		  <th>Nom</th>
		  <th>Prénom</th>
		  <th>Formation</th>
		  <th>Moyenne</th>
		  <th>Abscence</th>
		  <th></th>
		</tr>
		<% for (Etudiant etu : etudiants) { %>
			<tr>
			  <td><a href="detail?id=<%= etu.getId() %>"><%= etu.getNom() %></a></td>
			  <td><%= etu.getPrenom() %></td>
			  <td><%= etu.getFormation().getIntitule() %></td>
			  <td>15.5</td>
			  <td><%= etu.getNbAbsence() %>
	  		  <a href="ajoutAbsence?id=<%= etu.getId() %>" class="btn btn-default">
			    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		      </a></td>
			  <td>
			  	<a href="detail?id=<%= etu.getId() %>" class="btn btn-default">
				  <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				</a>
			</tr>
			<%
		} %>
	</table>
</form>

<jsp:include page="<%= getServletContext().getInitParameter(\"footer\") %>"/>
</body>
</html>