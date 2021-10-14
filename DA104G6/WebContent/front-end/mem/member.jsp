<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mem.model.MemVO"%>
<%@ page import="java.util.List" %>
<%@ page import="com.track.model.TrackService" %>
<%@ page import="com.track.model.TrackVO" %>

<%

%>

<jsp:useBean id="trackSvc" scope="request" class="com.track.model.TrackService" />
<jsp:useBean id="rankSvc" scope="request" class="com.bicyclist_rank.model.Bicyclist_RankService" />
<jsp:useBean id="memSvc" scope="request" class="com.mem.model.MemService" />

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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/member.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>


	<!--上線時改位址-->

	<title>Bike Seeker</title>

</head>
<body>
<jsp:include page="/front-end/include/NavBar.jsp"/>

<!--Container開頭-->
<div class="container-fluid h-100">

	<!--首頁開頭-->
	<div class="row align-items-end h-100" id="MemberPage">

		<!--個人頁面開頭-->

				<div class="col-12 col-xl-1 text-center">
				</div>

				<!--會員側邊欄-->
				<div class="col-12 col-xl-3 mr-3 text-center" id="MemberInfo">

					<div class="row align-items-center h-25 pt-2 pb-2" id="ProfilePic">

						<div class="col-12 text-center">
							<img src="<%= request.getContextPath()%>/mem/mem.do?action=showImg" id="UserPic">
						</div>

					</div>

					<div class="row align-items-start h-75 pt-2" id="Profile">
						<div class="col-12 text-center">
							<h4 id="NickName">${sessionScope.memVO.mem_name}</h4>
							<div class="row align-items-center">
								<div class="col-4" id="JournalCount">
									<a href="#">3  篇日誌</a>
								</div>
								<div class="col-4" id="FollowerCount">
									<a href="#">${trackSvc.getFollowedCount(sessionScope.memVO.mem_no)} 追蹤者</a>
								</div>
								<div class="col-4" id="FollowingCount">
									<a href="#">${trackSvc.getFollowersCount(sessionScope.memVO.mem_no)} 追蹤中</a>
								</div>
							</div>
							<hr>
							<table class="table table-borderless">
								<tr><th>性別</th><td>${applicationScope.memGender[sessionScope.memVO.mem_gender]}</td></tr>
								<tr><th>生日</th><td>${sessionScope.memVO.mem_dob}</td></tr>
								<tr><th>加入時間</th><td><fmt:formatDate value="${sessionScope.memVO.jointime}" pattern="yyyy-MM-dd" /></td></tr>
								<tr><th>總哩程數</th><td>${sessionScope.memVO.total_record} KM</td></tr>
								<tr><th>騎士階級</th><td><img src="<%= request.getContextPath()%>/bicyclist_rank/bicyclist_rank.do?action=showImg&rank_no=${sessionScope.memVO.rank_no}">${applicationScope.memRank[sessionScope.memVO.rank_no]}</td></tr>
							</table>

						</div>
						<div class="col-12 align-items-end">

                            <form action="<%= request.getContextPath()%>/mem/mem.do" method="post" style="width: 180px; display: inline">
                                <input type="hidden" name="action" value="getOne_For_Update">
                                <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
                                <input type="hidden" name="mem_no" value="${sessionScope.memVO.mem_no}">
							<button type="submit" class="btn btn-primary">修改基本資料</button>
                            </form>
						</div>
					</div>

				</div>

				<!--會員中間-->
				<div class="col-12 col-xl-7 align-items-center" id="MemberFiles">
					<ul class="nav nav-pills nav-justified" id="myTab" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" id="MyRecord-tab" data-toggle="tab" href="#MyRecord" role="tab" aria-controls="contact" aria-selected="false">里程統計</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MyRoute-tab" data-toggle="tab" href="#MyRoute" role="tab" aria-controls="home" aria-selected="true">我的路線</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MyjoinGroup-tab" data-toggle="tab" href="#MyjoinGroup" role="tab" aria-controls="profile" aria-selected="false">參加揪團</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MyGroup-tab" data-toggle="tab" href="#MyGroup" role="tab" aria-controls="profile" aria-selected="false">我的揪團</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MyBlog-tab" data-toggle="tab" href="#MyBlog" role="tab" aria-controls="contact" aria-selected="false">我的日誌</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MyOrder-tab" data-toggle="tab" href="#MyOrder" role="tab" aria-controls="contact" aria-selected="false">我的訂單</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MyFollowing-tab" data-toggle="tab" href="#MyFollowing" role="tab" aria-controls="contact" aria-selected="false">我的追蹤</a>
						</li>



					</ul>
					<div class="tab-content" id="myTabContent">

						<div class="tab-pane fade h-100 show active" id="MyRecord" role="tabpanel" aria-labelledby="MyRecord-tab">
							<jsp:include page="/front-end/include/record.jsp" flush="true" />
						</div>

						<div class="tab-pane fade" id="MyRoute" role="tabpanel" aria-labelledby="MyRoute-tab">
							<jsp:include page="/front-end/include/myRoute.jsp" flush="true" />
						</div>

						<div class="tab-pane fade" id="MyjoinGroup" role="tabpanel" aria-labelledby="MyjoinGroup-tab">
							<jsp:include page="/front-end/include/reviewGroup.jsp" flush="true" />
						</div>

						<div class="tab-pane fade" id="MyGroup" role="tabpanel" aria-labelledby="MyGroup-tab">
							<jsp:include page="/front-end/include/updateGroup.jsp" flush="true" />
						</div>

						<div class="tab-pane fade" id="MyBlog" role="tabpanel" aria-labelledby="MyBlog-tab">
							<jsp:include page="/front-end/include/listMyBlog.jsp" flush="true" />
						</div>

						<div class="tab-pane fade" id="MyOrder" role="tabpanel" aria-labelledby="MyOrder-tab">
							<jsp:include page="/front-end/include/order_master.jsp" flush="true" />
						</div>

						<div class="tab-pane fade" id="MyFollowing" role="tabpanel" aria-labelledby="MyFollowing-tab">
							<jsp:include page="/front-end/include/track.jsp" flush="true" />
						</div>





					</div>
				</div>


				<div class="col-12 col-xl-1 text-center">
				</div>
	</div>

</div>


<script>
	
	//解散寄信(通知)
// 		var sendMail = '${mail}';
// 		console.log(sendMail);
// 		if(sendMail != ''){
<%-- 			<%request.removeAttribute("mail");%> --%>
// 			$.ajax({
<%-- 					url: '<%=request.getContextPath()%>/front-end/Sign_up/sign_up.do', --%>
// 					type : 'post',
// 					data : {
// 						action : 'mail',
// 						gro_no : sendMail,
// 						message : 'dismiss'
// 					},
// 					success : function() {
// 						console.log('發送成功')
// 					},
// 					error : function() {
// 						console.log('發送失敗')
// 					}
// 			});
// 		}

	//修改成功的通知
	<c:if test="${not empty requestScope.updateSuccess}">
	swal.fire({
		icon : 'success',
		title : '叮鈴',
		text : "${requestScope.updateSuccess}"
	});
	<c:remove var="updateSuccess" scope="request" />
	</c:if>

	//其他錯誤的訊息
	<c:if test="${not empty requestScope.errorMsgs}">
	swal.fire({
		icon : 'error',
		title : '唉唷',
		text : "<c:out value="${requestScope.errorMsgs}" />"
	});
	<c:remove var="errorMsgs" scope="request" />
	</c:if>

	<c:if test="${not empty requestScope.DismissSuccess}">
	swal.fire({
		icon : 'success',
		title : '叮鈴',
		text : "解散行程"
	});
<%request.removeAttribute("DismissSuccess");%>
    </c:if>
    
    <c:if test="${not empty requestScope.dismiss}">
	    swal.fire({
	        icon: 'warning',
	        title: '叮鈴',
	        text: "此行程已結束"
	    });
	    <%request.removeAttribute("dismiss");%>
	</c:if>


</script>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>