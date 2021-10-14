<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.MemService" %>
<%@ page import="com.mem.model.MemVO" %>
<%@ page import="java.util.List" %>

<%
	MemService memSvc = new MemService();
	List<MemVO> list = memSvc.getAllMem();
	pageContext.setAttribute("memList" , list);
%>
<jsp:useBean id="rankSvc" scope="request" class="com.bicyclist_rank.model.Bicyclist_RankService" />


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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/memberList.css">
	<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">

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
					<div class="display-3" id="Title">會員列表</div>
				</div>

				<div class="col-10 text-center mb-4" id="Control-panel">

					<div class="row justify-content-center">

						<div class="col-9 text-center">

							<form method="post" action="<%=request.getContextPath()%>/mem/mem.do">
								<div class="form-row justify-content-around">
									<div class="col-2"><h3 style="color:#0c5460">複合查詢</h3></div>

									<div class="form-group text-left mb-0 col-2">
										<input type="text" class="form-control" name="mem_no" id="InputNo" maxlength="5" placeholder="會員編號">
									</div>

									<div class="form-group text-left mb-0 col-2">
										<input type="text" class="form-control" name="mem_name" id="InputName" maxlength="8" placeholder="會員暱稱">
									</div>

									<div class="form-group text-left mb-0 col-2">
										<input type="text" class="form-control" name="mem_email" id="InputEmail" placeholder="會員E-mail">
									</div>

									<select class="custom-select col-2" name="rank_no" id="InputRank">
										<option value="">選擇騎士階級</option>
											<c:forEach var="rankVO" items="${rankSvc.allBicyclist_Rank}" >
										<option value="${rankVO.rank_no}">${rankVO.rank_name}
											</c:forEach>
									</select>

									<input type="hidden" name="action" value="listMem_ByCompositeQuery">
									<button type="submit" class="btn btn-outline-info col-1" style="height: 38px">送出</button>
									</div>
							</form>
						</div>

						<div class="col-3">
							<button class="btn btn-outline-info" onclick="location.href='<%= request.getContextPath()%>/back-end/mem/memListAll.jsp';">顯示全部會員</button>
						</div>


					</div>

				</div>

				<div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

				<div class="col-10 align-middle" id="ManagerListTable">

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col">會員編號</th>
							<th scope="col">相片</th>
							<th scope="col">暱稱</th>
							<th scope="col">電子信箱</th>
							<th scope="col">點數</th>
							<th scope="col">總里程數</th>
							<th scope="col">騎士階級</th>
							<th scope="col">狀態</th>
						</tr>
						</thead>

						<tbody>
						<%@ include file="page1.file" %>
						<c:forEach var="memVO" items="${memList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<th scope="row">${memVO.mem_no}</th>
							<td><img src="<%= request.getContextPath()%>/mem/mem.do?action=showOthersImg&mem_no=${memVO.mem_no}" alt="會員相片"></td>
							<td>${memVO.mem_name}</td>
							<td>${memVO.mem_email}</td>
							<td>${memVO.mem_point}pts</td>
							<td>${memVO.total_record}公里</td>
							<td>${applicationScope.memRank[memVO.rank_no]}</td>
							<td>${applicationScope.memStatus[memVO.status]}</td>

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

	//搜尋結果跳頁下拉選單

	$("#SelectPage").change(function () {
		let pageIndex = $(this).children("option:selected").val();
		location.href = location.pathname +"?whichPage=" + pageIndex;
	});

	//查詢失敗的訊息
	<c:if test="${not empty sessionScope.errorMsgs}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "<c:forEach var="msg" items="${sessionScope.errorMsgs}">${msg}</c:forEach>"
	});
	<c:remove var="errorMsgs" scope="session" />
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