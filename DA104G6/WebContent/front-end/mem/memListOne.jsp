<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%

%>

<jsp:useBean id="trackSvc" scope="request" class="com.track.model.TrackService" />


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
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/memListOne.css">
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
							<img src="<%= request.getContextPath()%>/mem/mem.do?action=showOthersImg&mem_no=${requestScope.memVO.mem_no}" id="UserPic">
						</div>

					</div>

					<div class="row align-items-end pt-2" id="Profile" style="height: 70%">
						<div class="col-12 text-center" style="height: 85%">
							<h4 id="NickName">${requestScope.memVO.mem_name}<c:if test="${requestScope.memVO.status == 3}">(已停權)</c:if></c></h4>
							<div class="row align-items-center">
								<div class="col-4" id="JournalCount">
									<a href="#">2  篇日誌</a>
								</div>
								<div class="col-4" id="FollowerCount">
									<a href="#">${trackSvc.getFollowedCount(requestScope.memVO.mem_no)} 追蹤者</a>
								</div>
								<div class="col-4" id="FollowingCount">
									<a href="#">${trackSvc.getFollowersCount(requestScope.memVO.mem_no)} 追蹤中</a>
								</div>
							</div>
							<hr>
							<table class="table table-borderless">
								<tr><th>加入時間</th><td><fmt:formatDate value="${requestScope.memVO.jointime}" pattern="yyyy-MM-dd" /></td></tr>
								<tr><th>總哩程數</th><td>${requestScope.memVO.total_record} KM</td></tr>
								<tr><th>騎士階級</th><td><img src="<%= request.getContextPath()%>/bicyclist_rank/bicyclist_rank.do?action=showImg&rank_no=${requestScope.memVO.rank_no}">${applicationScope.memRank[requestScope.memVO.rank_no]}</td></tr>
							</table>

						</div>
						<div class="col-12 text-center" style="height: 15%">
							<c:if test="${not empty sessionScope.account && requestScope.memVO.status != 3}">

								<c:if test="${not fn:contains(trackSvc.findMem_no2(sessionScope.memVO.mem_no), requestScope.memVO.mem_no)}">
									<button type="submit" class="btn btn-primary" id="FollowBtn">加入追蹤</button>
								</c:if>

								<c:if test="${fn:contains(trackSvc.findMem_no2(sessionScope.memVO.mem_no), requestScope.memVO.mem_no)}">
									<button type="submit" class="btn btn-primary" id="UnFollowBtn">取消追蹤</button>
								</c:if>

								<!-- Button trigger modal -->
								<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#ReportMember">檢舉會員</button>

								<!-- Modal -->
								<div class="modal fade" id="ReportMember" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
									<div class="modal-dialog modal-dialog-centered" role="document">
										<div class="modal-content" style="color: #000">
											<form action="<%= request.getContextPath()%>/member_report/member_report.do" method="post" enctype="multipart/form-data">
												<div class="modal-header">
													<h5 class="modal-title" id="ModalCenterTitle">檢舉會員</h5>
													<button type="button" class="close" data-dismiss="modal" aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
												</div>
												<div class="modal-body">
													<img src="<%= request.getContextPath()%>/images/icons/Report_Icon.png" alt="檢舉佐證" id="ReportProof" class="mx-auto">

													<div class="form-group mt-2 text-center">
														<label class="btn btn-info">
															<input id="InputProof" style="display:none;" type="file" name="proof" accept="image/*">
															<img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" id="UploadButton"> 上傳佐證
														</label>
													</div>
													<div class="form-group text-center">
														<label for="InputReason">檢舉原因</label><br>
														<textarea id="InputReason" name="reason" style="width:335.5px;height:75px;">${param.reason}</textarea>
														<br>
														<span style="color: #cc2357; font-size: 14px" id="ReasonError">
														</span>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
													<button type="button" class="btn btn-info" id="SendReport">送出</button>
												</div>
											</form>
										</div>
									</div>
								</div>

							</c:if>
						</div>
					</div>

				</div>

				<!--會員中間-->
				<div class="col-12 col-xl-7 align-items-center" id="MemberFiles">
					<ul class="nav nav-pills nav-justified" id="myTab" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" id="MemBlog-tab" data-toggle="tab" href="#MemBlog" role="tab" aria-controls="contact" aria-selected="true">日誌紀錄</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MemRoute-tab" data-toggle="tab" href="#MemRoute" role="tab" aria-controls="home" aria-selected="false">路線紀錄</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="MemGroup-tab" data-toggle="tab" href="#MemGroup" role="tab" aria-controls="profile" aria-selected="false">揪團列表</a>
						</li>


					</ul>
					<div class="tab-content" id="myTabContent">

						<div class="tab-pane active" id="MemBlog" role="tabpanel" aria-labelledby="MemBlog-tab">
							<jsp:include page="/front-end/include/listMemBlog.jsp" flush="true" />
						</div>

						<div class="tab-pane " id="MemRoute" role="tabpanel" aria-labelledby="MemRoute-tab">
							<jsp:include page="/front-end/include/otherRecord.jsp" flush="true" />
						</div>

						<div class="tab-pane " id="MemGroup" role="tabpanel" aria-labelledby="MemGroup-tab">
							<jsp:include page="/front-end/include/otherGroup.jsp" flush="true" />
						</div>

					</div>
				</div>


				<div class="col-12 col-xl-1 text-center">
				</div>
	</div>


</div>


<script>

	$("#UnFollowBtn").click(function () {
		$.ajax({
			url: "<%= request.getContextPath()%>/track/track.do",
			type:"POST",
			data:{
				action: 'delete',
				mem_no: '${requestScope.memVO.mem_no}'
			},
			success:function(data){
				swal.fire({
					icon: 'success',
					title: 'GoodGood',
					text: '取消追蹤成功 !',
					onClose: () => {location.reload()}
				});

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

	$("#FollowBtn").click(function () {
		$.ajax({
			url: "<%= request.getContextPath()%>/track/track.do",
			type:"POST",
			data:{
				action: 'insert',
				mem_no: '${requestScope.memVO.mem_no}'
			},
			success:function(data){
				swal.fire({
					icon: 'success',
					title: 'GoodGood',
					text: '追蹤成功 !',
					onClose: () => {location.reload()}
				});
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

	//其他錯誤訊息
	<c:if test="${not empty requestScope.errorMsgs}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "<c:forEach var="msg" items="${requestScope.errorMsgs}">${msg}</c:forEach>"
	});
	<c:remove var="errorMsgs" scope="request" />
	</c:if>

	// 上傳證明預覽
	function readURL(input) {
		console.log(input);
		if (input.files && input.files[0]) {
			let reader = new FileReader();

			reader.onload = function(e) {
				$('#ReportProof').attr('src', e.target.result);
			};

			reader.readAsDataURL(input.files[0]);
		} else {
			$('#ReportProof').attr("src", "<%= request.getContextPath()%>/images/icons/Report_Icon.png");
		}
	}

	$("#InputProof").change(function() {
		readURL(this);
	});


	//Ajax檢舉
	$("#SendReport").click(function () {

		var reasonArea = $("#InputReason");

		if($.trim(reasonArea.val()) === "") {
			reasonArea.focus();
			$("#ReasonError").text("請輸入檢舉原因！");
		} else {

			var fd = new FormData();
			var image = $("#InputProof")[0].files[0];
			var action = "insert";
			var mem_no = '${requestScope.memVO.mem_no}';
			var reason = reasonArea.val();
			fd.append('mem_no' , mem_no);
			fd.append('action' , action);
			fd.append('reason' , reason);
			fd.append('proof' , image);

			$.ajax({
				url: "<%= request.getContextPath()%>/member_report/member_report.do",
				type:"POST",
				contentType:false,
				processData:false,
				data: fd,
				success:function(data){
					swal.fire({
						icon: 'success',
						title: 'Okie Dokie',
						text: '檢舉已送出 !'
					});
				},

				error : function() {
					Swal.fire({
						icon: 'warning',
						title: 'Oops...',
						text: 'Something went wrong!'
					})
				}
			});
		}
	});


</script>
<jsp:include page="/front-end/include/LoginAlerts.jsp" />

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>