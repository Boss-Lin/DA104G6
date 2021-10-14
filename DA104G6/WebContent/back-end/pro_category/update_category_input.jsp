<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pro_category.model.*"%>

<%
  Pro_categoryVO pro_categoryVO = (Pro_categoryVO) request.getAttribute("pro_categoryVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/update_category_input.css">
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
					<div class="display-3" id="Title">商品類別更新</div>
				</div>


				<div class="col-3 align-middle text-center" id="ManagerProfile">
					<form action="<%=request.getContextPath()%>/pro_category/pro_category.do" method="post" class="m-0" name="form1">

						<h3>類別編號 : <%=pro_categoryVO.getCategory_no()%></h3>

						<div class="form-group text-left mt-4">
							<label for="InputCategory">類別名稱</label>
							<input type="text" class="form-control" name="category" id="InputCategory" value="<%=pro_categoryVO.getCategory()%>">
						</div>


						<input type="hidden" name="action" value="update">
						<input type="hidden" name="category_no" value="<%=pro_categoryVO.getCategory_no()%>">
						<button class="btn btn-info" type="submit">送出修改</button>
					</form>

				</div>

				<div class="w-100"></div>

				<c:if test="${not empty errorMsgs}">
					<div class="col-3 align-middle text-left mt-5" id="error">
						<font style="color:red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>

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