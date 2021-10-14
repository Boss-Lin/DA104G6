<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.manager.model.ManagerVO" %>

<%

	ManagerVO managerVO = (ManagerVO) request.getAttribute("managerVO");

%>

<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--網址縮圖-->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/managerLogin.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>

	<title>Bike Seeker Manager</title>
</head>
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-between">

		<div class="col-4 vh-100"></div>
		<div class="col-3 text-center" id="ManagerLogin">

			<img src="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">

			<form action="<%= request.getContextPath() %>/manager/managerLogin.do" method="post" class="text-left">

				<div class="form-group">

					<label for="InputAccount">帳號</label>

							<input type="text" class="form-control" name="mg_account" id="InputAccount" placeholder="請輸入帳號">
							<span style="color: #cc2357; font-size: 18px">
								<c:if test="${not empty requestScope.accountError}">
									<c:out value="${requestScope.accountError}" />
								</c:if>
							</span>

				</div>

				<div class="form-group">
					<label for="InputPassword">密碼</label>
					<input type="password" class="form-control" name="mg_password" id="InputPassword" placeholder="請輸入密碼" maxlength="8">
					<span style="color: #cc2357; font-size: 18px">
						<c:if test="${not empty requestScope.pswError}">
							<c:out value="${requestScope.pswError}" />
						</c:if>
					</span>

				</div>


				<div class="row justify-content-around pt-4">
					<div class="col-9 p-0"></div>

					<div class="col-3 text-center p-0">
						<input type="submit" class="btn btn-primary" value="登入">
					</div>

				</div>
			</form>

		</div>
		<div class="col-4 vh-100"></div>

	</div>

</div>


<div class="waveWrapper waveAnimation">
	<div class="waveWrapperInner bgTop">
		<div class="wave waveTop" style="background-image: url('http://front-end-noobs.com/jecko/img/wave-top.png')"></div>
	</div>
	<div class="waveWrapperInner bgMiddle">
		<div class="wave waveMiddle" style="background-image: url('http://front-end-noobs.com/jecko/img/wave-mid.png')"></div>
	</div>
	<div class="waveWrapperInner bgBottom">
		<div class="wave waveBottom" style="background-image: url('http://front-end-noobs.com/jecko/img/wave-bot.png')"></div>
	</div>
</div>

<script>

	//停權訊息

	<c:if test="${not empty requestScope.statusError}">
	swal.fire({
		icon: 'warning',
		title: '再見',
		text: '${requestScope.statusError}'
	});
	<c:remove var="statusError" scope="request" />
	</c:if>


</script>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>