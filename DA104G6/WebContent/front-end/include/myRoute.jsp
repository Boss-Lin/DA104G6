<%@page import="com.mem.model.MemVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.route.model.RouteService"%>
<%@ page import="com.route.model.RouteVO"%>
<%@ page import="java.util.List"%>
<%@ page import="com.route_collection.model.Route_CollectionService" %>
<%@ page import="com.route_collection.model.Route_CollectionVO" %>
<%
	RouteService routeSvc = new RouteService();
	MemVO memVO = (MemVO) session.getAttribute("memVO");
	String mem_no = memVO.getMem_no();
	List<RouteVO> routeVO = routeSvc.getOneMem(mem_no);
	request.setAttribute("routeVO" , routeVO);

	Route_CollectionService route_collectionSvc = new Route_CollectionService();
	List<Route_CollectionVO> route_collectionVO = route_collectionSvc.getMem_noRoute_Collection(mem_no);
	request.setAttribute("route_collectionVO" , route_collectionVO);
%>

<%--<jsp:useBean id="routeSvc" scope="page" class="com.route.model.RouteService" />--%>

<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--自訂CSS-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/Generic.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/my_route.css">
<script src="https://kit.fontawesome.com/e218ab780d.js"
	crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<title>Bike Seeker我的路線</title>

</head>

<body style="font-family: 'Noto Bold', sans-serif">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<section>
		<h3 id="myroute-title" style="font-weight:bold">我的路線</h3>
		<div class="tbl-header">
			<table>
				<thead>
					<tr>
						<th>路線名稱</th>
						<th>路線總距離(km)</th>
						<th>路線難度</th>
						<th>路線起點</th>
						<th>路線終點</th>
						<th>路線建立時間</th>
						<th></th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="tbl-content">
			<table>
				<tbody>
				
					<c:forEach var="routeVO" items="${requestScope.routeVO}">
					
						<tr>
							<td>${routeVO.route_name}</td>
							<td>${routeVO.route_length}</td>
							<td>${applicationScope.routeLevel[routeVO.difficulty]}</td>
							<td>${routeVO.route_start}</td>
							<td>${routeVO.route_end}</td>
							<td>${routeVO.route_date}</td>
							<td>
								<form action="<%=request.getContextPath()%>/route/route.do" class="m-0 mb-1">
									<input type="hidden" name="route_no" value="${routeVO.route_no}"> 
									<button class="btn btn-info" type="submit" name="action" value="getOne_For_Update">修改</button>
								</form>

									<input type="hidden" name="route_no" value="${routeVO.route_no}">
									<input type="hidden" name="status" value="${routeVO.status}">
									<button class="btn btn-info delRoute" type="submit" name="action" value="deleteRoute">刪除</button>
							</td>
						</tr>
						
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<section>
		<h3 style="font-weight:bold">已收藏路線</h3>
		<div class="tbl-header">
			<table>
				<thead>
					<tr>
						<th>路線名稱</th>
						<th>路線總距離(km)</th>
						<th>路線難度</th>
						<th>路線起點</th>
						<th>路線終點</th>
						<th>路線建立時間</th>
						<th></th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="tbl-content">
			<table id="myroute">
				<tbody>
					<c:forEach var="route_collectionVO" items="${requestScope.route_collectionVO}">
						<tr>
							<td>${routeSvc.getOneRoute(route_collectionVO.route_no).route_name}</td>
							<td>${routeSvc.getOneRoute(route_collectionVO.route_no).route_length}</td>
							<td>${applicationScope.routeLevel.get(routeSvc.getOneRoute(route_collectionVO.route_no).difficulty)}</td>
							<td>${routeSvc.getOneRoute(route_collectionVO.route_no).route_start}</td>
							<td>${routeSvc.getOneRoute(route_collectionVO.route_no).route_end}</td>
							<td>${routeSvc.getOneRoute(route_collectionVO.route_no).route_date}</td>
							
							<td>
									<input type="hidden" name="mem_no" value="${route_collectionVO.mem_no}">
									<input type="hidden" name="route_no" value="${route_collectionVO.route_no}">
									<button class="btn btn-info delCollection" name="action">取消收藏</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	
	<script>
//刪除路線ajax
	$(".delRoute").click(function(e){
    
			$.ajax({
				type:"POST",
				url: "<%=request.getContextPath()%>/route/route.do",
				data:{					
					route_no:$(e.target).prev().prev().val(),
					action: "deleteRoute"
				},
				success: function(data){
					$(e.target).parent().parent().remove();
				}
				
			})
		});	
	
//取消收藏ajax
    $(".delCollection").click(function(e){
    
			$.ajax({
				type:"POST",
				url: "<%=request.getContextPath()%>/route_collection/route_collection.do",
				data:{
					mem_no:$(e.target).prev().prev().val(),
					route_no:$(e.target).prev().val(),
					action: "deleteCollection"
				},
				success: function(data){
					$(e.target).parent().parent().remove();
				}
				
			})
		});
	
	</script>
	
</body>
</html>