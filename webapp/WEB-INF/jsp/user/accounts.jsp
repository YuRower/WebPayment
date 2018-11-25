<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Accounts" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<c:if test="${not empty accounts}">
					<h3>	<fmt:message key="acc.enum.header"/></h3>
					<table class="center">
						<tr>
							<th>No</th>
							<th><fmt:message key="acc.enum.name"/></th>
							<th><fmt:message key="acc.enum.balance"/></th>
							<th><fmt:message key="acc.enum.transactions"/></th>
							<th><fmt:message key="acc.enum.status"/></th>
							<th colspan="3"><fmt:message key="acc.enum.actions"/></th>
						</tr>
						<c:forEach var="account" items="${accounts}">
							<tr>
								<td><fmt:formatNumber type="number" minIntegerDigits="5"
										value="${account.id}" /></td>
								<td><c:out value="${account.name}" /></td>
								<td><fmt:formatNumber type="currency" currencySymbol="$"
										value="${account.balance}" /></td>
								<td><form action="controller" method="POST">
										<input type="hidden" name="command" value="listTransactions" />
										<input type="hidden" name="accountId" value="${account.id}" />
										<input type="submit" value="<fmt:message key="acc.enum.transactions.see"/>">
									</form></td>
								<c:choose>
									<c:when test="${account.accountStatusId == 0}">
										<td><fmt:message key="acc.enum.open"/></td>
										<td><form action="controller" method="POST">
												<input type="hidden" name="command" value="redirectNewPayment" />
												<input type="hidden" name="accountId" value="${account.id}" />
												<input type="submit" value="<fmt:message key="acc.enum.newpayment"/>">
											</form></td>
										<td><form action="controller" method="POST">
												<input type="hidden" name="command" value="redirectReplenishAccount" />
												<input type="hidden" name="accountId" value="${account.id}" />
												<input type="submit" value="<fmt:message key="acc.enum.replenish"/>">
											</form></td>
									</c:when>
									<c:otherwise>
										<td><fmt:message key="acc.enum.locked"/></td>
										<td><input type="submit" value="<fmt:message key="acc.enum.newpayment"/>" disabled></td>
										<td><input type="submit" value="<fmt:message key="acc.enum.replenish"/>" disabled></td>
									</c:otherwise>
								</c:choose>
								<td><form action="controller" method="POST">
										<input type="hidden" name="command" value="redirectAccountSettings" /> <input
											type="hidden" name="accountId" value="${account.id}" /> <input
											type="submit" value="<fmt:message key="acc.enum.settings"/>" /></form></td>
							</tr>
						</c:forEach>
					</table>
					<p>
						<fmt:message key="acc.enum.sortby"/>
						<select name = "sortby" form = "sortform">
							<option value = "number" <%if(session.getAttribute("accountsOrder").equals("number ")){ %> selected <%} %> >No.</option>
							<option value = "balance" <%if(session.getAttribute("accountsOrder").equals("balance")){ %> selected <%} %> ><fmt:message key="acc.enum.balance"/></option>
							<option value = "name" <%if(session.getAttribute("accountsOrder").equals("name")){ %> selected <%} %> ><fmt:message key="acc.enum.name"/></option>
						</select>
						</p>
						<form action="controller" id="sortform" method = "POST">
							<input type="hidden" name="command" value="changeAccountsOrder" />
							<input type="submit" value = "<fmt:message key="acc.enum.sort"/>"/>
						</form>
						<p>
							<a href="controller?command=redirectNewAccount"><fmt:message key="acc.enum.click"/></a> <fmt:message key="acc.enum.newacc"/>
						</p>
				</c:if>
				<c:if test="${empty accounts}">
					<h3><fmt:message key="acc.enum.noacc"/></h3>
					<p><a href="controller?command=redirectNewAccount"><fmt:message key="acc.enum.click"/></a> <fmt:message key="acc.enum.newacc"/></p>
				</c:if>
				
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>