<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.bike_rental.model.*"%>
<jsp:useBean id="bikeRentalStyleSvc" class="com.bike_rental_style.model.Bike_rental_styleService" />
<jsp:useBean id="bikeStyleSvc" class="com.bike_style.model.Bike_styleService" />

<%
	Bike_rentalVO bike_RentalVO = (Bike_rentalVO) session.getAttribute("bike_rentalVO");

	Bike_rentalService rentalSvc= new Bike_rentalService();
	List<Bike_rentalVO> rentalList = rentalSvc.getAll();
	pageContext.setAttribute("list",rentalList);

%>

<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic_Manager.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/listAllBike_rental.css">
	<script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>
	<title>Bike Seeker Management</title>

</head>
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-around">

		<jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

			<div class="row align-items-center justify-content-center">

				<div class="col-12 text-center">
					<div class="display-3" id="Title">租車店列表</div>
				</div>

				<div class="col-3 text-center mb-4" id="Control-panel">

					<div class="row justify-content-center">

						<div class="col-5">
							<button class="btn btn-outline-info" onclick="location.href='<%=request.getContextPath()%>/back-end/bike_rental/addbike_rental.jsp';">新增租車店</button>
						</div>

						<c:if test="${not empty requestScope.errorMsg}">
							<div class="col-5">
								${requestScope.errorMsg}
							</div>
						</c:if>
					</div>

				</div>

				<div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

				<div class="col-10 align-middle" id="ManagerListTable">

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col" class="text-nowrap">編號</th>
							<th scope="col" class="text-nowrap">照片</th>
							<th scope="col" class="text-nowrap">名稱</th>
							<th scope="col" class="text-nowrap">地址</th>
							<th scope="col" class="text-nowrap">電話</th>
							<th scope="col" class="text-nowrap">說明</th>
							<th scope="col" class="text-nowrap"></th>
						</tr>
						</thead>

						<tbody>
						<%@ include file = "page1.file" %>
						<c:forEach var="bike_Rental" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<tr>
								<th scope="row">${bike_Rental.bk_rt_no}</th>
								<td><img src="<%= request.getContextPath()%>/bike_rental/bike_rental.do?action=showImg&bk_rt_no=${bike_Rental.bk_rt_no}"></td>
								<td>${bike_Rental.bk_rt_name}</td>
								<td>${bike_Rental.bk_rt_address}</td>
								<td>${bike_Rental.bk_rt_phone}</td>
								<td style="overflow-y: auto;width: 350px;height: 160px;display: block;word-break: break-all">${bike_Rental.bk_rt_spec}</td>
								<td>
									<form method="post" action="<%=request.getContextPath()%>/bike_rental/bike_rental.do" class="m-0 mb-4">
										<button type="submit" class="btn btn-info">修改</button>
										<input type="hidden" name="bk_rt_no"  value="${bike_Rental.bk_rt_no}">
										<input type="hidden" name="action"	value="Ask_Update"></form>

									<form method="post" action="<%=request.getContextPath()%>/bike_rental/bike_rental.do" class="m-0">
										<button type="submit" class="btn btn-danger">刪除</button>
										<input type="hidden" name="bk_rt_no"  value="${bike_Rental.bk_rt_no}">
										<input type="hidden" name="action" value="delete-Rental"></form>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<%@ include file="page2.file" %>

				</div>

			</div>

		</main>
	</div>
</div>

<script>

	

	//修改成功的訊息

	<c:if test="${not empty requestScope.updateSuccess}">
	swal.fire({
		icon: 'success',
		title: '怎麼那麼棒',
		text: '${requestScope.updateSuccess}'
	});
	<c:remove var="updateSuccess" scope="request" />
	</c:if>
	
	//新增成功的訊息

	<c:if test="${not empty requestScope.assSuccess}">
	swal.fire({
		icon: 'success',
		title: '怎麼那麼棒',
		text: '${requestScope.updateSuccess}'
	});
	<c:remove var="addSuccess" scope="request" />
	</c:if>


</script>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
	feather.replace()
</script>
</body>
</html>