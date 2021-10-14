<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.route_category.model.*"%>

<% 
	Route_CategoryVO route_categoryVO = (Route_CategoryVO)request.getAttribute("route_categoryVO");
	request.getAttribute("route_categoryVO");
%>

<html>
<head>
<title>路線類別資料修改 - updateRouteCategory.jsp</title>

</head>
<body>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/route_category/route_category.do" class="m-0">
<table style="margin-left: 175px">
	<tr>
		<td>
			<div class="form-group text-left">
				<label for="InputNo">路線類別編號</label>
				<input type="text" class="form-control" id="InputNo" readonly value="${route_categoryVO.route_cate_no}">
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="form-group text-left">
				<label for="InputName">路線類別名稱</label>
				<input type="text" class="form-control" name="route_cate_name" id="InputName" value="${route_categoryVO.route_cate_name}">
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="form-group text-left">
				<label for="InputInfo">路線類別介紹</label>
				<input type="text" class="form-control" name="route_cate_info" id="InputInfo" value="${route_categoryVO.route_cate_info}">
			</div>
		</td>
	</tr>
	
</table>
<br>
<input type="hidden" name="action" value="updateRoute">
<input type="hidden" name="route_cate_no" value="<%=route_categoryVO.getRoute_cate_no()%>">
<input type="submit" class="btn btn-info" value="送出修改">
</FORM>

</body>

</html>