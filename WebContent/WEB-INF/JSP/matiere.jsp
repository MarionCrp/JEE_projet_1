<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="aideProjet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="etudiants" type="java.util.List<aideProjet.Etudiant>" scope="request"/>
<jsp:useBean id="formations" type="java.util.List<aideProjet.Formation>" scope="request"/>
<jsp:useBean id="choosen_formation_id" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="coefficients" type="java.util.List<aideProjet.Coefficient>" scope="request"/>
<jsp:useBean id="coefficient" type="aideProjet.Coefficient" scope="request"/>
<jsp:useBean id="choosen_coefficient_id" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="liste_notes" type="java.util.HashMap" scope="request"/>

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
  <li role="presentation"><a href="list">Etudiant</a></li>
  <li role="presentation" class="active"><a href="#">Matière</a></li>
</ul>

<!-- SELECT CHOIX FORMATION -->
<form method="get" action="matiere">
	<div class="form-group">
		<div class="row">
		    <div class="col-sm-2 control-label">Formation</div>
		    <div class="col-sm-8">
		      <select class="form-control select-bar" name="formation">
		     	 <option selected = "selected"> Choisissez une formation </option>
		      	<% for(Formation formation : formations) { %>
				  <option value="<%= formation.getId() %>" <% 
				  if(choosen_formation_id == formation.getId()) { %> selected = "selected"  <% } %> ><%= formation.getIntitule() %></option>
			  	<% } %>
			  </select>
		    </div>
		  </div>
		 </div>
</form>

<!-- SELECT CHOIX MATIERE -->
<% if(choosen_formation_id > -1){ %>
	<% if(coefficients.size() > 0){ %>
		<form method="get" action="matiere">
			<div class="form-group">
				<div class="row">
				    <div class="col-sm-2 control-label">Matiere</div>
				    <div class="col-sm-8">
				      <input type="hidden" name="formation" value="<%= choosen_formation_id %>">
				      <select class="form-control select-bar" name="coefficient">
				      	<option selected = "selected"> Choisissez une matière </option>
				      	<% for(Coefficient coeff : coefficients) { %>
						  <option value="<%= coeff.getId() %>" <% 
						  if(choosen_coefficient_id == coeff.getId()) { %> selected = "selected"  <% } %> ><%= coeff.getMatiere().getIntitule() %></option>
					  	<% } %>
					  </select>
				    </div>
				  </div>
			  </div>
		</form>
	<% } else { %>
	<div class="alert alert-danger alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong> Aucune </strong> matière n'est ajoutée pour cette formation.
	</div>

	<% }
   } %>


<!-- FORMULAIRE UPDATE VALEUR DU COEFFICIENT ET S'IL EST ACTIF (se calcule dans la moyenne) -->

<% if (choosen_formation_id > -1 && choosen_coefficient_id > -1 && etudiants.size() > 0) { %>

<form method="post" action="matiere?formation=<%= choosen_formation_id %>&coefficient=<%= choosen_coefficient_id %>">
    <input type="hidden" name="coefficient_id" value="<%= coefficient.getId() %>">
	<div class="form-group">
		<div class="row">
		    <div class="col-sm-2 control-label">Coefficient</div>
		    <div class="col-sm-8">

		      <input type="number" name="coefficient_value" value="<%= coefficient.getValeur() %>" class="form-control">
		    </div>
		  </div>
	  </div>
	  <div class="form-group">
		<div class="row">
		    <div class="col-sm-2 control-label"></div>
		    <div class="col-sm-8">
		      <div class="checkbox">
			    <label>
			      <input type="checkbox" name="coefficient_active" <%= coefficient.getActif() == true ? "checked" : "" %>> Activer
			    </label>
			  </div>
		    </div>
		  </div>
	  </div>
	
<!-- FORMULAIRE UPDATE NOTE ETUDIANT (se calcule dans la moyenne) -->
	<table class="table table-bordered table-striped">
		<tr>
		  <th>Nom</th>
		  <th>Prénom</th>
		  <th>Formation</th>
	  	  <th>Notes</th>
		</tr>
		<% for (Etudiant etu : etudiants) { %>
			<tr>
				<input type="hidden" name="id" value=<%= etu.getId() %>>
			  <td><a href="detail?id=<%= etu.getId() %>"><%= etu.getNom() %></a></td>
			  <td><%= etu.getPrenom() %></td>
			  <td><%= etu.getFormation().getIntitule() %></td>
			  <td>
			  	<input type="number" id="note[<%= etu.getId() %>]" name="note[<%= etu.getId() %>]" value="<%= liste_notes.get(etu) %>" class="form-control">
			  </td>
	
			</tr>
			<%
		} %>
	</table>
	<input type="submit" value="Valider les notes" name="updateMatiere">
<% } %>


<!-- FOOTER -->
<jsp:include page="<%= getServletContext().getInitParameter(\"footer\") %>"/>

<!-- JAVASCRIPT -->
<script type="text/javascript">

	window.addEventListener("load", function() {
		var selects = document.getElementsByClassName("select-bar");
		for(i=0; i < selects.length; i++){
			document.getElementsByClassName("select-bar")[i].addEventListener('change', function(){
				this.form.submit();
			});
		}
	});
	
</script>
</body>
</html>