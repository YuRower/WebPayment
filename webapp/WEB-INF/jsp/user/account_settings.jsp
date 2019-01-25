<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Account settings" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">
				<h3>
					<fmt:message key="acc.set.header" />
					<fmt:formatNumber type="number" minIntegerDigits="5"
						value="${settingsAcc.id}" />
				</h3> <br />
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="changeAccountName" /> <input
						type="hidden" name="accountId" value="${settingsAcc.id}" /> <input
						name="name" placeholder="enter account name" required
						value="${settingsAcc.name}" /> <input type="submit"
						value="<fmt:message key="acc.set.chname"/>">
				</form> <br /> <c:choose>
					<c:when test="${settingsAcc.accountStatusId == 0}">
						<form action="controller" method="POST"
							onsubmit="return confirm('<fmt:message key="acc.set.lock.confirm"/>');">
							<input type="hidden" name="command" value="lockAccount" /> <input
								type="hidden" name="accountId" value="${settingsAcc.id}" /> <input
								type="submit" value="<fmt:message key="acc.set.lock"/>">
						</form>
						<br />
					</c:when>
					<c:otherwise>
						<form action="controller" method="POST"
							onsubmit="return confirm('<fmt:message key="acc.set.unlock.confirm"/>');">
							<input type="hidden" name="command" value="unlockAccount" /> <input
								type="hidden" name="accountId" value="${settingsAcc.id}" /> <input
								type="submit" value="<fmt:message key="acc.set.unlock"/>">
						</form>
						<br />
					</c:otherwise>
				</c:choose>

				<form action="controller" method="POST"
					onsubmit="return confirm('<fmt:message key="acc.set.close.confirm"/>');">
					<input type="hidden" name="command" value="closeAccount" /> <input
						type="hidden" name="accountId" value="${settingsAcc.id}" /> <input
						type="submit" value="<fmt:message key="acc.set.close"/>" />
				</form>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>