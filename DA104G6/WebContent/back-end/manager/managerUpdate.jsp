<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.permission.model.PermissionService" %>
<%@ page import="com.permission.model.PermissionVO" %>
<%@ page import="java.util.List" %>

<%
//	ManagerVO managerVO = new ManagerVO();
	PermissionService permiSvc = new PermissionService();
	List<PermissionVO> list = permiSvc.getAll();
	request.setAttribute("list" , list);
%>

<jsp:useBean id="permitOwnerSvc" scope="request" class="com.permission_owner.model.Permission_ownerService" />


<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--網址縮圖-->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
	<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/managerUpdate.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>
	<title>Bike Seeker Manager</title>
</head>
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-around">

		<jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
			<form action="<%= request.getContextPath()%>/manager/manager.do" method="post" enctype="multipart/form-data" class="m-0">

				<div class="row align-items-center justify-content-center">

					<div class="col-12 text-center">
						<div class="display-3" id="Title">員工資料修改</div>
					</div>


					<div class="col-3 align-middle text-center" id="ManagerProfile">


							<img src="<%= request.getContextPath()%>/manager/manager.do?action=showOthersImg&mg_no=${requestScope.managerVO.mg_no}" alt="管理員頭貼預覽" id="ManagerProfilePic" class="mx-auto">

							<div class="form-group text-left">
								<label for="InputName">姓名</label>
								<input type="text" class="form-control" name="mg_name" id="InputName" readonly value="${requestScope.managerVO.mg_name}">
							</div>

							<div class="form-group text-left">
								<label for="InputTitle">職稱</label>
								<input type="text" class="form-control" name="mg_title" id="InputTitle" value="${requestScope.managerVO.mg_title}" maxlength="8">
								<span style="color: #cc2357; font-size: 14px">
									<c:if test="${not empty requestScope.titleError}">
										<c:out value="${requestScope.titleError}" />
									</c:if>
								</span>
							</div>

						<c:if test="${requestScope.managerVO.mg_no ne 'MG001'}">
							<div class="form-group">
									<div class="row">
										<div class="col-3 text-left">
											狀態
										</div>
										<div class="col-6">
											<input type="radio" name="status" id="Normal" value="1" <c:if test="${requestScope.managerVO.status == 1}">checked</c:if>>
											<label for="Normal">正常</label>
											<input type="radio" name="status" id="Suspend" value="2" <c:if test="${requestScope.managerVO.status == 2}">checked</c:if>>
											<label for="Suspend">停權</label>
										</div>
									</div>
								</div>
						</c:if>

							<div class="form-group text-left">
								<label for="InputSpec">說明</label><br>
								<textarea id="InputSpec" name="mg_spec" style="width:335.5px;height:75px;">${requestScope.managerVO.mg_spec}</textarea>
							</div>

							<input type="hidden" name="mg_account" value="${requestScope.managerVO.mg_account}">
							<input type="hidden" name="mg_no" value="${requestScope.managerVO.mg_no}">
							<input type="hidden" name="action" value="update">
							<button class="btn btn-info" type="submit">送出修改</button>


					</div>
					<c:if test="${requestScope.managerVO.mg_no ne 'MG001'}">
						<div class="col-1"></div>

						<div class="col-3 align-middle text-left" id="ManagerPermission">
							<h2 class="text-center">權限管理</h2>

							<c:forEach var="permission" items="${list}" varStatus="count">
								<div style="margin-left: 6.5em;margin-top: 1vh">
									<input type="checkbox" <c:if test="${fn:contains(permitOwnerSvc.getMgPermissions(requestScope.managerVO.mg_no), permission.pm_no)}"> checked </c:if> data-toggle="toggle" data-onstyle="success" data-on=" " data-off=" " name="permissions" value="${permission.pm_no}">
									<label style="font-size: 22px;color: #0c5460;">${permission.pm_name}</label>
								</div>
							</c:forEach>
						</div>
					</c:if>

				</div>
			</form>
		</main>
	</div>
</div>

<script>

	//其他錯誤的訊息
	<c:if test="${not empty requestScope.errorMsg}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "<c:out value="${requestScope.errorMsg}" />"
	});
	<c:remove var="errorMsg" scope="request" />
	</c:if>

</script>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>