<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.route_category.model.*"%>
<%@page import="java.util.List"%>
<% 
	Route_CategoryService routecateSvc = new Route_CategoryService();
	List<Route_CategoryVO> list = routecateSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<% 
	Route_CategoryVO route_categoryVO = (Route_CategoryVO)request.getAttribute("route_categoryVO");
	request.setAttribute("route_categoryVO", route_categoryVO);
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
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<title>Bike Seeker Management</title>
</head>
<body style="font-family: 'Noto Bold', sans-serif">


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-around">

		<jsp:include page="/back-end/include/NavBar.jsp" flush="true" />



		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

			<div class="row align-items-center justify-content-center">

				<div class="col-12 text-center">
					<div class="display-3" id="Title">路線分類</div>
				</div>

				<div class="col-10 text-center mb-4" id="Control-panel">

					<div class="row justify-content-center">

							<%if (request.getAttribute("route_categoryVO")!=null){%>
								<div class="col-6 p-3" style="background-color: rgba(0, 0, 0, 0.1);border-radius: 30px;">
									<jsp:include page="/back-end/include/update.jsp"/>
								</div>
							<%} %>

					</div>

				</div>

				<div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

				<div class="col-10 align-middle" id="ManagerListTable">

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col">路線類別編號</th>
							<th scope="col">路線類別名稱</th>
							<th scope="col">路線類別介紹</th>
							<th scope="col"></th>
						</tr>
						</thead>

						<tbody>
						<c:forEach var="route_categoryVO" items="${list}">
							<tr>
								<th scope="row">${route_categoryVO.route_cate_no}</th>
								<td>${route_categoryVO.route_cate_name}</td>
								<td>${route_categoryVO.route_cate_info}</td>
								<td>
									<form action="<%=request.getContextPath()%>/route_category/route_category.do"
										  method="post" class="m-0">
										<input type="hidden" name="route_cate_no"
											   value="${route_categoryVO.route_cate_no}">
										<input type="hidden" name="action" value="getOne_For_Update">
										<button type="submit" class="btn btn-info">修改</button>
									</form>
								</td>
							</tr>
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
	feather.replace()
</script>

</body>
</html>