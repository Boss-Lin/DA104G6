<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title></title>
</head>
<body>

<script>
    //登入失敗的訊息
    <c:if test="${not empty sessionScope.accountError}">
    swal.fire({
        icon: 'error',
        title: '唉唷',
        text: "${sessionScope.accountError}"
    });
    <c:remove var="accountError" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.pswError}">
    swal.fire({
        icon: 'error',
        title: '唉唷',
        text: "${sessionScope.pswError}"
    });
    <c:remove var="pswError" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.statusError}">
    swal.fire({
        icon: 'warning',
        title: '再見',
        text: '${sessionScope.statusError}'
    });
    <c:remove var="statusError" scope="session" />
    </c:if>

    //請求使用者登入的訊息
    <c:if test="${not empty sessionScope.needLogin}">
    swal.fire({
        icon: 'warning',
        title: '你在嗎?',
        text: "請你先登入喔"
    });
    <c:remove var="needLogin" scope="session" />
    </c:if>
</script>

</body>
</html>
