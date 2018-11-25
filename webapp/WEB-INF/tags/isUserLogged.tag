<%@ tag body-content="empty" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:if test="${not empty user}">
	<c:choose>
		<c:when test="${userType.name == 'admin' }">
			<c:redirect url="/controller?command=listRequests" />
		</c:when>
		<c:when test="${userType.name == 'user'}">
			<c:redirect url="/controller?command=listAccounts" />
		</c:when>
		<c:when test="${userType.name == 'superuser'}">
			<c:redirect url="/controller?command=listAdmins" />
		</c:when>
	</c:choose>
</c:if>