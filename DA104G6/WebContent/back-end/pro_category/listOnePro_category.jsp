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
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>

	<style>
  		table#table-1 {
			background-color: #CCCCFF;
    		border: 2px solid black;
    		text-align: center;
  		}
 		table#table-1 h4 {
    		color: red;
    		display: block;
   			margin-bottom: 1px;
  		}
 		 h4 {
    		color: blue;
   			display: inline;
 		 }
	</style>

	<style>
  		table {
			width: 800px;
			background-color: white;
			margin-top: 20px;
			margin-bottom: 5px;
  		}
  		table, th, td {
    		border: 3px solid #CCCCFF;
 		}
  		th, td {
    		padding: 5px;
    		text-align: center;
  		}
	</style>
	
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
		<div class="col-12" style="color:brown; font-size:50px;text-align: center;"><b>商品類別更新</b></div>

			<table>
				<tr>
					<th>產品類別編號</th>
					<th>產品類別名稱</th>
				</tr>
				<tr>
					<td><%=pro_categoryVO.getCategory_no()%></td>
					<td><%=pro_categoryVO.getCategory()%></td>
				</tr>
			</table>
				
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