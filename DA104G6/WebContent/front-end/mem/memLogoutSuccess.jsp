<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<%
	response.setHeader("Refresh", "3; URL=" + request.getContextPath() + "/front-end/index/Index.jsp");
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

	<!--登入燈箱-->
	<div class="modal fade" id="LoginBox" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">登入會員</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body text-center">
					<form action="<%= request.getContextPath()%>/mem/memLogin.do" method="post">
						<label>帳號 :
							<input type="email" class="textBox" name="mem_email">
						</label><br><br>
						<label>密碼 :
							<input type="password" class="textBox" name="mem_psw">
						</label><br>
						<p><a href="<%= request.getContextPath()%>/front-end/mem/memResendPsw.jsp">忘記密碼</a></p><br>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">登入</button>
							<button type="button" onclick="location.href = '<%= request.getContextPath()%>/front-end/mem/addMem.jsp';" class="btn btn-secondary" data-dismiss="modal">註冊</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--登入燈箱-->


	<!--首頁包全部col-->
	<div class="row align-items-center justify-content-center">

		<div class="col-5 vh-100 text-center" id="SuccessView">

			<img src="<%= request.getContextPath()%>/images/Redirect.gif" alt="登出成功">

			<h1>登出成功 ! 期待您的歸來</h1><br>
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