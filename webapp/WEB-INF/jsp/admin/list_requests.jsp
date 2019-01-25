<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Requests" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr><td class="content center">
			<c:if test="${not empty requests}">
					<h3><fmt:message key="adm.req.header"/></h3>
					<table class="center">
						<tr>
							<th><fmt:message key="adm.req.user"/></th>
							<th><fmt:message key="adm.req.account"/></th>
							<th colspan="2"><fmt:message key="acc.enum.actions"/></th>
						</tr>
						<c:forEach var="request" items="${requests}">
							<tr>
								<td><fmt:formatNumber type="number" minIntegerDigits="5"
										value="${request.userId}" /></td>
								<td><fmt:formatNumber type="number" minIntegerDigits="5"
										value="${request.accountId}" /></td>
								<td><form action="controller" method="POST">
								<input type="hidden" name="command" value="requestResponse" />
								<input type="hidden" name="action" value="unlock" />
								<input type="hidden" name="requestUser" value="${request.userId}" /> 
								<input type="hidden" name="requestAccount" value="${request.accountId}" /> 
								<input type="submit" value="<fmt:message key="adm.req.unlock"/>" /></form></td>
								<td><form action="controller" method="POST">
								<input type="hidden" name="command" value="requestResponse" />
								<input type="hidden" name="action" value="ignore" />
								<input type="hidden" name="requestUser" value="${request.userId}" /> 
								<input type="hidden" name="requestAccount" value="${request.accountId}" /> 
								<input type="submit" value="<fmt:message key="adm.req.ignore"/>" /></form></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${empty requests}">
					<h3><fmt:message key="adm.req.null"/></h3>
				</c:if>	
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>