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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/errorPage.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>

	<!--上線時改位址-->

	<title>Bike Seeker</title>

</head>
<body>

<!--導覽列開頭-->
<nav class="navbar fixed-top navbar-expand-xl navbar-light">

	<a class="navbar-brand" href="<%= request.getContextPath()%>/front-end/index/Index.jsp"><img src="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png" alt="Bike Seeker" title="Bike Seeker"></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>


	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav ml-auto">
			<li class="nav-item">
				<a class="nav-link" href="#">揪團去</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">路線</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">日誌</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">直播</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">租車點查詢</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">客服中心</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">商城</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#"><img src="<%= request.getContextPath()%>/images/icons/Cart_Icon.png" width="30" height="30" alt="購物車" style="margin-top: 10px"></a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#"><img src="<%= request.getContextPath()%>/images/icons/Msg_Icon.png" width="30" height="20" alt="訊息" style="margin-top: 14px"></a>
			</li>

			<%--			<!--登入前的會員登錄-->--%>
			<c:if test="${empty sessionScope.account}">
				<button type="button" class="btn btn-outline-light" id="Login" data-toggle="modal" data-target="#LoginBox">會員登入</button>
			</c:if>
			<%--			<!--登入前的會員登錄-->--%>

		</ul>

	</div>
</nav>

<!--Container開頭-->
<div class="container-fluid clear-top">

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
	<div class="row align-items-center justify-content-end">

		<div class="col-6 vh-100 text-center" id="SuccessView">


			<h1>出了點問題.....稍後再回來好嗎?</h1><br>
			<br>

		</div>

		<div class="col-1"></div>

	</div>

</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>