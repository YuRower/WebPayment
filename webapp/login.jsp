<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<file:isUserLogged />

	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">

				<form id="login_form" action="controller" method="post">

					<input type="hidden" name="command" value="login" />

					<fieldset>
						<legend>E-mail</legend>
						<input name="email" required /><br />
					</fieldset>
					<br />
					<fieldset>
						<legend>
							<fmt:message key="login.label.password" />
						</legend>
						<input type="password" name="password" required />
					</fieldset>
					<br /> <input type="submit"
						value="<fmt:message key="login.label.login"/>" />
				</form>
				<br /> <fmt:message key="login.text.noacc" /> &nbsp; <a
				href="controller?command=redirectRegistration"><fmt:message
						key="login.link.signup" /></a>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>