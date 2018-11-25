<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Action confirmed" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr><td class="content center">
			<tldLib:getTime/><br/>
			<h3><fmt:message key="adm.act.header"/></h3><br/>	
			<p>
				<a href="controller?command=listRequests"><fmt:message key="adm.act.req"/></a>			
				<a href="controller?command=actionsPage"><fmt:message key="adm.act.act"/></a>		
				<a href="controller?command=userList"><fmt:message key="adm.act.usrlst"/></a>
				<a href="controller?command=listAllAccounts"><fmt:message key="adm.act.accLst"/></a>&nbsp;			
							
					
			</p>
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>