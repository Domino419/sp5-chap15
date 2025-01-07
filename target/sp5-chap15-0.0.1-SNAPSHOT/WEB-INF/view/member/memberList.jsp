<%--
  Created by IntelliJ IDEA.
  User: bluem
  Date: 2025-01-07
  Time: 오후 2:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
  <head>
    <title>회원 조회</title>
  </head>

  <body>
  <form:form modelAttribute="cmd">
      <p>
          <label>from:<form:input path="from" /></label>
          <form:errors path="from" />
          ~
          <label>to: <form:input path="to"/></label>
          <form:errors path="to" />
          <input type="submit" value="조회">
      </p>
  </form:form>


  <script type="text/javascript">
      // test 할 때 타이핑 작업을 피하기 위해서 날짜 자동 설정

      window.onload = function() {
          var today = new Date();
          var oneMonthAgo = new Date();
          oneMonthAgo.setMonth(today.getMonth() - 1);

          // 날짜를 YYYYMMDDHH 형식으로 변환
          var formatDate = function(date) {
              var year = date.getFullYear();
              var month = ('0' + (date.getMonth() + 1)).slice(-2);
              var day = ('0' + date.getDate()).slice(-2);
              var hour = ('0' + date.getHours()).slice(-2);
              return year + month + day + hour;
          };

          // from, to에 날짜 값 설정
          document.getElementById('from').value = formatDate(oneMonthAgo);
          document.getElementById('to').value = formatDate(today);
      }
  </script>


  <c:if test="${! empty members}">
      <table>
          <tr>
              <th> 아이디 </th><th> 이메일</th>
              <th> 이름 </th><th> 가입일</th>
          </tr>
          <c:forEach var="mem" items="${members}">
              <tr>
                  <td>${mem.id}</td>
                  <td><a href="<c:url value="/members/${mem.id}"/>">
                          ${mem.email}</a></td>
                  <td>${mem.name}</td>
                  <td><tf:formatDateTime value="${mem.registerDateTime }"
                                         pattern="yyyy-MM-dd" /></td>
              </tr>
          </c:forEach>
      </table>
  </c:if>
  </body>
</html>
