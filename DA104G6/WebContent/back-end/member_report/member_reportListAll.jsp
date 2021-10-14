<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.MemService" %>
<%@ page import="com.mem.model.MemVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.member_report.model.MemberReportService" %>
<%@ page import="com.member_report.model.MemberReportVO" %>

<%
	MemberReportService memberReportSvc = new MemberReportService();
	List<MemberReportVO> memReportList = memberReportSvc.getAll();
	pageContext.setAttribute("memReportList" , memReportList);
%>

<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />

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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/member_reportListAll.css">
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
					<div class="display-3" id="Title">會員檢舉案件列表</div>
				</div>

				<div class="col-10 text-center mb-4" id="Control-panel">

					<div class="row justify-content-center">

						<div class="col-3 text-center">

							<form method="post" action="<%=request.getContextPath()%>/member_report/member_report.do" id="StatusSearch">
								<div class="form-row justify-content-around">

									<div class="col-9">
										<select class="custom-select" name="status" id="StatusDropDown">
											<option value="">選擇審核狀態</option>
											<option value="1">審核中
											<option value="2">通過
											<option value="3">未通過
										</select>
									</div>
									<input type="hidden" name="action" value="get_By_Status">
									</div>
							</form>
						</div>

						<div class="col-2">
							<button class="btn btn-outline-info" onclick="location.href='<%= request.getContextPath()%>/back-end/member_report/member_reportListAll.jsp';">顯示全部案件</button>
						</div>


					</div>

				</div>

				<div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

				<div class="col-10 align-middle" id="ManagerListTable">

					<table class="table table-striped text-center">
						<thead class="thead-dark">
						<tr>
							<th scope="col" class="text-nowrap">案件編號</th>
							<th scope="col" class="text-nowrap">檢舉人</th>
							<th scope="col" class="text-nowrap">被檢舉人</th>
							<th scope="col" class="text-nowrap">理由</th>
							<th scope="col" class="text-nowrap">佐證</th>
							<th scope="col" class="text-nowrap">狀態</th>
							<th scope="col" class="text-nowrap"></th>
						</tr>
						</thead>

						<tbody>
						<%@ include file="page1.file" %>
						<c:forEach var="reportVO" items="${memReportList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="count">
						<tr>
							<th scope="row">${reportVO.rep_no}</th>
							<td style="width: 156px">${memSvc.getOneMem(reportVO.mem_no1).mem_name}</td>
							<td style="width: 156px">${memSvc.getOneMem(reportVO.mem_no2).mem_name}</td>
							<td style="overflow-y: auto;width: 500px;height: 160px;display: block;word-break: break-all">${reportVO.reason}</td>
							<td style="width: 224px"><img src="<%= request.getContextPath()%>/member_report/member_report.do?action=showImg&rep_no=${reportVO.rep_no}" alt="檢舉佐證"></td>
							<td class="text-nowrap">${applicationScope.memberReport[reportVO.status]}</td>
							<td style="width: 116px"><button class="btn btn-info" data-toggle="modal" data-target="#ModalId${count.index}">審核</button></td>

							<!-- Modal -->
							<div class="modal fade" id="ModalId${count.index}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
								<div class="modal-dialog modal-dialog-centered" role="document">
									<div class="modal-content">
										<form method="post" action="<%= request.getContextPath()%>/member_report/member_report.do">
										<div class="modal-header">
											<h5 class="modal-title" id="ModalTitle${count.index}">審核檢舉</h5>
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											<div class="row align-items-center justify-content-around">
												<div class="col-6 text-center">
													<h4>會員狀態</h4>
													<div class="inputGroup">
														<input id="memRadio1${count.index}" name="mem_status" value="2" type="radio" <c:if test="${memSvc.getOneMem(reportVO.mem_no2).status == 2}"> checked </c:if> />
														<label for="memRadio1${count.index}">正常</label>
													</div>
													<div class="inputGroup">
														<input id="memRadio2${count.index}" name="mem_status" value="3" type="radio" <c:if test="${memSvc.getOneMem(reportVO.mem_no2).status == 3}"> checked </c:if> />
														<label for="memRadio2${count.index}">停權</label>
													</div>
												</div>
												<div class="col-6 text-center">
													<h4>案件審核結果</h4>
													<div class="inputGroup">
														<input id="repRadio1${count.index}" name="rep_status" value="2" type="radio" <c:if test="${reportVO.status == 2}"> checked </c:if> />
														<label for="repRadio1${count.index}">通過</label>
													</div>
													<div class="inputGroup">
														<input id="repRadio2${count.index}" name="rep_status" value="3" type="radio" <c:if test="${reportVO.status == 3 || reportVO.status == 1}"> checked </c:if> />
														<label for="repRadio2${count.index}">不通過</label>
													</div>
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="rep_no" value="${reportVO.rep_no}">
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
											<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
											<button type="submit" class="btn btn-primary">確認</button>
										</div>
											</form>
									</div>
								</div>
							</div>

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


	//審核狀態下拉選單
	$("#StatusDropDown").change(function () {
		$("#StatusSearch").submit();
	});

	//搜尋結果跳頁下拉選單
	$("#SelectPage").change(function () {
		let pageIndex = $(this).children("option:selected").val();
		location.href = location.pathname +"?whichPage=" + pageIndex;
	});

	//修改成功的訊息
	<c:if test="${not empty requestScope.updateSuccess}">
	swal.fire({
		icon: 'success',
		title: '叮鈴',
		text: "${requestScope.updateSuccess}"
	});
	<c:remove var="updateSuccess" scope="request" />
	</c:if>


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
<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>