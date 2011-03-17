<%@ include file="/common/taglibs.jsp"%>

<!-- 
<c:if test="${pageContext.request.locale.language ne 'en'}">
    <div id="switchLocale"><a href="<c:url value='/?locale=en'/>"><fmt:message key="webapp.name" /> in English</a></div>
</c:if>
<c:if test="${pageContext.request.locale.language ne 'ru'}">
    <div id="switchLocale"><a href="<c:url value='/?locale=ru'/>"><fmt:message key="webapp.name" /> in Russian</a></div>
</c:if>
 -->

<div id="branding">
<h1><a href="<c:url value='/'/>"><fmt:message key="webapp.name" /></a></h1>
</div>
<hr />