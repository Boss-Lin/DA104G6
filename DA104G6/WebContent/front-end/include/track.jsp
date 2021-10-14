<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mem.model.MemVO"%>
<%@ page import="com.track.model.TrackService" %>
<%@ page import="java.util.List" %>

<%
	TrackService trackSvc = new TrackService();
	MemVO memVO = (MemVO) session.getAttribute("memVO");
	String mem_no = memVO.getMem_no();
	List<String> trackList = trackSvc.findMem_no2(mem_no);
	pageContext.setAttribute("trackList" , trackList);
%>

<jsp:useBean id="memSvc" scope="request" class="com.mem.model.MemService" />
<jsp:useBean id="rankSvc" scope="request" class="com.bicyclist_rank.model.Bicyclist_RankService" />


<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/memTrack.css">

	<!--網址縮圖-->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

	<title>Bike Seeker</title>

</head>
<body>

<div class="row" id="FollowingTableScroll">
	<div class="col-12" id="TableContainer">
		<c:if test="${not empty trackList}">
			<table class="table table-striped text-center" id="TrackTable">
				<thead class="thead-light">
				<tr>
					<th scope="col">頭貼</th>
					<th scope="col">暱稱</th>
					<th scope="col">騎士階級</th>
					<th scope="col"></th>
				</tr>
				</thead>

				<tbody>
				<c:forEach var="trackVO" items="${trackList}" varStatus="count">
					<tr>
						<td><img src="<%= request.getContextPath()%>/mem/mem.do?action=showOthersImg&mem_no=${trackVO}" alt="會員圖片" style="max-width: 100px; max-height: 100px"></td>
						<td>${memSvc.getOneMem(trackVO).mem_name}</td>
						<td>${rankSvc.getOneBicyclist_Rank(memSvc.getOneMem(trackVO).rank_no).rank_name}</td>
						<td>
							<button type="button" class="trackBtn btn btn-primary" id="unFollow${count.index}">取消追蹤</button>
							<form action="<%= request.getContextPath()%>/mem/mem.do" method="get" class="m-0 mt-2 mb-2 d-inline">
								<input type="hidden" name="action" value="getOne_For_Display">
								<input type="hidden" name="mem_no" value="${trackVO}">
								<button type="submit" class="trackBtn btn btn-primary">查看詳情</button>
							</form>

						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>
</div>

<script>

	//取消追蹤Ajax
	<c:forEach var="trackVO" items="${trackList}" varStatus="count">

	$("#unFollow${count.index}").click(function () {
		$.ajax({
			url: "<%= request.getContextPath()%>/track/track.do",
			type:"POST",
			data:{
				action: 'delete',
				mem_no: '${trackVO}'
			},
			success:function(data){
				swal.fire({
					icon: 'success',
					title: 'GoodGood',
					text: '取消追蹤成功 !'
				});
				$("#TrackTable").load("<%= request.getContextPath()%>/front-end/include/track.jsp");

			},

			error : function() {
				Swal.fire({
					icon: 'warning',
					title: 'Oops...',
					text: 'Something went wrong!'
				})
			}
		});
	});

	</c:forEach>

	//其他錯誤的訊息
	<c:if test="${not empty requestScope.errorMsgs}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "<c:out value="${requestScope.errorMsgs}" />"
	});
	<c:remove var="errorMsgs" scope="request" />
	</c:if>

</script>
</body>
</html>