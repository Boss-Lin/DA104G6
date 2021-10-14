<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.order_master.model.*"%>

<%
    Order_masterService order_masterSvc = new Order_masterService();
    List<Order_masterVO> list = order_masterSvc.getAll();
    pageContext.setAttribute("list",list);
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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/listAllOrder_master.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>

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
					<div class="display-3" id="Title">訂單列表</div>
				</div>

				<div class="col-3 text-center" id="Control-panel">

					<div class="row justify-content-center">


					</div>

				</div>

				<div class="w-100">
					<hr style="border-color: #EAB965; border-width: 5px; width: 80em">
				</div>

				<div class="col-10 align-middle" id="ProductListTable">

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col">訂單編號</th>
							<th scope="col">訂單日期</th>
							<th scope="col">訂單會員</th>
							<th scope="col">訂單狀態</th>
							<th scope="col">訂單金額</th>
						</tr>
						</thead>

						<tbody>
						<%@ include file="page1.file" %>
						<c:forEach var="order_masterVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<tr>
								<th scope="row">${order_masterVO.order_id}</th>
								<td>${order_masterVO.order_date}</td>
								<td>${order_masterVO.mem_no}</td>
								<td>${applicationScope.orderMasterStatus[order_masterVO.status]}</td>
								<td>${order_masterVO.total_price}</td>
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
	//搜尋結果跳頁下拉選單
	$("#SelectPage").change(function () {
		let pageIndex = $(this).children("option:selected").val();
		location.href = location.pathname +"?whichPage=" + pageIndex;
	});

</script>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>