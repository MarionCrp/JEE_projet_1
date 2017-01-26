<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="aideProjet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="etudiant" type="aideProjet.Etudiant" scope="request"/>
<jsp:useBean id="formations" type="java.util.Collection<aideProjet.Formation>" scope="request"/>

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

	<a href="list" class="btn btn-default">
		<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
	</a>
	
	<form class="form-horizontal" method="post" action="modifEtudiant">
	  <input type="hidden" name="id" value="<%= etudiant.getId() %>">
	  <div class="form-group">
	    <label for="nom" class="col-sm-2 control-label">Nom</label>
	    <div class="col-sm-10">
	      <input type="input" class="form-control" name="nom" id="nom" placeholder="Nom" value="<%= etudiant.getNom() %>">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="prenom" class="col-sm-2 control-label">Prénom</label>
	    <div class="col-sm-10">
	      <input type="input" class="form-control" name="prenom" id="prenom" placeholder="Prénom" value="<%= etudiant.getPrenom() %>">
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-2 control-label">Formation</div>
	    <div class="col-sm-10">
	      <select class="form-control" name="formation">
	      	<% for(Formation formation : formations) { %>
			  <option value="<%= formation.getId() %>" <% if(etudiant.getFormation().getId() == formation.getId()) { %> selected = "selected"  <% } %> ><%= formation.getIntitule() %></option>
		  	<% } %>
		  </select>
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-2 control-label">Absence</div>
	    <div class="col-sm-2">
	      <input type="number" class="form-control" name="absence" id="absence" placeholder="0" value="<%= etudiant.getNbAbsence() %>">
	    </div>
	    <div class="col-sm-2">
	  	  <a href="ajoutAbsence?id=<%= etudiant.getId() %>" class="btn btn-default">
	   		 <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
   		  </a>
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <button type="submit" class="btn btn-default">Modifier</button>
	    </div>
	  </div>
	</form>
	
	<table class="table table-bordered table-striped">
	<tr>
	  <th>Matière</th>
	  <th>Coefficient</th>
	  <th>Note</th>
	  <th></th>
	</tr>


</body>
</html>