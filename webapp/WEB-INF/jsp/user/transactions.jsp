<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Transactions" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<c:if test="${not empty accPayments}">
					<h3><fmt:message key="trns.header"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${param.accountId}" /></h3>
					<table class="center">
						<tr>
							<th>ID</th>
							<th><fmt:message key="trns.type"/></th>
							<th colspan="2" ><fmt:message key="trns.det"/></th>
							<th><fmt:message key="newp.am"/></th>
							<th><fmt:message key="trns.date"/></th>
							<th><fmt:message key="trns.bill"/></th>
						</tr>
						<c:forEach var="payment" items="${accPayments}">
							<tr>
							<td><fmt:formatNumber type="number" minIntegerDigits="5"
										value="${payment.id }" />
							</td>
								<c:choose>
									<c:when test="${payment.paymentTypeId == 0}">
										<td><fmt:message key="cart.remit"/></td>
										<td><fmt:message key="cart.from"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${payment.accountIdFrom}" /></td>
										<td><fmt:message key="cart.to"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${payment.accountIdTo}" /></td>
										<td><fmt:formatNumber type="currency" currencySymbol="$"
										value="${payment.moneyAmount}" /></td>
									</c:when>
									<c:otherwise>
										<td><fmt:message key="cart.replen"/></td>
										<td colspan="2"><fmt:message key="cart.to"/> <fmt:formatNumber type="number" minIntegerDigits="5"
										value="${payment.accountIdTo}" /></td>
										<td><fmt:formatNumber type="currency" currencySymbol="$"
										value="${payment.moneyAmount}" /></td>
									</c:otherwise>
								</c:choose>
								<td>
									<fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${payment.date}" />
								</td>
								<td>
									<form action="controller" method="POST">
									<input type="hidden" name="command" value="getTransactionReport" /> 
									<input type="hidden" name="paymentId" value="${payment.id}" /> 
									<input type="submit" value="<fmt:message key="trns.getbill"/>" /></form>							
								</td>
							</tr>
						</c:forEach>
					</table>
					<p>
						<fmt:message key="acc.enum.sortby"/>
						<select name = "sortby" form = "sortform">
							<option value = "number" <%if(session.getAttribute("paymentsOrder").equals("number")){ %> selected <%} %> >No.</option>
							<option value = "dateASC" <%if(session.getAttribute("paymentsOrder").equals("dateASC")){ %> selected <%} %> ><fmt:message key="trns.sort.no"/></option>
							<option value = "dateDSC" <%if(session.getAttribute("paymentsOrder").equals("dateDSC")){ %> selected <%} %> ><fmt:message key="trns.sort.on"/></option>
						</select>
						</p>
						<form action="controller" id="sortform" method = "POST">
							<input type="hidden" name="command" value="changePaymentsOrder" />
							<input type="hidden" name="accountId" value="${param.accountId}" />
							<input type="submit" value = "<fmt:message key="acc.enum.sort"/>"/>
						</form>
				</c:if>
				<c:if test="${empty accPayments}">
					<h3><fmt:message key="trns.null"/></h3>
					<p><a href="controller?command=listAccounts"><fmt:message key="trns.back"/></a></p>
				</c:if>
				
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>