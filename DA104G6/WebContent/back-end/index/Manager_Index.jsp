<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.manager.model.ManagerVO" %>

<%

ManagerVO managerVO = (ManagerVO) session.getAttribute("managerVO");

%>

<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--網址縮圖-->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">

	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Bike_Seeker_Manager.css">
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>
	<title>管理者首頁</title>
</head>
<body>

<div class="container-fluid h-100 text-center">

	<div class="col-12 h-100" id="ManagerFrontPage">

		<div class="row align-items-center justify-content-center">

					<div class="col-12">

						<div class="row align-items-center justify-content-around vh-100">

							<div class="col-lg-2" id="Logo">
								<img src="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">
							</div>

							<div class="w-100"></div>

							<!--員工管理-->
							<c:if test="${fn:contains(sessionScope.permissions , 'P02')}">
								<div class="col-lg-2">
									<div class="main">

										<div class="front">
											<img src="<%= request.getContextPath()%>/images/icons/Manager_Icon_128.png" class="img-fluid" />
											<h3>員工管理</h3>
										</div>

										<div class="back">
											<ul type="none">
												<a href="<%= request.getContextPath()%>/back-end/manager/managerListAll.jsp"><li>員工列表</li></a>
												<a href="<%= request.getContextPath()%>/back-end/manager/managerAdd.jsp"><li>新增員工帳號</li></a>
											</ul>
										</div>

									</div>
								</div>
							</c:if>

							<!--分類管理-->
							<c:if test="${fn:contains(sessionScope.permissions , 'P04') || fn:contains(sessionScope.permissions , 'P06') || fn:contains(sessionScope.permissions , 'P07')}">
							<div class="col-lg-2">
								<div class="main">

									<div class="front">
										<img src="<%= request.getContextPath()%>/images/icons/Category_Icon.png" class="img-fluid" />
										<h3>分類管理</h3>
									</div>

									<div class="back">
										<ul type="none">
											<c:if test="${fn:contains(sessionScope.permissions , 'P07')}">
												<a href="<%= request.getContextPath()%>/back-end/pro_category/listAllPro_category.jsp"><li>商品分類</li></a>
											</c:if>
											<c:if test="${fn:contains(sessionScope.permissions , 'P06')}">
												<a href="<%=request.getContextPath()%>/back-end/route_category/routeManage.jsp"><li>路線分類</li></a>
											</c:if>
										</ul>
									</div>

								</div>
							</div>
							</c:if>

							<!--訂單管理-->
							<c:if test="${fn:contains(sessionScope.permissions , 'P07')}">
								<div class="col-lg-2">
									<div class="main">

										<div class="front">
											<img src="<%= request.getContextPath()%>/images/icons/Order_Icon.png" class="img-fluid" />
											<h3>訂單管理</h3>
										</div>

										<div class="back">
											<ul type="none">
												<a href="<%= request.getContextPath()%>/back-end/order_master/listAllOrder_master.jsp"><li>訂單資訊</li></a>
												<a href="<%= request.getContextPath()%>/back-end/order_master/deliver.jsp"><li>出貨管理</li></a>
											</ul>
										</div>

									</div>
								</div>
							</c:if>

							<!--客服頁面-->
							<c:if test='${fn:contains(sessionScope.permissions , "P08")}'>
								<div class="col-lg-2">
									<div class="main">

										<div class="front">
											<img src="<%= request.getContextPath()%>/images/icons/Service_Icon.png" class="img-fluid" />
											<h3>客服頁面</h3>
										</div>

										<div class="back">
											<ul type="none">
												<a href="#"><li>E-Mail</li></a>
												<a href="#"><li>即時回覆</li></a>
											</ul>
										</div>

									</div>
								</div>
							</c:if>
							
							<div class="w-100"></div>

							<!--檢舉審核-->
							<c:if test="${fn:contains(sessionScope.permissions , 'P09')}">
								<div class="col-lg-2">
									<div class="main">

										<div class="front">
											<img src="<%= request.getContextPath()%>/images/icons/Report_Icon.png" class="img-fluid" />
											<h3>檢舉審核</h3>
										</div>

										<div class="back">
											<ul type="none">
												<c:if test="${fn:contains(sessionScope.permissions , 'P03')}">
													<a href="<%= request.getContextPath()%>/back-end/member_report/member_reportListAll.jsp"><li>會員檢舉</li></a>
												</c:if>
												<c:if test="${fn:contains(sessionScope.permissions , 'P05')}">
													<a href="<%= request.getContextPath()%>/back-end/blog_report/manager_blog_report.jsp"><li>日誌檢舉</li></a>
												</c:if>
												<c:if test="${fn:contains(sessionScope.permissions , 'P04')}">
													<a href="<%= request.getContextPath()%>/back-end/Group_Report/listAllGroupReport.jsp"><li>揪團檢舉</li></a>
												</c:if>
											</ul>
										</div>

									</div>
								</div>
							</c:if>

							<!--會員管理-->
							<c:if test="${fn:contains(sessionScope.permissions , 'P03')}">
								<div class="col-lg-2">
									<div class="main">

										<div class="front">
											<img src="<%= request.getContextPath()%>/images/icons/User_Icon.png" class="img-fluid" />
											<h3>會員管理</h3>
										</div>

										<div class="back">
											<a href="<%= request.getContextPath()%>/back-end/mem/memListAll.jsp"><img src="<%= request.getContextPath()%>/images/icons/MemberBack.jpg"></a>
										</div>

									</div>
								</div>
							</c:if>

							<!--商品管理-->
							<c:if test="${fn:contains(sessionScope.permissions , 'P07')}">
								<div class="col-lg-2">
									<div class="main">

										<div class="front">
											<img src="<%= request.getContextPath()%>/images/icons/Product_Icon.png" class="img-fluid" />
											<h3>商品管理</h3>
										</div>

										<div class="back">
											<a href="<%= request.getContextPath()%>/back-end/product/listAllProduct.jsp"><img src="<%= request.getContextPath()%>/images/icons/ProductBack.jpg"></a>
										</div>

									</div>

								</div>
							</c:if>

							<!--租車店管理-->
							<c:if test="${fn:contains(sessionScope.permissions , 'P10')}">
								<div class="col-lg-2">
									<div class="main">

										<div class="front">
											<img src="<%= request.getContextPath()%>/images/icons/BikeShop_Icon.png" class="img-fluid" />
											<h3>租車店管理</h3>
										</div>

										<div class="back">
											<a href="<%=request.getContextPath()%>/back-end/bike_rental/listAllbike_rental.jsp"><img src="<%= request.getContextPath()%>/images/icons/BikeShopBack.jpg"></a>
										</div>

									</div>
								</div>
							</c:if>





							<div class="w-100"></div>

							<!--管理者登出-->
							<div class="col-lg-2">
								<div class="main">

									<div class="front">
										<figure>
											<img src="<%= request.getContextPath()%>/manager/manager.do?action=showImg" class="img-fluid" id="ManagerPic" />
										</figure>
										<h3>歡迎回來，<c:out value="${sessionScope.managerVO.mg_name}" /></h3>
									</div>


										<div class="back" id="LogOut">
											<a href="<%= request.getContextPath()%>/manager/manager.do?action=Logout" class="mr-5">
												<img src="<%= request.getContextPath()%>/images/icons/LogOut_Icon.png" class="img-fluid mt-3" />
											<h3>登出</h3>
											</a>
											<form action="<%= request.getContextPath()%>/manager/manager.do" method="post" id="EditProfileForm">
												<input type="hidden" name="mg_no" value="${sessionScope.managerVO.mg_no}">
												<input type="hidden" name="action" value="getOne_For_Self_Update">
												<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
												<a id="EditProfile">
													<img src="<%= request.getContextPath()%>/images/icons/Update_Icon.png" class="img-fluid mt-3" />
													<h3>修改資料</h3>
												</a>
											</form>
										</div>


								</div>
							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

<script type="text/javascript">

	$(document).ready(function () {
	    $("#Logo").fadeIn(1500);
	    $(".main").fadeIn(1500);
    });

	//其他錯誤的訊息
	<c:if test="${not empty requestScope.IndexErrorMsgs}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "<c:out value="${requestScope.IndexErrorMsgs}" />"
	});
	<c:remove var="IndexErrorMsgs" scope="request" />
	</c:if>

	$("#EditProfile").click(function () {
		$("#EditProfileForm").submit();
	})

</script>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>