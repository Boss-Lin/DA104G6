<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");
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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Bike_Seeker_Index.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>


	<!--上線時改位址-->

	<title>Bike Seeker</title>

</head>
<body>

<!--導覽列開頭-->

	<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />

<!--Container開頭-->
<div class="container-fluid h-100 clear-top">
	<!--首頁包全部col-->
	<div class="row align-items-center justify-content-around">

		<!--揪團開頭-->
		<div class="col-12 vh-100" id="GroupsCol">

			<div class="row align-items-center h-100">

				<div class="col-12 col-sm-1 col-md-1 text-center">
				</div>
				<!--顯示在畫面上的部分-->
				<div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4 align-items-center" id="GroupInfo">
					<h2>騎單車固然有趣，<br>與好友同遊，樂趣加倍。</h2>
					<hr align="left">
					<p>快使用Bike Seeker的揪團功能，<br>在線上與超過十萬名用戶組團，探索全台各地單車騎乘路線。</p>
					<br>
					<form method="post"  action="<%=request.getContextPath()%>/front-end/Group/group.do">
						<button type="submit" class="btn btn-primary">揪團去</button>
						<input type="hidden" name="action" value="getAll_For_Display">
					</form>
				</div>
				<!--顯示在畫面上的部分-->

				<div class="col-12 col-sm-1 col-md-3 col-lg-5 col-xl-7 text-center">
				</div>

			</div>
		</div>

		<!--路線開頭-->
		<div class="col-12 vh-100" id="RoutesCol">

			<div class="row align-items-center h-100">

				<div class="col-12 col-sm-1 col-md-1 text-center">
				</div>

				<!--顯示在畫面上的部分-->
				<div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4 text-center" id="RouteInfo">
					<img class="img-fluid" src="<%= request.getContextPath()%>/images/mapPlanning.jpg">
					<h2>及時規劃、分享、收藏你喜愛的路線</h2><br>
					<button type="button" onclick="location.href = '<%=request.getContextPath()%>/front-end/route/listAllRoute.jsp';" class="btn btn-primary">尋找路線</button>
					<button type="button" onclick="location.href = '<%=request.getContextPath()%>/front-end/route/addRoute.jsp';" class="btn btn-primary">建立路線</button>
				</div>
				<!--顯示在畫面上的部分-->

				<div class="col-12 col-sm-1 col-md-3 col-lg-5 col-xl-7 text-center">
				</div>

			</div>
		</div>

		<!--日誌開頭-->
		<div class="col-12 vh-100" id="JournalsCol">

			<div class="row align-items-center h-100">

				<div class="col-12 col-sm-1 col-md-3 col-lg-5 col-xl-7 text-center">
				</div>

				<!--顯示在畫面上的部分-->
				<div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4 text-center" id="JournalsInfo">
					<img src="<%= request.getContextPath()%>/images/journalStock.jpg">
					<h2>大小心情，隨時抒發</h2>
					<hr>
					<p>心情抒發、珍奇美景、單車交流...<br>四處揮灑你的文采，在日誌裡絕對能找到屬於你的一片天地</p>
					<br>
					<button type="button" class="btn btn-primary" onclick="location.href = '<%=request.getContextPath()%>/front-end/blog/select_Blog.jsp';" class="btn btn-primary">撰寫日誌</button>
				</div>
				<!--顯示在畫面上的部分-->

				<div class="col-12 col-sm-1 col-md-1 text-center">
				</div>

			</div>
		</div>


		<!--其他連結開始-->
		<div class="col-12" id="LinksCol">

			<div class="row align-items-center justify-content-center h-100" id="LinksBackGround">

				<div class="card-deck">

						<div class="card bg-transparent text-white mb-0 text-center">
							<img src="<%= request.getContextPath()%>/images/bikeStock1-1.jpg" class="card-img">
							<a href="<%= request.getContextPath()%>/front-end/mem/addMem.jsp">
								<div class="card-img-overlay">
									<h2 class="card-title">加入會員</h2>
								</div>
							</a>
						</div>

						<div class="card bg-transparent text-white mb-0 text-center">
							<img src="<%= request.getContextPath()%>/images/bikeStock9-1.jpg" class="card-img" alt="...">
							<a href="<%=request.getContextPath() %>/front-end/product/shop.jsp">
								<div class="card-img-overlay">
									<h2 class="card-title">周邊商城</h2>
								</div>
							</a>
						</div>

						<div class="card bg-transparent text-white mb-0 text-center">
							<img src="../../images/bikeStock12.jpg" class="card-img" alt="...">
							<a href="<%=request.getContextPath()%>/front-end/bike_rental/bike_List.jsp">
								<div class="card-img-overlay">
									<h2 class="card-title">查詢租車點</h2>
								</div>
							</a>
						</div>

				</div>
			</div>
		</div>
		<!--Footer開始-->
		<div class="col-12 vh-25" id="FooterCol">
			<div class="row align-items-center" id="FooterBackground">
				<div class="col-12 col-md-12 col-lg-2">
				</div>
				<div class="col-12 col-md-12 col-lg-2" id="FooterContact">
					<h1>Contact.</h1>
					<br>
					<br>
					<p>人加仕旅森股份有限公司</p>
					<p>320 桃園市中壢區中大路300號</p>
					<p>wieduappclass@gmail.com</p>
					<p>03-425-7387</p>
				</div>

				<div class="col-12 col-md-12 col-lg-4 text-center">
					<a href="#"><img src="<%= request.getContextPath()%>/images/bikeSeekerICRolling.gif"></a>
				</div>
				<div class="col-12 col-md-12 col-lg-4 text-center">
					<img src="<%= request.getContextPath()%>/images/bikeRolling2.gif" width="350" height="250">
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/front-end/include/LoginAlerts.jsp" />
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>