<%@ include file="/common/taglibs.jsp" %>

<page:applyDecorator name="default">

    <head>
        <title><fmt:message key="403.title"/></title>
        <meta name="heading" content="<fmt:message key='403.title'/>"/>
    </head>

    <p><fmt:message key="403.message">
        <fmt:param>
            <c:url value="/"/>
        </fmt:param>
    </fmt:message></p>

    <p style="text-align: center; margin-top: 20px">
        <img src="/images/access_denied.jpg"/></p>
</page:applyDecorator>