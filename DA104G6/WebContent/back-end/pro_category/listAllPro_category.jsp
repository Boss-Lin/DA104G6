<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro_category.model.*"%>

<%
    Pro_categoryService pro_categorySvc = new Pro_categoryService();
    List<Pro_categoryVO> list = pro_categorySvc.getAll();
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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/listAllPro_category.css">
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
					<div class="display-3" id="Title">商品類別管理</div>
				</div>

				<div class="col-10 text-center mb-4" id="Control-panel">

					<div class="row justify-content-center">

						<div class="col-6">


							<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/pro_category/pro_category.do" name="form1">
								<div class="form-group text-left mb-0">

									<div class="row">

										<div class="col-3 text-center my-auto">
											<h5 class="m-0">新增類別</h5>
										</div>

										<div class="col-6">
											<input type="text" class="form-control" name="category" id="InputCategory" placeholder="請輸入類別名稱">
										</div>

										<div class="col-3">
											<input type="hidden" name="action" value="insert">
											<input type="submit" class="btn btn-outline-info" value="送出新增">
										</div>
									</div>
								</div>
							</FORM>

						</div>

						<div class="w-100"></div>

						<div class="col-6">

							<c:if test="${not empty errorMsgs}">
								<font style="color:red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color:red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>

						</div>

					</div>

				</div>

				<div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

				<div class="col-10 align-middle" id="ManagerListTable">

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col">類別編號</th>
							<th scope="col">類別名稱</th>
							<th scope="col"></th>

						</tr>
						</thead>

						<tbody>
						<c:forEach var="pro_categoryVO" items="${list}">
							<tr>
								<th scope="row">${pro_categoryVO.category_no}</th>
								<td>${pro_categoryVO.category}</td>
								<td>
									<form action="<%= request.getContextPath()%>/pro_category/pro_category.do" method="post" class="m-0">
										<input type="hidden" name="category_no"  value="${pro_categoryVO.category_no}">
										<input type="hidden" name="action"	value="getOne_For_Update">
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