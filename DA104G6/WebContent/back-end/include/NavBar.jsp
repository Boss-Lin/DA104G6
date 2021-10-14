<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html lang="en">
<head>
	<title>管理者頁面框架</title>
</head>
<body>

		<nav class="col-md-2 d-none d-md-block bg-light sidebar" id="SideBar">
			<div class="sidebar-sticky">

				<a class="navbar-brand " href="<%= request.getContextPath()%>/back-end/index/Manager_Index.jsp"><img src="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png" alt="Bike Seeker" title="Bike Seeker"></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<ul class="nav flex-column">

					<c:if test="${fn:contains(sessionScope.permissions , 'P02')}">
						<li class="nav-item dropright">
							<a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span data-feather="briefcase"></span>員工管理
							</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/manager/managerListAll.jsp">員工列表</a>
								<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/manager/managerAdd.jsp">新增員工</a>
							</div>
						</li>
					</c:if>

					<c:if test="${fn:contains(sessionScope.permissions , 'P07')}">
						<li class="nav-item dropright">
							<a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span data-feather="shopping-cart"></span>訂單管理
							</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/order_master/listAllOrder_master.jsp">訂單資訊</a>
								<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/order_master/deliver.jsp">出貨管理</a>
							</div>
						</li>
					</c:if>

					<c:if test="${fn:contains(sessionScope.permissions , 'P04') || fn:contains(sessionScope.permissions , 'P06') || fn:contains(sessionScope.permissions , 'P07')}">
						<li class="nav-item dropright">
							<a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span data-feather="layers"></span>分類管理
							</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<c:if test="${fn:contains(sessionScope.permissions , 'P07')}">
									<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/pro_category/listAllPro_category.jsp">商品分類</a>
								</c:if>
								<c:if test="${fn:contains(sessionScope.permissions , 'P06')}">
									<a class="dropdown-item" href="<%=request.getContextPath()%>/back-end/route_category/routeManage.jsp">路線分類</a>
								</c:if>
							</div>
						</li>
					</c:if>

					<c:if test='${fn:contains(sessionScope.permissions , "P08")}'>
						<li class="nav-item dropright">
							<a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span data-feather="mail"></span>客服頁面
							</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="#">E-Mail</a>
								<a class="dropdown-item" href="#">即時回覆</a>
							</div>
						</li>
					</c:if>

					<c:if test="${fn:contains(sessionScope.permissions , 'P09')}">
						<li class="nav-item dropright">
							<a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span data-feather="alert-circle"></span>檢舉審核
							</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<c:if test="${fn:contains(sessionScope.permissions , 'P03')}">
									<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/member_report/member_reportListAll.jsp">會員檢舉</a>
								</c:if>
								<c:if test="${fn:contains(sessionScope.permissions , 'P05')}">
									<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/blog_report/manager_blog_report.jsp">日誌檢舉</a>
								</c:if>
								<c:if test="${fn:contains(sessionScope.permissions , 'P04')}">
									<a class="dropdown-item" href="<%= request.getContextPath()%>/back-end/Group_Report/listAllGroupReport.jsp">揪團檢舉</a>
								</c:if>
							</div>
						</li>
					</c:if>
					
					<c:if test="${fn:contains(sessionScope.permissions , 'P10')}">
					
						<li class="nav-item dropright">
							<a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span data-feather="mail"></span>租車店管理
							</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="<%=request.getContextPath()%>/back-end/bike_rental/listAllbike_rental.jsp">租車店管理</a>
								<a class="dropdown-item" href="<%=request.getContextPath()%>/back-end/bike_style/listAllBike_style.jsp">車型管理</a>
							</div>
						</li>
					</c:if>
					

					<c:if test="${fn:contains(sessionScope.permissions , 'P07')}">
						<li class="nav-item">
							<a class="nav-link" href="<%= request.getContextPath()%>/back-end/product/listAllProduct.jsp">
								<span data-feather="shopping-bag"></span>商品管理
							</a>
						</li>
					</c:if>

					<c:if test="${fn:contains(sessionScope.permissions , 'P03')}">
						<li class="nav-item">
							<a class="nav-link" href="<%= request.getContextPath()%>/back-end/mem/memListAll.jsp">
								<span data-feather="users"></span>會員管理
							</a>
						</li>
					</c:if>

					<li class="nav-item">
						<form action="<%= request.getContextPath()%>/manager/manager.do" method="post" id="EditProfileForm">
							<input type="hidden" name="mg_no" value="${sessionScope.managerVO.mg_no}">
							<input type="hidden" name="action" value="getOne_For_Self_Update">
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
							<a id="EditProfile" class="nav-link">
								<span data-feather="edit"></span>修改個人資料
							</a>
						</form>
					</li>

				</ul>
			</div>
		</nav>
		
		<script>
		
		$("#EditProfile").click(function () {
			$("#EditProfileForm").submit();
		})
		
		</script>

</body>
</html>