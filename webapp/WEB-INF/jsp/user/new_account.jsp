<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="New account" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<h3><fmt:message key="newacc.header"/></h3>
			<form action="controller" method="POST">
			<input type="hidden" name="command" value="createNewAccount" />
			<input name="name" required/><br/>			
			<input type="submit" value="<fmt:message key="newacc.submit"/>">
			</form>
			<p><fmt:message key="newacc.warning"/></p>
				
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>