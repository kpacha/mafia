<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${not empty gangster}">
			<h2>${gangster.name}</h2>
			<div>${gangster.comment}</div>
			<div>on duty: ${gangster.onDuty}</div>
			<c:if test="${not empty gangster.bosses}">
				<h3>Bosses</h3>
				<ul>
					<c:forEach items="${gangster.bosses}" var="boss">
						<li><a href="<c:url value="/gangsters/${boss.nodeId}"/>">-<c:out
									value="${boss.name}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${not empty gangster.managers}">
				<h3>Managers</h3>
				<ul>
					<c:forEach items="${gangster.managers}" var="manager">
						<li><a href="<c:url value="/gangsters/${manager.boss.nodeId}"/>">-<c:out
									value="${manager.boss.name}" /> from <c:out
									value="${manager.createdAt}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${not empty gangster.subordinates}">
				<h3>Subordinates</h3>
				<ul>
					<c:forEach items="${gangster.subordinates}" var="subordinate">
						<li><a href="<c:url value="/gangsters/${subordinate.nodeId}"/>">-<c:out
									value="${subordinate.name}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${not empty gangster.managed}">
				<h3>Managed ones</h3>
				<ul>
					<c:forEach items="${gangster.managed}" var="manager">
						<li><a href="<c:url value="/gangsters/${manager.subordinate.nodeId}"/>">-<c:out
									value="${manager.subordinate.name}" /> from <c:out
									value="${manager.createdAt}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
			<hr/>
			
			<c:if test="${not empty bosses}">
				<h3>Direct Bosses</h3>
				<ul>
					<c:forEach items="${bosses}" var="boss">
						<li><a href="<c:url value="/gangsters/${boss.nodeId}"/>">-<c:out
									value="${boss.name}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
			
			<c:if test="${not empty subordinates}">
				<h3>Direct Subordinates</h3>
				<ul>
					<c:forEach items="${subordinates}" var="subordinated">
						<li><a href="<c:url value="/gangsters/${subordinated.nodeId}"/>">-<c:out
									value="${subordinated.name}" /></a><br /></li>
					</c:forEach>
				</ul>
			</c:if>
	</c:when>
	<c:otherwise>
		<div>No gangsters found matching [${id}]!</div>
	</c:otherwise>
</c:choose>