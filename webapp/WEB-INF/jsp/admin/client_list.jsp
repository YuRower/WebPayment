<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Final Task" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">
			<c:if test="${not empty userList}">
					<h3>Clients list</h3>
					<table class="center">
						<tr>
							<th>user id</th>
							<th>name</th>
							<th>surname</th>
							<th>email</th>
							<th>status</th>
						</tr>
						<c:forEach var="item" items="${userList}">
							<tr>
								<td><c:out value="${item.id}" /></td>
								<td><c:out value="${item.name}" /></td>
								<td><c:out value="${item.surname}" /></td>
								<td><c:out value="${item.email}" /></td>
								<c:choose>
									<c:when test="${item.userStatusId == 0}">
										<td><fmt:message key="usr.enum.validation" /></td>
									</c:when>
									<c:when test="${item.userStatusId == 1}">
										<td><fmt:message key="usr.enum.allowed" /></td>
									</c:when>
									<c:otherwise>
										<td><fmt:message key="usr.enum.blocked" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				 <c:if test="${empty userList}">
					<h3>Clients list is empty</h3>
				</c:if></td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>