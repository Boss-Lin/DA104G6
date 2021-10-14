<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.manager.model.ManagerVO" %>

<%

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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/memAuthentication.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>

	<title>Bike Seeker</title>
</head>
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-between">

		<div class="col-4 vh-100"></div>
		<div class="col-3 text-center" id="MemAuth">

			<img src="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

			<form action="<%= request.getContextPath() %>/mem/mem.do" method="post" class="text-left">

				<div class="form-group">

					<label for="InputAuth">驗證碼</label>

							<input type="text" class="form-control" name="mem_auth" id="InputAuth" placeholder="請輸入驗證碼">
							<span style="color: #cc2357; font-size: 18px">
								<c:if test="${not empty requestScope.authError}">
									<c:out value="${requestScope.authError}" />
								</c:if>
							</span>

				</div>


				<div class="row justify-content-around pt-4">

					<div class="col-9 p-0"></div>

					<div class="col-3 text-center p-0">
						<input type="hidden" name="mem_no" value="${sessionScope.memVO.mem_no}">
						<input type="hidden" name="action" value="memAuth">
						<input type="submit" class="btn btn-primary" value="驗證">
					</div>

				</div>
			</form>

			<div class="row justify-content-around">
				<div class="col-8 p-0"></div>

				<div class="col-4 text-center p-0 pr-3">
					<form action="<%= request.getContextPath() %>/mem/mem.do" method="post">
						<input type="hidden" name="mem_no" value="${sessionScope.memVO.mem_no}">
						<input type="hidden" name="action" value="resendAuthMail">
						<button type="submit" class="btn btn-info">重發驗證信</button>
					</form>
				</div>
			</div>


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

	<c:if test="${not empty requestScope.authResend}">

	swal.fire({
		icon: 'success',
		title: 'OK',
		text: '驗證碼已重新發送，請至信箱收信'
	});
	<c:remove var="authResend" scope="request" />

	</c:if>


</script>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>