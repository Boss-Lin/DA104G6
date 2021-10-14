<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
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
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-around">

		<jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
			<div class="row">
		<div class="col-2"></div>
		<div class="col-6">
		<div class="col-12" style="color:brown; font-size:50px;text-align: center;"><b>商品新增</b></div>
		
		<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/product/product.do" enctype="multipart/form-data" name="form1">
		<div class="row mt-5">
		
			<div class="col-6">產品編號:</div>
			<div class="col-6"><%=productVO.getPro_no()%></div>
			
			<div class="col-6">產品名稱:</div>
			<div class="col-6"><%=productVO.getProduct()%></div>
			
			<div class="col-6">價錢:</div>
			<div class="col-6"><%=productVO.getPrice()%></div>
			
			<div class="col-6">圖片:</div>
			<div class="col-6"><img src="<%=request.getContextPath() %>/product/product.do?getImg=${productVO.pro_no}" style="height:100px"></div>
			
			<div class="col-6">產品資訊:</div>
			<div class="col-6"><%=productVO.getMessage()%></textarea></div>
			
			<div class="col-6">狀態:</div>
			<div class="col-6">${applicationScope.productStatus[productVO.status]}</div>
			
			<div class="col-6">分數:</div>
			<div class="col-6"><%=productVO.getScore()%></div>
			
			<div class="col-6">評分人數:</div>
			<div class="col-6"><%=productVO.getScore_peo()%></div>
			
			<jsp:useBean id="proSvc" scope="page" class="com.pro_category.model.Pro_categoryService" />
			<div class="col-6">類別:</div>
			<div class="col-6"><%=productVO.getCategory_no()%></div>
			
			
		
		</div>				
		<div class="col-4"></div>
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