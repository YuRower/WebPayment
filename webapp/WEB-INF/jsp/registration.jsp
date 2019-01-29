<%@include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="file" tagdir="/WEB-INF/tags"%>

<html>

<c:set var="title" value="Registration" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<file:isUserLogged />

<body>

	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">

				<form id="login_form" action="controller" method="post">

					<input type="hidden" name="command" value="registration" />

					<fieldset>
						<legend>
							<fmt:message key="registration.text.newuser" />
						</legend>
						<input name="email" placeholder="E-mail" type="email" required /><br />
						<input name="password"
							placeholder="<fmt:message key="login.label.password"/>" required
							pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$" /><br />
						<input name="name"
							placeholder="<fmt:message key="registration.name"/>" required /><br />
						<input name="surname"
							placeholder="<fmt:message key="registration.surname"/>" required /><br />
						<input type="submit"
							value="<fmt:message key="registration.submit"/>">
						<pre>
							<fmt:message key="registration.info1" />
							<br />
							<fmt:message key="registration.info2" />
							<br />
							<fmt:message key="registration.info3" />
							<br />
							<fmt:message key="registration.info4" />
						</pre>
						&#9;
					</fieldset>
					<br />
				</form>
				<br />
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>