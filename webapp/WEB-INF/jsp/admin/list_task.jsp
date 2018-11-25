<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Epam task" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<c:if test="${not empty showList}">
					<h3>Showing accounts count with balance greater than <c:out value = "${showListBalance}"/></h3>
					<table class="center">
						<tr>
							<th>user's email</th>
							<th>accounts</th>
						</tr>
						<c:forEach var="item" items="${showList}">
							<tr>
								<td><c:out value = "${item.email}"/></td>
								<td><c:out value = "${item.accounts}"/></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${empty showList}">
					<h3>No such users/accounts</h3>
				</c:if>	
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>