<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Replenishment confirmed" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center"><tldLib:getTime /><br /> <c:choose>
					<c:when test="${not empty fromCart}">
						<h3>
							<fmt:message key="paymconf.header1" />
						</h3>
						<br />
					</c:when>
					<c:otherwise>
						<h3>
							<fmt:message key="paymconf.header2" />
						</h3>
						<br />
					</c:otherwise>
				</c:choose>
				<p>
					<a href="controller?command=listCart"><fmt:message
							key="paymconf.cart" /></a> <a href="controller?command=listAccounts"><fmt:message
							key="paymconf.accs" /></a>
				</p></td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>