<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.order_master.controller.*"  %>
<%@ page import="com.order_detail.model.*" %>
<%@ page import="com.product.model.*"%>
<%@ page import="java.util.*" %>
<%@ page import="com.mem.model.*"%>

<% 
	Vector<Order_detailVO> buylist = (Vector<Order_detailVO>)session.getAttribute("shoppingcart");
	String amount = (String) request.getAttribute("amount");
	
	MemVO memVO = (MemVO) session.getAttribute("memVO");
%>

<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />

<!doctype html>
<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/Generic.css">
	<script src="<%=request.getContextPath() %>/js/jquery-slim-3.3.1.js"></script>
	<!--上線時改位址-->
	<script src="<%=request.getContextPath() %>/js/popper.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap-4.3.1.js"></script>
	
	<title>SHOP</title>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/shop.css">
</head>
<body>
<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />

		<div class="col-12"></div>
<!--信用卡結帳-->
	<div class="row">
		<div class="col-12" id="creditcard" style="padding-top: 10vh;">
		<jsp:include page="/front-end/product/creditcard.jsp" flush="true" />		
		</div>
	</div>	
<!-- 頁腳 -->
	<jsp:include page="/front-end/include/Footer.jsp" flush="true" />

<!-- nav.js -->
<script src="<%=request.getContextPath() %>/js/shop.js"></script>
<!-- nav.js -->
</body>