<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Cart" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<c:if test="${not empty prepPayments}">
					<h3><fmt:message key="cart.header"/></h3>
					<table class="center">
						<c:forEach var="payment" items="${prepPayments}">
							<tr>
								<c:choose>
									<c:when test="${payment.paymentTypeId == 0}">
										<td><fmt:message key="cart.remit"/></td>
										<td><fmt:message key="cart.from"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${payment.accountIdFrom}" /></td>
										<td><fmt:message key="cart.to"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${payment.accountIdTo}" /></td>
										<td><fmt:message key="cart.amount"/> <fmt:formatNumber type="currency" currencySymbol="$"
										value="${payment.moneyAmount}" /></td>
									</c:when>
									<c:otherwise>
										<td><fmt:message key="cart.replen"/></td>
										<td colspan="2"><fmt:message key="cart.to"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${payment.accountIdTo}" /></td>
										<td><fmt:message key="cart.amount"/> <fmt:formatNumber type="currency" currencySymbol="$"
										value="${payment.moneyAmount}" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</table>
						<form action="controller" method = "POST">
							<input type="hidden" name="command" value="executeCartPayments" />
							<input type="submit" value = "<fmt:message key="cart.process"/>"/>
						</form>
						<form action="controller" method = "POST">
							<input type="hidden" name="command" value="clearCart" />
							<input type="submit" value = "<fmt:message key="cart.clear"/>"/>
						</form>
				</c:if>
				<c:if test="${empty prepPayments}">
					<h3><fmt:message key="cart.null"/></h3>
				</c:if>
				
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>