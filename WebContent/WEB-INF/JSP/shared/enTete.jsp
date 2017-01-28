<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="flash_error" type="aideProjet.Flash" scope="request"/>
<jsp:useBean id="flash_success" type="aideProjet.Flash" scope="request"/>

<h2>Notes et Abscence des Etudiants</h2>
<% if(flash_error.hasAnyMessage()) {  %>
	<div id="flash" style="display: display;">
		<span class="alert alert-danger"> <%= flash_error.getFirstMessage() %> </span>
	</div>
<% } else if(flash_success.hasAnyMessage()) {  %>
	<div id="flash" style="display: display;">
		<span class="alert alert-success"> <%= flash_success.getFirstMessage() %> </span>
	</div>
<% } %>
