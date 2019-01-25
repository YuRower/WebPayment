<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Final task" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center"><c:if test="${not empty accounts}">
					<h3>Account list</h3>
					<table class="center">
						<tr>
							<th>id</th>
							<th>name</th>
							<th>balance</th>
							<th>status</th>

						</tr>
						<c:forEach var="item" items="${accounts}">
							<tr>
								<td><c:out value="${item.id}" /></td>
								<td><c:out value="${item.name}" /></td>
								<td><c:out value="${item.balance}" /></td>
								<c:choose>
									<c:when test="${item.accountStatusId == 0}">
										<td><fmt:message key="acc.enum.open" /></td>
									</c:when>
									<c:when test="${item.accountStatusId == 1}">
										<td><fmt:message key="acc.enum.locked" /></td>
									</c:when>
									<c:otherwise>
										<td><fmt:message key="acc.enum.closed" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</table>
				</c:if> <c:if test="${empty accounts}">
					<h3>Account list is empty</h3>
				</c:if></td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>