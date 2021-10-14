<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.manager.model.*" %>
<%@ page import="java.util.*" %>

<%
	ManagerService mgSvc = new ManagerService();
	List<ManagerVO> list = mgSvc.getAll();
	pageContext.setAttribute("list" , list);
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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/managerList.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>
	<title>Bike Seeker Manager</title>
</head>
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-around">

		<jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

			<div class="row align-items-center justify-content-center">

				<div class="col-12 text-center">
					<div class="display-3" id="Title">員工管理</div>
				</div>

				<div class="col-3 text-center mb-4" id="Control-panel">

					<div class="row justify-content-center">

						<div class="col-6">

							<form action="<%= request.getContextPath()%>/manager/manager.do" method="post" id="ManagerNoForm">
								<label for="ManagerNoDropdown"></label><select class="custom-select" id="ManagerNoDropdown" name="mg_no">
									<option selected>查詢特定員工</option>
									<c:forEach var="managerVO" items="${list}">
									<option  value="${managerVO.mg_no}">${managerVO.mg_name}</option>
									</c:forEach>
								</select>
								<input type="hidden" name="action" value="getOne_For_Display">
							</form>
						</div>

						<div class="col-6">
							<button class="btn btn-outline-info" onclick="location.href='<%= request.getContextPath()%>/back-end/manager/managerListAll.jsp';">顯示全部員工</button>
						</div>


					</div>

				</div>

				<div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

				<div class="col-10 align-middle" id="ManagerListTable">

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col">員工編號</th>
							<th scope="col">相片</th>
							<th scope="col">姓名</th>
							<th scope="col">職稱</th>
							<th scope="col">說明</th>
							<th scope="col">帳號</th>
							<th scope="col">狀態</th>
							<th scope="col">功能</th>
						</tr>
						</thead>

						<tbody>
						<%@ include file = "page1.file" %>
						<c:forEach var="managerVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<th scope="row">${managerVO.mg_no}</th>
							<td><img src="<%= request.getContextPath()%>/manager/manager.do?action=showOthersImg&mg_no=${managerVO.mg_no}" alt="員工相片"></td>
							<td>${managerVO.mg_name}</td>
							<td>${managerVO.mg_title}</td>
							<td>${managerVO.mg_spec}</td>
							<td>${managerVO.mg_account}</td>
							<td>${applicationScope.managerStatus[managerVO.status]}</td>
							<td>
								<c:if test="${!(managerVO.mg_no eq 'MG001' && (!sessionScope.managerVO.mg_no eq 'MG001'))}">
									<form action="<%= request.getContextPath()%>/manager/manager.do" method="post" class="m-0">
										<input type="hidden" name="action" value="getOne_For_Update">
										<input type="hidden" name="mg_no" value="${managerVO.mg_no}">
										<button type="submit" class="btn btn-info">修改</button>
									</form>
								</c:if>
							</td>
						</tr>
						</c:forEach>
						</tbody>
					</table>
					<%@ include file="page2.file" %>

				</div>

			</div>

		</main>
	</div>
</div>

<script>

	//員工編號下拉選單
	$("#ManagerNoDropdown").change(function () {
		$("#ManagerNoForm").submit();
	});


	//搜尋結果跳頁下拉選單

	$("#SelectPage").change(function () {
		let pageIndex = $(this).children("option:selected").val();
		location.href = location.pathname +"?whichPage=" + pageIndex;
	});

	//查詢失敗的訊息
	<c:if test="${not empty requestScope.errorMsgs}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "<c:forEach var="msg" items="${requestScope.errorMsgs}">${msg}</c:forEach>"
	});
	<c:remove var="errorMsgs" scope="request" />
	</c:if>



</script>


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