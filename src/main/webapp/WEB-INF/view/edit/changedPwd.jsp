<%--
  Created by IntelliJ IDEA.
  User: bluem
  Date: 2025-01-06
  Time: 오후 5:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code = "change.pwd.title" /></title>
</head>
<body>
<p>
  <spring:message code="change.pwd.done" />
</p>
<p>
  <a href="<c:url value='/main'/>">
    [<spring:message code="go.main" />]
  </a>
</p>
</body>
</html>
