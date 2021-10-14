<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.bike_rental_style.model.*"%>
<%@ page import="java.util.*"%>

<%
	Bike_rental_styleVO bike_rental_styleVO = (Bike_rental_styleVO) request.getAttribute("bike_List");
	List<Bike_rental_styleVO> list = (List<Bike_rental_styleVO>) request.getAttribute("bike_style");

	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="rentalSvc" scope="page"
	class="com.bike_rental.model.Bike_rentalService" />
<jsp:useBean id="styleSvc" scope="page"
	class="com.bike_rental.model.Bike_rentalService" />

<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!--網址縮圖-->
<link rel="icon"
	href="<%=request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<!--自訂CSS-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/Generic.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bike_rental_search.css">
<script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>


<!--上線時改位址-->

<title>Bike Seeker</title>

</head>
<body>

	<jsp:include page="/front-end/include/NavBar.jsp" />

	<!--Container開頭-->
	<div class="container-fluid h-100 clear-top">

		<!--首頁包全部col-->
		<div class="row align-items-center justify-content-around">

			<div class="col-12">

				<div class="row justify-content-around">


					<div class="col-6 text-center p-2" id="SearchPlace">

						<form method="post"
							action="<%=request.getContextPath()%>/bike_rental_style/bike_rental_style.do">
							<div class="row justify-content-center">

								<div class="col-3">
									請選擇租車店
									<jsp:useBean id="bikeRentalSvc" scope="page"
										class="com.bike_rental.model.Bike_rentalService" />

									<select class="form-control" name="bike_rental">

										<option value="all">請選擇租車店</option>
										<c:forEach var="bikeRentalVO" items="${bikeRentalSvc.all}">
											<option value="${bikeRentalVO.bk_rt_no}">${bikeRentalVO.bk_rt_name}</option>
										</c:forEach>

									</select>
								</div>

								<div class="col-3">
									請選擇車型
									<jsp:useBean id="bikeStyleSvc" scope="page"
										class="com.bike_style.model.Bike_styleService" />


									<select class="form-control" name="bike_style">

										<option value="all">我全都要</option>
										<c:forEach var="bikeStyleVO"
											items="${bikeStyleSvc.allBike_style}">
											<option value="${bikeStyleVO.bike_sty_no}">${bikeStyleVO.bike_sty_name}</option>
										</c:forEach>

									</select>
								</div>

							</div>
							<br> <input type="hidden" name="action" value="search">
							<button class="btn btn-info my-2 my-sm-0" type="submit">搜尋</button>

						</form>

					</div>

					<div class="col-5 text-center" id="GoogleTitle">

						<h1 class="display-4 my-auto">都不滿意?試試谷哥大神&#8595;</h1>

					</div>

				</div>
			</div>

			<div class="col-12">
				<div class="row justify-content-around">

					<div class="col-6 text-center p-2" id="ResultPage">

						<div class="row align-items-center">

							<div class="col-12 text-center">搜尋結果 :</div>

							<div class="col-12 mb-2">
								<hr style="border-width: 5px; border-color: #EAB965">
							</div>

						</div>

						<div class="row justify-content-center align-items-center"
							id="ResultContainer">

							<c:forEach var="bystyleVO" items="${pageScope.list}">

								<div class="col-3 mb-5">
									<a
										href="<%=request.getContextPath()%>/front-end/bike_rental/bike_rental_detail.jsp?bk_rt_no=${bystyleVO.bk_rt_no}"><img
										src="<%=request.getContextPath()%>/bike_rental/bike_rental.do?action=showImg&bk_rt_no=${bystyleVO.bk_rt_no}"></a>
								</div>
								<div class="col-3">
									<a
										href="<%=request.getContextPath()%>/front-end/bike_rental/bike_rental_detail.jsp?bk_rt_no=${bystyleVO.bk_rt_no}">${bikeRentalSvc.getOneBike_rental(bystyleVO.bk_rt_no).bk_rt_name }</a>
								</div>
								<div class="col-2">${bikeStyleSvc.getOneBike_style(bystyleVO.bk_sty_no).bike_sty_name}</div>
								<div class="col-4 text-nowrap">${rentalSvc.getOneBike_rental(bystyleVO.bk_rt_no).bk_rt_address}</div>
							</c:forEach>

						</div>

					</div>

					<div class="col-5 p-2" id="SearchWithGoogle">

						<jsp:include page="/front-end/include/rentalData.jsp" flush="true" />

					</div>

				</div>
			</div>


		</div>

	</div>



	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
	<script src="<%=request.getContextPath()%>/js/popper.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>