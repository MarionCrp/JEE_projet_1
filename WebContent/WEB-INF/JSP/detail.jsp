<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="aideProjet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="etudiant" type="aideProjet.Etudiant" scope="request"/>
<jsp:useBean id="formations" type="java.util.Collection<aideProjet.Formation>" scope="request"/>
<jsp:useBean id="choosen_formation_id" type="java.lang.String" scope="request"/>
<jsp:useBean id="active_coefficients" type="java.util.List<aideProjet.Coefficient>" scope="request"/>
<jsp:useBean id="previous_link" type="java.lang.String" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<title><%= getServletContext().getInitParameter("title")%></title>
</head>

<body> 

<%-- Element d'action : jsp:include --%>
	<jsp:include page="<%= getServletContext().getInitParameter(\"header\")%>" />

	<a href="<%= previous_link == null ? "list" : previous_link %>" class="btn btn-default">
		<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
		 Retour
	</a>
	
	<form class="form-horizontal" method="post" action="modifEtudiant">
	  <input type="hidden" name="id" value="<%= etudiant.getId() %>">
	  <div class="form-group">
	    <label for="nom" class="col-sm-2 control-label">Nom</label>
	    <div class="col-sm-10">
	      <input type="input" class="form-control" name="nom" id="nom" placeholder="Nom" value="<%= etudiant.getNom() %>" size="20">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="prenom" class="col-sm-2 control-label">Pr�nom</label>
	    <div class="col-sm-10">
	      <input type="input" class="form-control" name="prenom" id="prenom" placeholder="Pr�nom" value="<%= etudiant.getPrenom() %>" size="20">
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-2 control-label">Absence</div>
	    <div class="col-sm-1">
	      <input type="number" class="form-control" name="absence" id="absence" placeholder="0" value="<%= etudiant.getNbAbsence() %>">
	    </div>
	    <div class="col-sm-1">
   	  	  <a href="supprimerAbsence?id=<%= etudiant.getId() %>" class="btn btn-default">
	   		 <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
   		  </a>
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
	  <th>Mati�re</th>
	  <th>Coefficient</th>
	  <th>Note</th>
	  <th></th>
	</tr>
	<% if(active_coefficients.isEmpty()){ %>
		</table>
		<div class="alert alert-danger alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		  <strong> Aucune </strong> note disponible pour l'instant.
		</div>
		
	<% } else { %>
		
		<% for(Coefficient coeff : active_coefficients){ %>
			<tr>
				<td><a href="matiere?formation=<%= etudiant.getFormation().getId() %>&coefficient=<%= coeff.getId() %>" ><%= coeff.getMatiere().getIntitule() %></a></td>
				<td><%= coeff.getValeur() %></td>
				<td><%= NoteDAO.getByEtudiantAndMatiere(etudiant, coeff.getMatiere()).getResultat() %></td>
			</tr>
		<% } %>
	</table>
	<% } %>

<!-- JAVASCRIPT -->
<script type="text/javascript">

	window.addEventListener("load", function() {
		// Suppression de la flash message.
		setTimeout(fade_out, 4000);
		function fade_out() {
		  $("#flash").fadeOut(500);
		}
	});
</script>
</body>
</html>