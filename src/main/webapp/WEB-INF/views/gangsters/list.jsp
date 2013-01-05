<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<h3>Search gangsters</h3>
	<form action="<c:url value="/search/"/>" method="POST">
		<input type="text" name="query" value="${query}"/> <input type="submit" />
	</form>
</div>
<hr />
<c:choose>
	<c:when test="${not empty gangsters}">
		<c:forEach items="${gangsters}" var="gangster">
			<h2>
				<a href="<c:url value="/gangsters/${gangster.nodeId}"/>">${gangster.name}</a>
			</h2>
			<div>${gangster.comment}</div>
			<c:if test="${not empty gangster.managers}">
				<h3>Managers</h3>
				<ul>
					<c:forEach items="${gangster.managers}" var="manager">
						<li><a
							href="<c:url value="/gangsters/${manager.boss.nodeId}"/>"><c:out
									value="${manager.boss.name}" /> from <c:out
									value="${manager.createdAt}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${not empty gangster.managed}">
				<h3>Managed ones</h3>
				<ul>
					<c:forEach items="${gangster.managed}" var="manager">
						<li><a
							href="<c:url value="/gangsters/${manager.subordinate.nodeId}"/>"><c:out
									value="${manager.subordinate.name}" /> from <c:out
									value="${manager.createdAt}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div>No gangsters found matching [${query}]!</div>
	</c:otherwise>
</c:choose>