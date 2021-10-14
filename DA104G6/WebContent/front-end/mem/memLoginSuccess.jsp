<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<%
	MemVO memVO = (MemVO) session.getAttribute("memVO");
	String preUrl = request.getHeader("Referer");
	session.setAttribute("preUrl",preUrl);
	response.setHeader("Refresh", "3; URL=" + preUrl);
%>


<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--網址縮圖-->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/memRegisterSuccess.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>

	<!--上線時改位址-->

	<title>Bike Seeker</title>

</head>
<body>

<!--導覽列開頭-->
								<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />
<!--Container開頭-->
<div class="container clear-top">


	<!--首頁包全部col-->
	<div class="row align-items-center justify-content-center">

		<div class="col-5 vh-100 text-center" id="SuccessView">

			<img src="<%= request.getContextPath()%>/images/LoginSuccess.gif" alt="登入成功">

			<h1>登入成功 ! 歡迎回來</h1><br>
			<h1><c:out value="${sessionScope.memVO.mem_name}" /></h1>
			<br>
			<h4><a href="<%= request.getContextPath()%>/front-end/index/Index.jsp">回首頁</a></h4>

		</div>

	</div>

	<!--Footer開始-->

							<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>
</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>