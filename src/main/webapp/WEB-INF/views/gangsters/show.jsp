<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${not empty gangster}">
			<h2>${gangster.name}</h2>
			<div>Comment: ${gangster.comment}</div>
			<div>Is on duty: ${gangster.onDuty}</div>
			<div>Knows [${gangster.known.size()}] other members (directly)</div>
			<c:choose>
				<c:when test="${not empty boss}">
				<div>Current boss: [${boss.nodeId}] - ${boss.name}</div>
				</c:when>
				<c:otherwise>
					<div>Without known bosses!</div>
				</c:otherwise>
			</c:choose>
			<div>Level: ${level}</div>
			<div>Total subordinates: ${totalSubordinates}</div>
			<hr/>
			<c:if test="${not empty gangster.managers}">
				<h3>Managers</h3>
				<ul>
					<c:forEach items="${gangster.managers}" var="manager">
						<li><a href="<c:url value="/gangsters/${manager.boss.nodeId}"/>">[${manager.boss.nodeId}] <c:out
									value="${manager.boss.name}" /> onDuty=${manager.onDuty}</a> from <c:out
									value="${manager.createdAt}" />. Last updated at <c:out
									value="${manager.updatedAt}" /></li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${not empty gangster.managed}">
				<h3>Managed ones</h3>
				<ul>
					<c:forEach items="${gangster.managed}" var="manager">
						<li><a href="<c:url value="/gangsters/${manager.subordinate.nodeId}"/>">[${manager.subordinate.nodeId}] <c:out
									value="${manager.subordinate.name}" /> onDuty=${manager.onDuty}</a> from <c:out
									value="${manager.createdAt}" />. Last updated at <c:out
									value="${manager.updatedAt}" /></li>
					</c:forEach>
				</ul>
			</c:if>
			<hr/>
	</c:when>
	<c:otherwise>
		<div>No gangsters found matching [${id}]!</div>
	</c:otherwise>
</c:choose>