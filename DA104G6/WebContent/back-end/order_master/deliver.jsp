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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/deliver.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	
	<!-- sweetalert -->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@8"></script>
	
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

				<div class="col-10 align-middle text-center" id="Control-panel">

					<h4>未出貨訂單列表</h4>

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col">訂單編號</th>
							<th scope="col">訂單日期</th>
							<th scope="col">訂單會員</th>
							<th scope="col">訂單狀態</th>
							<th scope="col">訂單金額</th>
							<th scope="col"></th>

						</tr>
						</thead>

						<tbody>
						<c:forEach var="order_masterVO" items="${list}">
							<c:if test="${order_masterVO.status==1}">
								<tr>
									<th scope="row">${order_masterVO.order_id}</th>
									<td>${order_masterVO.order_date}</td>
									<td>${order_masterVO.mem_no}</td>
									<td>${applicationScope.orderMasterStatus[order_masterVO.status]}</td>
									<td>${order_masterVO.total_price}</td>
									<td>
										<form method="post" action="<%=request.getContextPath()%>/order_master/order_master.do" class="m-0">

											<input type="hidden" name="order_id"  value="${order_masterVO.order_id}">
											<input type="hidden" name="status"  value="2">
											<input type="hidden" name="action"	value="update">
											<input onclick="ABC()" type="submit" class="btn btn-info" value="確認出貨">
										</form>
									</td>
								</tr>
							</c:if>
						</c:forEach>
						</tbody>
					</table>

				</div>

				<div class="w-100">
					<hr style="border-color: #EAB965; border-width: 5px; width: 80em">
				</div>

				<div class="col-10 align-middle text-center" id="ProductListTable">

					<h4>已出貨訂單列表</h4>

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
						<c:forEach var="order_masterVO" items="${list}">
							<c:if test="${order_masterVO.status==2}">
								<tr>
									<th scope="row">${order_masterVO.order_id}</th>
									<td>${order_masterVO.order_date}</td>
									<td>${order_masterVO.mem_no}</td>
									<td>${applicationScope.orderMasterStatus[order_masterVO.status]}</td>
									<td>${order_masterVO.total_price}</td>
								</tr>
							</c:if>
						</c:forEach>
						</tbody>
					</table>

				</div>

			</div>

		</main>

	</div>
</div>




<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace();
    
   function ABC(){
    	swal.fire({
            type: 'success',
         title:'已確認出貨',
         showConfirmButton: false,
       });
    }
</script>
</body>
</html>