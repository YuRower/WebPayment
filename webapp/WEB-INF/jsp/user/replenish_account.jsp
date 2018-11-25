<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Replenish" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<h3><fmt:message key="newr.header"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${param.accountId}" /></h3>
			<form action="controller" method="POST">
			<input type="hidden" name="command" value="replanishAccount" />
			<input type="hidden" name="accountId" value="${param.accountId}" />
			<input name="amount" placeholder="<fmt:message key="newp.am"/>" required pattern = "^(\d)+(\.(\d{1,2}))?"/><br/>			
			<input type="submit" value="<fmt:message key="acc.enum.replenish"/>">
			</form>
			<p><fmt:message key="newp.hint"/></p>
				
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>