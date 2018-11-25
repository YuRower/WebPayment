<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Admins" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<c:if test="${not empty admins}">
					<h3><fmt:message key="su.header"/></h3>
					<table class="center">
						<tr>
							<th>ID</th>
							<th><fmt:message key="registration.name"/></th>
							<th><fmt:message key="registration.surname"/></th>
							<th><fmt:message key="su.login"/></th>
							<th><fmt:message key="su.access"/></th>
							<th colspan="2"><fmt:message key="acc.enum.actions"/></th>
						</tr>
						<c:forEach var="admin" items="${admins}">
							<tr>
								<td><fmt:formatNumber type="number" minIntegerDigits="3"
										value="${admin.id}" /></td>
								<td><c:out value="${admin.name}" /></td>
								<td><c:out value="${admin.surname}" /></td>
								<td><c:out value="${admin.email}" /></td>
								<c:choose>
									<c:when test="${admin.userStatusId == 1}">
										<td><fmt:message key="su.access.al"/></td>
										<td><form action="controller" method="POST">
												<input type="hidden" name="command" value="superuserAction" />
												<input type="hidden" name="action" value="block" />
												<input type="hidden" name="adminId" value="${admin.id}" />
												<input type="submit" value="<fmt:message key="su.access.lock"/>">
											</form></td>
									</c:when>
									<c:otherwise>
										<td><fmt:message key="su.access.bl"/></td>
										<td><form action="controller" method="POST">
												<input type="hidden" name="command" value="superuserAction" />
												<input type="hidden" name="action" value="unlock" />
												<input type="hidden" name="adminId" value="${admin.id}" />
												<input type="submit" value="<fmt:message key="su.access.unlock"/>">
											</form></td>
									</c:otherwise>
								</c:choose>
								<td><form action="controller" method="POST">
										<input type="hidden" name="command" value="superuserAction" />
										<input type="hidden" name="action" value="delete" />
										<input type="hidden" name="adminId" value="${admin.id}" /> 
										<input type="submit" value="<fmt:message key="su.delete"/>" /></form></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${empty admins}">
					<h3><fmt:message key="su.null"/></h3>
				</c:if>
				<p><fmt:message key="su.new"/></p>
					<form action="controller" method="POST">
					<input type="hidden" name="command" value="addAdmin" />
					<input name="name" placeholder="<fmt:message key="registration.name"/>" required/><br/>
					<input name="surname" placeholder="<fmt:message key="registration.surname"/>" required/><br/>
					<input name="login" placeholder="<fmt:message key="su.login"/>" required/><br/>
					<input name="password" placeholder="<fmt:message key="login.label.password"/>" required pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"/><br/>
					<input type="submit" value="<fmt:message key="su.add"/>" /></form>
			
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>