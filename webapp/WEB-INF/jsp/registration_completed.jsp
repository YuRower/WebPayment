<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Registration completed" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<file:isUserLogged/>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr>
			<td class="content center">
				<fmt:message key="reg.compl"/>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>