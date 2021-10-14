<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.bike_rental.model.*" %>

<%
	Bike_rentalService boke_rentalSvc = new Bike_rentalService();
	String bk_rt_no = request.getParameter("bk_rt_no");
	Bike_rentalVO bike_rentalVO = boke_rentalSvc.getOneBike_rental(bk_rt_no);
	pageContext.setAttribute("bike_rentalVO",bike_rentalVO);
%>
<html>
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!--網址縮圖-->
	<link rel="icon" href="<%=request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bike_rental_detail.css">
	<script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>


	<title>Bike Seeker</title>

</head>
<body>

<jsp:include page="/front-end/include/NavBar.jsp" />

<!--Container開頭-->
<div class="container-fluid h-100 clear-top">

	<!--首頁包全部col-->
	<div class="row align-items-center justify-content-around">

		<div class="col-12">

			<div class="row">

				<div class="col-2"></div>
				<div class="col-8 text-center p-2" id="BikeRentalProfile">

					<div class="row justify-content-around h-100 pt-2 pl-3 pr-3">

						<div class="col-6 align-self-center">
							<img src="<%=request.getContextPath() %>/bike_rental/bike_rental.do?action=showImg&bk_rt_no=${bike_rentalVO.bk_rt_no}">
						</div>


						<div class="col-6">

							<div class="row text-left" id="BikeRentalDetail">

								<div class="col-12">店名 : ${bike_rentalVO.bk_rt_name}</div>
								<div class="col-12">地址 : ${bike_rentalVO.bk_rt_address}</div>
								<div class="col-12">電話 : ${bike_rentalVO.bk_rt_phone}</div>
								<div class="col-12" style="overflow-y: auto; max-height: 227px;">${bike_rentalVO.bk_rt_spec}</div>

							</div>

						</div>

					</div>

				</div>

				<div class="col-2"></div>

			</div>
		</div>

		<div class="col-12">
			<div class="row justify-content-center">

				<div class="col-2"></div>

				<div class="col-8 text-center p-2 overflow-hidden" id="MapPage">

					<div id="map">
					
					
					</div>

				</div>

				<div class="col-2">

					<a href="<%= request.getContextPath()%>/front-end/bike_rental/bike_List.jsp"><button type="button" class="btn control" style="position: fixed; right: 0;bottom: 0"><img src="<%= request.getContextPath()%>/images/icons/Return_Icon.png"></button></a>

				</div>

			</div>
		</div>



	</div>

</div>

<script>	

    var map;

    function initMap() {
        var location = {lat: ${bike_rentalVO.lat}, lng: ${bike_rentalVO.lon}};
        map = new google.maps.Map(document.getElementById('map'), {
            center: location,
            zoom: 16
        });

        var marker = new google.maps.Marker ({
	        position: location,
	        map: map,
        });

    }
</script>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDim1wrKK_MVnCI_D6vwveKjR-9rv_cDts&callback=initMap" async defer></script>
<script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>