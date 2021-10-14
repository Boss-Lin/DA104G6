<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%
//	ManagerVO managerVO = new ManagerVO();
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

				<div class="row align-items-center justify-content-center">

					<div class="col-12 text-center">
						<div class="display-3" id="Title">修改個人資料</div>
					</div>


					<div class="col-3 align-middle text-center" id="ManagerProfile">
						<form action="<%= request.getContextPath()%>/manager/manager.do" method="post" enctype="multipart/form-data" class="m-0">

							<img src="<%= request.getContextPath()%>/manager/manager.do?action=showOthersImg&mg_no=${requestScope.managerVO.mg_no}" alt="管理員頭貼預覽" id="ManagerProfilePic" class="mx-auto">

							<div class="form-group mt-2 text-center">
								<label class="btn btn-info">
									<input id="InputProfilePic" style="display:none;" type="file" name="mg_profile_pic" accept="image/*">
									<img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" id="UploadButton"> 上傳頭貼
								</label>
							</div>

							<div class="form-group text-left">
								<label for="InputName">姓名</label>
								<input type="text" class="form-control" name="mg_name" id="InputName" placeholder="請輸入姓名" maxlength="5" value="${requestScope.managerVO.mg_name}">
								<span style="color: #cc2357; font-size: 14px">
									<c:if test="${not empty requestScope.errorMsgs.mg_name}">
										<c:out value="${requestScope.errorMsgs.mg_name}" />
									</c:if>
								</span>
							</div>

							<div class="form-group text-left">
								<label for="InputTitle">職稱</label>
								<input type="text" class="form-control" name="mg_title" id="InputTitle" value="${requestScope.managerVO.mg_title}" maxlength="8">
								<span style="color: #cc2357; font-size: 14px">
									<c:if test="${not empty requestScope.errorMsgs.mg_title}">
										<c:out value="${requestScope.errorMsgs.mg_title}" />
									</c:if>
								</span>
							</div>

							<div class="form-group text-left">
								<label for="InputSpec">說明</label><br>
								<textarea id="InputSpec" name="mg_spec" style="width:335.5px;height:75px;">${requestScope.managerVO.mg_spec}</textarea>
							</div>

							<div class="form-group text-left">
								<label for="InputPassword">輸入密碼以執行修改手續</label>
								<input type="password" class="form-control" name="mg_password" id="InputPassword">
								<span style="color: #cc2357; font-size: 14px">
									<c:if test="${not empty requestScope.errorMsgs.mg_password}">
										<c:out value="${requestScope.errorMsgs.mg_password}" />
									</c:if>
								</span>
							</div>

							<input type="hidden" name="mg_account" value="${requestScope.managerVO.mg_account}">
							<input type="hidden" name="mg_no" value="${requestScope.managerVO.mg_no}">
							<input type="hidden" name="status" value="${requestScope.managerVO.status}">
							<input type="hidden" name="action" value="selfUpdate">
							<button class="btn btn-info" type="submit">送出修改</button>
						</form>

					</div>

					<div class="col-1"></div>

					<div class="col-3 align-middle text-left" id="ManagerPasswordUpdate">
						<h2 class="text-center">更換密碼</h2>

						<form action="<%= request.getContextPath()%>/manager/manager.do" method="post">

							<div class="form-group text-left">
								<label for="InputOldPassword">舊密碼</label>
								<input type="password" class="form-control" name="mg_password" id="InputOldPassword">
								<span style="color: #cc2357; font-size: 14px">
							<c:if test="${not empty requestScope.errorMsgs.old_mg_password}">
								<c:out value="${requestScope.errorMsgs.old_mg_password}" />
							</c:if>
						</span>
							</div>

							<div class="form-group text-left">
								<label for="InputNewPassword">新密碼</label>
								<input type="password" class="form-control" name="new_mg_password" id="InputNewPassword">
								<span style="color: #cc2357; font-size: 14px">
							<c:if test="${not empty requestScope.errorMsgs.new_mg_password}">
								<c:out value="${requestScope.errorMsgs.new_mg_password}" />
							</c:if>
						</span>
							</div>
							<input type="hidden" name="mg_no" value="${requestScope.managerVO.mg_no}">
							<input type="hidden" name="action" value="pswUpdate">
							<button class="btn btn-info" type="submit">更新密碼</button>
						</form>

					</div>

				</div>

		</main>
	</div>
</div>

<script>

	// 會員上傳頭貼預覽
	function readURL(input) {
		console.log(input);
		if (input.files && input.files[0]) {
			let reader = new FileReader();

			reader.onload = function(e) {
				$('#ManagerProfilePic').attr('src', e.target.result);
			};

			reader.readAsDataURL(input.files[0]);
		} else {
			$('#ManagerProfilePic').attr("src", "<%= request.getContextPath()%>/manager/manager.do?action=showOthersImg&mg_no=${requestScope.managerVO.mg_no}");
		}
	}

	$("#InputProfilePic").change(function() {
		readURL(this);
	});

	//其他錯誤的訊息
	<c:if test="${not empty requestScope.errorMsgs.exception}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "<c:out value="${requestScope.errorMsgs.exception}" />"
	});
	<c:remove var="errorMsgs" scope="request" />
	</c:if>

	//修改成功的訊息
	<c:if test="${not empty requestScope.updateSuccess}">
	swal.fire({
		icon: 'success',
		title: '叮鈴',
		text: "${requestScope.updateSuccess}"
	});
	<c:remove var="updateSuccess" scope="request" />
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